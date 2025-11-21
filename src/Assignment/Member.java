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
public class Member extends Person {

    public Member() {
    }

    public Member(String ID, String name, String email, String phoneNo) {
        super(ID, name, email, phoneNo);
    }

    @Override
    boolean validateID(String tempID) {
        boolean valid = false;
        ArrayList<Person> data = Person.read();

        for (Person person : data) {
            if (person instanceof Member && person.getID().equals(tempID)) {
                valid = true;
                break;
            }
        }

        return valid;
    }

    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("|Member ID   : %-17s|\n", ID));
        builder.append(String.format("|Member Name : %-17s|\n", name));
        builder.append(String.format("|Email       : %-17s|\n", email));
        builder.append(String.format("|Phone       : %-17s|\n", phoneNo));
        return builder.toString();
    }

}
