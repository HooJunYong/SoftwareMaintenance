// /*
//  * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
//  * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
//  */
// package Assignment;

// import java.util.ArrayList;
// import java.util.Iterator;
// import java.util.Scanner;
// import Assignment.Model.Member;
// import Assignment.Model.Person;
// import Assignment.Model.Staff;
// import Assignment.Model.Admin;
// import Assignment.Model.PersonDAO;

// /**
//  *
//  * @author User
//  */
// public class StaffManagement {

   
//     public static void Login() {
//         String inputID;
//         String pass;
//         int reset = 0;
//         int valid = 0;
//         MainMenu menu = new MainMenu();
//         Scanner sc = new Scanner(System.in);
//         ArrayList<Person> data = PersonDAO.readAll();

//         System.out.println("==========================");
//         System.out.println("|         LOGIN          |");
//         System.out.println("==========================");
//         System.out.println("\n");

//         do {
//             System.out.printf("Enter Staff/Admin ID (X for exit): ");
//             inputID = sc.nextLine().toUpperCase();

//             if (inputID.equals("X")) {
//               System.exit(0);
//             }

//             Person user = null; // Store the user object

//             for (int i = 0; i < data.size(); i++) {
//                 Person person = data.get(i);

//                 if (person.getID().equals(inputID)) {
//                     user = person; // Store the user object
//                     // Check if the user is a Staff or Admin (and not a Member)
//                     if (user instanceof Staff || user instanceof Admin) {
//                         do {
//                             System.out.printf("Enter Password: ");
//                             pass = sc.nextLine();

//                             if (user instanceof Staff) {
//                                 // Check staff password
//                                 if (((Staff) user).validatePassword(pass)) {
//                                     valid = 1;
//                                     System.out.println("LOGIN SUCCESSFULLY!");
//                                     menu.setRole("Staff");
//                                     menu.setCashierID(inputID);
//                                 } else {
//                                     System.out.println("WRONG PASSWORD!");
//                                     valid = 0;
//                                 }
//                             } else if (user instanceof Admin) {
//                                 // Check admin password
//                                 if (((Admin) user).validatePassword(pass)) {
//                                     valid = 1;
//                                     System.out.println("LOGIN SUCCESSFULLY!");
//                                     menu.setRole("Admin");
//                                      menu.setCashierID(inputID);
//                                 } else {
//                                     System.out.println("WRONG PASSWORD!");
//                                     valid = 0;
//                                 }
//                             }
//                         } while (valid == 0);
//                     } else {
//                         System.out.println("Members are not allowed to log in.");
//                         valid = 0;
//                     }
//                 }
//             }

//             if (valid == 1) {
//                 // Check the role of the user and redirect to the appropriate menu
//                 if (user instanceof Staff) {
//                     menu.StaffMenu();
//                 } else if (user instanceof Admin) {
//                     menu.AdminMenu();
//                 }
//             } else {
//                 System.out.println("WRONG ID!");
//                 reset = 0;
//             }

//         } while (reset == 0);
//     }

//     public static void AddStaff() {
//         String name;
//         String phoneNo;
//         String email;
//         String NextID = "";
//         String NewStaffPass;
//         String choice;
//         int reset = 0;
//         MainMenu menu = new MainMenu();
//         Staff staff = new Staff();
//         Scanner sc = new Scanner(System.in);
//         System.out.println("");
//         System.out.println("==============================");
//         System.out.println("|        REGISTRATION        |");
//         System.out.println("==============================");
//         do {
//             System.out.print("Add new Staff/Admin? (S for Staff, A for Admin, X to exit): ");
//             choice = sc.nextLine().toUpperCase();
//             switch (choice) {
//                 case "S":
//                     NextID = Person.getNextID("Staff"); // Get a new staff ID
//                     break;
//                 case "A":
//                     NextID = Person.getNextID("Admin"); // Get a new admin ID
//                     break;
//                 case "X":
//                     menu.StaffPage();
//                     break;
//                 default:
//                     System.out.println("Invalid input!!! Please Try Again.");
//                     reset = 1;
//                     break;
//             }

