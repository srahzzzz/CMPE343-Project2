package menu;

import dao.ContactDAO;
import dao.UndoOperationDAO;
import dao.UserDAO;
import model.Contact;
import model.User;
import service.AuthService;
import service.UndoService;
import util.ColorUtils;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Menu for Tester role.
 *
 * <p>Testers can:
 * - Change password,
 * - Logout,
 * - List all contacts,
 * - Search by selected field or fields,
 * - Sort results by a user-selected field.
 * @author sarah nauman
 */
public class TesterMenu extends BaseMenu {

    private final ContactDAO contactDAO;
    private final UndoService undoService;

    /**
     * Constructor for TesterMenu.
     *
     * @param user the logged-in tester user
     */
    public TesterMenu(User user) {
        super(user);
        this.contactDAO = new ContactDAO();
        this.undoService = new UndoService(new UndoOperationDAO(), contactDAO, new UserDAO());
    }

    @Override
    protected void displayMenu() {
        System.out.println(ColorUtils.testerMenuOption("1. List all contacts"));
        System.out.println(ColorUtils.testerMenuOption("2. Search by field"));
        System.out.println(ColorUtils.testerMenuOption("3. Search by multiple fields"));
        System.out.println(ColorUtils.testerMenuOption("4. Sort contacts by selected field"));
        System.out.println(ColorUtils.testerMenuOption("5. Undo last operation"));
        System.out.println(ColorUtils.testerMenuOption("6. Change password"));
        System.out.println(ColorUtils.testerMenuOption("0. Logout"));
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
                handleUndoOperation(undoService);
                break;
            case 6:
                changePassword();
                break;
            default:
                System.out.println("\n" + ColorUtils.error("Invalid option! Please try again."));
        }
    }

    /**
     * Lists all contacts in the database.
     * Displays all contact records in a formatted table.
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
        
        System.out.println("\n" + ColorUtils.testerPrompt("Press Enter to continue..."));
        waitForEnter();
    }

    /**
     * Searches for contacts by a single field.
     * Allows searching by first name, last name, or phone number.
     */
    private void searchByField() {
        System.out.println("\n" + ColorUtils.header("--- Search by Field ---"));
        System.out.println(ColorUtils.header("======================================="));
        System.out.println(ColorUtils.testerMenuOption("1. Search by First Name"));
        System.out.println(ColorUtils.testerMenuOption("2. Search by Last Name"));
        System.out.println(ColorUtils.testerMenuOption("3. Search by Phone Number"));
        System.out.println(ColorUtils.testerMenuOption("4. Search by Email"));
        System.out.print("\n" + ColorUtils.testerPrompt("Select search type: "));
        
        String choiceInput = scanner.nextLine().trim();
        Integer choice = BaseMenu.safeParseInt(choiceInput);
        
        if (choice == null || choice < 1 || choice > 4) {
            System.out.println(ColorUtils.error("Invalid choice. Please select 1, 2, 3, or 4."));
            System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        List<Contact> results;
        
        switch (choice) {
            case 1:
                System.out.print(ColorUtils.testerPrompt("Enter first name (or partial): "));
                String firstName = scanner.nextLine().trim();
                if (firstName.isEmpty()) {
                    System.out.println(ColorUtils.error("First name cannot be empty."));
                    System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
                    scanner.nextLine();
                    return;
                }
                results = contactDAO.searchByFirstName(firstName);
                break;
                
            case 2:
                System.out.print(ColorUtils.testerPrompt("Enter last name (or partial): "));
                String lastName = scanner.nextLine().trim();
                if (lastName.isEmpty()) {
                    System.out.println(ColorUtils.error("Last name cannot be empty."));
                    System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
                    scanner.nextLine();
                    return;
                }
                results = contactDAO.searchByLastName(lastName);
                break;
                
            case 3:
                System.out.print(ColorUtils.testerPrompt("Enter phone number (or partial): "));
                String phone = scanner.nextLine().trim();
                if (phone.isEmpty()) {
                    System.out.println(ColorUtils.error("Phone number cannot be empty."));
                    System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
                    scanner.nextLine();
                    return;
                }
                results = contactDAO.searchByPhone(phone);
                break;

            case 4:
                System.out.print(ColorUtils.testerPrompt("Enter email (or partial): "));
                String email = scanner.nextLine().trim();
                if (email.isEmpty()) {
                    System.out.println(ColorUtils.error("Email cannot be empty."));
                    System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
                    scanner.nextLine();
                    return;
                }
                // Re-use multi-field search for email-only searches.
                Map<String, String> singleEmailCriteria = new HashMap<>();
                singleEmailCriteria.put("email", email);
                results = contactDAO.searchByMultipleFields(singleEmailCriteria);
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
        
        System.out.println("\n" + ColorUtils.testerPrompt("Press Enter to continue..."));
        waitForEnter();
    }

    /**
     * Searches for contacts using multiple field criteria.
     * Allows combining multiple search conditions (e.g., name + birth month, phone + email).
     */
    private void searchByMultipleFields() {
        System.out.println("\n" + ColorUtils.header("--- Search by Multiple Fields ---"));
        System.out.println(ColorUtils.header("======================================="));
        System.out.println(ColorUtils.info("Enter search criteria (press Enter to skip a field):\n"));
        
        Map<String, String> criteria = new HashMap<>();
        
        System.out.print(ColorUtils.testerPrompt("First Name: "));
        String firstName = scanner.nextLine().trim();
        if (!firstName.isEmpty()) {
            criteria.put("first_name", firstName);
        }
        
        System.out.print(ColorUtils.testerPrompt("Last Name: "));
        String lastName = scanner.nextLine().trim();
        if (!lastName.isEmpty()) {
            criteria.put("last_name", lastName);
        }
        
        System.out.print(ColorUtils.testerPrompt("Phone Number: "));
        String phone = scanner.nextLine().trim();
        if (!phone.isEmpty()) {
            criteria.put("phone", phone);
        }
        
        System.out.print(ColorUtils.testerPrompt("Email (partial): "));
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) {
            criteria.put("email", email);
        }
        
        System.out.print(ColorUtils.testerPrompt("Birth Month (1-12): "));
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
            System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
            waitForEnter();
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
        
        System.out.println("\n" + ColorUtils.testerPrompt("Press Enter to continue..."));
        waitForEnter();
    }

    /**
     * Sorts contacts by a selected field in ascending or descending order.
     * Allows sorting by any contact field (name, email, birth date, etc.).
     */
    private void sortContacts() {
        System.out.println("\n" + ColorUtils.header("--- Sort Contacts ---"));
        System.out.println(ColorUtils.header("======================================="));
        System.out.println(ColorUtils.info("Select field to sort by:"));
        System.out.println(ColorUtils.testerMenuOption("1. First Name"));
        System.out.println(ColorUtils.testerMenuOption("2. Last Name"));
        System.out.println(ColorUtils.testerMenuOption("3. Email"));
        System.out.println(ColorUtils.testerMenuOption("4. Birth Date (oldest / youngest)"));
        System.out.println(ColorUtils.testerMenuOption("5. Phone Primary"));
        System.out.print("\n" + ColorUtils.testerPrompt("Enter your choice: "));
        
        String choiceInput = scanner.nextLine().trim();
        Integer choice = BaseMenu.safeParseInt(choiceInput);
        
        if (choice == null || choice < 1 || choice > 5) {
            System.out.println(ColorUtils.error("Invalid choice."));
            System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
            waitForEnter();
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
            System.out.println(ColorUtils.testerMenuOption("1. Ascending (oldest to youngest)"));
            System.out.println(ColorUtils.testerMenuOption("2. Descending (youngest to oldest)"));
        } else {
            System.out.println(ColorUtils.testerMenuOption("1. Ascending (A-Z, 1-9)"));
            System.out.println(ColorUtils.testerMenuOption("2. Descending (Z-A, 9-1)"));
        }
        System.out.print(ColorUtils.testerPrompt("Enter your choice: "));
        
        String orderInput = scanner.nextLine().trim();
        
        // Strict validation: only accept exactly "1" or "2"
        if (!orderInput.equals("1") && !orderInput.equals("2")) {
            System.out.println(ColorUtils.error("Invalid choice. Please enter 1 or 2 only."));
            System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        boolean ascending = orderInput.equals("1");
        
        List<Contact> contacts = contactDAO.findAllSorted(field, ascending);
        
        if (contacts.isEmpty()) {
            System.out.println(ColorUtils.warning("No contacts found."));
        } else {
            System.out.println("\n" + ColorUtils.header("Sorted Contacts:"));
            displayContactsTable(contacts);
            System.out.println(ColorUtils.info("Total contacts: " + contacts.size()));
        }
        
        System.out.println("\n" + ColorUtils.testerPrompt("Press Enter to continue..."));
        waitForEnter();
    }

    /**
     * Changes the password for the currently logged-in user.
     * Verifies current password before allowing change.
     */
    private void changePassword() {
        System.out.println("\n" + ColorUtils.header("--- Change Password ---"));
        System.out.println(ColorUtils.header("======================================="));
        
        UserDAO userDAO = new UserDAO();
        
        System.out.print(ColorUtils.testerPrompt("Enter current password: "));
        String currentPassword = scanner.nextLine().trim();
        
        // Verify current password
        User currentUserFromDB = userDAO.findById(currentUser.getUserId());
        if (currentUserFromDB == null) {
            System.out.println(ColorUtils.error("Error: Could not retrieve user information."));
            System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        // Check password using AuthService logic
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
            System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        // Get new password
        System.out.print(ColorUtils.testerPrompt("Enter new password: "));
        String newPassword = scanner.nextLine().trim();
        if (newPassword.isEmpty()) {
            System.out.println(ColorUtils.error("Password cannot be empty."));
            System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        System.out.print(ColorUtils.testerPrompt("Confirm new password: "));
        String confirmPassword = scanner.nextLine().trim();
        
        if (!newPassword.equals(confirmPassword)) {
            System.out.println(ColorUtils.error("Passwords do not match. Password not changed."));
            System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }
        
        // Update password
        String hashedPassword = AuthService.hashPassword(newPassword);
        userDAO.updatePasswordHash(currentUser.getUserId(), hashedPassword);
        System.out.println(ColorUtils.success("Password changed successfully!"));
        // Update current user object
        currentUser.setPasswordHash(hashedPassword);
        
        System.out.println(ColorUtils.testerPrompt("Press Enter to continue..."));
        scanner.nextLine();
    }

    /**
     * Helper method to display contacts in a formatted table.
     * Shows all contact information including middle name, nickname, secondary phone, and LinkedIn URL.
     * Creates a table with dynamic column widths based on content, enabling horizontal scrolling without truncation.
     *
     * @param contacts the list of contacts to display
     */
    private void displayContactsTable(List<Contact> contacts) {
        if (contacts.isEmpty()) {
            return;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Prepare data and calculate column widths
        String[] headers = {"ID", "First Name", "Middle Name", "Last Name", "Nickname", 
                           "Phone 1", "Phone 2", "Email", "LinkedIn", "Birth Date"};
        
        String[][] rows = new String[contacts.size()][10];
        int[] columnWidths = new int[10];
        
        // Initialize column widths with header lengths
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = headers[i].length();
        }
        
        // Process contacts and calculate max width for each column
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            
            String id = String.valueOf(contact.getContactId());
            String firstName = contact.getFirstName() != null ? contact.getFirstName() : "N/A";
            String middleName = contact.getMiddleName() != null ? contact.getMiddleName() : "N/A";
            String lastName = contact.getLastName() != null ? contact.getLastName() : "N/A";
            String nickname = contact.getNickname() != null ? contact.getNickname() : "N/A";
            String phonePrimary = contact.getPhonePrimary() != null ? contact.getPhonePrimary() : "N/A";
            String phoneSecondary = contact.getPhoneSecondary() != null ? contact.getPhoneSecondary() : "N/A";
            String email = contact.getEmail() != null ? contact.getEmail() : "N/A";
            String linkedinUrl = contact.getLinkedinUrl() != null ? contact.getLinkedinUrl() : "N/A";
            String birthDate = contact.getBirthDate() != null ? 
                contact.getBirthDate().format(dateFormatter) : "N/A";
            
            rows[i][0] = id;
            rows[i][1] = firstName;
            rows[i][2] = middleName;
            rows[i][3] = lastName;
            rows[i][4] = nickname;
            rows[i][5] = phonePrimary;
            rows[i][6] = phoneSecondary;
            rows[i][7] = email;
            rows[i][8] = linkedinUrl;
            rows[i][9] = birthDate;
            
            // Update column widths
            columnWidths[0] = Math.max(columnWidths[0], id.length());
            columnWidths[1] = Math.max(columnWidths[1], firstName.length());
            columnWidths[2] = Math.max(columnWidths[2], middleName.length());
            columnWidths[3] = Math.max(columnWidths[3], lastName.length());
            columnWidths[4] = Math.max(columnWidths[4], nickname.length());
            columnWidths[5] = Math.max(columnWidths[5], phonePrimary.length());
            columnWidths[6] = Math.max(columnWidths[6], phoneSecondary.length());
            columnWidths[7] = Math.max(columnWidths[7], email.length());
            columnWidths[8] = Math.max(columnWidths[8], linkedinUrl.length());
            columnWidths[9] = Math.max(columnWidths[9], birthDate.length());
        }
        
        // Build separator line - match column widths exactly with no extra spacing
        StringBuilder separator = new StringBuilder();
        for (int i = 0; i < columnWidths.length; i++) {
            separator.append("+");
            for (int j = 0; j < columnWidths[i] + 1; j++) {
                separator.append("-");
            }
        }
        separator.append("+");
        
        // Display header with no spacing - columns close together
        StringBuilder headerLine = new StringBuilder();
        for (int i = 0; i < headers.length; i++) {
            headerLine.append("|");
            headerLine.append(String.format("%-" + columnWidths[i] + "s", headers[i]));
        }
        headerLine.append("|");
        
        System.out.println(ColorUtils.colorize(separator.toString(), ColorUtils.CYAN));
        System.out.println(ColorUtils.header(headerLine.toString()));
        System.out.println(ColorUtils.colorize(separator.toString(), ColorUtils.CYAN));
        
        // Display rows with no spacing - columns close together, properly aligned
        for (String[] row : rows) {
            StringBuilder rowLine = new StringBuilder();
            for (int i = 0; i < row.length; i++) {
                rowLine.append("|");
                rowLine.append(String.format("%-" + columnWidths[i] + "s", row[i]));
            }
            rowLine.append("|");
            System.out.println(rowLine.toString());
        }
        
        System.out.println(ColorUtils.colorize(separator.toString(), ColorUtils.CYAN));
    }
}

