package service;

import dao.ContactDAO;
import dao.UndoOperationDAO;
import dao.UserDAO;
import model.Contact;
import model.UndoOperation;
import model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Service for managing undo operations.
 * 
 * <p>Handles recording operations, listing them, and undoing specific operations.
 * Converts Contact and User objects to/from JSON for storage.
 * @author sarah nauman
 */
public class UndoService {
    
    private final UndoOperationDAO undoOperationDAO;
    private final ContactDAO contactDAO;
    private final UserDAO userDAO;
    
    /**
     * Creates a new UndoService.
     * 
     * @param undoOperationDAO the DAO for undo operations
     * @param contactDAO the DAO for contact operations
     * @param userDAO the DAO for user operations
     */
    public UndoService(UndoOperationDAO undoOperationDAO, ContactDAO contactDAO, UserDAO userDAO) {
        this.undoOperationDAO = undoOperationDAO;
        this.contactDAO = contactDAO;
        this.userDAO = userDAO;
    }
    
    /**
     * Records an ADD operation for a contact.
     * Should be called after a contact is successfully created.
     * 
     * @param contactId the ID of the newly created contact
     * @param userId optional user ID who performed the operation
     */
    public void recordContactAdd(int contactId, Integer userId) {
        UndoOperation operation = new UndoOperation();
        operation.setOperationType(UndoOperation.OperationType.ADD);
        operation.setEntityType(UndoOperation.EntityType.CONTACT);
        operation.setEntityId(contactId);
        operation.setEntityDataJson(null); // ADD operations don't need entity data
        operation.setUserId(userId);
        undoOperationDAO.create(operation);
    }
    
    /**
     * Records an UPDATE operation for a contact.
     * Should be called before updating a contact, with the old state.
     * 
     * @param oldContact the contact state before the update
     * @param userId optional user ID who performed the operation
     */
    public void recordContactUpdate(Contact oldContact, Integer userId) {
        if (oldContact != null) {
            UndoOperation operation = new UndoOperation();
            operation.setOperationType(UndoOperation.OperationType.UPDATE);
            operation.setEntityType(UndoOperation.EntityType.CONTACT);
            operation.setEntityId(oldContact.getContactId());
            operation.setEntityDataJson(contactToJson(oldContact));
            operation.setUserId(userId);
            undoOperationDAO.create(operation);
        }
    }
    
    /**
     * Records a DELETE operation for a contact.
     * Should be called before deleting a contact, with the contact to be deleted.
     * 
     * @param contactToDelete the contact that will be deleted
     * @param userId optional user ID who performed the operation
     */
    public void recordContactDelete(Contact contactToDelete, Integer userId) {
        if (contactToDelete != null) {
            UndoOperation operation = new UndoOperation();
            operation.setOperationType(UndoOperation.OperationType.DELETE);
            operation.setEntityType(UndoOperation.EntityType.CONTACT);
            operation.setEntityId(contactToDelete.getContactId());
            operation.setEntityDataJson(contactToJson(contactToDelete));
            operation.setUserId(userId);
            undoOperationDAO.create(operation);
        }
    }
    
    /**
     * Records an ADD operation for a user.
     * Should be called after a user is successfully created.
     * 
     * @param userId the ID of the newly created user
     */
    public void recordUserAdd(int userId) {
        UndoOperation operation = new UndoOperation();
        operation.setOperationType(UndoOperation.OperationType.ADD);
        operation.setEntityType(UndoOperation.EntityType.USER);
        operation.setEntityId(userId);
        operation.setEntityDataJson(null); // ADD operations don't need entity data
        operation.setUserId(userId);
        undoOperationDAO.create(operation);
    }
    
    /**
     * Records an UPDATE operation for a user.
     * Should be called before updating a user, with the old state.
     * 
     * @param oldUser the user state before the update
     */
    public void recordUserUpdate(User oldUser) {
        if (oldUser != null) {
            UndoOperation operation = new UndoOperation();
            operation.setOperationType(UndoOperation.OperationType.UPDATE);
            operation.setEntityType(UndoOperation.EntityType.USER);
            operation.setEntityId(oldUser.getUserId());
            operation.setEntityDataJson(userToJson(oldUser));
            operation.setUserId(oldUser.getUserId());
            undoOperationDAO.create(operation);
        }
    }
    
