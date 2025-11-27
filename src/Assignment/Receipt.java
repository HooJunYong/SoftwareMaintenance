/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.prefs.Preferences;

/**
 *
 * @author yutic
 */
public class Receipt {

    private String invoiceID;
    private String createdDateTime;

    public Receipt() {
        Preferences prefs;
        prefs = Preferences.userRoot().node("Receipt");
        int currentID = prefs.getInt("ReceiptID", 1000); // Retrieve the current ID
        int newID = currentID + 1; // Increment the ID
        prefs.putInt("ReceiptID", newID); // Save the new ID back to preferences
        invoiceID = 'I' + String.valueOf(newID); // Use the new ID as invoiceID
        LocalDateTime dateNow = LocalDateTime.now();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        createdDateTime = dateNow.format(timeFormat);
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

}
