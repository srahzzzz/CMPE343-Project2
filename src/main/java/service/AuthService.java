package service;

import dao.UserDAO;
import model.User;
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
     * Starts the login loop. Returns after a single attempt for now.
     */
    public void startLogin() {
        System.out.println("---------- LOGIN ----------");

        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        User user = userDAO.findByUsername(username);

        if (user == null) {
            System.out.println("User not found.\n");
            return;
        }

        if (!verifyPassword(password, user)) {
            System.out.println("Invalid password.\n");
            return;
        }

        System.out.printf("Welcome %s %s! Role: %s%n%n",
                user.getName(),
                user.getSurname(),
                user.getRole());

        // Placeholder for future role-specific menu launch.
        System.out.println("Role-based menus coming soon...\n");
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
}
