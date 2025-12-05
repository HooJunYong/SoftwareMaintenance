package Assignment.View;

import Assignment.Model.Cart;
import Assignment.Model.CartItem;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CartView {

    private Scanner scan;

    public CartView() {
        this.scan = new Scanner(System.in);
    }

    public void displayCartTable(Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("\n========================================");
            System.out.println("|           Cart is Empty              |");
            System.out.println("========================================");
            return;
        }

        System.out.println("\n============================================================================================");
        System.out.printf("|%-4s %-17s %-29s %-15s %-13s %s|%n", 
                "No.", "Product ID", "Product Name", "Price", "Quantity", "Total");
        System.out.println("============================================================================================");

        int index = 1;
        for (CartItem item : cart.getCartlist()) {
            System.out.printf("|%-4d %s|%n", index++, item.toString());
        }

        System.out.println("============================================================================================");
        System.out.printf("|%79s: RM%-10.2f|%n", "Subtotal", cart.calculateSubtotal());
        System.out.println("============================================================================================");
    }

    public int displayCartMenu() {
        System.out.println("\n==============================");
        System.out.println("|         Cart Menu          |");
        System.out.println("==============================");
        System.out.println("|    1. Add More Products    |");
        System.out.println("|    2. Edit Item Quantity   |");
        System.out.println("|    3. Remove Item          |");
        System.out.println("|    4. Clear Cart           |");
        System.out.println("|    5. Proceed to Checkout  |");
        System.out.println("|    6. Cancel Order         |");
        System.out.println("==============================");
        System.out.print("Enter your choice: ");

        try {
            int choice = scan.nextInt();
            scan.nextLine(); // consume newline
            return choice;
        } catch (InputMismatchException e) {
            scan.nextLine();
            return -1;
        }
    }

    public int getItemSelection(int maxItems) {
        while (true) {
            System.out.print("Select item number (0 to cancel): ");
            try {
                int selection = scan.nextInt();
                scan.nextLine();
                if (selection >= 0 && selection <= maxItems) {
                    return selection;
                }
                System.out.println("Please enter a value between 0 and " + maxItems);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scan.nextLine();
            }
        }
    }

    public int getNewQuantity(int currentQty, int maxStock) {
        while (true) {
            System.out.printf("Current quantity: %d | Available stock: %d%n", currentQty, maxStock);
            System.out.print("Enter new quantity (0 to cancel): ");
            try {
                int newQty = scan.nextInt();
                scan.nextLine();
                if (newQty == 0) {
                    return -1; // cancelled
                }
                if (newQty > 0 && newQty <= maxStock) {
                    return newQty;
                }
                System.out.println("Invalid quantity! Must be between 1 and " + maxStock);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scan.nextLine();
            }
        }
    }

    public boolean confirmAction(String action) {
        System.out.print("Are you sure you want to " + action + "? (Y/N): ");
        String input = scan.nextLine().trim().toUpperCase();
        return input.equals("Y") || input.equals("YES");
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displaySuccess(String message) {
        System.out.println("\n[SUCCESS] " + message);
    }

    public void displayError(String message) {
        System.out.println("\n[ERROR] " + message);
    }

    public void displayEmptyCartWarning() {
        System.out.println("\n[WARNING] Your cart is empty. Please add products first.");
    }
}