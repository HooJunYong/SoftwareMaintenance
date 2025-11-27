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
public class Payment {
    protected double discount;
   final double taxRate = 0.06; // 6% tax rate - should be configurable
   protected Cart cart;
   protected String paymentMethod;
   
   
   public Payment(Cart cart){
       this.cart = cart;
   }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
   
    public double calDiscount(){
        return cart.CalcSubtotal() * discount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    
    
    public double calTaxRate(){
         return cart.CalcSubtotal() * taxRate;
    }
   
    public double calTotal(){
        return cart.CalcSubtotal() - (calDiscount())+ (calTaxRate());
    }
   
    
   
}
