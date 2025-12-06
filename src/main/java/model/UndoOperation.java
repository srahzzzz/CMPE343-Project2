package model;

import java.time.LocalDateTime;

/**
 * Represents an undoable operation stored in the database.
 * 
 * <p>This model maps to the 'undo_operations' table and stores information
 * about operations (ADD, UPDATE, DELETE) that can be undone.
 * @author sarah nauman
 */
public class UndoOperation {
    
    private int operationId;
    private OperationType operationType;
    private EntityType entityType;
    private int entityId;
    private String entityDataJson;  // JSON string containing entity data
    private LocalDateTime operationTimestamp;
    private Integer userId;  // Optional: which user performed the operation
    
    /**
     * Enum representing the type of operation.
     */
    public enum OperationType {
        ADD,    // Entity was added - undo by deleting it
        UPDATE, // Entity was updated - undo by restoring old state
        DELETE  // Entity was deleted - undo by recreating it
    }
    
    /**
     * Enum representing the type of entity.
     */
    public enum EntityType {
        CONTACT,
        USER
    }
    
    // Getters and Setters
    /** @return the unique operation identifier */
    public int getOperationId() { return operationId; }
    /** @param id the unique operation identifier */
    public void setOperationId(int id) { this.operationId = id; }
    
    /** @return the operation type (ADD, UPDATE, DELETE) */
    public OperationType getOperationType() { return operationType; }
    /** @param type the operation type */
    public void setOperationType(OperationType type) { this.operationType = type; }
    
    /** @return the entity type (CONTACT, USER) */
    public EntityType getEntityType() { return entityType; }
    /** @param type the entity type */
    public void setEntityType(EntityType type) { this.entityType = type; }
    
    /** @return the ID of the entity affected by this operation */
    public int getEntityId() { return entityId; }
    /** @param id the ID of the entity affected */
    public void setEntityId(int id) { this.entityId = id; }
    
    /** @return the JSON string containing entity data (old state for UPDATE/DELETE, null for ADD) */
    public String getEntityDataJson() { return entityDataJson; }
    /** @param json the JSON string containing entity data */
    public void setEntityDataJson(String json) { this.entityDataJson = json; }
    
    /** @return the timestamp when the operation occurred */
    public LocalDateTime getOperationTimestamp() { return operationTimestamp; }
    /** @param timestamp the timestamp when the operation occurred */
    public void setOperationTimestamp(LocalDateTime timestamp) { this.operationTimestamp = timestamp; }
    
    /** @return the user ID who performed the operation (may be null) */
    public Integer getUserId() { return userId; }
    /** @param id the user ID who performed the operation */
    public void setUserId(Integer id) { this.userId = id; }
}



