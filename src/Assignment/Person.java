/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author User
 */
abstract class Person {

    String ID;
    String name;
    String email;
    String phoneNo;

    abstract boolean validateID(String tempID);

    public Person() {

    }

    public Person(String ID, String name, String email, String phoneNo) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static boolean ValidatePhoneNum(String phoneNum) {
        boolean isNum = false;
        if (phoneNum.length() == 10) {
            for (int i = 0; i < phoneNum.length(); i++) {
                if (Character.isDigit(phoneNum.charAt(i))) {
                    isNum = true;
                } else {
                    isNum = false;
                    break;
                }

            }
        } else {
            isNum = false;
        }
        return isNum;
    }

    public static boolean validateEmail(String email) {
        boolean isValid = false;

        if (email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    public static ArrayList<Person> read() {
        ArrayList<Person> data = new ArrayList<>();
        try {
            File staffFile = new File("C:\\Users\\Acer\\Downloads\\DFT2(G1)-YuTickSang-IvanPekYuHeng-HooJunYong\\person.txt");
            Scanner sc = new Scanner(staffFile);
            Staff staff;
            while (sc.hasNextLine()) {
                String[] text = sc.nextLine().split("\\,");
                if (text[0].indexOf("M") != -1) {
                    data.add(new Member(text[0], text[1], text[2], text[3]));
                } else if (text[0].indexOf("S") != -1) {
                    data.add(new Staff(text[0], text[1], text[2], text[3], text[4]));
                } else if (text[0].indexOf("A") != -1) {
                    data.add(new Admin(text[0], text[1], text[2], text[3], text[4]));
                }
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error had occured");
        }

        if (data.get(0) instanceof Staff) {
            Staff firstStaff = (Staff) data.get(0);
        } else if (data.get(0) instanceof Member) {
            Member fistMember = (Member) data.get(0);
        } else {
            Admin firstAdmin = (Admin) data.get(0);
        }

        return data;
    }

    public static void writeDataToFile(Person person, String filePath) {
        ArrayList<Person> info = read(); // Read existing data
        info.add(person); // Add the new person to the list

        try {
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file, false); // Use false to overwrite the file
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (Person p : info) {
                // Check if it's a Staff or Member and format the data accordingly
                if (p instanceof Staff) {
                    // Staff and Admin have a password field
                    printWriter.println(p.getID() + "," + ((Staff) p).getPassword() + "," + p.getName() + "," + p.getEmail() + "," + p.getPhoneNo());
                } else if (p instanceof Admin) {
                    printWriter.println(p.getID() + "," + ((Admin) p).getPassword() + "," + p.getName() + "," + p.getEmail() + "," + p.getPhoneNo());
                } else if (p instanceof Member) {
                    // Member does not have a password
                    printWriter.println(p.getID() + "," + p.getName() + "," + p.getEmail() + "," + p.getPhoneNo());
                }
            }

            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error had occurred");
        }
    }

    public static void writeArray(ArrayList<Person> info, String filePath) {
        try {
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file, false); // Use false to overwrite the file
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (Person person : info) {
                // Check if it's a Staff or Admin and format the data accordingly
                if (person instanceof Staff) {
                    // Staff and Admin have a password field
                    printWriter.println(person.getID() + "," + ((Staff) person).getPassword() + "," + person.getName() + "," + person.getEmail() + "," + person.getPhoneNo());
                } else if (person instanceof Admin) {
                    printWriter.println(person.getID() + "," + ((Admin) person).getPassword() + "," + person.getName() + "," + person.getEmail() + "," + person.getPhoneNo());
                } else if (person instanceof Member) {
                    // Member does not have a password
                    printWriter.println(person.getID() + "," + person.getName() + "," + person.getEmail() + "," + person.getPhoneNo());
                }
            }

            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error had occurred");
        }
    }

    public static String getNextID(String type) {
        try {
            String filePath = "C:\\Users\\Acer\\Downloads\\DFT2(G1)-YuTickSang-IvanPekYuHeng-HooJunYong\\person.txt";
            File staffFile = new File(filePath);
            staffFile.createNewFile();
            Scanner sc = new Scanner(staffFile);
            ArrayList<String> staffIDs = new ArrayList<>();
            ArrayList<String> adminIDs = new ArrayList<>();
            ArrayList<String> memberIDs = new ArrayList<>();
            String lastStaffID = "";
            String lastAdminID = "";
            String lastMemberID = "";
            String line;

            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String[] data = line.split(",");
                if (data[0].startsWith("S")) {
                    staffIDs.add(data[0]);
                    lastStaffID = data[0];
                } else if (data[0].startsWith("A")) {
                    adminIDs.add(data[0]);
                    lastAdminID = data[0];
                } else if (data[0].startsWith("M")) {
                    memberIDs.add(data[0]);
                    lastMemberID = data[0];
                }
            }

            int nextID;

            if (type.equals("Staff")) {
                // If adding a new staff, increment the staff ID
                nextID = Integer.parseInt(lastStaffID.substring(1)) + 1;
            } else if (type.equals("Admin")) {
                // If adding a new admin, increment the admin ID
                nextID = Integer.parseInt(lastAdminID.substring(1)) + 1;
            } else if (type.equals("Member")) {
                // If adding a new member, increment the member ID
                nextID = Integer.parseInt(lastMemberID.substring(1)) + 1;
            } else {
                // Invalid type, return null
                return null;
            }

            // Format the next ID with leading zeros
            String formattedNextID;
            switch (type) {
                case "Staff":
                    formattedNextID = "S" + String.format("%03d", nextID);
                    break;
                case "Admin":
                    formattedNextID = "A" + String.format("%03d", nextID);
                    break;
                case "Member":
                    formattedNextID = "M" + String.format("%03d", nextID);
                    break;
                default:
                    formattedNextID = null;
            }

            sc.close();

            return formattedNextID;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error had occurred");
            return null;
        }
    }

    @Override
    public String toString() {
        return "Person{" + "ID=" + ID + ", name=" + name + ", email=" + email + ", phoneNo=" + phoneNo + '}';
    }

}
