package Assignment.Controller;

import Assignment.Model.Cart;
import Assignment.Model.CartItem;
import Assignment.Model.Product;
import Assignment.Model.ProductDAO;
import Assignment.View.CartView;

public class CartController {

    private Cart cart;
    private CartView view;
    private ProductController productController;

    public CartController() {
        this.cart = new Cart();
        this.view = new CartView();
        this.productController = new ProductController();
    }

    public Cart getCart() {
        return cart;
    }

    public boolean addProductToCart() {
        Product selectedProduct = productController.selectProductForOrder(cart);

        if (selectedProduct == null) {
            return false; // User cancelled
        }

        int availableStock = getAvailableStock(selectedProduct.getProductID());

        if (availableStock <= 0) {
            view.displayError("This product is out of stock.");
            return false;
        }

        int quantity = productController.getQuantityForProduct(availableStock);

        if (quantity > 0) {
            CartItem item = new CartItem(selectedProduct, quantity);
            cart.addItem(item);
            view.displaySuccess("Added " + quantity + "x " + selectedProduct.getProductName() + " to cart.");
            return true;
        }
        return false;
    }

    public void editItemQuantity() {
        if (cart.isEmpty()) {
            view.displayEmptyCartWarning();
            return;
        }

        view.displayCartTable(cart);
        int selection = view.getItemSelection(cart.getItemCount());

        if (selection == 0) {
            return; // cancelled
        }

        CartItem item = cart.getItem(selection - 1);
        if (item == null) {
            view.displayError("Invalid selection.");
            return;
        }

        // Calculate max available stock
        int dbStock = getProductStockFromDB(item.getCartProd().getProductID());
        int otherCartQty = getQuantityInCartExcluding(item.getCartProd().getProductID(), selection - 1);
        int maxAvailable = dbStock - otherCartQty;

        int newQty = view.getNewQuantity(item.getQuantity(), maxAvailable);

        if (newQty > 0) {
            cart.updateItemQuantity(selection - 1, newQty);
            view.displaySuccess("Quantity updated successfully.");
        }
    }

    public void removeItem() {
        if (cart.isEmpty()) {
            view.displayEmptyCartWarning();
            return;
        }

        view.displayCartTable(cart);
        int selection = view.getItemSelection(cart.getItemCount());

        if (selection == 0) {
            return; // cancelled
        }

        CartItem item = cart.getItem(selection - 1);
        if (item != null && view.confirmAction("remove " + item.getCartProd().getProductName())) {
            cart.removeItem(selection - 1);
            view.displaySuccess("Item removed from cart.");
        }
    }

    public void clearCart() {
        cart.clearCart();
    }

    public void displayCart() {
        view.displayCartTable(cart);
    }

    public boolean processCartMenu() {
        boolean continueOrdering = true;

        while (continueOrdering) {
            view.displayCartTable(cart);
            int choice = view.displayCartMenu();

            switch (choice) {
                case 1: // Add more products
                    addProductToCart();
                    break;
                case 2: // Edit quantity
                    editItemQuantity();
                    break;
                case 3: // Remove item
                    removeItem();
                    break;
                case 4: // Clear cart
                    clearCart();
                    break;
                case 5: // Proceed to checkout
                    if (cart.isEmpty()) {
                        view.displayEmptyCartWarning();
                    } else {
                        return true; // proceed to checkout
                    }
                    break;
                case 6: // Cancel order
                    if (view.confirmAction("cancel this order")) {
                        cart.clearCart();
                        return false;
                    }
                    break;
                default:
                    view.displayError("Invalid option. Please try again.");
            }
        }
        return false;
    }

    // Helper methods
    private int getAvailableStock(String productID) {
        int dbStock = getProductStockFromDB(productID);
        int cartQty = getQuantityInCart(productID);
        return dbStock - cartQty;
    }

    private int getProductStockFromDB(String productID) {
        Product product = ProductDAO.findById(productID);
        return (product != null) ? product.getQuantity() : 0;
    }

    private int getQuantityInCart(String productID) {
        for (CartItem item : cart.getCartlist()) {
            if (item.getCartProd().getProductID().equals(productID)) {
                return item.getQuantity();
            }
        }
        return 0;
    }

    private int getQuantityInCartExcluding(String productID, int excludeIndex) {
        int total = 0;
        for (int i = 0; i < cart.getCartlist().size(); i++) {
            if (i != excludeIndex) {
                CartItem item = cart.getCartlist().get(i);
                if (item.getCartProd().getProductID().equals(productID)) {
                    total += item.getQuantity();
                }
            }
        }
        return total;
    }

    public double getCartSubtotal() {
        return cart.calculateSubtotal();
    }

    public boolean isCartEmpty() {
        return cart.isEmpty();
    }
}