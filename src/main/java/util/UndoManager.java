package util;

import dao.ContactDAO;
import dao.UserDAO;
import model.Contact;
import model.User;

import java.util.Stack;

/**
 * Manages undo operations for contact and user add, update, and delete actions.
 * 
 * <p>This class maintains a stack of undoable operations. Each operation stores
 * the necessary information to reverse the action:
 * - ADD: stores the entity ID to delete
 * - UPDATE: stores the old entity state to restore
 * - DELETE: stores the deleted entity to recreate
 * 
 * <p>The undo mechanism works by:
 * 1. Recording operations before they execute (for UPDATE/DELETE) or after (for ADD)
 * 2. Storing the reverse operation data
 * 3. Executing the reverse operation when undo() is called
 * 
 * @author sarah nauman
 */
public class UndoManager {
    
    /**
     * Represents the type of operation that can be undone.
     */
    public enum OperationType {
        ADD,    // Entity was added - undo by deleting it
        UPDATE, // Entity was updated - undo by restoring old state
        DELETE  // Entity was deleted - undo by recreating it
    }
    
    /**
     * Represents the entity type being managed.
     */
    private enum EntityType {
        CONTACT,
        USER
    }
    
    /**
     * Represents a single undoable operation.
     */
    private static class UndoOperation {
        final OperationType type;
        final EntityType entityType;
        final Object entityData; // For UPDATE: old state, for DELETE: deleted entity, for ADD: null (use entityId)
        final int entityId;      // For ADD: new entity ID, for UPDATE/DELETE: entity ID
        
        UndoOperation(OperationType type, EntityType entityType, Object entityData, int entityId) {
            this.type = type;
            this.entityType = entityType;
            this.entityData = entityData;
            this.entityId = entityId;
        }
    }
    
    private final Stack<UndoOperation> undoStack;
    private final ContactDAO contactDAO;
    private final UserDAO userDAO;
    
    /**
     * Creates a new UndoManager instance for Contact operations.
     * 
     * @param contactDAO the ContactDAO instance to use for undo operations
     */
    public UndoManager(ContactDAO contactDAO) {
        this.undoStack = new Stack<>();
        this.contactDAO = contactDAO;
        this.userDAO = null;
    }
    
    /**
     * Creates a new UndoManager instance for User operations.
     * 
     * @param userDAO the UserDAO instance to use for undo operations
     */
    public UndoManager(UserDAO userDAO) {
        this.undoStack = new Stack<>();
        this.contactDAO = null;
        this.userDAO = userDAO;
    }
    
    /**
     * Records an ADD operation for a contact.
     * Should be called after a contact is successfully created.
     * 
     * @param contactId the ID of the newly created contact
     */
    public void recordAdd(int contactId) {
        if (contactDAO == null) {
            throw new IllegalStateException("UndoManager not initialized for Contact operations");
        }
        undoStack.push(new UndoOperation(OperationType.ADD, EntityType.CONTACT, null, contactId));
    }
    
    /**
     * Records an ADD operation for a user.
     * Should be called after a user is successfully created.
     * 
     * @param userId the ID of the newly created user
     */
    public void recordUserAdd(int userId) {
        if (userDAO == null) {
            throw new IllegalStateException("UndoManager not initialized for User operations");
        }
        undoStack.push(new UndoOperation(OperationType.ADD, EntityType.USER, null, userId));
    }
    
    /**
     * Records an UPDATE operation for a contact.
     * Should be called before updating a contact, with the old state.
     * 
     * @param oldContact the contact state before the update
     */
    public void recordUpdate(Contact oldContact) {
        if (contactDAO == null) {
            throw new IllegalStateException("UndoManager not initialized for Contact operations");
        }
        if (oldContact != null) {
            Contact copy = copyContact(oldContact);
            undoStack.push(new UndoOperation(OperationType.UPDATE, EntityType.CONTACT, copy, oldContact.getContactId()));
        }
    }
    
    /**
     * Records an UPDATE operation for a user.
     * Should be called before updating a user, with the old state.
     * 
     * @param oldUser the user state before the update
     */
    public void recordUpdate(User oldUser) {
        if (userDAO == null) {
            throw new IllegalStateException("UndoManager not initialized for User operations");
        }
        if (oldUser != null) {
            User copy = copyUser(oldUser);
            undoStack.push(new UndoOperation(OperationType.UPDATE, EntityType.USER, copy, oldUser.getUserId()));
        }
    }
    
