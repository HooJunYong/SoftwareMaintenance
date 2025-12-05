package Assignment.Model;

public class OrderItem {

    private int orderItemID;
    private String orderID;
    private String productID;
    private int quantity;

    public OrderItem() {
    }

     public OrderItem(String productID, int quantity) {
        this.productID = productID;
        this.quantity = quantity;
        
    }

    // Getters and Setters
    public int getOrderItemID() {
        return orderItemID;
    }

    public void setOrderItemID(int orderItemID) {
        this.orderItemID = orderItemID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
