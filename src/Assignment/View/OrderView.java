package Assignment.View;

import Assignment.Model.Cart;
import Assignment.Model.CartItem;
import Assignment.Model.Member;
import Assignment.Model.Payment;
import Assignment.Model.ReceiptData;
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

    public void displayPaymentSummary(Member member, Payment payment, double subtotal, Cart cart) {
        System.out.println("\n================================================================");
        System.out.println("|                         Checkout                             |");
        System.out.println("================================================================");
        System.out.println("----------------------------------------------------------------");
        System.out.printf("| Member ID       : %39s    |\n", member.getID());
        System.out.println("----------------------------------------------------------------");
        System.out.printf("| %-4s %-25s %-12s %-8s %-7s |\n", "No.", "Product", "Price", "Qty", "Total");
        System.out.println("----------------------------------------------------------------");

        int no = 1;
        for (CartItem item : cart.getCartList()) {
            System.out.printf("| %-4d %-25s RM%-10.2f %-8d RM%-4.2f|\n",
                    no++,
                    item.getProduct().getProductName(),
                    item.getProduct().getPrice(),
                    item.getQuantity(),
                    (item.getProduct().getPrice() * item.getQuantity()));
        }

        System.out.println("----------------------------------------------------------------");
        System.out.printf("| Subtotal        : RM %40.2f|\n", subtotal);
        System.out.printf("| Tax Amount      : RM %40.2f|\n", payment.calculateTax());
        System.out.printf("| Member Discount : RM -%39.2f|\n", payment.calculateDiscount());
        System.out.println("----------------------------------------------------------------");
        System.out.printf("| GRAND TOTAL     : RM %40.2f|\n", payment.calculateTotal());
        System.out.println("----------------------------------------------------------------");
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
        System.out.println();
        System.out.println("Success: " + msg);
    }

    public void pause() {
        System.out.println("Press Enter to continue...");
        scan.nextLine();
    }

    public char promptReceiptConfirmation() {
        System.out.print("Would you like to generate a receipt? (Y/N): ");
        String input = scan.nextLine().toUpperCase();
        return input.isEmpty() ? 'N' : input.charAt(0);
    }

    // Using the models to print the receipt data
    public void displayReceipt(ReceiptData data) {

        System.out.println();
        System.out.println("============================================================");
        System.out.println("|                        RECEIPT                           |");
        System.out.println("============================================================");
        System.out.printf("| Invoice No     : %-39s |\n", data.getInvoiceID());
        System.out.printf("| Date/Time      : %-39s |\n", data.getCreatedDateTime());
        System.out.printf("| Cashier ID     : %-39s |\n", data.getCashierID());
        System.out.printf("| Member ID      : %-39s |\n", data.getMember().getID());
        System.out.println("------------------------------------------------------------");
        System.out.printf("| %-3s | %-20s | %-9s | %-3s | %-9s |\n",
                "No.", "Product", "Price", "Qty", "Total");
        System.out.println("------------------------------------------------------------");

        int no = 1;
        for (CartItem item : data.getCart().getCartList()) {
            System.out.printf("| %-3d | %-20s | RM %-6.2f | %-3d | RM %-6.2f |\n",
                    no++,
                    item.getProduct().getProductName(),
                    item.getProduct().getPrice(),
                    item.getQuantity(),
                    item.calculateItemTotal()
            );
        }

        System.out.println("------------------------------------------------------------");
        System.out.printf("| Subtotal         : RM %34.2f |\n", data.getSubtotal());
        System.out.printf("| Tax              : RM %34.2f |\n", data.getPayment().calculateTax());
        System.out.printf("| Discount         : RM -%33.2f |\n", data.getPayment().calculateDiscount());
        System.out.println("------------------------------------------------------------");
        System.out.printf("| GRAND TOTAL      : RM %34.2f |\n", data.getPayment().calculateTotal());
        System.out.printf("| Payment Method   : %37s |\n", data.getPaymentMethod());
        System.out.printf("| Amount Paid      : RM %34.2f |\n", data.getMoney());

        double change = data.getChange() > 0 ? data.getChange() : 0.00;
        System.out.printf("| Change           : RM %34.2f |\n", change);

        System.out.println("============================================================");
        System.out.println("|                Thank you for shopping!                   |");
        System.out.println("============================================================");
        System.out.println();
    }

}
