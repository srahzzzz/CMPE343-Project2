package dao;

import db.DBConnection;
import model.User;

import java.sql.*;

/**
 * Data Access Object for User operations.
 *
 * <p>This class contains SQL queries for:
 * 1. Fetching users for login authentication,
 * 2. Getting role and hashed password,
 * 3. Later: for Manager ops (add/delete/update user).
 */
public class UserDAO {

    /**
     * Finds a user by username.
     *
     * @param username input username
     * @return User object or null if not found
     */
    public User findByUsername(String username) {

        String query = "SELECT * FROM users WHERE username = ?";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setPasswordHash(rs.getString("password_hash"));
                u.setName(rs.getString("name"));
                u.setSurname(rs.getString("surname"));
                u.setRole(rs.getString("role"));
                return u;
            }

        } catch (SQLException e) {
            System.out.println("UserDAO Error: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("UserDAO Unexpected Error: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Persists a new password hash for the specified user.
     *
     * @param userId       database identifier
     * @param passwordHash bcrypt hash to store
     */
    public void updatePasswordHash(int userId, String passwordHash) {
        String query = "UPDATE users SET password_hash = ? WHERE user_id = ?";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.setString(1, passwordHash);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("UserDAO Error (updatePasswordHash): " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("UserDAO Unexpected Error (updatePasswordHash): " + e.getMessage());
            e.printStackTrace();
        }
    }
}