//             if (choice.equals("S") || choice.equals("A")) {
//                 System.out.println("| Staff/Admin ID     : " + NextID);
//                 System.out.print("| Enter Name         : ");
//                 name = sc.nextLine();
//                 System.out.print("| Enter Password     : ");
//                 NewStaffPass = sc.nextLine();
//                 do {
//                     System.out.print("| Enter Phone Number : ");
//                     phoneNo = sc.nextLine();
//                     if (!(Person.ValidatePhoneNum(phoneNo))) {
//                         System.out.println("Invalid Phone Number Format!!! Please Try Again.");
//                     }
//                 } while (!(Person.ValidatePhoneNum(phoneNo)));
//                 do {
//                     System.out.print("| Enter Email        : ");
//                     email = sc.nextLine();
//                     if (!(Person.validateEmail(email))) {
//                         System.out.println("Invalid Email Format!!! Please Try Again.");
//                     }
//                 } while (!(Person.validateEmail(email)));

//                 Staff newstaff = new Staff(NextID, NewStaffPass, name, email, phoneNo);

//                 Person.writeDataToFile(newstaff, "/Users/yutic/Documents/NetBeansProjects/DFT2(G1)-YuTickSang-IvanPekYuHeng-HooJunYong/person.txt");
//                 System.out.println("==============================");
//                 System.out.println("| Staff Added Successfully!  |");
//                 System.out.println("==============================");
//                 reset = 0;
//                 menu.StaffPage();
//             }
//         } while (reset == 1);
//     }

//     public static void SearchStaffOrAdmin() {
//         int reset = 0;
//         String tempID;
//         MainMenu menu = new MainMenu();
//         Scanner sc = new Scanner(System.in);
//         ArrayList<Person> info = Person.read();

//         System.out.println("");
//         System.out.println("=================================");
//         System.out.println("|            Search             |");
//         System.out.println("=================================");

//         do {
//             System.out.print("Enter Staff/Admin ID (X to exit): ");
//             tempID = sc.nextLine().toUpperCase();

//             if (tempID.equals("X")) {
//                 menu.StaffPage();
//                 break;
//             } else {
//                 boolean found = false;
//                 for (Person person : info) {
//                     if (person instanceof Staff && person.getID().equals(tempID)) {
//                         found = true;
//                         System.out.println("");
//                         System.out.println("=================================");
//                         System.out.println("|       Staff Details           |");
//                         System.out.println("=================================");
//                         System.out.print(person);
//                         System.out.println("=================================");
//                         menu.StaffPage();
//                         break; // Exit the loop once a matching ID is found
//                     } else if (person instanceof Admin && person.getID().equals(tempID)) {
//                         found = true;
//                         System.out.println("");
//                         System.out.println("=================================");
//                         System.out.println("|       Admin Details           |");
//                         System.out.println("=================================");
//                         System.out.print(person);
//                         System.out.println("=================================");
//                         menu.StaffPage();
//                         break; // Exit the loop once a matching ID is found
//                     }
//                 }

//                 if (!found) {
//                     System.out.println("ID not found.");
//                     reset = 0;
//                 } else {
//                     reset = 1;
//                 }
//             }
//         } while (reset == 0);
//     }

//     public static void EditStaffOrAdmin() {
//         String tempID, newInfo, choice;
//         int reset = 0, invalid = 0;
//         MainMenu menu = new MainMenu();
//         Scanner sc = new Scanner(System.in);
//         ArrayList<Person> info = Person.read();

//         System.out.println("");
//         System.out.println("=================================");
//         System.out.println("|             Edit              |");
//         System.out.println("=================================");

//         do {
//             System.out.print("Enter Staff/Admin ID (X to exit): ");
//             tempID = sc.nextLine().toUpperCase();

//             if (tempID.equals("X")) {
//                 menu.StaffPage();
//                 break;
//             }

//             boolean found = false;
//             for (Person person : info) {
//                 if ((person instanceof Staff || person instanceof Admin) && person.getID().equals(tempID)) {
//                     found = true;
//                     System.out.println("=================================");
//                     System.out.println("|       Employee Details        |");
//                     System.out.println("=================================");
//                     System.out.print(person);
//                     System.out.println("=================================");

