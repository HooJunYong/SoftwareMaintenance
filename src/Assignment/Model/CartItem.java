package Assignment.Model;

public class CartItem {
    
    
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem() {
    }

    public Product getProduct() {
        return product;
    }
 
    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double calculateItemTotal() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format("%-17s %-29s %-15.2f %-13d %-10.2f",
                product.getProductID(),
                product.getProductName(),
                product.getPrice(),
                quantity,
                calculateItemTotal());
    }
}