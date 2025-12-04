package model;

/**
 * Represents a system user (Tester, Junior, Senior, Manager).
 *
 * <p>This model maps directly to the 'users' table in MySQL.
 * It stores authentication info and role-based privilege levels.
 */
public class User {

    private int userId;
    private String username;
    private String passwordHash;
    private String name;
    private String surname;
    private String role;

    // Getters and Setters
    /** @return the unique user identifier */
    public int getUserId() { return userId; }
    /** @param id the unique user identifier */
    public void setUserId(int id) { this.userId = id; }

    /** @return the username */
    public String getUsername() { return username; }
    /** @param u the username */
    public void setUsername(String u) { this.username = u; }

    /** @return the password hash (BCrypt) */
    public String getPasswordHash() { return passwordHash; }
    /** @param p the password hash (BCrypt) */
    public void setPasswordHash(String p) { this.passwordHash = p; }

    /** @return the user's first name */
    public String getName() { return name; }
    /** @param n the user's first name */
    public void setName(String n) { this.name = n; }

    /** @return the user's surname */
    public String getSurname() { return surname; }
    /** @param s the user's surname */
    public void setSurname(String s) { this.surname = s; }

    /** @return the user's role (Tester, Junior, Senior, Manager) */
    public String getRole() { return role; }
    /** @param r the user's role (Tester, Junior, Senior, Manager) */
    public void setRole(String r) { this.role = r; }
}
