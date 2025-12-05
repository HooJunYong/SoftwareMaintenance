package Assignment.Service;

import Assignment.Model.Cart;
import Assignment.Model.Member;
import Assignment.Model.Payment;

public interface PaymentServiceInterface {
    boolean processPayment(Payment payment, double total, Member member, Cart cart, double subtotal);
}