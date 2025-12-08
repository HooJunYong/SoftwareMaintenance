package Assignment.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    private Cart cart;
    private Payment payment;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        // Create test products
        product1 = new Product();
        product1.setProductID("P001");
        product1.setProductName("Test Product 1");
        product1.setPrice(100.00);
        product1.setQuantity(10);

        product2 = new Product();
        product2.setProductID("P002");
        product2.setProductName("Test Product 2");
        product2.setPrice(50.00);
        product2.setQuantity(5);

        // Create cart and add items
        cart = new Cart();
        cart.addItem(new CartItem(product1, 2));  // 100 * 2 = 200
        cart.addItem(new CartItem(product2, 3));  // 50 * 3 = 150
        // Subtotal = 350

        // Create payment
        payment = new Payment(cart);
    }

    @Test
    @DisplayName("Calculate total without discount")
    void testCalculateTotalWithoutDiscount() {
        // Arrange
        payment.setDiscount(0.0);  // No discount
        
        // Act
        double total = payment.calculateTotal();
        
        // Assert
        // Subtotal = 350, Tax = 350 * 0.06 = 21, Total = 371
        double expectedSubtotal = 350.00;
        double expectedTax = expectedSubtotal * 0.06;  // Assuming 6% tax
        double expectedTotal = expectedSubtotal + expectedTax;
        
        assertEquals(expectedTotal, total, 0.01, "Total should be subtotal + tax");
    }

    @Test
    @DisplayName("Calculate total with 10% member discount")
    void testCalculateTotalWithMemberDiscount() {
        // Arrange
        payment.setDiscount(0.10);  // 10% member discount
        
        // Act
        double total = payment.calculateTotal();
        
        // Assert
        // Subtotal = 350
        // Discount = 350 * 0.10 = 35
        // After discount = 315
        // Tax = 315 * 0.06 = 18.90
        // Total = 315 + 18.90 = 333.90
        double subtotal = 350.00;
        double discount = subtotal * 0.10;
        double afterDiscount = subtotal - discount;
        double tax = afterDiscount * 0.06;
        double expectedTotal = afterDiscount + tax;
        
        assertEquals(expectedTotal, total, 0.01, "Total should include member discount");
    }

    @Test
    @DisplayName("Calculate discount amount")
    void testCalculateDiscount() {
        // Arrange
        payment.setDiscount(0.10);  // 10% discount
        
        // Act
        double discountAmount = payment.calculateDiscount();
        
        // Assert
        // Subtotal = 350, Discount = 350 * 0.10 = 35
        assertEquals(35.00, discountAmount, 0.01, "Discount should be 10% of subtotal");
    }

    @Test
    @DisplayName("Calculate tax amount")
    void testCalculateTax() {
        // Arrange
        payment.setDiscount(0.0);  // No discount
        
        // Act
        double taxAmount = payment.calculateTax();
        
        // Assert
        // Subtotal = 350, Tax = 350 * 0.06 = 21
        assertEquals(21.00, taxAmount, 0.01, "Tax should be 6% of subtotal");
    }

    @Test
    @DisplayName("Calculate tax with discount applied")
    void testCalculateTaxAfterDiscount() {
        // Arrange
        payment.setDiscount(0.10);  // 10% discount
        
        // Act
        double taxAmount = payment.calculateTax();
        
        // Assert
        // Subtotal = 350, Discount = 35, After discount = 315
        // Tax = 315 * 0.06 = 18.90
        assertEquals(18.90, taxAmount, 0.01, "Tax should be calculated after discount");
    }

    @Test
    @DisplayName("Empty cart should return zero total")
    void testEmptyCartTotal() {
        // Arrange
        Cart emptyCart = new Cart();
        Payment emptyPayment = new Payment(emptyCart);
        
        // Act
        double total = emptyPayment.calculateTotal();
        
        // Assert
        assertEquals(0.00, total, 0.01, "Empty cart should have zero total");
    }
}