    /**
     * Records a DELETE operation for a contact.
     * Should be called before deleting a contact, with the contact to be deleted.
     * 
     * @param contactToDelete the contact that will be deleted
     */
    public void recordDelete(Contact contactToDelete) {
        if (contactDAO == null) {
            throw new IllegalStateException("UndoManager not initialized for Contact operations");
        }
        if (contactToDelete != null) {
            Contact copy = copyContact(contactToDelete);
            undoStack.push(new UndoOperation(OperationType.DELETE, EntityType.CONTACT, copy, contactToDelete.getContactId()));
        }
    }
    
    /**
     * Records a DELETE operation for a user.
     * Should be called before deleting a user, with the user to be deleted.
     * 
     * @param userToDelete the user that will be deleted
     */
    public void recordDelete(User userToDelete) {
        if (userDAO == null) {
            throw new IllegalStateException("UndoManager not initialized for User operations");
        }
        if (userToDelete != null) {
            User copy = copyUser(userToDelete);
            undoStack.push(new UndoOperation(OperationType.DELETE, EntityType.USER, copy, userToDelete.getUserId()));
        }
    }
    
    /**
     * Undoes the most recent operation.
     * 
     * @return true if undo was successful, false if there was nothing to undo or undo failed
     */
    public boolean undo() {
        if (undoStack.isEmpty()) {
            return false;
        }
        
        UndoOperation op = undoStack.pop();
        
        try {
            switch (op.type) {
                case ADD:
                    if (op.entityType == EntityType.CONTACT) {
                        return contactDAO.delete(op.entityId);
                    } else {
                        return userDAO.delete(op.entityId);
                    }
                    
                case UPDATE:
                    if (op.entityType == EntityType.CONTACT) {
                        return contactDAO.update((Contact) op.entityData);
                    } else {
                        return userDAO.update((User) op.entityData);
                    }
                    
                case DELETE:
                    if (op.entityType == EntityType.CONTACT) {
                        Contact restored = copyContact((Contact) op.entityData);
                        restored.setContactId(0); // Reset ID so it gets a new one
                        return contactDAO.create(restored);
                    } else {
                        User restored = copyUser((User) op.entityData);
                        restored.setUserId(0); // Reset ID so it gets a new one
                        return userDAO.create(restored);
                    }
                    
                default:
                    return false;
            }
        } catch (Exception e) {
            System.out.println("UndoManager Error: Failed to undo operation - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Checks if there is an operation available to undo.
     * 
     * @return true if undo is available, false otherwise
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
    
    /**
     * Clears all undo history.
     */
    public void clear() {
        undoStack.clear();
    }
    
    /**
     * Gets the number of operations available to undo.
     * 
     * @return the size of the undo stack
     */
    public int getUndoCount() {
        return undoStack.size();
    }
    
    /**
     * Creates a deep copy of a Contact object to avoid reference issues.
     * 
     * @param original the contact to copy
     * @return a new Contact object with the same data
     */
    private Contact copyContact(Contact original) {
        Contact copy = new Contact();
        copy.setContactId(original.getContactId());
        copy.setFirstName(original.getFirstName());
        copy.setMiddleName(original.getMiddleName());
        copy.setLastName(original.getLastName());
        copy.setNickname(original.getNickname());
        copy.setPhonePrimary(original.getPhonePrimary());
        copy.setPhoneSecondary(original.getPhoneSecondary());
        copy.setEmail(original.getEmail());
        copy.setLinkedinUrl(original.getLinkedinUrl());
        copy.setBirthDate(original.getBirthDate());
        return copy;
    }
    
    /**
     * Creates a deep copy of a User object to avoid reference issues.
     * 
     * @param original the user to copy
     * @return a new User object with the same data
     */
    private User copyUser(User original) {
        User copy = new User();
        copy.setUserId(original.getUserId());
        copy.setUsername(original.getUsername());
        copy.setPasswordHash(original.getPasswordHash());
        copy.setName(original.getName());
        copy.setSurname(original.getSurname());
        copy.setRole(original.getRole());
        return copy;
    }
}
