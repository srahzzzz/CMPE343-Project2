/**
 * THIS IS A TESTER RIGHT NOW
 *
 * <p>This class:
 * 1. Tests the database connection,
 * 2. Starts the login service.
 * 3. Launches role-specific menus after authentication.
 * @author sarah nauman
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("   ROLE-BASED CONTACT MANAGEMENT APP   ");
        System.out.println("=======================================\n");

        // Test DB connection
        if (db.DBConnection.getConnection() != null) {
            System.out.println("Database connection successful.\n");
        } else {
            System.out.println("Database connection failed. Exiting...");
            return;
        }

        // Start login flow
        service.AuthService auth = new service.AuthService();
        auth.startLogin();
    }
}
