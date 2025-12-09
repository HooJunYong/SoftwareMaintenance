package Assignment.Controller;

import Assignment.Model.Cart;
import Assignment.Model.CartItem;
import Assignment.Model.Product;
import Assignment.Model.ProductDAO;
import Assignment.View.CartView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartControllerTest {

    @Mock
    private CartView cartView;

    @Mock
    private ProductController productController;

    private CartController cartController;
    private Cart cart;

    @BeforeEach
    void setUp() throws Exception {
        cartController = new CartController();
        cart = cartController.getCart();

        // Inject mocks using reflection
        injectMock(cartController, "view", cartView);
        injectMock(cartController, "productController", productController);
    }

    // Helper to inject mocks via reflection
    private void injectMock(Object target, String fieldName, Object mock) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, mock);
    }

    // Helper to create test product
    private Product createTestProduct(String id, String name, double price, int quantity) {
        Product product = new Product();
        product.setProductID(id);
        product.setProductName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        return product;
    }

    // ========== 1. Add Product (Critical Logic) ==========

    @Test
    @DisplayName("addProductToCart successfully adds product to cart")
    void testAddProductToCartSuccess() {
        Product product = createTestProduct("P001", "Laptop", 1000.0, 10);
        when(productController.selectProductForOrder(cart)).thenReturn(product);
        when(productController.getQuantityForProduct(10)).thenReturn(2);

        try (MockedStatic<ProductDAO> productDAO = mockStatic(ProductDAO.class)) {
            productDAO.when(() -> ProductDAO.findById("P001")).thenReturn(product);

            boolean result = cartController.addProductToCart();

            assertTrue(result);
            assertEquals(1, cart.getItemCount());
            assertEquals(2, cart.getItem(0).getQuantity());
            verify(cartView).displaySuccess("Added 2x Laptop to cart.");
        }
    }

    @Test
    @DisplayName("addProductToCart prevents adding out-of-stock products")
    void testAddProductToCartOutOfStock() {
        Product product = createTestProduct("P001", "Laptop", 1000.0, 0); // 0 Quantity
        when(productController.selectProductForOrder(cart)).thenReturn(product);

        try (MockedStatic<ProductDAO> productDAO = mockStatic(ProductDAO.class)) {
            productDAO.when(() -> ProductDAO.findById("P001")).thenReturn(product);

            boolean result = cartController.addProductToCart();

            assertFalse(result);
            verify(cartView).displayError("This product is out of stock.");
            assertTrue(cart.isEmpty());
        }
    }

    @Test
    @DisplayName("addProductToCart accurately calculates remaining stock (DB - Cart)")
    void testAddProductToCartCalculatesAvailableStock() {
        // Critical: Ensures we don't oversell if item is already in cart
        Product product = createTestProduct("P001", "Laptop", 1000.0, 10);
        cart.addItem(new CartItem(product, 3)); // Already have 3 in cart

        when(productController.selectProductForOrder(cart)).thenReturn(product);
        // The controller should verify 10 (DB) - 3 (Cart) = 7 available
        when(productController.getQuantityForProduct(7)).thenReturn(2); 

        try (MockedStatic<ProductDAO> productDAO = mockStatic(ProductDAO.class)) {
            productDAO.when(() -> ProductDAO.findById("P001")).thenReturn(product);

            boolean result = cartController.addProductToCart();

            assertTrue(result);
            assertEquals(5, cart.getItem(0).getQuantity()); // 3 + 2 = 5
        }
    }

    // ========== 2. Edit & Remove (User Actions) ==========

    @Test
    @DisplayName("editItemQuantity updates quantity successfully")
    void testEditItemQuantitySuccess() {
        Product product = createTestProduct("P001", "Laptop", 1000.0, 10);
        cart.addItem(new CartItem(product, 2));

        when(cartView.getItemSelection(1)).thenReturn(1);
        when(cartView.getNewQuantity(2, 10)).thenReturn(5);

        try (MockedStatic<ProductDAO> productDAO = mockStatic(ProductDAO.class)) {
            productDAO.when(() -> ProductDAO.findById("P001")).thenReturn(product);

            cartController.editItemQuantity();

            assertEquals(5, cart.getItem(0).getQuantity());
            verify(cartView).displaySuccess("Quantity updated successfully.");
        }
    }

    @Test
    @DisplayName("editItemQuantity safely handles empty cart")
    void testEditItemQuantityEmptyCart() {
        // Important guard clause to prevent crash
        cartController.editItemQuantity();
        verify(cartView).displayEmptyCartWarning();
    }

    @Test
    @DisplayName("removeItem removes item when confirmed")
    void testRemoveItemSuccess() {
        Product product = createTestProduct("P001", "Laptop", 1000.0, 10);
        cart.addItem(new CartItem(product, 2));

        when(cartView.getItemSelection(1)).thenReturn(1);
        when(cartView.confirmAction("remove Laptop")).thenReturn(true);

        cartController.removeItem();

        assertTrue(cart.isEmpty());
        verify(cartView).displaySuccess("Item removed from cart.");
    }

    // ========== 3. Cart State (Maintenance) ==========

    @Test
    @DisplayName("clearCart removes all items (State Reset)")
    void testClearCart() {
        cart.addItem(new CartItem(createTestProduct("P1", "A", 10.0, 10), 1));
        
        cartController.clearCart();
        
        assertTrue(cart.isEmpty());
    }

    @Test
    @DisplayName("getCartSubtotal calculates correctly via delegation")
    void testGetCartSubtotalWithItems() {
        Product product1 = createTestProduct("P001", "Laptop", 1000.0, 10);
        Product product2 = createTestProduct("P002", "Mouse", 50.0, 20);
        cart.addItem(new CartItem(product1, 2)); // 2000
        cart.addItem(new CartItem(product2, 3)); // 150

        assertEquals(2150.0, cartController.getCartSubtotal(), 0.001);
    }

    // ========== 4. Menu Orchestration (Flow Control) ==========

    @Test
    @DisplayName("processCartMenu continues loop when adding product")
    void testProcessCartMenuAddProduct() {
        // Verifies the menu loop logic
        when(cartView.displayCartMenu()).thenReturn(1).thenReturn(6); // Add then Cancel
        when(productController.selectProductForOrder(cart)).thenReturn(null);
        when(cartView.confirmAction("cancel this order")).thenReturn(true);

        boolean result = cartController.processCartMenu();

        assertFalse(result);
        verify(productController).selectProductForOrder(cart);
    }

    @Test
    @DisplayName("processCartMenu option 5 proceeds to checkout with items")
    void testProcessCartMenuCheckoutWithItems() {
        Product product = createTestProduct("P001", "Laptop", 1000.0, 10);
        cart.addItem(new CartItem(product, 2));

        when(cartView.displayCartMenu()).thenReturn(5); // Checkout

        boolean result = cartController.processCartMenu();

        assertTrue(result); // Returns true to signal OrderController to proceed
    }

    @Test
    @DisplayName("processCartMenu option 5 blocks checkout if cart is empty")
    void testProcessCartMenuCheckoutEmptyCart() {
        when(cartView.displayCartMenu()).thenReturn(5).thenReturn(6); // Checkout -> Cancel
        when(cartView.confirmAction("cancel this order")).thenReturn(true);

        boolean result = cartController.processCartMenu();

        assertFalse(result);
        verify(cartView).displayEmptyCartWarning();
    }

    @Test
    @DisplayName("processCartMenu option 6 cancels order and clears cart")
    void testProcessCartMenuCancelConfirmed() {
        Product product = createTestProduct("P001", "Laptop", 1000.0, 10);
        cart.addItem(new CartItem(product, 2));

        when(cartView.displayCartMenu()).thenReturn(6); // Cancel
        when(cartView.confirmAction("cancel this order")).thenReturn(true);

        boolean result = cartController.processCartMenu();

        assertFalse(result); // Returns false to signal exit
        assertTrue(cart.isEmpty()); // Cart must be cleared
    }
}