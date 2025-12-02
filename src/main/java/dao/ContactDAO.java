package dao;

import db.DBConnection;
import model.Contact;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object for Contact operations.
 *
 * <p>This class contains SQL queries for:
 * 1. CRUD operations (Create, Read, Update, Delete) on contacts,
 * 2. Search operations (single field and multi-field),
 * 3. Sorting operations,
 * 4. Statistical information retrieval.
 *
 * @author sarah nauman
 */
public class ContactDAO {

    /**
     * Retrieves all contacts from the database.
     * Returns all contact records ordered by contact_id.
     *
     * @return List of all Contact objects, empty list if none found or error occurs
     * @author sarah nauman
     */
    public List<Contact> findAll() {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts ORDER BY contact_id";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                contacts.add(mapResultSetToContact(rs));
            }

        } catch (SQLException e) {
            System.out.println("ContactDAO Error (findAll): " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ContactDAO Unexpected Error (findAll): " + e.getMessage());
            e.printStackTrace();
        }

        return contacts;
    }

    /**
     * Retrieves a contact by their unique identifier.
     * Finds a single contact record using the contact_id.
     *
     * @param contactId the unique identifier of the contact
     * @return Contact object if found, null otherwise
     * @author sarah nauman
     */
    public Contact findById(int contactId) {
        String query = "SELECT * FROM contacts WHERE contact_id = ?";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.setInt(1, contactId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToContact(rs);
            }

        } catch (SQLException e) {
            System.out.println("ContactDAO Error (findById): " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ContactDAO Unexpected Error (findById): " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Resets the AUTO_INCREMENT value to MAX(contact_id) + 1.
     * This ensures that new contacts always get the next sequential ID,
     * regardless of deletions.
     *
     * @author sarah nauman
     */
    private void resetAutoIncrement() {
        try {
            // Get the maximum contact_id
            String maxQuery = "SELECT MAX(contact_id) as max_id FROM contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(maxQuery);
            ResultSet rs = ps.executeQuery();
            
            int nextId = 1; // Default if table is empty
            if (rs.next() && rs.getObject("max_id") != null) {
                nextId = rs.getInt("max_id") + 1;
            }
            
            // Reset AUTO_INCREMENT to the next sequential ID
            String alterQuery = "ALTER TABLE contacts AUTO_INCREMENT = ?";
            ps = DBConnection.getConnection().prepareStatement(alterQuery);
            ps.setInt(1, nextId);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            // Silently fail - AUTO_INCREMENT will still work, just might have gaps
            // This is not critical enough to stop the operation
        }
    }

    /**
     * Creates a new contact in the database.
     * Inserts a new contact record with all provided information.
     * Ensures AUTO_INCREMENT is set correctly before inserting.
     *
     * @param contact the Contact object containing contact information
     * @return true if contact was created successfully, false otherwise
     * @author sarah nauman
     */
    public boolean create(Contact contact) {
        // Reset AUTO_INCREMENT to ensure sequential IDs
        resetAutoIncrement();
        
        String query = "INSERT INTO contacts (first_name, middle_name, last_name, nickname, " +
                "phone_primary, phone_secondary, email, linkedin_url, birth_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, contact.getFirstName());
            ps.setString(2, contact.getMiddleName());
            ps.setString(3, contact.getLastName());
            ps.setString(4, contact.getNickname());
            ps.setString(5, contact.getPhonePrimary());
            ps.setString(6, contact.getPhoneSecondary());
            ps.setString(7, contact.getEmail());
            ps.setString(8, contact.getLinkedinUrl());
            
            // Handle LocalDate to SQL Date conversion
            if (contact.getBirthDate() != null) {
                ps.setDate(9, Date.valueOf(contact.getBirthDate()));
            } else {
                ps.setDate(9, null);
            }

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the generated contact_id
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    contact.setContactId(generatedKeys.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println("ContactDAO Error (create): " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ContactDAO Unexpected Error (create): " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Updates an existing contact in the database.
     * Modifies all fields of a contact record identified by contact_id.
     *
     * @param contact the Contact object with updated information
     * @return true if contact was updated successfully, false otherwise
     * @author sarah nauman
     */
    public boolean update(Contact contact) {
        String query = "UPDATE contacts SET first_name = ?, middle_name = ?, last_name = ?, " +
                "nickname = ?, phone_primary = ?, phone_secondary = ?, email = ?, " +
                "linkedin_url = ?, birth_date = ? WHERE contact_id = ?";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.setString(1, contact.getFirstName());
            ps.setString(2, contact.getMiddleName());
            ps.setString(3, contact.getLastName());
            ps.setString(4, contact.getNickname());
            ps.setString(5, contact.getPhonePrimary());
            ps.setString(6, contact.getPhoneSecondary());
            ps.setString(7, contact.getEmail());
            ps.setString(8, contact.getLinkedinUrl());
            
            // Handle LocalDate to SQL Date conversion
            if (contact.getBirthDate() != null) {
                ps.setDate(9, Date.valueOf(contact.getBirthDate()));
            } else {
                ps.setDate(9, null);
            }
            
            ps.setInt(10, contact.getContactId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("ContactDAO Error (update): " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ContactDAO Unexpected Error (update): " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Deletes a contact from the database by their unique identifier.
     * Removes a contact record permanently from the database.
     *
     * @param contactId the unique identifier of the contact to delete
     * @return true if contact was deleted successfully, false otherwise
     * @author sarah nauman
     */
    public boolean delete(int contactId) {
        String query = "DELETE FROM contacts WHERE contact_id = ?";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.setInt(1, contactId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("ContactDAO Error (delete): " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ContactDAO Unexpected Error (delete): " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Searches for contacts by first name using partial matching.
     * Finds all contacts whose first name contains the search term (case-insensitive).
     *
     * @param firstName the first name or partial first name to search for
     * @return List of Contact objects matching the search criteria
     * @author sarah nauman
     */
    public List<Contact> searchByFirstName(String firstName) {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts WHERE first_name LIKE ? ORDER BY first_name";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.setString(1, "%" + firstName + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                contacts.add(mapResultSetToContact(rs));
            }

        } catch (SQLException e) {
            System.out.println("ContactDAO Error (searchByFirstName): " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ContactDAO Unexpected Error (searchByFirstName): " + e.getMessage());
            e.printStackTrace();
        }

        return contacts;
    }

    /**
     * Searches for contacts by last name using partial matching.
     * Finds all contacts whose last name contains the search term (case-insensitive).
     *
     * @param lastName the last name or partial last name to search for
     * @return List of Contact objects matching the search criteria
     * @author sarah nauman
     */
    public List<Contact> searchByLastName(String lastName) {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts WHERE last_name LIKE ? ORDER BY last_name";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.setString(1, "%" + lastName + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                contacts.add(mapResultSetToContact(rs));
            }

        } catch (SQLException e) {
            System.out.println("ContactDAO Error (searchByLastName): " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ContactDAO Unexpected Error (searchByLastName): " + e.getMessage());
            e.printStackTrace();
        }

        return contacts;
    }

    /**
     * Searches for contacts by phone number using partial matching.
     * Searches both primary and secondary phone fields for the search term.
     *
     * @param phone the phone number or partial phone number to search for
     * @return List of Contact objects matching the search criteria
     * @author sarah nauman
     */
    public List<Contact> searchByPhone(String phone) {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts WHERE phone_primary LIKE ? OR phone_secondary LIKE ? ORDER BY phone_primary";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            String searchPattern = "%" + phone + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                contacts.add(mapResultSetToContact(rs));
            }

        } catch (SQLException e) {
            System.out.println("ContactDAO Error (searchByPhone): " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ContactDAO Unexpected Error (searchByPhone): " + e.getMessage());
            e.printStackTrace();
        }

        return contacts;
    }

    /**
     * Searches for contacts using multiple field criteria.
     * Allows searching by any combination of fields (first name, last name, phone, email, birth month).
     * All criteria are combined with AND logic (all must match).
     *
     * @param criteria Map where keys are field names and values are search terms
     * @return List of Contact objects matching all search criteria
     * @author sarah nauman
     */
    public List<Contact> searchByMultipleFields(Map<String, String> criteria) {
        List<Contact> contacts = new ArrayList<>();
        
        if (criteria == null || criteria.isEmpty()) {
            return contacts;
        }

        // Build dynamic query based on provided criteria
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM contacts WHERE ");
        List<String> conditions = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        // Handle first_name
        if (criteria.containsKey("first_name") && criteria.get("first_name") != null && !criteria.get("first_name").trim().isEmpty()) {
            conditions.add("first_name LIKE ?");
            parameters.add("%" + criteria.get("first_name") + "%");
        }

        // Handle last_name
        if (criteria.containsKey("last_name") && criteria.get("last_name") != null && !criteria.get("last_name").trim().isEmpty()) {
            conditions.add("last_name LIKE ?");
            parameters.add("%" + criteria.get("last_name") + "%");
        }

        // Handle phone (searches both primary and secondary)
        if (criteria.containsKey("phone") && criteria.get("phone") != null && !criteria.get("phone").trim().isEmpty()) {
            conditions.add("(phone_primary LIKE ? OR phone_secondary LIKE ?)");
            String phonePattern = "%" + criteria.get("phone") + "%";
            parameters.add(phonePattern);
            parameters.add(phonePattern);
        }

        // Handle email
        if (criteria.containsKey("email") && criteria.get("email") != null && !criteria.get("email").trim().isEmpty()) {
            conditions.add("email LIKE ?");
            parameters.add("%" + criteria.get("email") + "%");
        }

        // Handle birth_month (searches for contacts born in a specific month)
        if (criteria.containsKey("birth_month") && criteria.get("birth_month") != null && !criteria.get("birth_month").trim().isEmpty()) {
            conditions.add("MONTH(birth_date) = ?");
            parameters.add(Integer.parseInt(criteria.get("birth_month")));
        }

        if (conditions.isEmpty()) {
            return contacts; // No valid criteria provided
        }

        queryBuilder.append(String.join(" AND ", conditions));
        queryBuilder.append(" ORDER BY first_name, last_name");

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(queryBuilder.toString());
            
            // Set all parameters
            for (int i = 0; i < parameters.size(); i++) {
                Object param = parameters.get(i);
                if (param instanceof String) {
                    ps.setString(i + 1, (String) param);
                } else if (param instanceof Integer) {
                    ps.setInt(i + 1, (Integer) param);
                }
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                contacts.add(mapResultSetToContact(rs));
            }

        } catch (SQLException e) {
            System.out.println("ContactDAO Error (searchByMultipleFields): " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ContactDAO Unexpected Error (searchByMultipleFields): " + e.getMessage());
            e.printStackTrace();
        }

        return contacts;
    }

    /**
     * Retrieves all contacts sorted by a specified field in ascending or descending order.
     * Allows sorting by any contact field (first_name, last_name, email, birth_date, etc.).
     *
     * @param field the field name to sort by (e.g., "first_name", "last_name", "email", "birth_date")
     * @param ascending true for ascending order, false for descending order
     * @return List of Contact objects sorted by the specified field
     * @author sarah nauman
     */
    public List<Contact> findAllSorted(String field, boolean ascending) {
        List<Contact> contacts = new ArrayList<>();
        
        // Validate field name to prevent SQL injection
        String[] validFields = {"contact_id", "first_name", "middle_name", "last_name", 
                                "nickname", "phone_primary", "phone_secondary", "email", 
                                "linkedin_url", "birth_date", "created_at", "updated_at"};
        
        boolean isValidField = false;
        for (String validField : validFields) {
            if (validField.equalsIgnoreCase(field)) {
                isValidField = true;
                field = validField; // Use the correct case
                break;
            }
        }
        
        if (!isValidField) {
            System.out.println("Invalid field name for sorting: " + field);
            return contacts;
        }

        String orderDirection = ascending ? "ASC" : "DESC";
        String query = "SELECT * FROM contacts ORDER BY " + field + " " + orderDirection;

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                contacts.add(mapResultSetToContact(rs));
            }

        } catch (SQLException e) {
            System.out.println("ContactDAO Error (findAllSorted): " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ContactDAO Unexpected Error (findAllSorted): " + e.getMessage());
            e.printStackTrace();
        }

        return contacts;
    }

    /**
     * Retrieves statistical information about contacts.
     * Calculates various statistics including name counts, LinkedIn presence, age statistics.
     *
     * @return Map containing statistical data with descriptive keys
     * @author sarah nauman
     */
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();

        try {
            // Total contacts count
            String totalQuery = "SELECT COUNT(*) as total FROM contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(totalQuery);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                stats.put("total_contacts", rs.getInt("total"));
            }

            // Contacts with LinkedIn URL
            String linkedinQuery = "SELECT COUNT(*) as count FROM contacts WHERE linkedin_url IS NOT NULL AND linkedin_url != ''";
            ps = DBConnection.getConnection().prepareStatement(linkedinQuery);
            rs = ps.executeQuery();
            if (rs.next()) {
                stats.put("contacts_with_linkedin", rs.getInt("count"));
            }

            // Contacts without LinkedIn URL
            String noLinkedinQuery = "SELECT COUNT(*) as count FROM contacts WHERE linkedin_url IS NULL OR linkedin_url = ''";
            ps = DBConnection.getConnection().prepareStatement(noLinkedinQuery);
            rs = ps.executeQuery();
            if (rs.next()) {
                stats.put("contacts_without_linkedin", rs.getInt("count"));
            }

            // Most common first name
            String commonFirstNameQuery = "SELECT first_name, COUNT(*) as count FROM contacts " +
                    "WHERE first_name IS NOT NULL GROUP BY first_name ORDER BY count DESC LIMIT 1";
            ps = DBConnection.getConnection().prepareStatement(commonFirstNameQuery);
            rs = ps.executeQuery();
            if (rs.next()) {
                stats.put("most_common_first_name", rs.getString("first_name"));
                stats.put("most_common_first_name_count", rs.getInt("count"));
            }

            // Most common last name
            String commonLastNameQuery = "SELECT last_name, COUNT(*) as count FROM contacts " +
                    "WHERE last_name IS NOT NULL GROUP BY last_name ORDER BY count DESC LIMIT 1";
            ps = DBConnection.getConnection().prepareStatement(commonLastNameQuery);
            rs = ps.executeQuery();
            if (rs.next()) {
                stats.put("most_common_last_name", rs.getString("last_name"));
                stats.put("most_common_last_name_count", rs.getInt("count"));
            }

            // Youngest contact (oldest birth date = youngest person)
            String youngestQuery = "SELECT * FROM contacts WHERE birth_date IS NOT NULL ORDER BY birth_date DESC LIMIT 1";
            ps = DBConnection.getConnection().prepareStatement(youngestQuery);
            rs = ps.executeQuery();
            if (rs.next()) {
                Contact youngest = mapResultSetToContact(rs);
                stats.put("youngest_contact", youngest.getFirstName() + " " + youngest.getLastName());
                stats.put("youngest_birth_date", youngest.getBirthDate());
            }

            // Oldest contact (earliest birth date = oldest person)
            String oldestQuery = "SELECT * FROM contacts WHERE birth_date IS NOT NULL ORDER BY birth_date ASC LIMIT 1";
            ps = DBConnection.getConnection().prepareStatement(oldestQuery);
            rs = ps.executeQuery();
            if (rs.next()) {
                Contact oldest = mapResultSetToContact(rs);
                stats.put("oldest_contact", oldest.getFirstName() + " " + oldest.getLastName());
                stats.put("oldest_birth_date", oldest.getBirthDate());
            }

            // Average age calculation
            String avgAgeQuery = "SELECT AVG(YEAR(CURDATE()) - YEAR(birth_date)) as avg_age FROM contacts WHERE birth_date IS NOT NULL";
            ps = DBConnection.getConnection().prepareStatement(avgAgeQuery);
            rs = ps.executeQuery();
            if (rs.next()) {
                double avgAge = rs.getDouble("avg_age");
                if (!rs.wasNull()) {
                    stats.put("average_age", Math.round(avgAge * 10.0) / 10.0); // Round to 1 decimal
                }
            }

        } catch (SQLException e) {
            System.out.println("ContactDAO Error (getStatistics): " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ContactDAO Unexpected Error (getStatistics): " + e.getMessage());
            e.printStackTrace();
        }

        return stats;
    }

    /**
     * Helper method to map a ResultSet row to a Contact object.
     * Converts database result set data into a Contact model object.
     *
     * @param rs the ResultSet containing contact data
     * @return Contact object populated with data from ResultSet
     * @throws SQLException if database access error occurs
     * @author sarah nauman
     */
    private Contact mapResultSetToContact(ResultSet rs) throws SQLException {
        Contact contact = new Contact();
        contact.setContactId(rs.getInt("contact_id"));
        contact.setFirstName(rs.getString("first_name"));
        contact.setMiddleName(rs.getString("middle_name"));
        contact.setLastName(rs.getString("last_name"));
        contact.setNickname(rs.getString("nickname"));
        contact.setPhonePrimary(rs.getString("phone_primary"));
        contact.setPhoneSecondary(rs.getString("phone_secondary"));
        contact.setEmail(rs.getString("email"));
        contact.setLinkedinUrl(rs.getString("linkedin_url"));
        
        // Handle SQL Date to LocalDate conversion
        Date birthDate = rs.getDate("birth_date");
        if (birthDate != null) {
            contact.setBirthDate(birthDate.toLocalDate());
        } else {
            contact.setBirthDate(null);
        }
        
        return contact;
    }
}
