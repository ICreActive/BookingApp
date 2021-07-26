package com.shkubel.project.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeParser {


    private DateTimeParser() {
    }

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String parseToString(LocalDateTime localDateTime) {
        return localDateTime.format(dtf);
    }

    public static LocalDateTime parseToDate(String date) {
        return LocalDateTime.parse(date, dtf);
    }

    public static String nowToString() {
        return LocalDateTime.now().format(dtf);
    }

}
