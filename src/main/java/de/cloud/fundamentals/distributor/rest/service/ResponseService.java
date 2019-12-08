package de.cloud.fundamentals.distributor.rest.service;

import de.cloud.fundamentals.distributor.telegram.Command;

import java.util.Arrays;

public enum ResponseService {
    NORDBAHN("http://nordbahn:8003/api", Command.NB),
    URL_SHORTENER("http://urlshortener:8005/api", Command.SHORTEN_URL),
    TRANSLATER("http://translate:8004/api", Command.TRANSLATE);

    private final String url;
    private final Command command;

    ResponseService(String url, Command command) {
        this.url = url;
        this.command = command;
    }

    public static String urlFor(Command command) {
        return Arrays.stream(values())
                .filter(service -> service.getCommand().equals(command))
                .findFirst()
                .map(ResponseService::getUrl)
                .orElse("");
    }

    public String getUrl() {
        return url;
    }

    public Command getCommand() {
        return command;
    }
}
