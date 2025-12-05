package Assignment.Controller;

import Assignment.Model.*;
import Assignment.View.OrderView;
import Assignment.View.ProductView; // Used to get quantity
import Assignment.MainMenu; // To get User Role/Cashier ID
import Assignment.Config.AppConfig;
import Assignment.View.CartView;

public class OrderController {

    private OrderView orderView;
    private CartController cartController;
    private ProductController productController; // To select products
    private ProductView productView;
    private CartView cartView; // To ask for quantity
    private Cart cart;

    public OrderController() {
        this.orderView = new OrderView();
        this.cartController = new CartController();
        this.productController = new ProductController();
        this.productView = new ProductView();
        this.cartView = new CartView();
        this.cart = cartController.getCart();
    }

    public void startOrderProcess(boolean isAdmin) {
        boolean ordering = true;

        while (ordering) {
            int choice = orderView.displayOrderMenu();

            switch (choice) {
                case 1:
                    performProductSelectionLoop();

                    // 2. If Cart is not empty after loop, automatically show Cart & Checkout prompt
                    if (!cart.getCartlist().isEmpty()) {
                        displayCartMenu(isAdmin);
                    }
                    break;

                case 2:
                    if (cart.getCartlist().isEmpty()) {
                        orderView.displayError("Cart is empty!");
                    } else {
                        displayCartMenu(isAdmin);
                    }
                    break;

                case 3:
                    ordering = false; // Exit
                    break;
                default:
                    orderView.displayError("Invalid selection.");
            }
        }

        // Return to main menu based on role
        if (isAdmin)
            MainMenu.AdminMenu();
        else
            MainMenu.StaffMenu();
    }

    private void performProductSelectionLoop() {
        boolean adding = true;
        while (adding) {
            // Use CartController to handle product selection and adding
            boolean added = cartController.addProductToCart();

            if (added) {
                // Ask to add more
                char choice = orderView.promptAddMore();
                if (choice == 'N') {
                    adding = false;
                }
            } else {
                // User cancelled or no product selected
                adding = false;
            }
        }
    }

    private void displayCartMenu(boolean isAdmin) {
        // Use CartController to display cart
        cartController.displayCart();

        int choice = cartView.displayCartMenu();

        switch (choice) {
            case 1:
                cartController.addProductToCart();
                displayCartMenu(isAdmin); // Redisplay menu
                break;
            case 2:
                cartController.editItemQuantity();
                displayCartMenu(isAdmin); // Redisplay menu
                break;
            case 3:
                cartController.removeItem();
                displayCartMenu(isAdmin); // Redisplay menu
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
                displayCartMenu(isAdmin); // Redisplay menu
        }
    }

    private Member handleMemberLogin(Payment payment) {
        while (true) {
            int type = orderView.promptMemberType();

            if (type == 2) {
                // Non-member
                Member nonMember = new Member();
                nonMember.setID("-");
                nonMember.setName("Walk-in Customer");
                payment.setDiscount(0.00);
                return nonMember;

            } else if (type == 1) {
                // Member
                String id = orderView.promptMemberID();

                // Use MemberDAO to validate and retrieve member
                Member member = MemberDAO.findById(id);

                if (member != null) {
                    payment.setDiscount(AppConfig.getMemberDiscount()); 
                    orderView.displaySuccess("Welcome back, " + member.getName() + "!");
                    return member;
                } else {
                    orderView.displayError("Invalid Member ID. Please try again.");
                }

            } else {
                orderView.displayError("Invalid selection. Please enter 1 or 2.");
            }
        }
    }

    private void processCheckout(boolean isAdmin) {
        Cart cart = cartController.getCart();
        Payment payment = new Payment(cart);

        // Updated: handleMemberLogin now returns Member
        Member member = handleMemberLogin(payment);

        orderView.displayPaymentSummary(member, payment, cart.calculateSubtotal());

        double total = payment.calTotal();
        boolean paid = handlePaymentTransaction(payment, total, member);

        if (paid) {
            updateStock();
            cartController.clearCart();
            orderView.pause();
            if (isAdmin)
                MainMenu.AdminMenu();
            else
                MainMenu.StaffMenu();
        }
    }

    private boolean handlePaymentTransaction(Payment payment, double total, Member member) {
        while (true) {
            int method = orderView.promptPaymentMethod();

            if (method == 1) { // CASH
                Payment cashPayment = new Cash(cart);
                double money = orderView.promptCashAmount();

                if (money >= total) {
                    ((Cash) cashPayment).setAmount(money);
                    double change = ((Cash) cashPayment).change(total);

                    // Show Receipt
                    Receipt receipt = new Receipt();
                    orderView.displayReceipt(receipt, cart, payment, member, MainMenu.getCashierID(), change, "Cash");
                    return true;
                } else {
                    orderView.displayError("Insufficient amount!");
                }
            } else if (method == 2) { // CARD
                Payment cardPayment = new Card(cart);
                String cardNo = orderView.promptCardNumber();

                if (((Card) cardPayment).isValidCardNumber(cardNo)) {
                    String cvv = orderView.promptCVV();
                    if (((Card) cardPayment).isValidCVV(cvv)) {
                        // Show Receipt (Change is 0 for card)
                        Receipt receipt = new Receipt();
                        orderView.displayReceipt(receipt, cart, payment, member, MainMenu.getCashierID(), 0.0, "Card");
                        return true;
                    } else {
                        orderView.displayError("Invalid CVV.");
                    }
                } else {
                    orderView.displayError("Invalid Card Number.");
                }
            }
        }
    }

    private void updateStock() {
        // Iterate through cart and update database
        for (CartItem item : cart.getCartlist()) {
            Product prod = item.getCartProd();
            int purchasedQty = item.getQuantity();
            int currentStock = prod.getQuantity();

            prod.setQuantity(currentStock - purchasedQty);

            // Calls the Database DAO
            ProductDAO.update(prod);
        }
        orderView.displaySuccess("Stock updated successfully.");
    }
}