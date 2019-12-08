package de.cloud.fundamentals.distributor.rest.service;

import de.cloud.fundamentals.distributor.telegram.Command;

import java.util.Arrays;

public enum ResponseService {
    NORDBAHN("http://nordbahn:8080/api", Command.NB),
    URL_SHORTENER("http://urlshortener:8080/api", Command.SHORTEN_URL),
    TRANSLATER("http://translate:8080/api", Command.TRANSLATE),
    TEMPERATURE("http://weather:8080/weather/temperature", Command.TEMPERATURE),
    HUMIDITY("http://weather:8080/weather/humidity", Command.HUMIDITY),
    WIND("http://weather:8080/weather/wind", Command.WIND),
    VISIBILITY("http://weather:8080/weather/visibility", Command.VISIBILITY),
    WIKI("http://wikipedia:8080/api", Command.WIKI);

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
