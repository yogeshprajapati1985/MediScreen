package com.mediscreen.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;

public class MediscreenUtil {
    public static int countMatchingTerms(String input, Set<String> terms) {
        return (int) terms.stream()
                .map(String::toLowerCase)
                .filter(input::contains).count();
    }
    public static int getAge(String dobString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adjust format if needed
            LocalDate dateOfBirth = LocalDate.parse(dobString, formatter);
            LocalDate today = LocalDate.now();

            if (!dateOfBirth.isAfter(today)) {
                return Period.between(dateOfBirth, today).getYears();
            } else {
                throw new IllegalArgumentException("Date of birth must be in the past.");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-MM-dd.");
        }
    }
}
