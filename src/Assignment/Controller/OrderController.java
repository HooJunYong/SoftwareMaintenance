package Assignment.Controller;

import Assignment.MainMenu;
import Assignment.Model.*;
import Assignment.Service.InventoryService;
import Assignment.Service.MemberService;
import Assignment.Service.PaymentService;
import Assignment.View.CartView;
import Assignment.View.OrderView;
import Assignment.View.ProductView;

public class OrderController {

    private OrderView orderView;
    private CartController cartController;
    private ProductController productController;
    private ProductView productView;
    private CartView cartView;
    private Cart cart;

    // Services (Single Responsibility)
    private MemberService memberService;
    private PaymentService paymentService;
    private InventoryService inventoryService;

    public OrderController(
            OrderView orderView,
            CartController cartController,
            ProductController productController,
            ProductView productView,
            CartView cartView,
            MemberService memberService,
            PaymentService paymentService,
            InventoryService inventoryService) {

        this.orderView = orderView;
        this.cartController = cartController;
        this.productController = productController;
        this.productView = productView;
        this.cartView = cartView;
        this.cart = cartController.getCart();

        // Injected services (DIP)
        this.memberService = memberService;
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
    }

    // Default constructor for backward compatibility
    public OrderController() {
        this(
            new OrderView(),
            new CartController(),
            new ProductController(),
            new ProductView(),
            new CartView(),
            null, null, null // Will be set below
        );
        
        // Initialize with default implementations
        this.memberService = new Assignment.Service.MemberService(this.orderView);
        this.paymentService = new Assignment.Service.PaymentService(this.orderView);
        this.inventoryService = new Assignment.Service.InventoryService();
    }

    public void startOrderProcess(boolean isAdmin) {
        boolean ordering = true;

        while (ordering) {
            int choice = orderView.displayOrderMenu();

            switch (choice) {
                case 1:
                    performProductSelectionLoop();
                    if (!cart.getCartList().isEmpty()) {
                        displayCartMenu(isAdmin);
                    }
                    break;

                case 2:
                    if (cart.getCartList().isEmpty()) {
                        orderView.displayError("Cart is empty!");
                    } else {
                        displayCartMenu(isAdmin);
                    }
                    break;

                case 3:
                    ordering = false;
                    break;

                default:
                    orderView.displayError("Invalid selection.");
            }
        }

        navigateToMenu(isAdmin);
    }

    private void performProductSelectionLoop() {
        boolean adding = true;
        while (adding) {
            boolean added = cartController.addProductToCart();

            if (added) {
                char choice = orderView.promptAddMore();
                if (choice == 'N') {
                    adding = false;
                }
            } else {
                adding = false;
            }
        }
    }

    private void displayCartMenu(boolean isAdmin) {
        cartController.displayCart();

        int choice = cartView.displayCartMenu();

        switch (choice) {
            case 1:
                cartController.addProductToCart();
                displayCartMenu(isAdmin);
                break;
            case 2:
                cartController.editItemQuantity();
                displayCartMenu(isAdmin);
                break;
            case 3:
                cartController.removeItem();
                displayCartMenu(isAdmin);
                break;
            case 4:
                cartController.clearCart();
                orderView.displaySuccess("Cart cleared.");
                break;
            case 5:
                processCheckout(isAdmin);
                break;
            case 6:
                cartController.clearCart();
                orderView.displaySuccess("Order cancelled and cart cleared.");
                break;
            default:
                orderView.displayError("Invalid selection.");
                displayCartMenu(isAdmin);
        }
    }

    private void processCheckout(boolean isAdmin) {
        Cart cart = cartController.getCart();
        Payment payment = new Payment(cart);

        // Use MemberService (SRP)
        Member member = memberService.handleMemberLogin(payment);

        orderView.displayPaymentSummary(member, payment, cart.calculateSubtotal(), cart);

        double total = payment.calculateTotal();

        // Use PaymentService (SRP)
        boolean paid = paymentService.processPayment(payment, total, member, cart, cart.calculateSubtotal());

        if (paid) {
            saveOrderToDatabase(cart, payment, member);

            // Use InventoryService (SRP)
            inventoryService.updateStock(cart);

            cartController.clearCart();
            orderView.pause();
            navigateToMenu(isAdmin);
        }
    }

    private void saveOrderToDatabase(Cart cart, Payment payment, Member member) {
        Order order = new Order();
        order.setOrderID(OrderDAO.getNextID());
        order.setInvoiceNo(new Receipt().getInvoiceID());
        order.setMemberID(member.getID());
        order.setCashierID(MainMenu.getCashierID());
        order.setSubtotal(cart.calculateSubtotal());
        order.setDiscountAmount(payment.calculateDiscount());
        order.setTaxAmount(payment.calculateTax());
        order.setGrandTotal(payment.calculateTotal());
        order.setPaymentMethod(payment.getPaymentMethod());

        for (CartItem cartItem : cart.getCartList()) {
            OrderItem orderItem = new OrderItem(
                    cartItem.getProduct().getProductID(),
                    cartItem.getQuantity()
            );
            order.addOrderItem(orderItem);
        }

        if (OrderDAO.insert(order)) {
            orderView.displaySuccess("Order saved successfully!");
        } else {
            orderView.displayError("Failed to save order.");
        }
    }

    private void navigateToMenu(boolean isAdmin) {
        if (isAdmin) {
            MainMenu.AdminMenu();
        } else {
            MainMenu.StaffMenu();
        }
    }
}
