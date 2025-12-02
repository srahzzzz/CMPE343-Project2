package menu;

import dao.ContactDAO;
import dao.UserDAO;
import model.Contact;
import model.User;
import service.AuthService;
import service.ValidationUtils;
import util.ColorUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Menu for Senior Developer role.
 * 
 * <p>Senior Developers can:
 * - Change password
 * - Logout
 * - List all contacts
 * - Search by selected field or fields
 * - Sort results in ascending or descending order
 * - Update existing contact
 * - Add new contact or contacts
 * - Delete existing contact or contacts
 * </p>
 */
public class SeniorMenu extends BaseMenu {

    private final ContactDAO contactDAO;
    private final UserDAO userDAO;

    /**
     * Constructor for SeniorMenu.
     *
     * @param user the logged-in senior developer user
     */
    public SeniorMenu(User user) {
        super(user);
        this.contactDAO = new ContactDAO();
        this.userDAO = new UserDAO();
    }

    @Override
    protected void displayMenu() {
        System.out.println(ColorUtils.seniorMenuOption("1. List all contacts"));
        System.out.println(ColorUtils.seniorMenuOption("2. Search by field"));
        System.out.println(ColorUtils.seniorMenuOption("3. Search by multiple fields"));
        System.out.println(ColorUtils.seniorMenuOption("4. Sort contacts"));
        System.out.println(ColorUtils.seniorMenuOption("5. Update contact"));
        System.out.println(ColorUtils.seniorMenuOption("6. Add new contact(s)"));
        System.out.println(ColorUtils.seniorMenuOption("7. Delete contact(s)"));
        System.out.println(ColorUtils.seniorMenuOption("8. Change password"));
        System.out.println(ColorUtils.seniorMenuOption("0. Logout"));
    }

    @Override
    protected void handleOption(int choice) {
        switch (choice) {
            case 1:
                listAllContacts();
                break;
            case 2:
                searchByField();
                break;
            case 3:
                searchByMultipleFields();
                break;
            case 4:
                sortContacts();
                break;
            case 5:
                updateContact();
                break;
            case 6:
                addContacts();
                break;
            case 7:
                deleteContacts();
                break;
            case 8:
                changePassword();
                break;
            default:
                System.out.println("\n" + ColorUtils.error("Invalid option! Please try again."));
        }
    }

    /**
     * Lists all contacts in the database.
     */
    private void listAllContacts() {
        System.out.println("\n" + ColorUtils.header("--- List All Contacts ---"));
        System.out.println(ColorUtils.header("======================================="));
        
        List<Contact> contacts = contactDAO.findAll();
        
        if (contacts.isEmpty()) {
            System.out.println(ColorUtils.warning("No contacts found in the database."));
        } else {
            displayContactsTable(contacts);
            System.out.println(ColorUtils.info("Total contacts: " + contacts.size()));
        }
        
        System.out.println("\n" + ColorUtils.seniorPrompt("Press Enter to continue..."));
        scanner.nextLine();
    }

