package Assignment.Service;

import Assignment.Model.*;
import Assignment.View.OrderView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private OrderView orderView;

    private PaymentService paymentService;
    private Cart cart;
    private Payment payment;
    private Member member;
    private double subtotal;
    private double total;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService(orderView);

        // Setup test data
        Product product = new Product();
        product.setProductID("P001");
        product.setProductName("Test Product");
        product.setPrice(100.00);
        product.setQuantity(10);

        cart = new Cart();
        cart.addItem(new CartItem(product, 2)); // 100 * 2 = 200

        payment = new Payment(cart);
        payment.setDiscount(0.0);

        member = new Member();
        member.setID("M001");

        subtotal = 200.00;
        total = 212.00; // subtotal + 6% tax
    }

    @Test
    @DisplayName("Process cash payment with sufficient amount returns true")
    void testProcessCashPaymentSuccess() {
        // Arrange
        when(orderView.promptPaymentMethod()).thenReturn(1); // Cash
        when(orderView.promptCashAmount()).thenReturn(250.00);
        when(orderView.promptReceiptConfirmation()).thenReturn('N');

        // Act
        boolean result = paymentService.processPayment(payment, total, member, cart, subtotal);

        // Assert
        assertTrue(result);
        verify(orderView).displaySuccess("Order Successful!");
    }

    @Test
    @DisplayName("Process cash payment with insufficient amount returns false")
    void testProcessCashPaymentInsufficientAmount() {
        // Arrange
        when(orderView.promptPaymentMethod()).thenReturn(1); // Cash
        when(orderView.promptCashAmount()).thenReturn(100.00); // Less than total

        // Act
        boolean result = paymentService.processPayment(payment, total, member, cart, subtotal);

        // Assert
        assertFalse(result);
        verify(orderView).displayError("Insufficient amount!");
    }

    @Test
    @DisplayName("Process cash payment with exact amount returns true")
    void testProcessCashPaymentExactAmount() {
        // Arrange
        when(orderView.promptPaymentMethod()).thenReturn(1); // Cash
        when(orderView.promptCashAmount()).thenReturn(212.00); // Exact total
        when(orderView.promptReceiptConfirmation()).thenReturn('N');

        // Act
        boolean result = paymentService.processPayment(payment, total, member, cart, subtotal);

        // Assert
        assertTrue(result);
        verify(orderView).displaySuccess("Order Successful!");
    }

    @Test
    @DisplayName("Process card payment with valid card and CVV returns true")
    void testProcessCardPaymentSuccess() {
        // Arrange
        when(orderView.promptPaymentMethod()).thenReturn(2); // Card
        when(orderView.promptCardNumber()).thenReturn("1234567890123456"); // 16 digits
        when(orderView.promptCVV()).thenReturn("123"); // 3 digits
        when(orderView.promptReceiptConfirmation()).thenReturn('N');

        // Act
        boolean result = paymentService.processPayment(payment, total, member, cart, subtotal);

        // Assert
        assertTrue(result);
        verify(orderView).displaySuccess("Order Successful!");
    }

    @Test
    @DisplayName("Process card payment with invalid card number returns false")
    void testProcessCardPaymentInvalidCardNumber() {
        // Arrange
        when(orderView.promptPaymentMethod()).thenReturn(2); // Card
        when(orderView.promptCardNumber()).thenReturn("123"); // Invalid - too short

        // Act
        boolean result = paymentService.processPayment(payment, total, member, cart, subtotal);

        // Assert
        assertFalse(result);
        verify(orderView).displayError("Invalid Card Number.");
    }

    @Test
    @DisplayName("Process card payment with invalid CVV returns false")
    void testProcessCardPaymentInvalidCVV() {
        // Arrange
        when(orderView.promptPaymentMethod()).thenReturn(2); // Card
        when(orderView.promptCardNumber()).thenReturn("1234567890123456"); // Valid
        when(orderView.promptCVV()).thenReturn("12"); // Invalid - too short

        // Act
        boolean result = paymentService.processPayment(payment, total, member, cart, subtotal);

        // Assert
        assertFalse(result);
        verify(orderView).displayError("Invalid CVV.");
    }

  
}