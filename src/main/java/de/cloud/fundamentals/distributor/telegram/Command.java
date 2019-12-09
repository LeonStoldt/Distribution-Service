package de.cloud.fundamentals.distributor.telegram;

import de.cloud.fundamentals.distributor.userfeedback.I18n;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Command {
    START("description.command.start", "/start"),
    HELP("description.command.help", "/hilfe"),
    STOP("description.command.stop", "/stop"),
    DELETE("description.command.delete", "/delete"),
    INFO("description.command.info", "/info"),
    NB("description.command.nb", "/nb", "/nordbahn"),
    SHORTEN_URL("description.command.shorten-url", "/shorten", "/shorturl"),
    TRANSLATE("description.command.translate", "/translate"),
    TEMPERATURE("description.command.temperature", "/temperatur", "/temp"),
    HUMIDITY("description.command.humidity", "/luftfeuchtigkeit", "/humidity"),
    WIND("description.command.wind", "/wind"),
    VISIBILITY("description.command.visibility", "/sichtweite", "/sicht"),
    PRESSURE("description.command.pressure", "/luftdruck", "/pressure"),
    COORDINATES("description.command.coordinates", "/koordinaten", "/coords"),
    SUNRISE("description.command.sunrise", "/sonnenaufgang", "/sunrise"),
    SUNSET("description.command.sunset", "/sonnenuntergang", "/sunset"),
    WIKI("description.command.wiki", "/wiki", "/lexikon"),
    NO_COMMAND("", "");

    private static final I18n USER_FEEDBACK = new I18n();

    private final String descriptionKey;
    private final List<String> keyWords;

    Command(String descriptionKey, String... keyWords) {
        this.descriptionKey = descriptionKey;
        this.keyWords = Arrays.asList(keyWords);
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public static Command of(String message) {
        return Arrays
                .stream(values())
                .filter(command -> isCommandInList(message, command.getKeyWords()))
                .findFirst()
                .orElse(NO_COMMAND);
    }

    private static boolean isCommandInList(String message, List<String> keywordList) {
        return keywordList
                .stream()
                .anyMatch(message::contains);
    }

    public static String getCommandList() {
        return Arrays.stream(values())
                .filter(command -> !command.equals(Command.NO_COMMAND))
                .map(Command::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String toString() {
        return String.join(" | ", keyWords) + " - " + USER_FEEDBACK.get(descriptionKey);
    }
}
