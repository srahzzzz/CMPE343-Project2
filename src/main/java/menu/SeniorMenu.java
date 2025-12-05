package menu;

import dao.ContactDAO;
import dao.UserDAO;
import model.Contact;
import model.User;
import service.AuthService;
import service.ValidationUtils;
import util.ColorUtils;
import util.UndoManager;

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
    private final UndoManager undoManager;

    /**
     * Constructor for SeniorMenu.
     *
     * @param user the logged-in senior developer user
     */
    public SeniorMenu(User user) {
        super(user);
        this.contactDAO = new ContactDAO();
        this.userDAO = new UserDAO();
        this.undoManager = new UndoManager(contactDAO);
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
        System.out.println(ColorUtils.seniorMenuOption("8. Undo last operation"));
        System.out.println(ColorUtils.seniorMenuOption("9. Change password"));
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
                undoLastOperation();
                break;
            case 9:
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
        waitForEnter();
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
            waitForEnter();
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
        waitForEnter();
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
        
        System.out.println("\n" + ColorUtils.seniorPrompt("Press Enter to continue..."));
        waitForEnter();
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
            System.out.println(ColorUtils.seniorMenuOption("1. Ascending (oldest to youngest)"));
            System.out.println(ColorUtils.seniorMenuOption("2. Descending (youngest to oldest)"));
        } else {
            System.out.println(ColorUtils.seniorMenuOption("1. Ascending (A-Z, 1-9)"));
            System.out.println(ColorUtils.seniorMenuOption("2. Descending (Z-A, 9-1)"));
        }
        System.out.print(ColorUtils.seniorPrompt("Enter your choice: "));
        
        String orderInput = scanner.nextLine().trim();
        
        // Strict validation: only accept exactly "1" or "2"
        if (!orderInput.equals("1") && !orderInput.equals("2")) {
            System.out.println(ColorUtils.error("Invalid choice. Please enter 1 or 2 only."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
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
        
        System.out.println("\n" + ColorUtils.seniorPrompt("Press Enter to continue..."));
        waitForEnter();
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
            waitForEnter();
            return;
        }

        Contact contact = contactDAO.findById(contactId);
        if (contact == null) {
            System.out.println(ColorUtils.error("Contact with ID " + contactId + " not found."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }

        // Record the old state for undo before updating
        undoManager.recordUpdate(contact);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("\n" + ColorUtils.header("Current Contact Information:"));
        displayContactsTable(List.of(contact));
        System.out.println("\n" + ColorUtils.info("Enter new values (press Enter to keep current value):"));

        // First name - letters only
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("First Name [" + nullToEmpty(contact.getFirstName()) + "] (letters only, Turkish characters supported): "));
            String firstName = scanner.nextLine().trim();

            if (firstName.isEmpty()) {
                break; // keep existing
            }

            if (!ValidationUtils.isValidName(firstName)) {
                System.out.println(ColorUtils.error("Invalid first name. Use letters only (Turkish characters like İ, ı, ş, ğ, ü, ö, ç are supported). You may use spaces and hyphens. Apostrophes/single quotes are not supported."));
                continue;
            }

            contact.setFirstName(firstName);
            break;
        }

        // Middle name - optional, letters only
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Middle Name [" + nullToEmpty(contact.getMiddleName()) + "] (optional, letters only, Turkish characters supported or Enter to keep): "));
            String middleName = scanner.nextLine().trim();

            if (middleName.isEmpty()) {
                break; // keep existing
            }

            if (!ValidationUtils.isValidName(middleName)) {
                System.out.println(ColorUtils.error("Invalid middle name. Use letters only (Turkish characters like İ, ı, ş, ğ, ü, ö, ç are supported). You may use spaces and hyphens. Apostrophes/single quotes are not supported. Press Enter to keep current value."));
                continue;
            }

            contact.setMiddleName(middleName);
            break;
        }

        // Last name - letters only
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Last Name [" + nullToEmpty(contact.getLastName()) + "] (letters only, Turkish characters supported): "));
            String lastName = scanner.nextLine().trim();

            if (lastName.isEmpty()) {
                break; // keep existing
            }

            if (!ValidationUtils.isValidName(lastName)) {
                System.out.println(ColorUtils.error("Invalid last name. Use letters only (Turkish characters like İ, ı, ş, ğ, ü, ö, ç are supported). You may use spaces and hyphens. Apostrophes/single quotes are not supported."));
                continue;
            }

            contact.setLastName(lastName);
            break;
        }

        // Nickname - optional
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Nickname [" + nullToEmpty(contact.getNickname()) + "] (optional, letters/digits/-/_, Turkish characters supported or Enter to keep): "));
            String nickname = scanner.nextLine().trim();

            if (nickname.isEmpty()) {
                break; // keep existing
            }

            if (!ValidationUtils.isValidNickname(nickname)) {
                System.out.println(ColorUtils.error("Invalid nickname. Allowed characters: letters (Turkish characters like İ, ı, ş, ğ, ü, ö, ç are supported), digits, spaces, '-' and '_'. Press Enter to keep current value."));
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
                System.out.println(ColorUtils.error("Invalid email. It must contain '@' and '.', and no spaces (e.g user@example.com)."));
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
                // Parse the date components for validation
                String[] parts = birthDateInput.split("-");
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);
                
                // Validate month
                if (month < 1 || month > 12) {
                    System.out.println(ColorUtils.error("Invalid month. Month must be between 1 and 12."));
                    continue;
                }
                
                // Validate day based on month
                int maxDays = getMaxDaysInMonth(month, year);
                if (day < 1 || day > maxDays) {
                    String monthName = getMonthName(month);
                    if (month == 2 && day == 29) {
                        if (!isLeapYear(year)) {
                            System.out.println(ColorUtils.error("Invalid date. February 29 does not exist in " + year + " (not a leap year). February has 28 days in non-leap years."));
                        } else {
                            System.out.println(ColorUtils.error("Invalid date. Day must be between 1 and " + maxDays + " for " + monthName + "."));
                        }
                    } else {
                        System.out.println(ColorUtils.error("Invalid date. " + monthName + " only has " + maxDays + " day(s). Please enter a valid day (1-" + maxDays + ")."));
                    }
                    continue;
                }
                
                LocalDate birthDate = LocalDate.parse(birthDateInput, dateFormatter);
                if (!ValidationUtils.isValidPastOrToday(birthDate)) {
                    System.out.println(ColorUtils.error("Birth date cannot be in the future. Please enter a past or today's date."));
                    continue;
                }
                contact.setBirthDate(birthDate);
                break;
            } catch (DateTimeParseException e) {
                System.out.println(ColorUtils.error("Invalid date value. Please enter a real calendar date in yyyy-MM-dd format."));
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println(ColorUtils.error("Invalid date format. Please enter the date as yyyy-MM-dd (e.g., 2002-05-05)."));
            }
        }

        if (contactDAO.update(contact)) {
            System.out.println("\n" + ColorUtils.success("Contact updated successfully!"));
        } else {
            System.out.println("\n" + ColorUtils.error("Failed to update contact."));
        }

        System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
        waitForEnter();
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
            System.out.print(ColorUtils.seniorPrompt("First Name (required, letters only, Turkish characters supported): "));
            String firstName = scanner.nextLine().trim();

            if (firstName.isEmpty()) {
                System.out.println(ColorUtils.error("First Name cannot be empty."));
                continue;
            }

            if (!ValidationUtils.isValidName(firstName)) {
                System.out.println(ColorUtils.error("Invalid first name. Use letters only (Turkish characters like İ, ı, ş, ğ, ü, ö, ç are supported). You may use spaces and hyphens. Apostrophes/single quotes are not supported."));
                continue;
            }

            contact.setFirstName(firstName);
            break;
        }

        // Middle Name (optional, letters only)
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Middle Name (optional, letters only, Turkish characters supported or Enter to skip): "));
            String middleName = scanner.nextLine().trim();

            if (middleName.isEmpty()) {
                contact.setMiddleName(null);
                break;
            }

            if (!ValidationUtils.isValidName(middleName)) {
                System.out.println(ColorUtils.error("Invalid middle name. Use letters only (Turkish characters like İ, ı, ş, ğ, ü, ö, ç are supported). You may use spaces and hyphens. Apostrophes/single quotes are not supported. Press Enter to skip."));
                continue;
            }

            contact.setMiddleName(middleName);
            break;
        }

        // Last Name (required, letters only)
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Last Name (required, letters only, Turkish characters supported): "));
            String lastName = scanner.nextLine().trim();

            if (lastName.isEmpty()) {
                System.out.println(ColorUtils.error("Last Name cannot be empty."));
                continue;
            }

            if (!ValidationUtils.isValidName(lastName)) {
                System.out.println(ColorUtils.error("Invalid last name. Use letters only (Turkish characters like İ, ı, ş, ğ, ü, ö, ç are supported). You may use spaces and hyphens. Apostrophes/single quotes are not supported."));
                continue;
            }

            contact.setLastName(lastName);
            break;
        }

        // Nickname (optional, relaxed)
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Nickname (optional, letters/digits/-/_, Turkish characters supported or Enter to skip): "));
            String nickname = scanner.nextLine().trim();

            if (nickname.isEmpty()) {
                contact.setNickname(null);
                break;
            }

            if (!ValidationUtils.isValidNickname(nickname)) {
                System.out.println(ColorUtils.error("Invalid nickname. Allowed characters: letters (Turkish characters like İ, ı, ş, ğ, ü, ö, ç are supported), digits, spaces, '-' and '_'. Press Enter to skip."));
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
                // Parse the date components for validation
                String[] parts = birthDateInput.split("-");
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);
                
                // Validate month
                if (month < 1 || month > 12) {
                    System.out.println(ColorUtils.error("Invalid month. Month must be between 1 and 12, or press Enter to skip."));
                    continue;
                }
                
                // Validate day based on month
                int maxDays = getMaxDaysInMonth(month, year);
                if (day < 1 || day > maxDays) {
                    String monthName = getMonthName(month);
                    if (month == 2 && day == 29) {
                        if (!isLeapYear(year)) {
                            System.out.println(ColorUtils.error("Invalid date. February 29 does not exist in " + year + " (not a leap year). February has 28 days in non-leap years, or press Enter to skip."));
                        } else {
                            System.out.println(ColorUtils.error("Invalid date. Day must be between 1 and " + maxDays + " for " + monthName + ", or press Enter to skip."));
                        }
                    } else {
                        System.out.println(ColorUtils.error("Invalid date. " + monthName + " only has " + maxDays + " day(s). Please enter a valid day (1-" + maxDays + "), or press Enter to skip."));
                    }
                    continue;
                }
                
                LocalDate birthDate = LocalDate.parse(birthDateInput, dateFormatter);
                if (!ValidationUtils.isValidPastOrToday(birthDate)) {
                    System.out.println(ColorUtils.error("Birth date cannot be in the future. Please enter a past or today's date, or press Enter to skip."));
                    continue;
                }
                contact.setBirthDate(birthDate);
                break;
            } catch (DateTimeParseException e) {
                System.out.println(ColorUtils.error("Invalid date value. Please enter a real calendar date in yyyy-MM-dd format, or press Enter to skip."));
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println(ColorUtils.error("Invalid date format. Please enter the date as yyyy-MM-dd (e.g., 2002-05-05), or press Enter to skip."));
            }
        }

        if (contactDAO.create(contact)) {
            System.out.println(ColorUtils.success("Contact created successfully! New Contact ID: " + contact.getContactId()));
            // Record the add operation for undo
            undoManager.recordAdd(contact.getContactId());
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
            waitForEnter();
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
            waitForEnter();
            return;
        }

        System.out.println("\n" + ColorUtils.header("The following contact(s) will be deleted:"));
        displayContactsTable(List.copyOf(toDelete.values()));

        // Strict y/n validation
        String confirm;
        while (true) {
            System.out.print("\n" + ColorUtils.seniorPrompt("Are you sure you want to delete these contact(s)? (y/n): "));
            confirm = scanner.nextLine().trim().toLowerCase();
            
            if (confirm.equals("y") || confirm.equals("n")) {
                break;
            }
            
            System.out.println(ColorUtils.error("Invalid choice. Please enter 'y' or 'n'."));
        }

        if (!confirm.equals("y")) {
            System.out.println(ColorUtils.info("Deletion cancelled."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }

        // Record delete operations before executing them (in reverse order to maintain stack order)
        // We'll record them in the order they'll be undone (last deleted first)
        java.util.List<Integer> idsToDelete = new java.util.ArrayList<>(toDelete.keySet());
        for (int i = idsToDelete.size() - 1; i >= 0; i--) {
            Integer id = idsToDelete.get(i);
            Contact contactToDelete = toDelete.get(id);
            undoManager.recordDelete(contactToDelete);
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
        waitForEnter();
    }

    /**
     * Undoes the last add, update, or delete operation.
     */
    private void undoLastOperation() {
        System.out.println("\n" + ColorUtils.header("--- Undo Last Operation ---"));
        System.out.println(ColorUtils.header("======================================="));

        if (!undoManager.canUndo()) {
            System.out.println(ColorUtils.warning("No operations available to undo."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }

        // Confirmation prompt
        String confirm;
        while (true) {
            System.out.print(ColorUtils.seniorPrompt("Are you sure you want to undo the last operation? (y/n): "));
            confirm = scanner.nextLine().trim().toLowerCase();
            
            if (confirm.equals("y") || confirm.equals("n")) {
                break;
            }
            
            System.out.println(ColorUtils.error("Invalid choice. Please enter 'y' or 'n' only."));
        }

        if (!confirm.equals("y")) {
            System.out.println(ColorUtils.info("Undo cancelled."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }

        System.out.println(ColorUtils.info("Undoing the last operation..."));
        
        if (undoManager.undo()) {
            System.out.println(ColorUtils.success("Operation undone successfully!"));
        } else {
            System.out.println(ColorUtils.error("Failed to undo the last operation."));
        }
        
        System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
        waitForEnter();
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
            waitForEnter();
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
            waitForEnter();
            return;
        }

        System.out.print(ColorUtils.seniorPrompt("Enter new password: "));
        String newPassword = scanner.nextLine().trim();
        if (newPassword.isEmpty()) {
            System.out.println(ColorUtils.error("Password cannot be empty."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }

        System.out.print(ColorUtils.seniorPrompt("Confirm new password: "));
        String confirmPassword = scanner.nextLine().trim();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println(ColorUtils.error("Passwords do not match. Password not changed."));
            System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
            waitForEnter();
            return;
        }

        String hashedPassword = AuthService.hashPassword(newPassword);
        userDAO.updatePasswordHash(currentUser.getUserId(), hashedPassword);
        currentUser.setPasswordHash(hashedPassword);

        System.out.println(ColorUtils.success("Password changed successfully!"));
        System.out.println(ColorUtils.seniorPrompt("Press Enter to continue..."));
        waitForEnter();
    }

    /**
     * Helper to display contacts in a formatted table.
     * Creates a table with dynamic column widths based on content, enabling horizontal scrolling without truncation.
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
            String birthDate = contact.getBirthDate() != null
                    ? contact.getBirthDate().format(dateFormatter)
                    : "N/A";
            
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

    /**
     * Small helper to avoid "null" when printing defaults.
     */
    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
    
    /**
     * Gets the maximum number of days in a given month, accounting for leap years.
     * 
     * @param month the month (1-12)
     * @param year the year (for leap year calculation)
     * @return the maximum number of days in the month
     */
    private int getMaxDaysInMonth(int month, int year) {
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                return isLeapYear(year) ? 29 : 28;
            default:
                return 31;
        }
    }
    
    /**
     * Checks if a year is a leap year.
     * A year is a leap year if it is divisible by 4, but not by 100 unless also divisible by 400.
     * 
     * @param year the year to check
     * @return true if the year is a leap year, false otherwise
     */
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
    
    /**
     * Gets the name of a month.
     * 
     * @param month the month number (1-12)
     * @return the name of the month
     */
    private String getMonthName(int month) {
        String[] monthNames = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };
        if (month >= 1 && month <= 12) {
            return monthNames[month - 1];
        }
        return "Invalid Month";
    }
}


