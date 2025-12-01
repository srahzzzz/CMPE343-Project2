import util.ColorUtils;

/**
 * <p>This class:
 * 1. Tests the database connection,
 * 2. Starts the login service.
 * 3. Launches role-specific menus after authentication.
 * @author sarah nauman
 */
public class Main {
    public static void main(String[] args) {

        System.out.println(ColorUtils.header("======================================="));
        System.out.println(ColorUtils.header("   ROLE-BASED CONTACT MANAGEMENT APP   "));
        System.out.println(ColorUtils.header("=======================================\n"));

        // Test DB connection
        if (db.DBConnection.getConnection() != null) {
            System.out.println(ColorUtils.success("Database connection successful.\n"));
        } else {
            System.out.println(ColorUtils.error("Database connection failed. Exiting..."));
            return;
        }

        // Start login flow
        service.AuthService auth = new service.AuthService();
        auth.startLogin();
    }
}
