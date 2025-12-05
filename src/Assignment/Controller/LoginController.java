
package Assignment.Controller;

import Assignment.Model.Person;
import Assignment.Model.PersonDAO;
import Assignment.Model.Staff;
import Assignment.Model.Admin;
import Assignment.View.LoginView;
import Assignment.MainMenu;

public class LoginController {
    
    private LoginView view;
    private Person loggedInUser;
    
    public LoginController() {
        this.view = new LoginView();
    }
    
    public void start() {
        view.displayLogo();
        login();
    }
    
    public void login() {
        view.displayLoginHeader();
        
        while (true) {
            String inputID = view.promptForID();
            
            // Exit option
            if (inputID.equals("X")) {
                System.exit(0);
            }
            
            // Find user by ID
            Person user = PersonDAO.findById(inputID);
            
            if (user == null) {
                view.displayUserNotFound();
                continue;
            }
            
            // Check if user is Staff or Admin
            if (user instanceof Staff || user instanceof Admin) {
                // Password validation loop
                while (true) {
                    String password = view.promptForPassword();
                    
                    Person authenticatedUser = PersonDAO.authenticate(inputID, password);
                    
                    if (authenticatedUser != null) {
                        view.displayLoginSuccess();
                        loggedInUser = authenticatedUser;
                        
                        // Set role and cashier ID in MainMenu
                        if (authenticatedUser instanceof Staff) {
                            MainMenu.setRole("Staff");
                            MainMenu.setCashierID(inputID);
                            MainMenu.StaffMenu();
                        } else if (authenticatedUser instanceof Admin) {
                            MainMenu.setRole("Admin");
                            MainMenu.setCashierID(inputID);
                            MainMenu.AdminMenu();
                        }
                        return; // Exit login after successful authentication
                    } else {
                        view.displayWrongPassword();
                    }
                }
            } else {
                view.displayMemberNotAllowed();
            }
        }
    }
    
    public Person getLoggedInUser() {
        return loggedInUser;
    }
}