    /**
     * Search by a single selected field.
     */
    private void searchByField() {
        System.out.println("\n" + ColorUtils.header("--- Search by Field ---"));
        System.out.println(ColorUtils.header("======================================="));
        System.out.println(ColorUtils.seniorMenuOption("1. Search by First Name"));
        System.out.println(ColorUtils.seniorMenuOption("2. Search by Last Name"));
        System.out.println(ColorUtils.seniorMenuOption("3. Search by Phone Number"));
        System.out.println(ColorUtils.seniorMenuOption("4. Search by Email"));
        System.out.print("\n" + ColorUtils.seniorPrompt("Select search type: "));

        String choiceInput = scanner.nextLine().trim();
        Integer choice = BaseMenu.safeParseInt(choiceInput);

        if (choice == null || choice < 1 || choice > 4) {
            System.out.println(ColorUtils.error("Invalid choice. Please select 1, 2, 3, or 4."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
            scanner.nextLine();
            return;
        }

        List<Contact> results;

        switch (choice) {
            case 1:
                System.out.print(ColorUtils.seniorPrompt("Enter first name (or partial): "));
                String firstName = scanner.nextLine().trim();
                if (firstName.isEmpty()) {
                    System.out.println(ColorUtils.error("First name cannot be empty."));
                    System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
                    scanner.nextLine();
                    return;
                }
                results = contactDAO.searchByFirstName(firstName);
                break;

            case 2:
                System.out.print(ColorUtils.seniorPrompt("Enter last name (or partial): "));
                String lastName = scanner.nextLine().trim();
                if (lastName.isEmpty()) {
                    System.out.println(ColorUtils.error("Last name cannot be empty."));
                    System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
                    scanner.nextLine();
                    return;
                }
                results = contactDAO.searchByLastName(lastName);
                break;

            case 3:
                System.out.print(ColorUtils.seniorPrompt("Enter phone number (or partial): "));
                String phone = scanner.nextLine().trim();
                if (phone.isEmpty()) {
                    System.out.println(ColorUtils.error("Phone number cannot be empty."));
                    System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
                    scanner.nextLine();
                    return;
                }
                results = contactDAO.searchByPhone(phone);
                break;

            case 4:
                System.out.print(ColorUtils.seniorPrompt("Enter email (or partial): "));
                String email = scanner.nextLine().trim();
                if (email.isEmpty()) {
                    System.out.println(ColorUtils.error("Email cannot be empty."));
                    System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
                    scanner.nextLine();
                    return;
                }
                Map<String, String> emailCriteria = new HashMap<>();
                emailCriteria.put("email", email);
                results = contactDAO.searchByMultipleFields(emailCriteria);
                break;

            default:
                results = List.of();
        }

        if (results.isEmpty()) {
            System.out.println("\n" + ColorUtils.warning("No contacts found matching your search criteria."));
        } else {
            System.out.println("\n" + ColorUtils.header("Search Results:"));
            displayContactsTable(results);
            System.out.println(ColorUtils.success("Found " + results.size() + " contact(s)."));
        }
        
        System.out.println("\n" + ColorUtils.seniorPrompt("Press Enter to continue..."));
        scanner.nextLine();
    }

    /**
     * Search by multiple selected fields combined with AND logic.
     */
    private void searchByMultipleFields() {
        System.out.println("\n" + ColorUtils.header("--- Search by Multiple Fields ---"));
        System.out.println(ColorUtils.header("======================================="));
        System.out.println(ColorUtils.info("Enter search criteria (press Enter to skip a field):\n"));
        
        Map<String, String> criteria = new HashMap<>();
        
        System.out.print(ColorUtils.seniorPrompt("First Name: "));
        String firstName = scanner.nextLine().trim();
        if (!firstName.isEmpty()) {
            criteria.put("first_name", firstName);
        }

        System.out.print(ColorUtils.seniorPrompt("Last Name: "));
        String lastName = scanner.nextLine().trim();
        if (!lastName.isEmpty()) {
            criteria.put("last_name", lastName);
        }
        
        System.out.print(ColorUtils.seniorPrompt("Phone Number: "));
        String phone = scanner.nextLine().trim();
        if (!phone.isEmpty()) {
            criteria.put("phone", phone);
        }
        
        System.out.print(ColorUtils.seniorPrompt("Email (partial): "));
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) {
            criteria.put("email", email);
        }
        
        System.out.print(ColorUtils.seniorPrompt("Birth Month (1-12): "));
        String birthMonth = scanner.nextLine().trim();
        if (!birthMonth.isEmpty()) {
            Integer month = BaseMenu.safeParseInt(birthMonth);
            if (month != null && month >= 1 && month <= 12) {
                criteria.put("birth_month", month.toString());
            } else {
                System.out.println(ColorUtils.warning("Invalid month. Skipping birth month filter."));
            }
        }
        
        if (criteria.isEmpty()) {
            System.out.println("\n" + ColorUtils.error("No search criteria provided. Please enter at least one field."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
            scanner.nextLine();
            return;
        }

        List<Contact> results = contactDAO.searchByMultipleFields(criteria);

        if (results.isEmpty()) {
            System.out.println("\n" + ColorUtils.warning("No contacts found matching all your search criteria."));
        } else {
            System.out.println("\n" + ColorUtils.header("Search Results:"));
            displayContactsTable(results);
            System.out.println(ColorUtils.success("Found " + results.size() + " contact(s) matching all criteria."));
        }
        
        System.out.println("\n" + ColorUtils.seniorPrompt("Press Enter to continue..."));
        scanner.nextLine();
    }

    /**
     * Sort contacts by a user-selected field and order.
     */
    private void sortContacts() {
        System.out.println("\n" + ColorUtils.header("--- Sort Contacts ---"));
        System.out.println(ColorUtils.header("======================================="));
        System.out.println(ColorUtils.info("Select field to sort by:"));
        System.out.println(ColorUtils.seniorMenuOption("1. First Name"));
        System.out.println(ColorUtils.seniorMenuOption("2. Last Name"));
        System.out.println(ColorUtils.seniorMenuOption("3. Email"));
        System.out.println(ColorUtils.seniorMenuOption("4. Birth Date"));
        System.out.println(ColorUtils.seniorMenuOption("5. Phone Primary"));
        System.out.print("\n" + ColorUtils.seniorPrompt("Enter your choice: "));
        
        String choiceInput = scanner.nextLine().trim();
        Integer choice = BaseMenu.safeParseInt(choiceInput);
        
        if (choice == null || choice < 1 || choice > 5) {
            System.out.println(ColorUtils.error("Invalid choice."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
            scanner.nextLine();
            return;
        }

        String field;
        switch (choice) {
            case 1: field = "first_name"; break;
            case 2: field = "last_name"; break;
            case 3: field = "email"; break;
            case 4: field = "birth_date"; break;
            case 5: field = "phone_primary"; break;
            default: field = "contact_id";
        }

        System.out.println("\n" + ColorUtils.info("Sort order:"));
        if ("birth_date".equals(field)) {
            System.out.println(ColorUtils.seniorMenuOption("1. Ascending (oldest to youngest)"));
            System.out.println(ColorUtils.seniorMenuOption("2. Descending (youngest to oldest)"));
        } else {
            System.out.println(ColorUtils.seniorMenuOption("1. Ascending (A-Z, 1-9)"));
            System.out.println(ColorUtils.seniorMenuOption("2. Descending (Z-A, 9-1)"));
        }
        System.out.print(ColorUtils.seniorPrompt("Enter your choice: "));
        
        String orderInput = scanner.nextLine().trim();
        Integer orderChoice = BaseMenu.safeParseInt(orderInput);
        
        boolean ascending = orderChoice == null || orderChoice != 2;
        
        List<Contact> contacts = contactDAO.findAllSorted(field, ascending);
        
        if (contacts.isEmpty()) {
            System.out.println(ColorUtils.warning("No contacts found."));
        } else {
            System.out.println("\n" + ColorUtils.header("Sorted Contacts:"));
            displayContactsTable(contacts);
            System.out.println(ColorUtils.info("Total contacts: " + contacts.size()));
        }
        
        System.out.println("\n" + ColorUtils.seniorPrompt("Press Enter to continue..."));
        scanner.nextLine();
    }

    /**
     * Update an existing contact by ID.
     */
    private void updateContact() {
        System.out.println("\n" + ColorUtils.header("--- Update Contact ---"));
        System.out.println(ColorUtils.header("======================================="));

        System.out.print(ColorUtils.seniorPrompt("Enter Contact ID to update: "));
        String idInput = scanner.nextLine().trim();
        Integer contactId = BaseMenu.safeParseInt(idInput);

        if (contactId == null) {
            System.out.println(ColorUtils.error("Invalid contact ID. Please enter a valid number."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
            scanner.nextLine();
            return;
        }

        Contact contact = contactDAO.findById(contactId);
        if (contact == null) {
            System.out.println(ColorUtils.error("Contact with ID " + contactId + " not found."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
            scanner.nextLine();
            return;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("\n" + ColorUtils.header("Current Contact Information:"));
        displayContactsTable(List.of(contact));
        System.out.println("\n" + ColorUtils.info("Enter new values (press Enter to keep current value):"));

        // First name - letters only
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("First Name [" + nullToEmpty(contact.getFirstName()) + "] (letters only): "));
            String firstName = scanner.nextLine().trim();

            if (firstName.isEmpty()) {
                break; // keep existing
            }

            if (!ValidationUtils.isValidName(firstName)) {
                System.out.println(ColorUtils.error("Invalid first name. Use letters only (you may use spaces, '-' or ')."));
                continue;
            }

            contact.setFirstName(firstName);
            break;
        }

        // Middle name - optional, letters only
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Middle Name [" + nullToEmpty(contact.getMiddleName()) + "] (optional, letters only or Enter to keep): "));
            String middleName = scanner.nextLine().trim();

            if (middleName.isEmpty()) {
                break; // keep existing
            }

            if (!ValidationUtils.isValidName(middleName)) {
                System.out.println(ColorUtils.error("Invalid middle name. Use letters only (you may use spaces, '-' or '), or press Enter to keep current value."));
                continue;
            }

            contact.setMiddleName(middleName);
            break;
        }

        // Last name - letters only
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Last Name [" + nullToEmpty(contact.getLastName()) + "] (letters only): "));
            String lastName = scanner.nextLine().trim();

            if (lastName.isEmpty()) {
                break; // keep existing
            }

            if (!ValidationUtils.isValidName(lastName)) {
                System.out.println(ColorUtils.error("Invalid last name. Use letters only (you may use spaces, '-' or ')."));
                continue;
            }

            contact.setLastName(lastName);
            break;
        }

        // Nickname - optional
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Nickname [" + nullToEmpty(contact.getNickname()) + "] (optional, letters/digits/-/_ or Enter to keep): "));
            String nickname = scanner.nextLine().trim();

            if (nickname.isEmpty()) {
                break; // keep existing
            }

            if (!ValidationUtils.isValidNickname(nickname)) {
                System.out.println(ColorUtils.error("Invalid nickname. Allowed characters: letters, digits, spaces, '-' and '_', or press Enter to keep current value."));
                continue;
            }

            contact.setNickname(nickname);
            break;
        }

        // Phone 1 - digits only, at least 10 digits
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Phone 1 [" + nullToEmpty(contact.getPhonePrimary()) + "] (digits only, min 10): "));
            String phone1 = scanner.nextLine().trim();

            if (phone1.isEmpty()) {
                break; // keep existing
            }

            if (!ValidationUtils.isValidPhone(phone1)) {
                System.out.println(ColorUtils.error("Invalid phone number. Use digits only and at least 10 digits."));
                continue;
            }

            contact.setPhonePrimary(phone1);
            break;
        }

        // Phone 2 - optional
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Phone 2 [" + nullToEmpty(contact.getPhoneSecondary()) + "] (optional, digits only, min 10 or Enter to keep): "));
            String phone2 = scanner.nextLine().trim();

            if (phone2.isEmpty()) {
                break; // keep existing
            }

            if (!ValidationUtils.isValidPhone(phone2)) {
                System.out.println(ColorUtils.error("Invalid phone number. Use digits only and at least 10 digits, or press Enter to keep the current value."));
                continue;
            }

            contact.setPhoneSecondary(phone2);
            break;
        }

        // Email - must have basic structure
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Email [" + nullToEmpty(contact.getEmail()) + "]: "));
            String email = scanner.nextLine().trim();

            if (email.isEmpty()) {
                break; // keep existing
            }

            if (!ValidationUtils.isValidEmail(email)) {
                System.out.println(ColorUtils.error("Invalid email. It must contain '@', a domain with '.', and no spaces (e.g., user@example.com)."));
                continue;
            }

            contact.setEmail(email);
            break;
        }

        System.out.print(ColorUtils.seniorPrompt("LinkedIn URL [" + nullToEmpty(contact.getLinkedinUrl()) + "]: "));
        String linkedin = scanner.nextLine().trim();
        if (!linkedin.isEmpty()) {
            contact.setLinkedinUrl(linkedin);
        }

        String currentBirthDate = contact.getBirthDate() != null
                ? contact.getBirthDate().format(dateFormatter)
                : "N/A";

        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Birth Date (" + currentBirthDate + ") [yyyy-MM-dd or Enter to keep]: "));
            String birthDateInput = scanner.nextLine().trim();

            if (birthDateInput.isEmpty()) {
                // Keep existing value
                break;
            }

            // Enforce strict yyyy-MM-dd pattern (e.g., 2002-05-05)
            if (!birthDateInput.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println(ColorUtils.error("Invalid format. Please enter the date exactly as yyyy-MM-dd (e.g., 2002-05-05)."));
                continue;
            }

            try {
                LocalDate birthDate = LocalDate.parse(birthDateInput, dateFormatter);
                if (!ValidationUtils.isValidPastOrToday(birthDate)) {
                    System.out.println(ColorUtils.error("Birth date cannot be in the future. Please enter a past or today's date."));
                    continue;
                }
                contact.setBirthDate(birthDate);
                break;
            } catch (DateTimeParseException e) {
                System.out.println(ColorUtils.error("Invalid date value. Please enter a real calendar date in yyyy-MM-dd format."));
            }
        }

        if (contactDAO.update(contact)) {
            System.out.println("\n" + ColorUtils.success("Contact updated successfully!"));
        } else {
            System.out.println("\n" + ColorUtils.error("Failed to update contact."));
        }

        System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
        scanner.nextLine();
    }

    /**
     * Add one or more new contacts.
     */
    private void addContacts() {
        System.out.println("\n" + ColorUtils.header("--- Add New Contact(s) ---"));
        System.out.println(ColorUtils.header("======================================="));

        boolean addMore = true;
        while (addMore) {
            addSingleContact();

            System.out.print("\n" + ColorUtils.seniorPrompt("Do you want to add another contact? (y/n): "));
            String again = scanner.nextLine().trim().toLowerCase();
            addMore = "y".equals(again) || "yes".equals(again);
        }
    }

    /**
     * Helper to add a single contact.
     */
    private void addSingleContact() {
        Contact contact = new Contact();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // First Name (required, letters only)
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("First Name (required, letters only): "));
            String firstName = scanner.nextLine().trim();

            if (firstName.isEmpty()) {
                System.out.println(ColorUtils.error("First Name cannot be empty."));
                continue;
            }

            if (!ValidationUtils.isValidName(firstName)) {
                System.out.println(ColorUtils.error("Invalid first name. Use letters only (you may use spaces, '-' or ')."));
                continue;
            }

            contact.setFirstName(firstName);
            break;
        }

