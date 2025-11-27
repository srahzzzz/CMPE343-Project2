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
    public int getUserId() { return userId; }
    public void setUserId(int id) { this.userId = id; }

    public String getUsername() { return username; }
    public void setUsername(String u) { this.username = u; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String p) { this.passwordHash = p; }

    public String getName() { return name; }
    public void setName(String n) { this.name = n; }

    public String getSurname() { return surname; }
    public void setSurname(String s) { this.surname = s; }

    public String getRole() { return role; }
    public void setRole(String r) { this.role = r; }
}
