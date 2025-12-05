package Assignment.Controller;

import Assignment.Model.Cart;
import Assignment.Model.CartItem;
import Assignment.Model.Product;
import Assignment.Model.ProductDAO;
import Assignment.View.ProductView;
import java.util.ArrayList;

public class ProductController {

    private ProductView view;

    public ProductController() {
        this.view = new ProductView();
    }

    public Product selectProductForOrder(Cart cart) {
        ArrayList<Product> productList = ProductDAO.readAll();

        // Adjust quantities based on what's in cart
        if (cart != null && !cart.getCartlist().isEmpty()) {
            for (Product dbProduct : productList) {
                for (CartItem item : cart.getCartlist()) {
                    if (dbProduct.getProductID().equals(item.getCartProd().getProductID())) {
                        int currentDbQty = dbProduct.getQuantity();
                        int inCartQty = item.getQuantity();
                        int effectiveStock = currentDbQty - inCartQty;
                        dbProduct.setQuantity(effectiveStock < 0 ? 0 : effectiveStock);
                    }
                }
            }
        }

        view.displayProductTable(productList);

        int choice = view.getProductSelection(productList.size());

        if (choice == 0) {
            return null;
        }
        return productList.get(choice - 1);
    }

    public int getQuantityForProduct(int availableStock) {
        return view.getQuantityInput(availableStock);
    }

    public ArrayList<Product> getAllProducts() {
        return ProductDAO.readAll();
    }
}