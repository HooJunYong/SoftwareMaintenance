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
public class Staff extends Person {

    protected String password;

    public Staff() {

    }

    public Staff(String ID, String password, String name, String phoneNo, String email) {
        super(ID, name, phoneNo, email);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public boolean validateID(String tempID) {
        boolean valid = false;
        ArrayList<Person> data = Person.read();

        for (Person person : data) {
            if (person instanceof Staff && person.getID().equals(tempID)) {
                valid = true;
                break;
            }
        }

        return valid;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("|Staff ID   : %-18s|\n", ID));
        builder.append(String.format("|Staff Name : %-18s|\n", name));
        builder.append(String.format("|Email      : %-18s|\n", email));
        builder.append(String.format("|Phone No   : %-18s|\n", phoneNo));
        return builder.toString();
    }

}