//                     do {
//                         System.out.println("");
//                         System.out.println("==============================");
//                         System.out.println("|    Choose One To Edit      |");
//                         System.out.println("==============================");
//                         System.out.println("|    1. Name                 |");
//                         System.out.println("|    2. Password             |");
//                         System.out.println("|    3. Email                |");
//                         System.out.println("|    4. Phone Number         |");
//                         System.out.println("|    5. Exit                 |");
//                         System.out.println("==============================");
//                         System.out.print("Enter your choice : ");
//                         choice = sc.nextLine();

//                         switch (choice) {
//                             case "1":
//                                 System.out.print("Enter New Name : ");
//                                 newInfo = sc.nextLine();
//                                 person.setName(newInfo);
//                                 invalid = 0;
//                                 break;
//                             case "2":
//                                 if (person instanceof Staff) {
//                                     Staff staff = (Staff) person;
//                                     boolean validPass = false;
//                                     String currentPass = "";
//                                     do {
//                                         System.out.print("Enter Current Password : ");
//                                         currentPass = sc.nextLine();
//                                         if (staff.validatePassword(currentPass)) {
//                                             validPass = true;
//                                         } else {
//                                             System.out.println("Wrong Password!!! Please Try Again.");
//                                         }
//                                     } while (!validPass);
//                                     System.out.print("Enter New Password : ");
//                                     newInfo = sc.nextLine();
//                                     staff.setPassword(newInfo);
//                                 } else if (person instanceof Admin) {
//                                     Admin admin = (Admin) person;
//                                     boolean validPass = false;
//                                     String currentPass = "";
//                                     do {
//                                         System.out.print("Enter Current Password : ");
//                                         currentPass = sc.nextLine();
//                                         if (admin.validatePassword(currentPass)) {
//                                             validPass = true;
//                                         } else {
//                                             System.out.println("Wrong Password!!! Please Try Again.");
//                                         }
//                                     } while (!validPass);
//                                     System.out.print("Enter New Password : ");
//                                     newInfo = sc.nextLine();
//                                     admin.setPassword(newInfo);
//                                 }
//                                 invalid = 0;
//                                 break;
//                             case "3":
//                                 boolean validEmail = false;
//                                 String newEmail = "";
//                                 do {
//                                     System.out.print("Enter New Email : ");
//                                     newEmail = sc.nextLine();
//                                     if (person.validateEmail(newEmail)) {
//                                         validEmail = true;
//                                     } else {
//                                         System.out.println("Invalid Email Format!!! Please Try Again.");
//                                     }
//                                 } while (!validEmail);
//                                 person.setEmail(newEmail);
//                                 invalid = 0;
//                                 break;
//                             case "4":
//                                 boolean validPhoneNo = false;
//                                 String newPhoneNo = "";
//                                 do {
//                                     System.out.print("Enter New Phone Number : ");
//                                     newPhoneNo = sc.nextLine();
//                                     if (person.ValidatePhoneNum(newPhoneNo)) {
//                                         validPhoneNo = true;
//                                     } else {
//                                         System.out.println("Invalid Phone Number Format!!! Please Try Again.");
//                                     }
//                                 } while (!validPhoneNo);
//                                 person.setPhoneNo(newPhoneNo);
//                                 invalid = 0;
//                                 break;
//                             case "5":
//                                 menu.StaffPage();
//                                 break;
//                             default:
//                                 System.out.println("Invalid Input!!!");
//                                 invalid = 1;
//                         }

//                     } while (invalid == 1);
//                 }
//             }

//             if (!found) {
//                 System.out.println("ID not found.");
//                 reset = 1;
//             } else {
//                 reset = 0;
//             }
//         } while (reset == 1);

//         // Write the updated data back to the file
//         Person.writeArray(info, "/Users/yutic/Documents/NetBeansProjects/DFT2(G1)-YuTickSang-IvanPekYuHeng-HooJunYong/person.txt");

//         System.out.println("==================================");
//         System.out.println("|     Update Successful!!!       |");
//         System.out.println("==================================");
//         menu.StaffPage();
//     }

