package Assignment.Model;

import java.util.ArrayList;

public class Cart {
    private ArrayList<CartItem> cartList;

    public Cart() {
        this.cartList = new ArrayList<>();
    }

    public ArrayList<CartItem> getCartlist() {
        return cartList;
    }

    public void addItem(CartItem item) {
        // Check if product already exists in cart
        for (CartItem existingItem : cartList) {
            if (existingItem.getCartProd().getProductID().equals(item.getCartProd().getProductID())) {
                // Update quantity instead of adding new item
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                return;
            }
        }
        cartList.add(item);
    }

    public void removeItem(int index) {
        if (index >= 0 && index < cartList.size()) {
            cartList.remove(index);
        }
    }

    public void updateItemQuantity(int index, int newQuantity) {
        if (index >= 0 && index < cartList.size()) {
            cartList.get(index).setQuantity(newQuantity);
        }
    }

    public void clearCart() {
        cartList.clear();
    }

    public boolean isEmpty() {
        return cartList.isEmpty();
    }

    public int getItemCount() {
        return cartList.size();
    }

    public double calculateSubtotal() {
        double subtotal = 0;
        for (CartItem item : cartList) {
            subtotal += item.calculateItemTotal();
        }
        return subtotal;
    }

    public int CalcTotalQty() {
        int total = 0;
        for (CartItem item : cartList) {
            total += item.getQuantity();
        }
        return total;
    }

    public CartItem getItem(int index) {
        if (index >= 0 && index < cartList.size()) {
            return cartList.get(index);
        }
        return null;
    }
}