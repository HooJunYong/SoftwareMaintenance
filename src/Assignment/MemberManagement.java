/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import Assignment.Model.Member;
import Assignment.Model.Person;
import Assignment.Model.Staff;
/**
 *
 * @author User
 */
public class MemberManagement {

    public static void AddMember() {
        String memberID;
        String name;
        String phoneNo;
        String email;
        String confirm;
        int reset = 0;
        Scanner sc = new Scanner(System.in);
        MainMenu menu = new MainMenu();

        System.out.println("");
        System.out.println("==============================");
        System.out.println("|        REGISTRATION        |");
        System.out.println("==============================");
        do {
            System.out.print("Add new Member? (Y/N) : ");
            confirm = sc.nextLine().toUpperCase();
            switch (confirm) {
                case "Y":
                    memberID = Person.getNextID("Member");
                    System.out.println("| Member ID   : " + memberID);
                    System.out.print("| Enter Name  : ");
                    name = sc.nextLine();
                    do {
                        System.out.print("| Enter Phone Number : ");
                        phoneNo = sc.nextLine();
                        if (!(Person.ValidatePhoneNum(phoneNo))) {
                            System.out.println("Invalid Phone Number Format!!! Please Try Again.");
                        }
                    } while (!(Person.ValidatePhoneNum(phoneNo)));
                    do {
                        System.out.print("| Enter Email        : ");
                        email = sc.nextLine();
                        if (!(Person.validateEmail(email))) {
                            System.out.println("Invalid Email Format!!! Please Try Again.");
                        }
                    } while (!(Staff.validateEmail(email)));
                    Member newMember = new Member(memberID, name, email, phoneNo);
                    Person.writeDataToFile(newMember, "/Users/yutic/Documents/NetBeansProjects/DFT2(G1)-YuTickSang-IvanPekYuHeng-HooJunYong/person.txt");
                    System.out.println("==============================");
                    System.out.println("| Member Added Successfully! |");
                    System.out.println("==============================");
                case "N":
                    menu.MemberPage();
                    break;
                default:
                    System.out.println("Invalid Input!!! Please Try Again.");
                    reset = 1;
            }
        } while (reset == 1);

    }

