package com.shkubel.project.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeParser {

    private DateTimeParser() {
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public static String parseToString(LocalDateTime localDateTime) {
        return localDateTime.format(FORMATTER);
    }

    public static LocalDateTime parseToDate(String date) {
        return LocalDateTime.parse(date, FORMATTER);
    }

    public static String nowToString() {
        return LocalDateTime.now().format(FORMATTER);
    }

}
