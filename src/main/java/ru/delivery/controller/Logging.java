package ru.delivery.controller;

import org.slf4j.Logger;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

public class Logging {

    public static void logErrors(Logger logger, Errors errors) {
        for (String detailedMessage: getDetailedMessages(errors))
            logger.info(detailedMessage);
    }

    public static List<String> getDetailedMessages(Errors errors) {
        List<String> result = new ArrayList<>();

        var fieldErrors = errors.getFieldErrors();

        for (var fieldError: fieldErrors) {
            String name = fieldError.getField();
            Object rejected = fieldError.getRejectedValue();

            result.add("Field \"" + name + "\"" + " caused error. " + "Rejected value: " + rejected);
        }

        return result;
    }
}
