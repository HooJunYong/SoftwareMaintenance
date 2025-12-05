/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment;


import java.util.Scanner;
import Assignment.Controller.LoginController;
import Assignment.Controller.OrderController;


/**
 * ^
 *
 * @author yutic
 */
public class MainMenu {

    private static String userRole;
    private static String cashierID;

    public static void main(String[] agrs) {
        LoginController loginController = new LoginController();
        loginController.start();
    }

    public static void setRole(String role) {
        userRole = role;
    }

    public static void setCashierID(String cashierID) {
        MainMenu.cashierID = cashierID;
    }

    public static String getCashierID() {
        return cashierID;
    }

    public static void StaffMenu() {
        int opt = 0;
        int optCheck = 0;
        int choice = 0;
        LoginController loginController = new LoginController();
        Scanner scan = new Scanner(System.in);
        System.out.println("");

        System.out.println("==============================");
        System.out.println("|      TECH GADGET POS       |");
        System.out.println("==============================");
        System.out.println("|    1. Product              |");
        System.out.println("|    2. Order                |");
        System.out.println("|    3. Member               |");
        System.out.println("|    4. Logout               |");
        System.out.println("==============================");
        do {
            try {
                System.out.printf("Enter your choice (1-4) > ");
                opt = scan.nextInt();

                switch (opt) {
                    case 1:
                        //ProductPage(false);
                        break;
                    case 2:
                        OrderPage(false);
                        break;
                    case 3:
                        // MemberPage();
                        break;
                    case 4:
                       loginController.start();
                        break;
                    default:
                        System.out.println("Invalid Input!!!\n");
                        choice = 1;
                        break;

                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.\n");
                choice = 1;
                scan.nextLine(); // Consume the invalid input
            }

        } while (choice == 1);

    }

    public static void AdminMenu() {
        int opt = 0;
        int optCheck = 0;
        int choice = 0;
        LoginController loginController = new LoginController();
        Scanner scan = new Scanner(System.in);
        System.out.println("");

        System.out.println("==============================");
        System.out.println("|      TECH GADGET POS       |");
        System.out.println("==============================");
        System.out.println("|    1. Product              |");
        System.out.println("|    2. Order                |");
        System.out.println("|    3. Member               |");
        System.out.println("|    4. Staff                |");
        System.out.println("|    5. Logout               |");
        System.out.println("==============================");
        do {
            try {
                System.out.printf("Enter your choice (1-5) > ");
                opt = scan.nextInt();

                switch (opt) {
                    case 1: {

                        //ProductPage(true);
                    }
                    break;

                    case 2:
                        OrderPage(true);
                        break;
                    case 3:
                        // MemberPage();
                        break;
                    case 4:
                        // StaffPage();
                        break;
                    case 5:
                        loginController.start();
                        break;
                    default:
                        System.out.println("Invalid Input!!!\n");
                        choice = 1;
                        break;

                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.\n");
                choice = 1;
                scan.nextLine(); // Consume the invalid input
            }
        } while (choice == 1);
    }

//----------------------------------------Member Module-------------------------------------------
//     public static void MemberPage() {
//         String choice;
//         int reset = 0;
//         Scanner sc = new Scanner(System.in);
//         MemberManagement memberManage = new MemberManagement();
//         String filePath = "/Users/yutic/Documents/SoftwareMaintenance/person.txt";
//         do {
//             System.out.println("");
//             System.out.println("==============================");
//             System.out.println("|        Member Menu         |");
//             System.out.println("==============================");
//             System.out.println("|    1. Add Member           |");
//             System.out.println("|    2. Search Member        |");
//             System.out.println("|    3. Edit Member          |");
//             System.out.println("|    4. Delete Member        |");
//             System.out.println("|    5. Display Member       |");
//             System.out.println("|    6. Return to Main Menu  |");
//             System.out.println("==============================");
//             System.out.print("Enter you choice : ");
//             choice = sc.nextLine();

//             switch (choice) {
//                 case "1":
//                     memberManage.AddMember();
//                     break;
//                 case "2":
//                     memberManage.SearchMember();
//                     break;
//                 case "3":
//                     memberManage.EditMember();
//                     break;
//                 case "4":
//                     memberManage.DeleteMember(personList, filePath);
//                     break;
//                 case "5":
//                     memberManage.DisplayMember();
//                     break;
//                 case "6":
//                     // Check the user's role and call the appropriate menu
//                     if ("Admin".equals(userRole)) {
//                         AdminMenu();
//                     } else {
//                         StaffMenu();
//                     }
//                     break;
//                 default:
//                     System.out.println("Invalid Input!!! Please Try Again.");
//                     reset = 1;
//             }
//         } while (reset == 1);
//     }
// //----------------------------------------Member Module-------------------------------------------

// //----------------------------------------Staff Module-------------------------------------------
//     public static void StaffPage() {
//         String choice;
//         int reset = 0;
//         StaffManagement manage = new StaffManagement();
//         Scanner sc = new Scanner(System.in);
//         ArrayList<Person> personList = Person.read();
//         String filePath = "/Users/yutic/Documents/SoftwareMaintenance/person.txt";
//         do {
//             System.out.println("");
//             System.out.println("==============================");
//             System.out.println("|        Staff Menu          |");
//             System.out.println("==============================");
//             System.out.println("|    1. Add Staff            |");
//             System.out.println("|    2. Search Staff         |");
//             System.out.println("|    3. Edit Staff           |");
//             System.out.println("|    4. Delete Staff         |");
//             System.out.println("|    5. Display Staff        |");
//             System.out.println("|    6. Return to Main Menu  |");
//             System.out.println("==============================");
//             System.out.print("Enter you choice : ");
//             choice = sc.nextLine();
//             switch (choice) {
//                 case "1":
//                     manage.AddStaff();
//                     break;
//                 case "2":
//                     manage.SearchStaffOrAdmin();
//                     break;
//                 case "3":
//                     manage.EditStaffOrAdmin();
//                     break;
//                 case "4":
//                     manage.DeleteStaff(personList, filePath);
//                     break;
//                 case "5":
//                     manage.DisplayStaffOrAdmin();
//                     break;
//                 case "6":
//                     if ("Admin".equals(userRole)) {
//                         AdminMenu();
//                     } else {
//                         StaffMenu();
//                     }
//                     break;
//                 default:
//                     System.out.println("Invalid Input!!! Please Try Again.");
//                     reset = 1;
//             }
//         } while (reset == 1);

//     }
//----------------------------------------Staff Module-------------------------------------------

    // public static void ProductPage(boolean isAdmin) {
    //     ArrayList<Product> product = Product.readProduct();
    //     Product prod = new Product();
    //     String opt;
    //     int optCheck = 0;
    //     int back = 0;
    //     int repeat = 0;

    //     Scanner scan = new Scanner(System.in);
    //     System.out.println("");
    //     do {

    //         System.out.println("==============================");
    //         System.out.println("|       Product Page         |");
    //         System.out.println("==============================");
    //         System.out.println("|    1. View Products        |");
    //         System.out.println("|    2. Add Product          |");
    //         System.out.println("|    3. Modify Product       |");
    //         System.out.println("|    4. Delete Product       |");
    //         System.out.println("|    5. Exit                 |");
    //         System.out.println("==============================");

    //         do {

    //             System.out.printf("Enter your choice (1-5) > ");
    //             opt = scan.nextLine();

    //             switch (opt) {
    //                 case "1":
    //                     Product.VProduct();
    //                     repeat = 1;
    //                     back = 0;
    //                     break;
    //                 case "2":
    //                     Product.AProduct();
    //                     repeat = 1;
    //                     back = 0;
    //                     break;
    //                 case "3":
    //                     Product.MProduct();
    //                     repeat = 1;
    //                     back = 0;
    //                     break;
    //                 case "4":
    //                     Product.DProduct();
    //                     repeat = 1;
    //                     back = 0;
    //                     break;
    //                 case "5":
    //                     repeat = 0;
    //                     back = 0;
    //                     break;

    //                 default:
    //                     System.out.println("Invalid Input!!!");
    //                     back = 1;
    //                     break;

    //             }

    //         } while (back == 1);

    //         if (repeat == 0) {
    //             // If the user chooses to exit, go back to the appropriate menu based on the user's role
    //             if (isAdmin) {
    //                 AdminMenu();
    //             } else {
    //                 StaffMenu();
    //             }
    //         }

    //     } while (repeat == 1);

    // }

    //order module
    public static void OrderPage(boolean isAdmin) {
        OrderController orderController = new OrderController();
        orderController.startOrderProcess(isAdmin);
           
    }

    
    

}
