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
public class Cash extends Payment {

    private double amount;

    public Cash(Cart cart) {
        super(cart);
        this.setPaymentMethod("Cash");
        
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double change(double totalAfterDiscount) {
        return amount - totalAfterDiscount;
    }

    public boolean validateAmount(double money) {
        boolean isValid = false;
        if (money >= calTotal()) {
           
            isValid = true;

        }
        return isValid;
    }
}
