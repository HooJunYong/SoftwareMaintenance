package Assignment.Service;

import Assignment.Model.Cart;
import Assignment.Model.CartItem;
import Assignment.Model.Product;
import Assignment.Model.ProductDAO;

public class InventoryService implements InventoryServiceInterface {

    @Override
    public void updateStock(Cart cart) {
        for (CartItem item : cart.getCartList()) {
            Product product = item.getProduct();
            int purchasedQuantity = item.getQuantity();
            int currentStock = product.getQuantity();

            product.setQuantity(currentStock - purchasedQuantity);
            ProductDAO.update(product);
        }
    }

    public boolean hasEnoughStock(Product product, int requestedQuantity) {
        return product.getQuantity() >= requestedQuantity;
    }
}