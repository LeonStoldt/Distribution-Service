package de.cloud.fundamentals.distributor.rest.dto;

import de.cloud.fundamentals.distributor.userfeedback.I18n;

import java.io.Serializable;

public class Answer implements Serializable {

    private Long chatId;
    private String message;
    private static final I18n USER_FEEDBACK = new I18n();

    public Answer(Long chatId) {
        this.chatId = chatId;
        this.message = USER_FEEDBACK.get("error.wrong-status-code");
    }

    public static Answer error(Long chatId) {
        return new Answer(chatId);
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
