package org.example.myminibank.dto;

public class AccountApiResponse {

    private final String message;

    public AccountApiResponse(String message) {
        this.message = message;
    }

    public AccountApiResponse(String message, Object... args) {
        this.message = formatMessage(message, args);
    }

    public static AccountApiResponse of(String message, Object... args) {
        return new AccountApiResponse(message, args);
    }

    private String formatMessage(String message, Object... args) {
        for (Object arg : args) {
            message = message.replaceFirst("\\{}", arg.toString());
        }
        return message;
    }

    public String getMessage() {
        return message;
    }
}