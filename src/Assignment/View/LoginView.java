package Assignment.View;

import java.util.Scanner;

public class LoginView {
    
    private Scanner scanner;
    
    public LoginView() {
        this.scanner = new Scanner(System.in);
    }
    
    public void displayLogo() {
        String logo
                = " _______        _        _____           _            _   \n"
                + "|__   __|      | |      / ____|         | |          | |  \n"
                + "   | | ___  ___| |__   | |  __  __ _  __| | __ _  ___| |_ \n"
                + "   | |/ _ \\/ __| '_ \\  | | |_ |/ _` |/ _` |/ _` |/ _ \\ __|\n"
                + "   | |  __/ (__| | | | | |__| | (_| | (_| | (_| |  __/ |_ \n"
                + "   |_|\\___|\\___|_| |_|  \\_____|\\__,_|\\__,_|\\__, |\\___|\\__|\n"
                + "                                            __/ |         \n"
                + "                                           |___/          ";
        System.out.println(logo);
    }
    
    public void displayLoginHeader() {
        System.out.println("==========================");
        System.out.println("|         LOGIN          |");
        System.out.println("==========================");
        System.out.println("\n");
    }
    
    public String promptForID() {
        System.out.print("Enter Staff/Admin ID (X for exit): ");
        return scanner.nextLine().toUpperCase();
    }
    
    public String promptForPassword() {
        System.out.print("Enter Password: ");
        return scanner.nextLine();
    }
    
    public void displayLoginSuccess() {
        System.out.println("LOGIN SUCCESSFULLY!");
    }
    
    public void displayWrongPassword() {
        System.out.println("WRONG PASSWORD!");
    }
    
    public void displayWrongID() {
        System.out.println("WRONG ID!");
    }
    
    public void displayMemberNotAllowed() {
        System.out.println("Members are not allowed to log in.");
    }
    
    public void displayUserNotFound() {
        System.out.println("User not found!");
    }
}