package de.cloud.fundamentals.distributor.persistence.dao;

import de.cloud.fundamentals.distributor.bo.Client;
import de.cloud.fundamentals.distributor.persistence.domain.ClientEntity;
import de.cloud.fundamentals.distributor.persistence.repo.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ClientDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDao.class);
    private final ClientRepository repository;

    public ClientDao(ClientRepository repository) {
        this.repository = repository;
    }

    public boolean existsById(Long id) {
        LOGGER.info("check if client with id {} exists", id);
        return repository.existsById(id);
    }

    public Optional<Client> getClientById(Long chatId) {
        return repository.findById(chatId).map(Client::new);
    }

    public void changeActiveState(Long chatId) {
        getClientById(chatId).ifPresent(client -> {
            client.setActive(!client.isActive());
            persist(client);
        });
        LOGGER.info("changed active state of client {}", chatId);
    }

    public String getClientAsString(Long chatId) {
        return getClientById(chatId)
                .map(Client::toString)
                .orElse("{}");
    }

    public void persist(Client client) {
        LOGGER.info("persist new client with id {}", client.getChatId());
        persist(new ClientEntity(client));
    }

    private void persist(ClientEntity entity) {
        repository.saveAndFlush(entity);
    }

    public void deleteClient(Client client) {
        LOGGER.info("deleting data of client with id {}", client.getChatId());
        repository
                .findById(client.getChatId())
                .ifPresent(repository::delete);
    }
}