    /**
     * Records a DELETE operation for a user.
     * Should be called before deleting a user, with the user to be deleted.
     * 
     * @param userToDelete the user that will be deleted
     */
    public void recordUserDelete(User userToDelete) {
        if (userToDelete != null) {
            UndoOperation operation = new UndoOperation();
            operation.setOperationType(UndoOperation.OperationType.DELETE);
            operation.setEntityType(UndoOperation.EntityType.USER);
            operation.setEntityId(userToDelete.getUserId());
            operation.setEntityDataJson(userToJson(userToDelete));
            operation.setUserId(userToDelete.getUserId());
            undoOperationDAO.create(operation);
        }
    }
    
    /**
     * Gets all undo operations, ordered by timestamp (newest first).
     * 
     * @return list of all undo operations
     */
    public List<UndoOperation> getAllOperations() {
        return undoOperationDAO.findAll();
    }
    
    /**
     * Gets the number of available undo operations.
     * 
     * @return the count of operations
     */
    public int getOperationCount() {
        return undoOperationDAO.getCount();
    }
    
    /**
     * Undoes a specific operation by its operation ID.
     * 
     * @param operationId the ID of the operation to undo
     * @return true if undo was successful, false otherwise
     */
    public boolean undoOperation(int operationId) {
        UndoOperation operation = undoOperationDAO.findById(operationId);
        if (operation == null) {
            return false;
        }
        
        try {
            boolean success = false;
            
            switch (operation.getOperationType()) {
                case ADD:
                    // Undo ADD by deleting the entity
                    if (operation.getEntityType() == UndoOperation.EntityType.CONTACT) {
                        success = contactDAO.delete(operation.getEntityId());
                    } else {
                        success = userDAO.delete(operation.getEntityId());
                    }
                    break;
                    
                case UPDATE:
                    // Undo UPDATE by restoring the old state
                    if (operation.getEntityType() == UndoOperation.EntityType.CONTACT) {
                        Contact oldContact = jsonToContact(operation.getEntityDataJson());
                        if (oldContact != null) {
                            success = contactDAO.update(oldContact);
                        }
                    } else {
                        User oldUser = jsonToUser(operation.getEntityDataJson());
                        if (oldUser != null) {
                            success = userDAO.update(oldUser);
                        }
                    }
                    break;
                    
                case DELETE:
                    // Undo DELETE by recreating the entity
                    if (operation.getEntityType() == UndoOperation.EntityType.CONTACT) {
                        Contact deletedContact = jsonToContact(operation.getEntityDataJson());
                        if (deletedContact != null) {
                            deletedContact.setContactId(0); // Reset ID so it gets a new one
                            success = contactDAO.create(deletedContact);
                        }
                    } else {
                        User deletedUser = jsonToUser(operation.getEntityDataJson());
                        if (deletedUser != null) {
                            deletedUser.setUserId(0); // Reset ID so it gets a new one
                            success = userDAO.create(deletedUser);
                        }
                    }
                    break;
            }
            
            // If undo was successful, remove the operation from the list
            if (success) {
                undoOperationDAO.delete(operationId);
            }
            
            return success;
            
        } catch (Exception e) {
            System.out.println("UndoService Error: Failed to undo operation - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Converts a Contact object to a JSON string.
     * Simple manual serialization without external JSON library.
     */
    private String contactToJson(Contact contact) {
        if (contact == null) return null;
        
        StringBuilder json = new StringBuilder("{");
        json.append("\"contactId\":").append(contact.getContactId()).append(",");
        json.append("\"firstName\":\"").append(escapeJson(contact.getFirstName())).append("\",");
        json.append("\"middleName\":").append(contact.getMiddleName() == null ? "null" : "\"" + escapeJson(contact.getMiddleName()) + "\"").append(",");
        json.append("\"lastName\":\"").append(escapeJson(contact.getLastName())).append("\",");
        json.append("\"nickname\":").append(contact.getNickname() == null ? "null" : "\"" + escapeJson(contact.getNickname()) + "\"").append(",");
        json.append("\"phonePrimary\":\"").append(escapeJson(contact.getPhonePrimary())).append("\",");
        json.append("\"phoneSecondary\":").append(contact.getPhoneSecondary() == null ? "null" : "\"" + escapeJson(contact.getPhoneSecondary()) + "\"").append(",");
        json.append("\"email\":\"").append(escapeJson(contact.getEmail())).append("\",");
        json.append("\"linkedinUrl\":").append(contact.getLinkedinUrl() == null ? "null" : "\"" + escapeJson(contact.getLinkedinUrl()) + "\"").append(",");
        json.append("\"birthDate\":").append(contact.getBirthDate() == null ? "null" : "\"" + contact.getBirthDate() + "\"");
        json.append("}");
        return json.toString();
    }
    
    /**
     * Converts a JSON string to a Contact object.
     */
    private Contact jsonToContact(String json) {
        if (json == null || json.trim().isEmpty()) return null;
        
        try {
            Contact contact = new Contact();
            // Simple JSON parsing - extract values between quotes and colons
            // This is a simplified parser - for production, use a proper JSON library
            json = json.trim().replace("{", "").replace("}", "");
            String[] pairs = json.split(",");
            
            for (String pair : pairs) {
                String[] keyValue = pair.split(":", 2);
                if (keyValue.length != 2) continue;
                
                String key = keyValue[0].trim().replace("\"", "");
                String value = keyValue[1].trim();
                
                // Remove quotes if present
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }
                
                switch (key) {
                    case "contactId":
                        contact.setContactId(Integer.parseInt(value));
                        break;
                    case "firstName":
                        contact.setFirstName(unescapeJson(value));
                        break;
                    case "middleName":
                        if (!value.equals("null")) contact.setMiddleName(unescapeJson(value));
                        break;
                    case "lastName":
                        contact.setLastName(unescapeJson(value));
                        break;
                    case "nickname":
                        if (!value.equals("null")) contact.setNickname(unescapeJson(value));
                        break;
                    case "phonePrimary":
                        contact.setPhonePrimary(unescapeJson(value));
                        break;
                    case "phoneSecondary":
                        if (!value.equals("null")) contact.setPhoneSecondary(unescapeJson(value));
                        break;
                    case "email":
                        contact.setEmail(unescapeJson(value));
                        break;
                    case "linkedinUrl":
                        if (!value.equals("null")) contact.setLinkedinUrl(unescapeJson(value));
                        break;
                    case "birthDate":
                        if (!value.equals("null")) {
                            contact.setBirthDate(LocalDate.parse(value));
                        }
                        break;
                }
            }
            
            return contact;
        } catch (Exception e) {
            System.out.println("UndoService Error: Failed to parse Contact JSON - " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Converts a User object to a JSON string.
     */
    private String userToJson(User user) {
        if (user == null) return null;
        
        StringBuilder json = new StringBuilder("{");
        json.append("\"userId\":").append(user.getUserId()).append(",");
        json.append("\"username\":\"").append(escapeJson(user.getUsername())).append("\",");
        json.append("\"passwordHash\":\"").append(escapeJson(user.getPasswordHash())).append("\",");
        json.append("\"name\":\"").append(escapeJson(user.getName())).append("\",");
        json.append("\"surname\":\"").append(escapeJson(user.getSurname())).append("\",");
        json.append("\"role\":\"").append(escapeJson(user.getRole())).append("\"");
        json.append("}");
        return json.toString();
    }
    
    /**
     * Converts a JSON string to a User object.
     */
    private User jsonToUser(String json) {
        if (json == null || json.trim().isEmpty()) return null;
        
        try {
            User user = new User();
            json = json.trim().replace("{", "").replace("}", "");
            String[] pairs = json.split(",");
            
            for (String pair : pairs) {
                String[] keyValue = pair.split(":", 2);
                if (keyValue.length != 2) continue;
                
                String key = keyValue[0].trim().replace("\"", "");
                String value = keyValue[1].trim();
                
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }
                
                switch (key) {
                    case "userId":
                        user.setUserId(Integer.parseInt(value));
                        break;
                    case "username":
                        user.setUsername(unescapeJson(value));
                        break;
                    case "passwordHash":
                        user.setPasswordHash(unescapeJson(value));
                        break;
                    case "name":
                        user.setName(unescapeJson(value));
                        break;
                    case "surname":
                        user.setSurname(unescapeJson(value));
                        break;
                    case "role":
                        user.setRole(unescapeJson(value));
                        break;
                }
            }
            
            return user;
        } catch (Exception e) {
            System.out.println("UndoService Error: Failed to parse User JSON - " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Escapes special characters in JSON strings.
     */
    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
    
    /**
     * Unescapes special characters from JSON strings.
     */
    private String unescapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\\"", "\"")
                  .replace("\\\\", "\\")
                  .replace("\\n", "\n")
                  .replace("\\r", "\r")
                  .replace("\\t", "\t");
    }
}

