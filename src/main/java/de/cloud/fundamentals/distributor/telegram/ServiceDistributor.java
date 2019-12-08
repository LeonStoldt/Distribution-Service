package de.cloud.fundamentals.distributor.telegram;

import de.cloud.fundamentals.distributor.bo.Client;
import de.cloud.fundamentals.distributor.persistence.dao.ClientDao;
import de.cloud.fundamentals.distributor.persistence.domain.ServiceEntity;
import de.cloud.fundamentals.distributor.persistence.repo.ServiceRepository;
import de.cloud.fundamentals.distributor.rest.dto.RequestDetails;
import de.cloud.fundamentals.distributor.rest.RequestCallback;
import de.cloud.fundamentals.distributor.rest.dto.Answer;
import de.cloud.fundamentals.distributor.userfeedback.I18n;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ServiceDistributor {

    private static final I18n USER_FEEDBACK = new I18n();

    private final ClientDao dao;
    private final ServiceRepository serviceRepository;
    private RequestCallback callback;

    public ServiceDistributor(ClientDao dao, ServiceRepository serviceRepository) {
        this.dao = dao;
        this.serviceRepository = serviceRepository;
    }

    public void setCallback(RequestCallback callback) {
        this.callback = callback;
    }

    public void answerClient(Answer answer, RequestDetails details) {
        Command command = Command.of(details.getMessage());
        Long chatId = answer.getChatId();
        answer.setChatId(chatId);

        switch (command) {
            case START:
                setStartResponse(details, isKnownAndActiveClient(chatId), answer);
                break;
            case INFO:
                answer.setMessage(USER_FEEDBACK.format("answer.command.info", dao.getClientAsString(details.getChatId())));
                break;
            case DELETE:
                deleteClient(answer, details.getChatId());
                break;
            default:
                if (isKnownAndActiveClient(chatId)) {
                    setServiceAnswer(answer, command, details);
                } else {
                    answer.setMessage(USER_FEEDBACK.get("hint.register"));
                }
        }
    }

    public void deleteClient(Answer answer, Long chatId) {
        dao.getClientById(chatId).ifPresentOrElse(
                client -> {
                    dao.deleteClient(client);
                    answer.setMessage(USER_FEEDBACK.get("answer.command.delete-success"));
                },
                () -> answer.setMessage(USER_FEEDBACK.get("answer.command.delete-empty"))
        );
    }

    private boolean isKnownAndActiveClient(Long chatId) {
        return dao.existsById(chatId) && isActiveClient(chatId);
    }

    private boolean isActiveClient(Long chatId) {
        Optional<Client> optionalClient = dao.getClientById(chatId);
        return optionalClient.isPresent() && optionalClient.get().isActive();
    }

    private void setStartResponse(RequestDetails details, boolean active, Answer answer) {
        if (active) {
            answer.setMessage(USER_FEEDBACK.format("answer.command.start-known", details.getFirstName()));
        } else {
            dao.getClientById(details.getChatId()).ifPresentOrElse(
                    client -> dao.changeActiveState(client.getChatId()),
                    () -> dao.persist(new Client(details)));
            answer.setMessage(USER_FEEDBACK.format("answer.command.start", details.getFirstName()));
        }
    }

    private void setServiceAnswer(Answer answer, Command command, RequestDetails details) {
        switch (command) {
            case HELP:
                answer.setMessage(Command.getCommandList());
                break;
            case STOP:
                dao.changeActiveState(details.getChatId());
                answer.setMessage(USER_FEEDBACK.get("answer.command.stop"));
                break;
            default:
                Optional<ServiceEntity> optionalServiceEntity = serviceRepository.findByCommand(command.toString());
                if (optionalServiceEntity.isPresent()) {
                    ServiceEntity serviceEntity = optionalServiceEntity.get();
                    String response = callback.getResponseFor(serviceEntity.getUrl(), getParams(details.getMessage(), command));
                    answer.setMessage(response);
                } else {
                    answer.setMessage(USER_FEEDBACK.get("answer.default"));
                }
        }
    }

    private String getParams(String messageText, Command command) {
        return command.getKeyWords()
                .stream()
                .filter(messageText::contains)
                .findFirst()
                .map(keyword -> messageText.substring(messageText.indexOf(keyword) + keyword.length()))
                .orElse(messageText);
    }
}
