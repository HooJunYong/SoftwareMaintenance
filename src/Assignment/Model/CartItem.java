package Assignment.Model;

public class CartItem {
    private Product cartProd;
    private int quantity;

    public CartItem(Product cartProd, int quantity) {
        this.cartProd = cartProd;
        this.quantity = quantity;
    }

    public Product getCartProd() {
        return cartProd;
    }

    public void setCartProd(Product cartProd) {
        this.cartProd = cartProd;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double calculateItemTotal() {
        return cartProd.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format("%-17s %-29s %-15.2f %-13d %-10.2f",
                cartProd.getProductID(),
                cartProd.getProductName(),
                cartProd.getPrice(),
                quantity,
                calculateItemTotal());
    }
}