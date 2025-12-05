package Assignment.Model;

import Assignment.Config.AppConfig;

public class Payment {
    protected double discount;
    protected Cart cart;
    protected String paymentMethod;
   
    public Payment(Cart cart) {
        this.cart = cart;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
   
    public double calDiscount() {
        return cart.calculateSubtotal() * discount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public double calTaxRate() {
        return cart.calculateSubtotal() * AppConfig.getTaxRate();
    }
   
    public double calTotal() {
        return cart.calculateSubtotal() - calDiscount() + calTaxRate();
    }
    
    // Get tax rate for display purposes
    public double getTaxRateValue() {
        return AppConfig.getTaxRate();
    }
}