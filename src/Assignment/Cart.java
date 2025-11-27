/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment;

import java.util.ArrayList;

/**
 *
 * @author yutic
 */
public class Cart {

    private ArrayList<CartItem> cartlist; // Create a list to store cart items

    public Cart() {
        cartlist = new ArrayList<>();

    }

    public ArrayList<CartItem> getCartlist() {
        return cartlist;
    }

    public double CalcSubtotal() {
        double subtotal = 0;
        for (int i = 0; i < cartlist.size(); i++) {
            subtotal += cartlist.get(i).CalcTotalProdPrice();
        }
        return subtotal;
    }

    public void addToCart(Product product, int quantity) {
        // Check if the product is already in the cart
        CartItem existingCartItem = null;
        for (CartItem item : cartlist) {
            if (item.getCartProd().getProductID().equals(product.getProductID())) {
                // Product already in the cart, update the quantity

                existingCartItem = item;
                break; // Exit the loop since the product was found
            }
        }
        if (existingCartItem != null) {
            // Update the quantity of the existing item
            existingCartItem.setQuantity( quantity);
        } else {
            // Product is not in the cart, so add it with the specified quantity
            CartItem cartItem = new CartItem(product, quantity);
            cartlist.add(cartItem);
        }

    }

    public int CalcTotalQty() {
        int totalQty = 0;
        for (int i = 0; i < cartlist.size(); i++) {
            totalQty += cartlist.get(i).getQuantity();
        }

        return totalQty;

    }

    public void viewCart() {
        System.out.println("");
        System.out.println("=======================================================================");
        System.out.println("|                                CART                                 | ");
        System.out.println("=======================================================================");
        System.out.println("");
        System.out.println("=======================================================================");
        System.out.printf("|%-9s %-20s %-13s %-11s %-12s|\n", "No.", "Product", "Price", "Quantity", "Total Price");
        System.out.println("=======================================================================");
        for (int i = 0; i < cartlist.size(); i++) {
            System.out.printf("|%-4s %-23s RM %-15s %-11s RM%7s|\n", i + 1, cartlist.get(i).getCartProd().getProductName(), cartlist.get(i).getCartProd().getPrice(), cartlist.get(i).getQuantity(), cartlist.get(i).CalcTotalProdPrice());

        }

        System.out.println("=======================================================================");
        System.out.printf("|%58s RM %7.2f|\n", "Subtotal =", CalcSubtotal(), "");
        System.out.println("=======================================================================");
    }

    public void viewPayment() {
        System.out.println("");
        System.out.println("=======================================================================");
        System.out.println("|                              PAYMENT                                | ");
        System.out.println("=======================================================================");
        System.out.printf("|%-9s %-20s %-13s %-11s %-12s|\n", "No.", "Product", "Price", "Quantity", "Total Price");
        System.out.println("=======================================================================");
        for (int i = 0; i < cartlist.size(); i++) {
            System.out.printf("|%-4s %-23s RM %-15s %-11s RM%7s|\n", i + 1, cartlist.get(i).getCartProd().getProductName(), cartlist.get(i).getCartProd().getPrice(), cartlist.get(i).getQuantity(), cartlist.get(i).CalcTotalProdPrice());

        }
    }

    public void viewReceipt() {

        System.out.println("=======================================================================");
        System.out.printf("|%-9s %-20s %-13s %-11s %-12s|\n", "No.", "Product", "Price", "Quantity", "Total Price");
        System.out.println("=======================================================================");
        for (int i = 0; i < cartlist.size(); i++) {
            System.out.printf("|%-4s %-23s RM %-15s %-11s RM%7s|\n", i + 1, cartlist.get(i).getCartProd().getProductName(), cartlist.get(i).getCartProd().getPrice(), cartlist.get(i).getQuantity(), cartlist.get(i).CalcTotalProdPrice());

        }
    }
}
