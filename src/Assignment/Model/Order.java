package Assignment.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {
    private String orderID;
    private String invoiceNo;
    private String memberID;
    private String cashierID;
    private LocalDateTime orderDate;
    private double subtotal;
    private double discountAmount;
    private double taxAmount;
    private double grandTotal;
    private String paymentMethod;
    private ArrayList<OrderItem> orderItems;

    public Order() {
        this.orderItems = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
    }

    // Getters and Setters
    public String getOrderID() { return orderID; }
    public void setOrderID(String orderID) { this.orderID = orderID; }

    public String getInvoiceNo() { return invoiceNo; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }

    public String getMemberID() { return memberID; }
    public void setMemberID(String memberID) { this.memberID = memberID; }

    public String getCashierID() { return cashierID; }
    public void setCashierID(String cashierID) { this.cashierID = cashierID; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }

    public double getTaxAmount() { return taxAmount; }
    public void setTaxAmount(double taxAmount) { this.taxAmount = taxAmount; }

    public double getGrandTotal() { return grandTotal; }
    public void setGrandTotal(double grandTotal) { this.grandTotal = grandTotal; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public ArrayList<OrderItem> getOrderItems() { return orderItems; }
    public void addOrderItem(OrderItem item) { this.orderItems.add(item); }
}