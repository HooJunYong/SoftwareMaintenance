package Assignment.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest {

    private Cart cart;
    private Product product1;
    private Product product2;
    private Product product3;

    @BeforeEach
    void setUp() {
        cart = new Cart();

        // Setup test products
        product1 = new Product();
        product1.setProductID("P001");
        product1.setProductName("Laptop");
        product1.setPrice(1000.00);
        product1.setQuantity(10);

        product2 = new Product();
        product2.setProductID("P002");
        product2.setProductName("Mouse");
        product2.setPrice(50.00);
        product2.setQuantity(20);

        product3 = new Product();
        product3.setProductID("P003");
        product3.setProductName("Keyboard");
        product3.setPrice(100.00);
        product3.setQuantity(15);
    }

    // ========== addItem() Tests ==========

    @Test
    @DisplayName("addItem adds new item to empty cart")
    void testAddItemToEmptyCart() {
        // Arrange
        CartItem item = new CartItem(product1, 2);

        // Act
        cart.addItem(item);

        // Assert
        assertEquals(1, cart.getItemCount());
        assertEquals("P001", cart.getItem(0).getProduct().getProductID());
        assertEquals(2, cart.getItem(0).getQuantity());
    }

    @Test
    @DisplayName("addItem adds multiple different items")
    void testAddMultipleDifferentItems() {
        // Arrange
        CartItem item1 = new CartItem(product1, 1);
        CartItem item2 = new CartItem(product2, 3);

        // Act
        cart.addItem(item1);
        cart.addItem(item2);

        // Assert
        assertEquals(2, cart.getItemCount());
    }

    @Test
    @DisplayName("addItem combines quantity for same product")
    void testAddItemCombinesQuantityForSameProduct() {
        // Arrange
        CartItem item1 = new CartItem(product1, 2);
        CartItem item2 = new CartItem(product1, 3);

        // Act
        cart.addItem(item1);
        cart.addItem(item2);

        // Assert
        assertEquals(1, cart.getItemCount()); // Only one item
        assertEquals(5, cart.getItem(0).getQuantity()); // 2 + 3 = 5
    }

    // ========== removeItem() Tests ==========

    @Test
    @DisplayName("removeItem removes item at valid index")
    void testRemoveItemAtValidIndex() {
        // Arrange
        cart.addItem(new CartItem(product1, 2));
        cart.addItem(new CartItem(product2, 3));

        // Act
        cart.removeItem(0);

        // Assert
        assertEquals(1, cart.getItemCount());
        assertEquals("P002", cart.getItem(0).getProduct().getProductID());
    }

     @Test
    @DisplayName("removeItem with negative index does nothing")
    void testRemoveItemNegativeIndex() {
        // Arrange
        cart.addItem(new CartItem(product1, 2));

        // Act
        cart.removeItem(-1);

        // Assert
        assertEquals(1, cart.getItemCount()); // Item still exists
    }

    // ========== calculateSubtotal() Tests ==========

    @Test
    @DisplayName("calculateSubtotal calculates correctly for single item")
    void testCalculateSubtotalSingleItem() {
        // Arrange
        cart.addItem(new CartItem(product1, 2)); // 1000 * 2 = 2000

        // Act
        double subtotal = cart.calculateSubtotal();

        // Assert
        assertEquals(2000.00, subtotal, 0.001);
    }

    @Test
    @DisplayName("calculateSubtotal calculates correctly for multiple items")
    void testCalculateSubtotalMultipleItems() {
        // Arrange
        cart.addItem(new CartItem(product1, 1)); // 1000 * 1 = 1000
        cart.addItem(new CartItem(product2, 2)); // 50 * 2 = 100
        cart.addItem(new CartItem(product3, 3)); // 100 * 3 = 300

        // Act
        double subtotal = cart.calculateSubtotal();

        // Assert
        assertEquals(1400.00, subtotal, 0.001); // 1000 + 100 + 300
    }


    // ========== calculateTotalQuantity() Tests ==========

    @Test
    @DisplayName("calculateTotalQuantity calculates correctly for single item")
    void testCalculateTotalQuantitySingleItem() {
        // Arrange
        cart.addItem(new CartItem(product1, 5));

        // Act
        int total = cart.calculateTotalQuantity();

        // Assert
        assertEquals(5, total);
    }

    @Test
    @DisplayName("calculateTotalQuantity calculates correctly for multiple items")
    void testCalculateTotalQuantityMultipleItems() {
        // Arrange
        cart.addItem(new CartItem(product1, 2));
        cart.addItem(new CartItem(product2, 3));
        cart.addItem(new CartItem(product3, 5));

        // Act
        int total = cart.calculateTotalQuantity();

        // Assert
        assertEquals(10, total); // 2 + 3 + 5
    }

    
     @Test
    @DisplayName("clearCart removes all items")
    void testClearCart() {
        // Arrange
        cart.addItem(new CartItem(product1, 1));
        cart.addItem(new CartItem(product2, 2));

        // Act
        cart.clearCart();

        // Assert
        assertTrue(cart.isEmpty());
        assertEquals(0, cart.getItemCount());
        assertEquals(0.0, cart.calculateSubtotal(), 0.001);
    }

    @Test
    @DisplayName("updateItemQuantity updates quantity correctly")
    void testUpdateItemQuantity() {
        // Arrange
        cart.addItem(new CartItem(product1, 2));

        // Act
        cart.updateItemQuantity(0, 10);

        // Assert
        assertEquals(10, cart.getItem(0).getQuantity());
        assertEquals(10000.00, cart.calculateSubtotal(), 0.001); // 1000 * 10
    }

}