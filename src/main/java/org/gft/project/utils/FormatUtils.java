package org.gft.project.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class FormatUtils {
    private FormatUtils() {
    }

    public static boolean validateName(String name) {
        String regex = "^[a-zA-Z ]+$"; //we can add some characters to allowed
        return !Pattern.matches(regex, name);
    }

    public static boolean validateDateTime(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime.parse(date, formatter);
            return false;
        } catch (Exception e) {
            System.err.println("Error parsing date time " + e.getMessage());
            return true;
        }
    }

    public static boolean validateDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(date, formatter);
            return false;
        } catch (Exception e) {
            System.err.println("Error parsing date " + e.getMessage());
            return true;
        }
    }

    public static String getEndDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dt = LocalDateTime.parse(date, formatter);
        return dt.plusHours(2).format(formatter);
    }
}
