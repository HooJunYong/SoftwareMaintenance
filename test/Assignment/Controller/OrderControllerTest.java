package Assignment.Controller;

import Assignment.Model.*;
import Assignment.Service.InventoryService;
import Assignment.Service.MemberService;
import Assignment.Service.PaymentService;
import Assignment.View.CartView;
import Assignment.View.OrderView;
import Assignment.View.ProductView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock private OrderView orderView;
    @Mock private CartController cartController;
    @Mock private ProductController productController;
    @Mock private ProductView productView;
    @Mock private CartView cartView;
    @Mock private MemberService memberService;
    @Mock private PaymentService paymentService;
    @Mock private InventoryService inventoryService;

    private OrderController orderController;
    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        when(cartController.getCart()).thenReturn(cart);

        orderController = new OrderController(
                orderView, cartController, productController, productView, cartView,
                memberService, paymentService, inventoryService
        );
    }

    // ========== Main Menu Flow Tests ==========

    @Test
    @DisplayName("startOrderProcess exits when user selects option 3")
    void testStartOrderProcessExitOption() {
        // Arrange
        when(orderView.displayOrderMenu()).thenReturn(3); // Exit

        try (MockedStatic<Assignment.MainMenu> mainMenu = mockStatic(Assignment.MainMenu.class)) {
            // Act
            orderController.startOrderProcess(false);
            // Assert
            verify(orderView).displayOrderMenu();
            mainMenu.verify(() -> Assignment.MainMenu.StaffMenu());
        }
    }

    @Test
    @DisplayName("startOrderProcess shows error for empty cart when viewing cart")
    void testStartOrderProcessEmptyCartError() {
        // Arrange
        when(orderView.displayOrderMenu())
                .thenReturn(2)  // View cart
                .thenReturn(3); // Exit

        try (MockedStatic<Assignment.MainMenu> mainMenu = mockStatic(Assignment.MainMenu.class)) {
            // Act
            orderController.startOrderProcess(false);
            // Assert
            verify(orderView).displayError("Cart is empty!");
        }
    }

    @Test
    @DisplayName("startOrderProcess navigates to AdminMenu for admin user")
    void testStartOrderProcessAdminNavigation() {
        // Arrange
        when(orderView.displayOrderMenu()).thenReturn(3); // Exit

        try (MockedStatic<Assignment.MainMenu> mainMenu = mockStatic(Assignment.MainMenu.class)) {
            // Act
            orderController.startOrderProcess(true); // isAdmin = true
            // Assert
            mainMenu.verify(() -> Assignment.MainMenu.AdminMenu());
        }
    }

    // ========== Product Selection Loop Tests ==========

    @Test
    @DisplayName("Product selection continues when user chooses to add more")
    void testProductSelectionContinuesOnYes() {
        // Arrange
        when(orderView.displayOrderMenu())
                .thenReturn(1)  // Add product
                .thenReturn(3); // Exit
        when(cartController.addProductToCart())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false); // Stop after 3rd attempt
        when(orderView.promptAddMore())
                .thenReturn('Y')
                .thenReturn('Y');

        try (MockedStatic<Assignment.MainMenu> mainMenu = mockStatic(Assignment.MainMenu.class)) {
            // Act
            orderController.startOrderProcess(false);
            // Assert
            verify(cartController, times(3)).addProductToCart();
        }
    }

    // ========== Cart Menu Tests (Reduced) ==========

    @Test
    @DisplayName("Cart menu option 1 adds product to cart")
    void testCartMenuAddProduct() {
        // Arrange - Add item to cart first
        cart.addItem(new CartItem(new Product(), 1));

        when(orderView.displayOrderMenu()).thenReturn(2).thenReturn(3);
        when(cartView.displayCartMenu())
                .thenReturn(1)  // Add more
                .thenReturn(6); // Cancel (exits cart menu)

        try (MockedStatic<Assignment.MainMenu> mainMenu = mockStatic(Assignment.MainMenu.class)) {
            // Act
            orderController.startOrderProcess(false);
            // Assert
            verify(cartController, atLeastOnce()).addProductToCart();
        }
    }

    @Test
    @DisplayName("Cart menu option 4 clears cart")
    void testCartMenuClearCart() {
        // Arrange
        cart.addItem(new CartItem(new Product(), 1));

        when(orderView.displayOrderMenu()).thenReturn(2).thenReturn(3);
        when(cartView.displayCartMenu()).thenReturn(4); // Clear cart

        try (MockedStatic<Assignment.MainMenu> mainMenu = mockStatic(Assignment.MainMenu.class)) {
            // Act
            orderController.startOrderProcess(false);
            // Assert
            verify(cartController).clearCart();
            verify(orderView).displaySuccess("Cart cleared.");
        }
    }

    // ========== Critical Checkout Logic (Must Keep) ==========

    @Test
    @DisplayName("Checkout process calls all services in correct order")
    void testCheckoutProcessCallsServices() {
        // Arrange
        Product product = new Product();
        product.setProductID("P001");
        product.setPrice(100.0);
        product.setQuantity(10);
        cart.addItem(new CartItem(product, 1));

        Member member = new Member();
        member.setID("M001");

        when(orderView.displayOrderMenu()).thenReturn(2).thenReturn(3);
        when(cartView.displayCartMenu()).thenReturn(5); // Checkout
        when(memberService.handleMemberLogin(any(Payment.class))).thenReturn(member);
        when(paymentService.processPayment(any(), anyDouble(), any(), any(), anyDouble())).thenReturn(true);

        try (MockedStatic<Assignment.MainMenu> mainMenu = mockStatic(Assignment.MainMenu.class);
             MockedStatic<OrderDAO> orderDAO = mockStatic(OrderDAO.class)) {

            mainMenu.when(Assignment.MainMenu::getCashierID).thenReturn("C001");
            orderDAO.when(OrderDAO::getNextID).thenReturn("O001");
            orderDAO.when(() -> OrderDAO.insert(any(Order.class))).thenReturn(true);

            // Act
            orderController.startOrderProcess(false);

            // Assert
            verify(memberService).handleMemberLogin(any(Payment.class));
            verify(paymentService).processPayment(any(), anyDouble(), eq(member), eq(cart), anyDouble());
            verify(inventoryService).updateStock(cart);
            verify(cartController, atLeastOnce()).clearCart();
        }
    }

   @Test
    @DisplayName("Checkout does not update stock if payment fails")
    void testCheckoutPaymentFailsNoStockUpdate() {
        // Arrange - Product must have price set to avoid NullPointerException
        Product product = new Product();
        product.setProductID("P001");
        product.setProductName("Test Product");
        product.setPrice(100.0);
        product.setQuantity(10);
        
        cart.addItem(new CartItem(product, 1));
        
        Member member = new Member();
        member.setID("-");

        when(orderView.displayOrderMenu()).thenReturn(2).thenReturn(3);
        when(cartView.displayCartMenu()).thenReturn(5).thenReturn(6);
        when(memberService.handleMemberLogin(any(Payment.class))).thenReturn(member);
        when(paymentService.processPayment(any(), anyDouble(), any(), any(), anyDouble())).thenReturn(false);

        try (MockedStatic<Assignment.MainMenu> mainMenu = mockStatic(Assignment.MainMenu.class)) {
            // Act
            orderController.startOrderProcess(false);
            // Assert
            verify(inventoryService, never()).updateStock(any()); // Critical Assert
        }
    }
}