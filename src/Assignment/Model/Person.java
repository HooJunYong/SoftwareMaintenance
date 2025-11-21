/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment.Model;

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
public class Person {

    String ID;
    String name;
    String email;
    String phoneNo;

    public boolean validateID(String tempID) {
        return false;
    }

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

    

    public static String getNextID(String type) {
        try {
            String filePath = "/Users/yutic/Documents/NetBeansProjects/DFT2(G1)-YuTickSang-IvanPekYuHeng-HooJunYong/person.txt";
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
