package Assignment.Service;

import Assignment.Model.Cart;
import Assignment.Model.CartItem;
import Assignment.Model.Product;
import Assignment.Model.ProductDAO;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@Tag("integration")
public class InventoryServiceIntegrationTest {

    private InventoryService inventoryService;
    private Cart cart;
    private Product testProduct;
    private int originalQuantity;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService();
        cart = new Cart();
        
        // Get product from real database
        testProduct = ProductDAO.findById("P001");
        
        // Skip test if database is not available or product doesn't exist
        assumeTrue(testProduct != null, "Database not available or P001 not found");
        
        originalQuantity = testProduct.getQuantity();
    }

    @AfterEach
    void tearDown() {
        // Only restore if product was found
        if (testProduct != null) {
            testProduct.setQuantity(originalQuantity);
            ProductDAO.update(testProduct);
        }
    }

    @Test
    @DisplayName("Integration: updateStock updates database correctly")
    void testUpdateStockIntegration() {
        // Arrange
        cart.addItem(new CartItem(testProduct, 2));

        // Act
        inventoryService.updateStock(cart);

        // Assert - verify database was updated
        Product updated = ProductDAO.findById("P001");
        assertNotNull(updated, "Product should exist in database");
        assertEquals(originalQuantity - 2, updated.getQuantity());
    }

    @Test
    @DisplayName("Integration: updateStock with multiple products")
    void testUpdateStockMultipleProductsIntegration() {
        // Arrange
        Product product2 = ProductDAO.findById("P002");
        assumeTrue(product2 != null, "P002 not found in database");
        
        int originalQty2 = product2.getQuantity();

        cart.addItem(new CartItem(testProduct, 1));
        cart.addItem(new CartItem(product2, 3));

        // Act
        inventoryService.updateStock(cart);

        // Assert
        Product updated1 = ProductDAO.findById("P001");
        Product updated2 = ProductDAO.findById("P002");
        
        assertNotNull(updated1);
        assertNotNull(updated2);
        
        assertEquals(originalQuantity - 1, updated1.getQuantity());
        assertEquals(originalQty2 - 3, updated2.getQuantity());

        // Cleanup product2
        product2.setQuantity(originalQty2);
        ProductDAO.update(product2);
    }
}