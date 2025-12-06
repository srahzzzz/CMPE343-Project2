/**
 * Menu package containing role-based user interface classes.
 * 
 * <p>This package implements the menu system for different user roles.
 * Uses inheritance and polymorphism to provide role-specific functionality
 * while maintaining a consistent base structure.</p>
 * 
 * <p>Classes in this package:
 * <ul>
 *   <li>{@link menu.BaseMenu} - Abstract base class for all role menus</li>
 *   <li>{@link menu.TesterMenu} - Menu for Tester role (read-only operations)</li>
 *   <li>{@link menu.JuniorMenu} - Menu for Junior Developer role (read + update)</li>
 *   <li>{@link menu.SeniorMenu} - Menu for Senior Developer role (full CRUD)</li>
 *   <li>{@link menu.ManagerMenu} - Menu for Manager role (user management + statistics)</li>
 * </ul>
 * </p>
 * 
 * @author sarah nauman
 */
package menu;



