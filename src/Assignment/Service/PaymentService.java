package Assignment.Service;

import Assignment.MainMenu;
import Assignment.Model.*;
import Assignment.View.OrderView;

public class PaymentService implements PaymentServiceInterface {

    private OrderView orderView;
    private ReceiptService receiptService;

    public PaymentService(OrderView orderView) {
        this.orderView = orderView;
        this.receiptService = new ReceiptService(orderView);
    }

    @Override
    public boolean processPayment(Payment payment, double total, Member member, Cart cart, double subtotal) {
        while (true) {
            int method = orderView.promptPaymentMethod();

            if (method == 1) {
                return processCashPayment(payment, total, member, cart, subtotal);
            } else if (method == 2) {
                return processCardPayment(payment, total, member, cart, subtotal);
            } else {
                orderView.displayError("Invalid payment method.");
            }
        }
    }

    private boolean processCashPayment(Payment payment, double total, Member member, Cart cart, double subtotal) {
        Cash cashPayment = new Cash(cart);
        double money = orderView.promptCashAmount();

        if (money < total) {
            orderView.displayError("Insufficient amount!");
            return false;
        }

        cashPayment.setAmount(money);
        double change = cashPayment.change(total);

        // Pass money to completePayment
        return completePayment(payment, member, cart, subtotal, money, change, "Cash");
    }

    private boolean processCardPayment(Payment payment, double total, Member member, Cart cart, double subtotal) {
        Card cardPayment = new Card(cart);
        String cardNo = orderView.promptCardNumber();

        if (!cardPayment.isValidCardNumber(cardNo)) {
            orderView.displayError("Invalid Card Number.");
            return false;
        }

        String cvv = orderView.promptCVV();
        if (!cardPayment.isValidCVV(cvv)) {
            orderView.displayError("Invalid CVV.");
            return false;
        }

        // For card payment, money = total (exact amount)
        return completePayment(payment, member, cart, subtotal, total, 0.0, "Card");
    }

    private boolean completePayment(Payment payment, Member member, Cart cart,
            double subtotal, double money, double change, String paymentMethod) {
        payment.setPaymentMethod(paymentMethod);
        orderView.displaySuccess("Order Successful!");
        generateReceipt(payment, member, cart, subtotal, money, change, paymentMethod);
        return true;
    }

    private void generateReceipt(Payment payment, Member member, Cart cart, double subtotal, double money, double change, String paymentMethod) {
        ReceiptData receiptData = new ReceiptData();
        receiptData.setPayment(payment);
        receiptData.setMember(member);
        receiptData.setCart(cart);
        receiptData.setSubtotal(subtotal);
        receiptData.setMoney(money); 
        receiptData.setChange(change);
        receiptData.setPaymentMethod(paymentMethod);
        receiptData.setCashierID(MainMenu.getCashierID());

        receiptService.handleReceiptGeneration(receiptData);
    }
}
