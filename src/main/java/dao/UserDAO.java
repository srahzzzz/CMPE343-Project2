package dao;

import db.DBConnection;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for User operations.
 *
 * <p>This class contains SQL queries for:
 * 1. Fetching users for login authentication,
 * 2. Getting role and hashed password,
 * 3. Manager operations (list, add, update, delete users).
 *
 * @author sarah nauman
 */
public class UserDAO {

    /**
     * Finds a user by username.
     *
     * @param username input username
     * @return User object or null if not found
     * @author sarah nauman
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
     * @author sarah nauman
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

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param userId the unique identifier of the user
     * @return User object if found, null otherwise
     * @author sarah nauman
     */
    public User findById(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
            // fix the exception !!!!!

        } catch (SQLException e) {
            System.out.println("UserDAO Error (findById): " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("UserDAO Unexpected Error (findById): " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return List of all User objects, empty list if none found or error occurs
     * @author sarah nauman
     */
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users ORDER BY user_id";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            System.out.println("UserDAO Error (findAll): " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("UserDAO Unexpected Error (findAll): " + e.getMessage());
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Creates a new user in the database.
     * The password should already be hashed before calling this method.
     *
     * @param user the User object containing user information (password must be hashed)
     * @return true if user was created successfully, false otherwise
     * @author sarah nauman
     */
    public boolean create(User user) {
        String query = "INSERT INTO users (username, password_hash, name, surname, role) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getName());
            ps.setString(4, user.getSurname());
            ps.setString(5, user.getRole());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the generated user_id
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println("UserDAO Error (create): " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            
            // Handle duplicate username constraint
            if (e.getErrorCode() == 1062) {
                System.out.println("Username already exists. Please choose a different username.");
            }
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("UserDAO Unexpected Error (create): " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Updates an existing user in the database.
     * All fields except user_id can be updated.
     * If password_hash is provided, it will be updated; otherwise, it remains unchanged.
     *
     * @param user the User object with updated information
     * @return true if user was updated successfully, false otherwise
     * @author sarah nauman
     */
    public boolean update(User user) {
        String query;
        boolean updatePassword = user.getPasswordHash() != null && !user.getPasswordHash().trim().isEmpty();

        if (updatePassword) {
            query = "UPDATE users SET username = ?, password_hash = ?, name = ?, surname = ?, role = ? WHERE user_id = ?";
        } else {
            query = "UPDATE users SET username = ?, name = ?, surname = ?, role = ? WHERE user_id = ?";
        }

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.setString(1, user.getUsername());
            
            int paramIndex = 2;
            if (updatePassword) {
                ps.setString(paramIndex++, user.getPasswordHash());
            }
            
            ps.setString(paramIndex++, user.getName());
            ps.setString(paramIndex++, user.getSurname());
            ps.setString(paramIndex++, user.getRole());
            ps.setInt(paramIndex, user.getUserId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("UserDAO Error (update): " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            
            // Handle duplicate username constraint
            if (e.getErrorCode() == 1062) {
                System.out.println("Username already exists. Please choose a different username.");
            }
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("UserDAO Unexpected Error (update): " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Deletes a user from the database by their unique identifier.
     *
     * @param userId the unique identifier of the user to delete
     * @return true if user was deleted successfully, false otherwise
     * @author sarah nauman
     */
    public boolean delete(int userId) {
        String query = "DELETE FROM users WHERE user_id = ?";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.setInt(1, userId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("UserDAO Error (delete): " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("UserDAO Unexpected Error (delete): " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Helper method to map a ResultSet row to a User object.
     *
     * @param rs the ResultSet containing user data
     * @return User object populated with data from ResultSet
     * @throws SQLException if database access error occurs
     * @author sarah nauman
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setUsername(rs.getString("username"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setName(rs.getString("name"));
        u.setSurname(rs.getString("surname"));
        u.setRole(rs.getString("role"));
        return u;
    }
}
