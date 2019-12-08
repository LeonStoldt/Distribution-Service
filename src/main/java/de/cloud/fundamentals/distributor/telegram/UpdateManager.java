package de.cloud.fundamentals.distributor.telegram;

import de.cloud.fundamentals.distributor.persistence.dao.ClientDao;
import de.cloud.fundamentals.distributor.rest.RequestCallback;
import de.cloud.fundamentals.distributor.rest.dto.Answer;
import de.cloud.fundamentals.distributor.rest.dto.RequestDetails;
import de.cloud.fundamentals.distributor.userfeedback.I18n;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class UpdateManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateManager.class);
    private static final I18n USER_FEEDBACK = new I18n();

    private final ServiceDistributor distributor;

    @Autowired
    public UpdateManager(ClientDao dao) {
        this.distributor = new ServiceDistributor(dao);
    }

    public void setRequestCallback(RequestCallback callback) {
        distributor.setCallback(callback);
    }

    public String onUpdateReceived(@NotNull RequestDetails details) {
        Answer answer = new Answer(details.getChatId());

        if (details.getMessage() != null) {
            final String messageText = details.getMessage();
            LOGGER.info("received message with chatId {} and message {}", answer.getChatId(), messageText);
            distributor.answerClient(answer, details);
        } else {
            LOGGER.warn("received details without message: {}", details);
            answer.setMessage(USER_FEEDBACK.get("error.invalid-message"));
        }
        return answer.getMessage();
    }
}