//     public static void DeleteStaff(ArrayList<Person> info, String filePath) {
//         Scanner sc = new Scanner(System.in);
//         String confirm;
//         MainMenu menu = new MainMenu();

//         System.out.println("");
//         System.out.println("=====================================");
//         System.out.println("|              Delete               |");
//         System.out.println("=====================================");

//         boolean found = false;
//         do {
//             System.out.print("Enter Staff/Admin ID (X to exit): ");
//             String tempID = sc.nextLine().toUpperCase();

//             if (tempID.equals("X")) {
//                 menu.StaffPage(); // Exit the method
//                 break;
//             }

//             for (Person person : info) {
//                 if ((person instanceof Staff || person instanceof Admin) && person.getID().equals(tempID)) {
//                     String personType = (person instanceof Staff) ? "Staff" : "Admin";

//                     System.out.println("=================================");
//                     System.out.println("|        " + personType + " Details          |");
//                     System.out.println("=================================");
//                     System.out.print(person);
//                     System.out.println("=================================");

//                     do {
//                         System.out.print("Confirm Delete? (Y/N) : ");
//                         confirm = sc.nextLine().toUpperCase();

//                         if (confirm.equals("Y")) {
//                             info.remove(person);
//                             found = true;
//                             System.out.println("=====================================");
//                             System.out.println("|        Deleted Successfully       |");
//                             System.out.println("=====================================");
//                             Person.writeArray(info, filePath); // Save the updated list
//                             menu.StaffPage(); // Exit the method
//                             break;
//                         } else if (confirm.equals("N")) {
//                             menu.StaffPage(); // Exit the method
//                             break;
//                         } else {
//                             System.out.println("Invalid Input!!!");
//                         }
//                     } while (true); // Repeat confirmation loop until valid input
//                 }
//             }

//             if (!found) {
//                 System.out.println("ID not found.");
//             }
//         } while (true); // Repeat confirmation loop until valid input
//     }

//     public static void DisplayStaffOrAdmin() {
//         String choice;
//         int reset = 0;
//         Scanner sc = new Scanner(System.in);
//         MainMenu menu = new MainMenu();

//         System.out.println("");
//         System.out.println("=========================================");
//         System.out.println("|            Display Details            |");
//         System.out.println("=========================================");
//         System.out.println("|   1. Staff                            |");
//         System.out.println("|   2. Admin                            |");
//         System.out.println("|   3. Main Menu                        |");
//         System.out.println("|   4. Exit                             |");
//         System.out.println("=========================================");
//         do {
//             System.out.print("Your choice (1/2/3/4) : ");
//             choice = sc.nextLine();

//             switch (choice) {
//                 case "1":
//                     displayDetails("Staff");
//                     break;
//                 case "2":
//                     displayDetails("Admin");
//                     break;
//                 case "3":
//                     menu.AdminMenu();
//                     break;
//                 case "4":
//                     System.exit(0);
//                 default:
//                     System.out.println("Invalid Input!!! Please Try Again.");
//                     reset = 1;
//             }
//         } while (reset == 1);
//     }

//     private static void displayDetails(String type) {
//         ArrayList<Person> info = Person.read();
//         MainMenu menu = new MainMenu();

//         // Determine the table header based on the type (Staff or Admin)
//         String header = (type.equals("Staff"))
//                 ? "========================================================================================\n|                                  Staff Details                                       |\n========================================================================================"
//                 : "========================================================================================\n|                                  Admin Details                                       |\n========================================================================================";

//         // Print the table header
//         System.out.println(header);
//         System.out.printf("| %-10s | %-20s | %-30s | %-15s |\n", "ID", "Name", "Email", "Phone Number");
//         System.out.println("========================================================================================");

//         // Iterate through the staff or admin list and print each member's details
//         for (Person person : info) {
//             if ((type.equals("Staff") && person instanceof Staff) || (type.equals("Admin") && person instanceof Admin)) {
//                 System.out.printf("| %-10s | %-20s | %-30s | %-15s |\n",
//                         person.getID(), person.getName(), person.getEmail(), person.getPhoneNo());
//             }
//         }

//         // Print the table footer
//         System.out.println("========================================================================================");
//         menu.StaffPage();
//     }

// }