        // Middle Name (optional, letters only)
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Middle Name (optional, letters only or Enter to skip): "));
            String middleName = scanner.nextLine().trim();

            if (middleName.isEmpty()) {
                contact.setMiddleName(null);
                break;
            }

            if (!ValidationUtils.isValidName(middleName)) {
                System.out.println(ColorUtils.error("Invalid middle name. Use letters only (you may use spaces, '-' or '), or press Enter to skip."));
                continue;
            }

            contact.setMiddleName(middleName);
            break;
        }

        // Last Name (required, letters only)
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Last Name (required, letters only): "));
            String lastName = scanner.nextLine().trim();

            if (lastName.isEmpty()) {
                System.out.println(ColorUtils.error("Last Name cannot be empty."));
                continue;
            }

            if (!ValidationUtils.isValidName(lastName)) {
                System.out.println(ColorUtils.error("Invalid last name. Use letters only (you may use spaces, '-' or ')."));
                continue;
            }

            contact.setLastName(lastName);
            break;
        }

        // Nickname (optional, relaxed)
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Nickname (optional, letters/digits/-/_ or Enter to skip): "));
            String nickname = scanner.nextLine().trim();

            if (nickname.isEmpty()) {
                contact.setNickname(null);
                break;
            }

            if (!ValidationUtils.isValidNickname(nickname)) {
                System.out.println(ColorUtils.error("Invalid nickname. Allowed characters: letters, digits, spaces, '-' and '_', or press Enter to skip."));
                continue;
            }

            contact.setNickname(nickname);
            break;
        }

        // Phone 1 (required, digits only, min 10)
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Phone 1 (required, digits only, min 10): "));
            String phone1 = scanner.nextLine().trim();

            if (phone1.isEmpty()) {
                System.out.println(ColorUtils.error("Primary phone cannot be empty."));
                continue;
            }

            if (!ValidationUtils.isValidPhone(phone1)) {
                System.out.println(ColorUtils.error("Invalid phone number. Use digits only and at least 10 digits."));
                continue;
            }

            contact.setPhonePrimary(phone1);
            break;
        }

        // Phone 2 (optional, digits only, min 10)
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Phone 2 (optional, digits only, min 10 or Enter to skip): "));
            String phone2 = scanner.nextLine().trim();

            if (phone2.isEmpty()) {
                contact.setPhoneSecondary(null);
                break;
            }

            if (!ValidationUtils.isValidPhone(phone2)) {
                System.out.println(ColorUtils.error("Invalid phone number. Use digits only and at least 10 digits, or press Enter to skip."));
                continue;
            }

            contact.setPhoneSecondary(phone2);
            break;
        }

        // Email (required)
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Email (required): "));
            String email = scanner.nextLine().trim();

            if (email.isEmpty()) {
                System.out.println(ColorUtils.error("Email cannot be empty."));
                continue;
            }

            if (!ValidationUtils.isValidEmail(email)) {
                System.out.println(ColorUtils.error("Invalid email. It must contain '@', a domain with '.', and no spaces (e.g., user@example.com)."));
                continue;
            }

            contact.setEmail(email);
            break;
        }

        System.out.print(ColorUtils.seniorPrompt("LinkedIn URL (optional): "));
        String linkedin = scanner.nextLine().trim();
        contact.setLinkedinUrl(linkedin.isEmpty() ? null : linkedin);

        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Birth Date (optional, yyyy-MM-dd): "));
            String birthDateInput = scanner.nextLine().trim();

            if (birthDateInput.isEmpty()) {
                contact.setBirthDate(null);
                break;
            }

            // Enforce strict yyyy-MM-dd pattern (e.g., 2002-05-05)
            if (!birthDateInput.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println(ColorUtils.error("Invalid format. Please enter the date exactly as yyyy-MM-dd (e.g., 2002-05-05), or press Enter to skip."));
                continue;
            }

            try {
                LocalDate birthDate = LocalDate.parse(birthDateInput, dateFormatter);
                if (!ValidationUtils.isValidPastOrToday(birthDate)) {
                    System.out.println(ColorUtils.error("Birth date cannot be in the future. Please enter a past or today's date, or press Enter to skip."));
                    continue;
                }
                contact.setBirthDate(birthDate);
                break;
            } catch (DateTimeParseException e) {
                System.out.println(ColorUtils.error("Invalid date value. Please enter a real calendar date in yyyy-MM-dd format, or press Enter to skip."));
            }
        }

        if (contactDAO.create(contact)) {
            System.out.println(ColorUtils.success("Contact created successfully! New Contact ID: " + contact.getContactId()));
        } else {
            System.out.println(ColorUtils.error("Failed to create contact."));
        }
    }

    /**
     * Delete one or more contacts by ID.
     */
    private void deleteContacts() {
        System.out.println("\n" + ColorUtils.header("--- Delete Contact(s) ---"));
        System.out.println(ColorUtils.header("======================================="));

        System.out.print(ColorUtils.seniorPrompt("Enter Contact ID or IDs (comma-separated) to delete: "));
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println(ColorUtils.error("No IDs provided."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
            scanner.nextLine();
            return;
        }

        String[] tokens = input.split(",");
        Map<Integer, Contact> toDelete = new HashMap<>();

        for (String token : tokens) {
            String trimmed = token.trim();
            if (trimmed.isEmpty()) continue;

            Integer id = BaseMenu.safeParseInt(trimmed);
            if (id == null) {
                System.out.println(ColorUtils.warning("Skipping invalid ID: " + trimmed));
                continue;
            }

            Contact contact = contactDAO.findById(id);
            if (contact == null) {
                System.out.println(ColorUtils.warning("Contact with ID " + id + " not found. Skipping."));
            } else {
                toDelete.put(id, contact);
            }
        }

        if (toDelete.isEmpty()) {
            System.out.println(ColorUtils.warning("No valid contacts found to delete."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
            scanner.nextLine();
            return;
        }

        System.out.println("\n" + ColorUtils.header("The following contact(s) will be deleted:"));
        displayContactsTable(List.copyOf(toDelete.values()));

        System.out.print("\n" + ColorUtils.seniorPrompt("Are you sure you want to delete these contact(s)? (yes/no): "));
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (!"yes".equals(confirm) && !"y".equals(confirm)) {
            System.out.println(ColorUtils.info("Deletion cancelled."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
            scanner.nextLine();
            return;
        }

        int deletedCount = 0;
        for (Integer id : toDelete.keySet()) {
            if (contactDAO.delete(id)) {
                deletedCount++;
            } else {
                System.out.println(ColorUtils.error("Failed to delete contact with ID: " + id));
            }
        }

        System.out.println(ColorUtils.success("Deleted " + deletedCount + " contact(s)."));
        System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
        scanner.nextLine();
    }

    /**
     * Change password for the currently logged-in Senior Developer.
     */
    private void changePassword() {
        System.out.println("\n" + ColorUtils.header("--- Change Password ---"));
        System.out.println(ColorUtils.header("======================================="));

        System.out.print(ColorUtils.seniorPrompt("Enter current password: "));
        String currentPassword = scanner.nextLine().trim();

        // Verify current password
        User currentUserFromDB = userDAO.findById(currentUser.getUserId());
        if (currentUserFromDB == null) {
            System.out.println(ColorUtils.error("Error: Could not retrieve user information."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
            scanner.nextLine();
            return;
        }

        String storedHash = currentUserFromDB.getPasswordHash();
        boolean passwordMatches = false;

        if (storedHash != null && !storedHash.isBlank()) {
            if (storedHash.startsWith("$2a$") || storedHash.startsWith("$2b$") || storedHash.startsWith("$2y$")) {
                passwordMatches = org.mindrot.jbcrypt.BCrypt.checkpw(currentPassword, storedHash);
            } else {
                passwordMatches = currentPassword.equals(storedHash);
            }
        }

        if (!passwordMatches) {
            System.out.println(ColorUtils.error("Current password is incorrect."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
            scanner.nextLine();
            return;
        }

        System.out.print(ColorUtils.seniorPrompt("Enter new password: "));
        String newPassword = scanner.nextLine().trim();
        if (newPassword.isEmpty()) {
            System.out.println(ColorUtils.error("Password cannot be empty."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
            scanner.nextLine();
            return;
        }

        System.out.print(ColorUtils.seniorPrompt("Confirm new password: "));
        String confirmPassword = scanner.nextLine().trim();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println(ColorUtils.error("Passwords do not match. Password not changed."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
            scanner.nextLine();
            return;
        }

        String hashedPassword = AuthService.hashPassword(newPassword);
        userDAO.updatePasswordHash(currentUser.getUserId(), hashedPassword);
        currentUser.setPasswordHash(hashedPassword);

        System.out.println(ColorUtils.success("Password changed successfully!"));
        System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
        scanner.nextLine();
    }

    /**
     * Helper to display contacts in a formatted table.
     */
    private void displayContactsTable(List<Contact> contacts) {
        System.out.printf(ColorUtils.header("%-6s %-15s %-15s %-15s %-15s %-15s %-15s %-25s %-25s %-12s%n"),
            "ID", "First Name", "Middle Name", "Last Name", "Nickname",
            "Phone 1", "Phone 2", "Email", "LinkedIn", "Birth Date");
        System.out.println(ColorUtils.colorize("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", ColorUtils.CYAN));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Contact contact : contacts) {
            String firstName = contact.getFirstName() != null ? contact.getFirstName() : "";
            String middleName = contact.getMiddleName() != null ? contact.getMiddleName() : "";
            String lastName = contact.getLastName() != null ? contact.getLastName() : "";
            String nickname = contact.getNickname() != null ? contact.getNickname() : "";
            String phonePrimary = contact.getPhonePrimary() != null ? contact.getPhonePrimary() : "";
            String phoneSecondary = contact.getPhoneSecondary() != null ? contact.getPhoneSecondary() : "";
            String email = contact.getEmail() != null ? contact.getEmail() : "";
            String linkedinUrl = contact.getLinkedinUrl() != null ? contact.getLinkedinUrl() : "";
            String birthDate = contact.getBirthDate() != null
                    ? contact.getBirthDate().format(dateFormatter)
                    : "N/A";

            if (firstName.length() > 13) firstName = firstName.substring(0, 10) + "...";
            if (middleName.length() > 13) middleName = middleName.substring(0, 10) + "...";
            if (lastName.length() > 13) lastName = lastName.substring(0, 10) + "...";
            if (nickname.length() > 13) nickname = nickname.substring(0, 10) + "...";
            if (phonePrimary.length() > 13) phonePrimary = phonePrimary.substring(0, 10) + "...";
            if (phoneSecondary.length() > 13) phoneSecondary = phoneSecondary.substring(0, 10) + "...";
            if (email.length() > 23) email = email.substring(0, 20) + "...";
            if (linkedinUrl.length() > 23) linkedinUrl = linkedinUrl.substring(0, 20) + "...";

            System.out.printf("%-6d %-15s %-15s %-15s %-15s %-15s %-15s %-25s %-25s %-12s%n",
                contact.getContactId(),
                firstName,
                middleName.isEmpty() ? "N/A" : middleName,
                lastName,
                nickname.isEmpty() ? "N/A" : nickname,
                phonePrimary.isEmpty() ? "N/A" : phonePrimary,
                phoneSecondary.isEmpty() ? "N/A" : phoneSecondary,
                email.isEmpty() ? "N/A" : email,
                linkedinUrl.isEmpty() ? "N/A" : linkedinUrl,
                birthDate);
        }

        System.out.println(ColorUtils.colorize("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", ColorUtils.CYAN));
    }

    /**
     * Small helper to avoid "null" when printing defaults.
     */
    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}


