package dao;

import db.DBConnection;
import model.Contact;
import model.UndoOperation;
import model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for UndoOperation operations.
 * 
 * <p>Manages undo operations stored in the database.
 * Maintains a maximum of 5 operations - when a 6th is added, the oldest is removed.
 * @author sarah nauman
 */
public class UndoOperationDAO {
    
    private static final int MAX_OPERATIONS = 5;
    
    /**
     * Retrieves all undo operations, ordered by timestamp (newest first).
     * 
     * @return List of all UndoOperation objects, empty list if none found
     */
    public List<UndoOperation> findAll() {
        List<UndoOperation> operations = new ArrayList<>();
        String query = "SELECT * FROM undo_operations ORDER BY operation_timestamp DESC";
        
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                operations.add(mapResultSetToUndoOperation(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("UndoOperationDAO Error (findAll): " + e.getMessage());
            e.printStackTrace();
        }
        
        return operations;
    }
    
    /**
     * Retrieves an undo operation by its ID.
     * 
     * @param operationId the unique identifier of the operation
     * @return UndoOperation if found, null otherwise
     */
    public UndoOperation findById(int operationId) {
        String query = "SELECT * FROM undo_operations WHERE operation_id = ?";
        
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.setInt(1, operationId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToUndoOperation(rs);
            }
            
        } catch (SQLException e) {
            System.out.println("UndoOperationDAO Error (findById): " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Inserts a new undo operation.
     * Automatically maintains the MAX_OPERATIONS limit by removing the oldest operation if needed.
     * 
     * @param operation the UndoOperation to insert
     * @return true if inserted successfully, false otherwise
     */
    public boolean create(UndoOperation operation) {
        // First, check if we need to remove the oldest operation
        maintainMaxOperations();
        
        String query = "INSERT INTO undo_operations (operation_type, entity_type, entity_id, entity_data, user_id) " +
                       "VALUES (?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.setString(1, operation.getOperationType().name());
            ps.setString(2, operation.getEntityType().name());
            ps.setInt(3, operation.getEntityId());
            
            // Set JSON data (can be null for ADD operations)
            if (operation.getEntityDataJson() != null && !operation.getEntityDataJson().isEmpty()) {
                ps.setString(4, operation.getEntityDataJson());
            } else {
                ps.setNull(4, Types.VARCHAR);
            }
            
            // Set user_id (optional)
            if (operation.getUserId() != null) {
                ps.setInt(5, operation.getUserId());
            } else {
                ps.setNull(5, Types.INTEGER);
            }
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("UndoOperationDAO Error (create): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Deletes an undo operation by its ID.
     * 
     * @param operationId the unique identifier of the operation to delete
     * @return true if deleted successfully, false otherwise
     */
    public boolean delete(int operationId) {
        String query = "DELETE FROM undo_operations WHERE operation_id = ?";
        
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.setInt(1, operationId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("UndoOperationDAO Error (delete): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Deletes all undo operations.
     * 
     * @return true if all operations were deleted successfully
     */
    public boolean deleteAll() {
        String query = "DELETE FROM undo_operations";
        
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println("UndoOperationDAO Error (deleteAll): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Gets the count of undo operations.
     * 
     * @return the number of operations stored
     */
    public int getCount() {
        String query = "SELECT COUNT(*) as count FROM undo_operations";
        
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
            
        } catch (SQLException e) {
            System.out.println("UndoOperationDAO Error (getCount): " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    /**
     * Maintains the maximum number of operations by removing the oldest ones.
     * Called automatically before inserting a new operation.
     */
    private void maintainMaxOperations() {
        try {
            // Get count of current operations
            int count = getCount();
            
            // If we're at or above the limit, delete the oldest operations
            if (count >= MAX_OPERATIONS) {
                int toDelete = count - MAX_OPERATIONS + 1; // Delete enough to make room for 1 new one
                
                // First, get the IDs of the oldest operations
                String selectQuery = "SELECT operation_id FROM undo_operations " +
                                    "ORDER BY operation_timestamp ASC LIMIT ?";
                PreparedStatement selectPs = DBConnection.getConnection().prepareStatement(selectQuery);
                selectPs.setInt(1, toDelete);
                ResultSet rs = selectPs.executeQuery();
                
                // Build list of IDs to delete
                List<Integer> idsToDelete = new ArrayList<>();
                while (rs.next()) {
                    idsToDelete.add(rs.getInt("operation_id"));
                }
                
                // Delete the oldest operations by ID
                if (!idsToDelete.isEmpty()) {
                    // Build IN clause with placeholders
                    StringBuilder placeholders = new StringBuilder();
                    for (int i = 0; i < idsToDelete.size(); i++) {
                        if (i > 0) placeholders.append(",");
                        placeholders.append("?");
                    }
                    
                    String deleteQuery = "DELETE FROM undo_operations WHERE operation_id IN (" + placeholders + ")";
                    PreparedStatement deletePs = DBConnection.getConnection().prepareStatement(deleteQuery);
                    for (int i = 0; i < idsToDelete.size(); i++) {
                        deletePs.setInt(i + 1, idsToDelete.get(i));
                    }
                    deletePs.executeUpdate();
                }
            }
            
        } catch (SQLException e) {
            // Silently fail - not critical enough to stop the operation
            System.out.println("UndoOperationDAO Warning: Could not maintain max operations limit");
        }
    }
    
    /**
     * Maps a ResultSet row to an UndoOperation object.
     * 
     * @param rs the ResultSet containing the operation data
     * @return UndoOperation object
     * @throws SQLException if database error occurs
     */
    private UndoOperation mapResultSetToUndoOperation(ResultSet rs) throws SQLException {
        UndoOperation operation = new UndoOperation();
        operation.setOperationId(rs.getInt("operation_id"));
        operation.setOperationType(UndoOperation.OperationType.valueOf(rs.getString("operation_type")));
        operation.setEntityType(UndoOperation.EntityType.valueOf(rs.getString("entity_type")));
        operation.setEntityId(rs.getInt("entity_id"));
        
        // Get JSON data (may be null)
        String jsonData = rs.getString("entity_data");
        operation.setEntityDataJson(jsonData);
        
        // Get timestamp
        Timestamp timestamp = rs.getTimestamp("operation_timestamp");
        if (timestamp != null) {
            operation.setOperationTimestamp(timestamp.toLocalDateTime());
        }
        
        // Get user_id (may be null)
        int userId = rs.getInt("user_id");
        if (!rs.wasNull()) {
            operation.setUserId(userId);
        }
        
        return operation;
    }
}

