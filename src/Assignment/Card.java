/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment;

/**
 *
 * @author yutic
 */
public class Card extends Payment{
    private String cardNumber;
    private String cvv;

  
    public Card(Cart cart) {
        super(cart);
          this.setPaymentMethod("Credit Card");
    }

    

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    
    public  boolean isValidCardNumber(String cardNumber) {
        // Remove spaces and check if it's all digits
        cardNumber = cardNumber.replaceAll("\\s", "");
        if (!cardNumber.matches("\\d+")) {
            return false;
        }

        // Check if the card number has exactly 16 digits 
        return cardNumber.length() == 16;
    }
    
     public  boolean isValidCVV(String cvv) {
        // Remove spaces and check if it's all digits
        cvv = cvv.replaceAll("\\s", "");
        if (!cvv.matches("\\d+")) {
            return false;
        }

        // Check if the CVV has exactly 3 digits 
        return cvv.length() == 3;
    }
    
    
}
