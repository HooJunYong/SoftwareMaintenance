package Assignment.View;

import Assignment.Model.Cart;
import Assignment.Model.CartItem;
import Assignment.Model.Payment;
import Assignment.Model.Receipt;
import Assignment.Model.Member;
import Assignment.Model.Cash;
import Assignment.Model.Card;
import java.util.Scanner;

public class OrderView {

    private Scanner scan;

    public OrderView() {
        this.scan = new Scanner(System.in);
    }

    public int displayOrderMenu() {
        System.out.println("\n==============================");
        System.out.println("|         ORDER MENU         |");
        System.out.println("==============================");
        System.out.println("| 1. Add Product to Cart     |");
        System.out.println("| 2. View Cart & Checkout    |");
        System.out.println("| 3. Cancel / Exit           |");
        System.out.println("==============================");
        System.out.print("Enter choice: ");
        try {
            return Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }


    public char promptConfirmOrder() {
        System.out.print("\nConfirm To Make Order? (Y = Yes / N = No): ");
        String input = scan.nextLine().toUpperCase();
        return input.isEmpty() ? 'N' : input.charAt(0);
    }

    public char promptAddMore() {
        System.out.print("Do you want to add more products? (Y/N): ");
        String input = scan.nextLine().toUpperCase();
        return input.isEmpty() ? 'N' : input.charAt(0);
    }

    // --- Payment Views ---

    public int promptMemberType() {
        System.out.println("\n----------------------");
        System.out.println("|      CUSTOMER TYPE |");
        System.out.println("----------------------");
        System.out.println("1. Member");
        System.out.println("2. Non-member");
        System.out.print("Selection: ");
        try {
            return Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String promptMemberID() {
        System.out.print("Enter Member ID: ");
        return scan.nextLine().toUpperCase();
    }

    public void displayPaymentSummary(Member member, Payment payment, double subtotal) {
        System.out.println("-------------------------------------------------------");
        System.out.printf("| Member ID       : %30s    |\n", member.getID());
        System.out.printf("| Subtotal        : RM %29.2f |\n", subtotal);
        System.out.printf("| Member Discount : RM %29.2f |\n", payment.calDiscount());
        System.out.printf("| Tax Amount      : RM %29.2f |\n", payment.calTaxRate());
        System.out.println("-------------------------------------------------------");
        System.out.printf("| GRAND TOTAL     : RM %29.2f |\n", payment.calTotal());
        System.out.println("-------------------------------------------------------");
    }

    public int promptPaymentMethod() {
        System.out.println("\nSelect Payment Method:");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.print("Choice: ");
        try {
            return Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public double promptCashAmount() {
        System.out.print("Enter Amount Given: RM ");
        try {
            return Double.parseDouble(scan.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String promptCardNumber() {
        System.out.print("Enter Card No: ");
        return scan.nextLine();
    }

    public String promptCVV() {
        System.out.print("Enter CVV: ");
        return scan.nextLine();
    }

    public void displayError(String msg) {
        System.out.println("Error: " + msg);
    }
    
    public void displaySuccess(String msg) {
        System.out.println("Success: " + msg);
    }
    
    public void pause() {
        System.out.println("Press Enter to continue...");
        scan.nextLine();
    }
    
    // Using the models to print the receipt data
    public void displayReceipt(Receipt receipt, Cart cart, Payment payment, Member member, String cashierID, double change, String payMethod) {
        System.out.println("\n=======================================================================");
        System.out.println("|                              RECEIPT                                | ");
        System.out.println("=======================================================================");
        System.out.printf("|Invoice no : %-53s |\n", receipt.getInvoiceID());
        System.out.printf("|Date       : %-53s |\n", receipt.getCreatedDateTime());
        System.out.printf("|Cashier ID : %-53s |\n", cashierID);
        System.out.println("-----------------------------------------------------------------------");
        
        // Iterate cart items
        for(CartItem item : cart.getCartlist()) {
             System.out.printf("| %-45s x%-3d  RM%8.2f |\n", item.getCartProd().getProductName(), item.getQuantity(), (item.getCartProd().getPrice() * item.getQuantity()));
        }

        System.out.println("=======================================================================");
        System.out.printf("|Total Qty       : %-50d |\n", cart.CalcTotalQty());
        System.out.printf("|Total (MYR)     : RM %-47.2f |\n", payment.calTotal());
        System.out.printf("|%s            : RM %-47.2f |\n", payMethod, (payment.calTotal() + change)); // Amount paid
        if(change > 0) {
            System.out.printf("|Change          : -RM %-46.2f |\n", change);
        }
        System.out.println("=======================================================================");
    }
}