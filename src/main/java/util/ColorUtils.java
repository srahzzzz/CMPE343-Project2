package util;

/**
 * Utility class for ANSI color codes to make console output colorful.
 * @author sarah nauman
 */
public class ColorUtils {
    
    // ANSI color codes
    public static final String RESET = "\033[0m";
    public static final String BOLD = "\033[1m";
    
    // Text colors
    public static final String BLACK = "\033[30m";
    public static final String RED = "\033[31m";
    public static final String GREEN = "\033[32m";
    public static final String YELLOW = "\033[33m";
    public static final String BLUE = "\033[34m";
    public static final String MAGENTA = "\033[35m";
    public static final String CYAN = "\033[36m";
    public static final String WHITE = "\033[37m";
    
    // Bright colors
    public static final String BRIGHT_BLACK = "\033[90m";
    public static final String BRIGHT_RED = "\033[91m";
    public static final String BRIGHT_GREEN = "\033[92m";
    public static final String BRIGHT_YELLOW = "\033[93m";
    public static final String BRIGHT_BLUE = "\033[94m";
    public static final String BRIGHT_MAGENTA = "\033[95m";
    public static final String BRIGHT_CYAN = "\033[96m";
    public static final String BRIGHT_WHITE = "\033[97m";
    
    // Background colors
    public static final String BG_BLACK = "\033[40m";
    public static final String BG_RED = "\033[41m";
    public static final String BG_GREEN = "\033[42m";
    public static final String BG_YELLOW = "\033[43m";
    public static final String BG_BLUE = "\033[44m";
    public static final String BG_MAGENTA = "\033[45m";
    public static final String BG_CYAN = "\033[46m";
    public static final String BG_WHITE = "\033[47m";
    
    private ColorUtils() {
        // Utility class
    }
    
    /**
     * Colors a string with the specified color.
     * @param text the text to color
     * @param color the ANSI color code
     * @return the colored text with reset
     */
    public static String colorize(String text, String color) {
        return color + text + RESET;
    }
    
    /**
     * Colors text as a header.
     */
    public static String header(String text) {
        return BOLD + BRIGHT_CYAN + text + RESET;
    }
    
    /**
     * Colors text as success (green).
     */
    public static String success(String text) {
        return GREEN + text + RESET;
    }
    
    /**
     * Colors text as error (red).
     */
    public static String error(String text) {
        return RED + text + RESET;
    }
    
    /**
     * Colors text as warning (yellow).
     */
    public static String warning(String text) {
        return YELLOW + text + RESET;
    }
    
    /**
     * Colors text as info (blue).
     */
    public static String info(String text) {
        return BLUE + text + RESET;
    }
    
    /**
     * Colors text as prompt (bright blue).
     */
    public static String prompt(String text) {
        return BRIGHT_BLUE + text + RESET;
    }
    
    /**
     * Colors text as menu option (cyan).
     */
    public static String menuOption(String text) {
        return CYAN + text + RESET;
    }
    
    // Role-specific color schemes
    
    /**
     * Colors text for Tester role (cyan/blue theme).
     */
    public static String testerPrompt(String text) {
        return BRIGHT_CYAN + text + RESET;
    }
    
    public static String testerMenuOption(String text) {
        return CYAN + text + RESET;
    }
    
    /**
     * Colors text for Junior Developer role (purple/magenta theme).
     */
    public static String juniorPrompt(String text) {
        return BRIGHT_MAGENTA + text + RESET;
    }
    
    public static String juniorMenuOption(String text) {
        return MAGENTA + text + RESET;
    }
    
    /**
     * Colors text for Senior Developer role (green/teal theme).
     */
    public static String seniorPrompt(String text) {
        return BRIGHT_GREEN + text + RESET;
    }
    
    public static String seniorMenuOption(String text) {
        return GREEN + text + RESET;
    }
    
    /**
     * Colors text for Manager role (yellow/gold theme).
     */
    public static String managerPrompt(String text) {
        return BRIGHT_YELLOW + text + RESET;
    }
    
    public static String managerMenuOption(String text) {
        return YELLOW + text + RESET;
    }
}

