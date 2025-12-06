package service;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Simple validation helpers for user input (phone numbers, emails, names, etc.).
 * @author sarah nauman
 */
public final class ValidationUtils {

    // Pattern to match ONLY letters (including Turkish characters) - NO spaces, hyphens, or special characters
    // Uses Unicode letter category and explicitly includes Turkish characters
    private static final Pattern NAME_PATTERN = Pattern.compile(
        "^[\\p{L}]+$", Pattern.UNICODE_CASE | Pattern.CANON_EQ
    );
    
    // Pattern for nickname: ONLY letters and digits - NO spaces, hyphens, underscores, or special characters
    private static final Pattern NICKNAME_PATTERN = Pattern.compile(
        "^[\\p{L}\\p{N}]+$", Pattern.UNICODE_CASE | Pattern.CANON_EQ
    );

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
     * - ONLY letters (including Turkish characters like İ, ı, ş, ğ, ü, ö, ç, Ç)
     * - STRICTLY NO spaces, hyphens, dashes, apostrophes, or ANY special characters allowed
     *
     * Uses Unicode-aware validation to properly handle Turkish and other international characters.
     * Validates using code points for maximum compatibility with encoding issues.
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
        
        // STRICT VALIDATION: Only letters allowed - NO spaces, hyphens, dashes, or special characters
        // Validate using code points - this is the most robust method
        // It handles multi-byte characters correctly even if encoding is problematic
        int codePointCount = trimmed.codePointCount(0, trimmed.length());
        for (int i = 0; i < codePointCount; i++) {
            int codePoint = trimmed.codePointAt(trimmed.offsetByCodePoints(0, i));
            
            // STRICT: Only Unicode letters allowed - NO spaces, hyphens, or any special characters
            // Character.isLetter() works for all Unicode letters including Turkish characters
            if (!Character.isLetter(codePoint)) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Validates a nickname.
     * - Non-null, non-empty
     * - ONLY letters (including Turkish characters) and digits allowed
     * - STRICTLY NO spaces, hyphens, dashes, underscores, or ANY special characters allowed
     *
     * Uses Unicode-aware validation to properly handle Turkish and other international characters.
     * Validates using code points to handle multi-byte characters correctly.
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
        
        // STRICT VALIDATION: Only letters and digits allowed - NO spaces, hyphens, underscores, or special characters
        // Validate using code points to properly handle all Unicode characters including Turkish
        int codePointCount = trimmed.codePointCount(0, trimmed.length());
        for (int i = 0; i < codePointCount; i++) {
            int codePoint = trimmed.codePointAt(trimmed.offsetByCodePoints(0, i));
            
            // STRICT: Only letters and digits allowed - NO spaces, hyphens, underscores, or any special characters
            if (!Character.isLetterOrDigit(codePoint)) {
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

