/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment.Model;

/**
 *
 * @author yutic
 */
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

    public int getQuantity() {
        return quantity;
    }

    public void setCartProd(Product cartProd) {
        this.cartProd = cartProd;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double CalcTotalProdPrice(){
        double productPrice = Double.parseDouble(cartProd.getPrice());
                
        return productPrice * quantity;
    }
    

}