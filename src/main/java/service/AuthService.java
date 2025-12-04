package service;

import dao.UserDAO;
import model.User;
import menu.*;
import org.mindrot.jbcrypt.BCrypt;
import runninghorse.HorseFrames;
import runninghorse.RunningHorseAnimator;
import util.ColorUtils;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Handles the authentication flow for console clients.
 *
 * <p>The current implementation is intentionally simple: it prompts for
 * credentials, checks them against the database via {@link UserDAO}, and
 * prints basic next-step information. The class will later be extended to
 * dispatch to role-specific menus once the rest of the application exists.
 * author sarah nauman
 */
public class AuthService {

    private final UserDAO userDAO = new UserDAO();
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Starts the login loop and launches appropriate menu based on role.
     *
     * <p>The application keeps running until the user explicitly chooses to
     * terminate it from within a menu (logout yes to terminate). After a
     * normal logout, control returns here and the user can log in again.</p>
     */
    public void startLogin() {
        while (true) {
            System.out.println(ColorUtils.header("---------- LOGIN ----------"));
            System.out.print(ColorUtils.prompt("Username: "));
            String username = scanner.nextLine().trim();

            System.out.print(ColorUtils.prompt("Password: "));
            String password = scanner.nextLine().trim();

            User user = userDAO.findByUsername(username);

            if (user == null) {
                System.out.println(ColorUtils.error("User not found. Please try again.\n"));
                continue;
            }

            if (!verifyPassword(password, user)) {
                System.out.println(ColorUtils.error("Invalid password. Please try again.\n"));
                continue;
            }

            // Login successful - show role-colored horse animation, then launch menu
            showRoleHorseAnimation(user.getRole());

            // Launch role-specific menu.
            // When the menu returns (user chose logout without terminating),
            // the loop continues and prompts for login again.
            launchMenu(user);
        }
    }

    /**
     * Launches the appropriate menu based on user role.
     *
     * @param user the authenticated user
     */
    private void launchMenu(User user) {
        BaseMenu menu;
        String role = user.getRole();

        switch (role) {
            case "Tester":
                menu = new TesterMenu(user);
                break;
            case "Junior":
                menu = new JuniorMenu(user);
                break;
            case "Senior":
                menu = new SeniorMenu(user);
                break;
            case "Manager":
                menu = new ManagerMenu(user);
                break;
            default:
                System.out.println(ColorUtils.error("Unknown role: " + role));
                return;
        }

        menu.run();
    }

    /**
     * Hashes a plain-text password using BCrypt.
     *
     * @param plainText password entered by a user/administrator
     * @return salted BCrypt hash suitable for persistence
     */
    public static String hashPassword(String plainText) {
        if (plainText == null || plainText.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        return BCrypt.hashpw(plainText, BCrypt.gensalt(10));
    }

    /**
     * Verifies a plain-text password against the user's stored hash.
     * @param plainText the password to verify
     * @param user the user containing the stored hash
     * @return true if password matches, false otherwise
     */
    private boolean verifyPassword(String plainText, User user) {
        String storedHash = user.getPasswordHash();

        if (storedHash == null || storedHash.isBlank()) {
            return false;
        }

        if (isBcryptHash(storedHash)) {
            return BCrypt.checkpw(plainText, storedHash);
        }

        boolean matches = plainText != null && plainText.equals(storedHash);
        if (matches) {
            // Upgrade legacy plain-text storage to BCrypt automatically.
            String upgradedHash = hashPassword(plainText);
            userDAO.updatePasswordHash(user.getUserId(), upgradedHash);
            user.setPasswordHash(upgradedHash);
            System.out.println(ColorUtils.info("Password upgraded to secure hash."));
        }
        return matches;
    }

    /**
     * Checks if a string is a BCrypt hash.
     * @param candidate the string to check
     * @return true if the string starts with BCrypt hash prefix
     */
    private boolean isBcryptHash(String candidate) {
        return candidate.startsWith("$2a$") || candidate.startsWith("$2b$") || candidate.startsWith("$2y$");
    }

    /**
     * Shows a 1-second horse animation colored based on the user's role.
     * 
     * @param role the user's role (Tester, Junior, Senior, Manager)
     */
    private void showRoleHorseAnimation(String role) {
        String horseColor;
        switch (role) {
            case "Junior":
                horseColor = ColorUtils.BRIGHT_MAGENTA; // Purple/magenta for Junior
                break;
            case "Manager":
                horseColor = ColorUtils.BRIGHT_YELLOW; // Yellow for Manager
                break;
            case "Tester":
                horseColor = ColorUtils.BRIGHT_CYAN; // Cyan for Tester
                break;
            case "Senior":
                horseColor = ColorUtils.BRIGHT_GREEN; // Green for Senior
                break;
            default:
                horseColor = ColorUtils.BRIGHT_CYAN; // Default to cyan
                break;
        }

        RunningHorseAnimator horseAnimator =
                new RunningHorseAnimator(
                        Arrays.asList(HorseFrames.FRAMES),
                        30,
                        true,
                        horseColor
                );
        horseAnimator.runForDuration(1500);
    }
}
