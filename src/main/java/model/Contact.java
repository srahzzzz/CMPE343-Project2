package model;

import java.time.LocalDate;

/**
 * Represents a contact record stored in the system.
 *
 * <p>Each Contact corresponds to one row in the 'contacts' table in mysql :)
 * Later roles (Senior, Manager) will perform CRUD on this model.
 */
public class Contact {

    private int contactId;
    private String firstName;
    private String lastName;
    private String phonePrimary;
    private String email;
    private String linkedinUrl;
    private LocalDate birthDate;

    // Getters and Setters
    public int getContactId() { return contactId; }
    public void setContactId(int id) { this.contactId = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String f) { this.firstName = f; }

    public String getLastName() { return lastName; }
    public void setLastName(String l) { this.lastName = l; }

    public String getPhonePrimary() { return phonePrimary; }
    public void setPhonePrimary(String p) { this.phonePrimary = p; }

    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }

    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String l) { this.linkedinUrl = l; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate b) { this.birthDate = b; }
}
