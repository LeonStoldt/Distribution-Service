package de.cloud.fundamentals.distributor.persistence.domain;

import com.pengrad.telegrambot.model.Chat;
import de.cloud.fundamentals.distributor.bo.Client;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clients")
public class ClientEntity {

    @Id
    private Long chatId;

    private boolean active;

    private String firstName;

    private String lastName;

    private String userName;

    private String title;

    @Enumerated(EnumType.STRING)
    private Chat.Type type;

    public ClientEntity() {
    }

    public ClientEntity(Client client) {
        this.chatId = client.getChatId();
        this.active = client.isActive();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.userName = client.getUserName();
        this.title = client.getTitle();
        this.type = client.getType();
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Chat.Type getType() {
        return type;
    }

    public void setType(Chat.Type type) {
        this.type = type;
    }
}
