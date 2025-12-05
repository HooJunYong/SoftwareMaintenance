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
   
    public double calculateDiscount() {
        return cart.calculateSubtotal() * discount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public double calculateTax() {
        double afterDiscount = cart.calculateSubtotal() - calculateDiscount();
        return afterDiscount * AppConfig.getTaxRate();
    }
   
    public double calculateTotal() {
        double subtotal = cart.calculateSubtotal();
        double discountAmount = calculateDiscount();
        double tax = calculateTax();
        return subtotal - discountAmount + tax;
    }
    
    // Get tax rate for display purposes
    public double getTaxRateValue() {
        return AppConfig.getTaxRate();
    }
}