    public static void SearchMember() {
        int reset = 0;
        String tempID;
        Member member = new Member();
        MainMenu menu = new MainMenu();
        Scanner sc = new Scanner(System.in);
        ArrayList<Person> info = Person.read();
        System.out.println("");
        System.out.println("=================================");
        System.out.println("|         Search Member         |");
        System.out.println("=================================");
        do {
            System.out.print("Enter Member ID (X to exit) : ");
            tempID = sc.nextLine().toUpperCase();
            if (tempID.equals("X")) {
                menu.MemberPage();
                break;
            } else {
                boolean found = false;
                for (Person person : info) {
                    if (person instanceof Member && person.getID().equals(tempID)) {
                        found = true;
                        System.out.println("");
                        System.out.println("=================================");
                        System.out.println("|         Member Details        |");
                        System.out.println("=================================");
                        System.out.print(person);
                        System.out.println("=================================");
                        menu.MemberPage();
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Member ID not found.");
                    reset = 0;
                } else {
                    reset = 1;
                }
            }
        } while (reset == 0);
    }

    public static void EditMember() {
        String tempID, newInfo, choice;
        int reset = 0, invalid = 0;
        Member member = new Member();
        MainMenu menu = new MainMenu();
        Scanner sc = new Scanner(System.in);
        ArrayList<Person> info = Person.read();
        System.out.println("");
        System.out.println("=================================");
        System.out.println("|         Edit Member           |");
        System.out.println("=================================");
        do {
            System.out.print("Enter Member ID(X to exit): ");
            tempID = sc.nextLine().toUpperCase();
            if (tempID.equals("X")) {
                menu.MemberPage();
                break;
            }

            boolean found = false;
            for (Person person : info) {
                if ((person instanceof Member) && person.getID().equals(tempID)) {
                    found = true;
                    System.out.println("=================================");
                    System.out.println("|         Member Details        |");
                    System.out.println("=================================");
                    System.out.print(person);
                    System.out.println("=================================");

                    do {
                        System.out.println("");
                        System.out.println("==============================");
                        System.out.println("|    Choose One To Edit      |");
                        System.out.println("==============================");
                        System.out.println("|    1. Name                 |");
                        System.out.println("|    2. Email                |");
                        System.out.println("|    3. Phone Number         |");
                        System.out.println("|    4. Exit                 |");
                        System.out.println("==============================");
                        System.out.print("Enter your choice : ");
                        choice = sc.nextLine();

                        switch (choice) {
                            case "1":
                                System.out.print("Enter New Member Name : ");
                                newInfo = sc.nextLine();
                                person.setName(newInfo);
                                invalid = 0;
                                break;
                            case "2":
                                boolean validEmail = false;
                                String newEmail = "";
                                do {
                                    System.out.print("Enter New Email : ");
                                    newEmail = sc.nextLine();
                                    if (person.validateEmail(newEmail)) {
                                        validEmail = true;
                                    } else {
                                        System.out.println("Invalid Email Format!!! Please Try Again.");
                                    }
                                } while (!validEmail);
                                person.setEmail(newEmail);
                                invalid = 0;
                                break;
                            case "3":
                                boolean validPhoneNo = false;
                                String newPhoneNo = "";
                                do {
                                    System.out.print("Enter New Phone Number : ");
                                    newPhoneNo = sc.nextLine();
                                    if (person.ValidatePhoneNum(newPhoneNo)) {
                                        validPhoneNo = true;
                                    } else {
                                        System.out.println("Invalid Phone Number Format!!! Please Try Again.");
                                    }
                                } while (!validPhoneNo);
                                person.setPhoneNo(newPhoneNo);
                                invalid = 0;
                                break;
                            case "4":
                                menu.MemberPage();
                                break;
                            default:
                                System.out.println("Invalid Input!!!");
                                invalid = 1;
                        }
                    } while (invalid == 1);
                }
            }
            if (!found) {
                System.out.println("Member ID not found.");
                reset = 1;
            } else {
                reset = 0;
            }
        } while (reset == 1);
        Person.writeArray(info, "/Users/yutic/Documents/NetBeansProjects/DFT2(G1)-YuTickSang-IvanPekYuHeng-HooJunYong/person.txt");
        System.out.println("==================================");
        System.out.println("|     Member details updated!    |");
        System.out.println("==================================");
        menu.MemberPage();
    }

    public static void DeleteMember(ArrayList<Person> info, String filePath) {
        String tempID;
        String confirm;
        boolean found = false;
        MainMenu menu = new MainMenu();
        Scanner sc = new Scanner(System.in);

        System.out.println("");
        System.out.println("=====================================");
        System.out.println("|           Delete Member           |");
        System.out.println("=====================================");

        do {
            System.out.print("Enter Member ID (X to exit): ");
            tempID = sc.nextLine().toUpperCase();

            if (tempID.equals("X")) {
                menu.MemberPage();
                break;
            }

            for (Person person : info) {
                if ((person instanceof Member) && person.getID().equals(tempID)) {
                    System.out.println("=================================");
                    System.out.println("|         Member Details        |");
                    System.out.println("=================================");
                    System.out.print(person);
                    System.out.println("=================================");
                    do {
                        System.out.print("Confirm Delete? (Y/N) : ");
                        confirm = sc.nextLine().toUpperCase();

                        if (confirm.equals("Y")) {
                            info.remove(person);
                            found = true;
                            System.out.println("=====================================");
                            System.out.println("|        Deleted Successfully       |");
                            System.out.println("=====================================");
                            Person.writeArray(info, filePath);
                            menu.MemberPage();
                            break;
                        } else if (confirm.equals("N")) {
                            menu.MemberPage();
                            break;
                        } else {
                            System.out.println("Invalid Input!!!");
                        }
                    } while (true);
                }
            }

            if (!found) {
                System.out.println("Member ID not found.");
            }
        } while (true);
    }

    public static void DisplayMember() {
        String choice;
        int reset = 0;
        Scanner sc = new Scanner(System.in);
        ArrayList<Person> info = Person.read();
        MainMenu menu = new MainMenu();
        // Print the table header
        System.out.println("");
        System.out.println("==============================================================================");
        System.out.println("|                                Member Details                              |");
        System.out.println("==============================================================================");
        System.out.printf("| %-10s | %-20s | %-20s | %-15s |\n", "Member ID", "Name", "Email", "Phone Number");
        System.out.println("==============================================================================");

        // Iterate through the staff list and print each staff member's details
        for (Person person : info) {
            if (person instanceof Member) {
                System.out.printf("| %-10s | %-20s | %-20s | %-15s |\n",
                        person.getID(), person.getName(), person.getEmail(), person.getPhoneNo());
            }

        }

        // Print the table footer
        System.out.println("==============================================================================");
        System.out.println("==================================");
        System.out.println("|Please select an option below.  |");
        System.out.println("==================================");
        System.out.println("| 1 Member Menu                  |");
        System.out.println("| 2 Main Menu                    |");
        System.out.println("| 3 Exit                         |");
        System.out.println("==================================");
        do {
            System.out.print("Your choice (1/2/3) : ");
            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    menu.MemberPage();
                    break;
                case "2":
                    menu.StaffMenu();
                    break;
                case "3":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Input!!! Please Try Again.");
                    reset = 1;
            }
        } while (reset == 1);
    }
}
