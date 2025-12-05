package service;

import java.time.LocalDate;

/**
 * Simple validation helpers for user input (phone numbers, emails, names, etc.).
 * @author sarah nauman
 */
public final class ValidationUtils {

    private ValidationUtils() {
        // Utility class
    }

    /**
     * Validates a phone number string.
     * - Must contain digits only
     * - Must be at least 10 digits long
     * @param phone the phone string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null) {
            return false;
        }
        String trimmed = phone.trim();
        if (trimmed.length() < 10) {
            return false;
        }
        for (int i = 0; i < trimmed.length(); i++) {
            if (!Character.isDigit(trimmed.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Basic email validation.
     * - Non-null, non-empty
     * - No spaces
     * - Must contain '@'
     * - '@' cannot be first or last character
     * - There must be at least one '.' after '@'
     *
     * @param email the email string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String trimmed = email.trim();
        if (trimmed.isEmpty() || trimmed.contains(" ")) {
            return false;
        }

        int atIndex = trimmed.indexOf('@');
        if (atIndex <= 0 || atIndex == trimmed.length() - 1) {
            return false;
        }

        String domainPart = trimmed.substring(atIndex + 1);
        if (!domainPart.contains(".")) {
            return false;
        }

        return true;
    }

    /**
     * Validates a personal name (first/middle/last).
     * - Non-null, non-empty
     * - Only letters (including Turkish characters like İ, ı, ş, ğ, ü, ö, ç), spaces, and hyphens are allowed
     * - Apostrophes/single quotes are NOT supported
     *
     * @param name the name string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidName(String name) {
        if (name == null) {
            return false;
        }
        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            return false;
        }
        for (int i = 0; i < trimmed.length(); i++) {
            char c = trimmed.charAt(i);
            if (!(Character.isLetter(c) || c == ' ' || c == '-')) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates a nickname.
     * - Non-null, non-empty
     * - Letters (including Turkish characters), digits, spaces, '-' and '_' are allowed
     *
     * @param nickname nickname string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidNickname(String nickname) {
        if (nickname == null) {
            return false;
        }
        String trimmed = nickname.trim();
        if (trimmed.isEmpty()) {
            return false;
        }
        for (int i = 0; i < trimmed.length(); i++) {
            char c = trimmed.charAt(i);
            if (!(Character.isLetterOrDigit(c) || c == ' ' || c == '-' || c == '_')) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks that a birth date is not in the future.
     *
     * @param birthDate the birth date to validate
     * @return true if the date is today or in the past, false if it is in the future
     */
    public static boolean isValidPastOrToday(LocalDate birthDate) {
        if (birthDate == null) {
            return true; // null means \"unknown\" / optional
        }
        return !birthDate.isAfter(LocalDate.now());
    }
}

