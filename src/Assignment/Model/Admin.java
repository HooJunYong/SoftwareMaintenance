/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment.Model;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Admin extends Person{
    protected String password;
    
    public Admin() {
        
    }

    public Admin(String ID, String password, String name, String email, String phoneNo) {
        super(ID, name, email, phoneNo);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    
    
    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("|Admin ID   : %-18s|\n", ID));
        builder.append(String.format("|Admin Name : %-18s|\n", name));
        builder.append(String.format("|Email      : %-18s|\n", email));
        builder.append(String.format("|Phone No   : %-18s|\n", phoneNo));
        return builder.toString();
    }
}
