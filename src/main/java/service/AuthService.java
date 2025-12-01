package service;

import dao.UserDAO;
import model.User;
import menu.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Scanner;

/**
 * Handles the authentication flow for console clients.
 *
 * <p>The current implementation is intentionally simple: it prompts for
 * credentials, checks them against the database via {@link UserDAO}, and
 * prints basic next-step information. The class will later be extended to
 * dispatch to role-specific menus once the rest of the application exists.
 */
public class AuthService {

    private final UserDAO userDAO = new UserDAO();
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Starts the login loop and launches appropriate menu based on role.
     */
    public void startLogin() {
        while (true) {
            System.out.println("---------- LOGIN ----------");

            System.out.print("Username: ");
            String username = scanner.nextLine().trim();

            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            User user = userDAO.findByUsername(username);

            if (user == null) {
                System.out.println("User not found. Please try again.\n");
                continue;
            }

            if (!verifyPassword(password, user)) {
                System.out.println("Invalid password. Please try again.\n");
                continue;
            }

            // Login successful - launch role-specific menu
            launchMenu(user);
            break;
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
                System.out.println("Unknown role: " + role);
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
            System.out.println("Password upgraded to secure hash.");
        }
        return matches;
    }

    private boolean isBcryptHash(String candidate) {
        return candidate.startsWith("$2a$") || candidate.startsWith("$2b$") || candidate.startsWith("$2y$");
    }

    public static boolean verifyPasswordStatic(String plainText, User user) {
        String storedHash = user.getPasswordHash();

        if (storedHash == null || storedHash.isBlank()) return false;

        if (storedHash.startsWith("$2a$") || storedHash.startsWith("$2b$") || storedHash.startsWith("$2y$")) {
            return org.mindrot.jbcrypt.BCrypt.checkpw(plainText, storedHash);
        }

        // legacy plain-text
        boolean matches = plainText.equals(storedHash);
        if (matches) {
            String upgradedHash = hashPassword(plainText);
            new UserDAO().updatePasswordHash(user.getUserId(), upgradedHash);
            user.setPasswordHash(upgradedHash);
        }
        return matches;
    }




}
