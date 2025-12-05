package Assignment.Model;

public class ReceiptData extends Receipt {
    
    private Cart cart;
    private Payment payment;
    private Member member;
    private String cashierID;
    private double subtotal;
     private double money;
    private double change;
    private String paymentMethod;

    public ReceiptData() {
        super(); // Calls Receipt constructor - generates invoiceID and dateTime
    }

    // Getters and Setters
    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }

    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public String getCashierID() { return cashierID; }
    public void setCashierID(String cashierID) { this.cashierID = cashierID; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getMoney() { return money; }
    public void setMoney(double money) { this.money = money; }

    public double getChange() { return change; }
    public void setChange(double change) { this.change = change; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}