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
    private String middleName; 
    private String lastName;
    private String nickname;
    private String phonePrimary;
    private String phoneSecondary;
    private String email;
    private String linkedinUrl;
    private LocalDate birthDate;

    // Getters and Setters
    /** @return the unique contact identifier */
    public int getContactId() { return contactId; }
    /** @param id the unique contact identifier */
    public void setContactId(int id) { this.contactId = id; }

    /** @return the first name */
    public String getFirstName() { return firstName; }
    /** @param f the first name */
    public void setFirstName(String f) { this.firstName = f; }

    /** @return the middle name (may be null) */
    public String getMiddleName() { return middleName; }
    /** @param m the middle name (may be null) */
    public void setMiddleName(String m) { this.middleName = m; }

    /** @return the last name */
    public String getLastName() { return lastName; }
    /** @param l the last name */
    public void setLastName(String l) { this.lastName = l; }

    /** @return the nickname (may be null) */
    public String getNickname() { return nickname; }
    /** @param n the nickname (may be null) */
    public void setNickname(String n) { this.nickname = n; }

    /** @return the primary phone number */
    public String getPhonePrimary() { return phonePrimary; }
    /** @param p the primary phone number */
    public void setPhonePrimary(String p) { this.phonePrimary = p; }

    /** @return the secondary phone number (may be null) */
    public String getPhoneSecondary() { return phoneSecondary; }
    /** @param p the secondary phone number (may be null) */
    public void setPhoneSecondary(String p) { this.phoneSecondary = p; }

    /** @return the email address */
    public String getEmail() { return email; }
    /** @param e the email address */
    public void setEmail(String e) { this.email = e; }

    /** @return the LinkedIn URL (may be null) */
    public String getLinkedinUrl() { return linkedinUrl; }
    /** @param l the LinkedIn URL (may be null) */
    public void setLinkedinUrl(String l) { this.linkedinUrl = l; }

    /** @return the birth date (may be null) */
    public LocalDate getBirthDate() { return birthDate; }
    /** @param b the birth date (may be null) */
    public void setBirthDate(LocalDate b) { this.birthDate = b; }
}
