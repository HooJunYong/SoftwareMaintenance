package Assignment.View;

import Assignment.Model.Product;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ProductView {
    
    private Scanner scan;

    public ProductView() {
        this.scan = new Scanner(System.in);
    }

    public void displayProductTable(ArrayList<Product> products) {
        System.out.println("==================================================================================================");
        System.out.printf("|%-4s %-17s %-29s %-15s %-13s %s|%n", "No.", "Product ID", "Product Name", "Price", "Quantity", "Warranty Year");
        System.out.println("==================================================================================================");
        
        for (int i = 0; i < products.size(); i++) {
            System.out.printf("|%-4d %s|%n", (i + 1), products.get(i).toString());
        }
        System.out.println("==================================================================================================");
    }

    public int getProductSelection(int maxOption) {
        int select = -1;
        while (true) {
            System.out.print("Please Enter Product No (0 for Exit): ");
            try {
                select = scan.nextInt();
                if (select >= 0 && select <= maxOption) {
                    return select;
                } else {
                    System.out.println("Please Enter Value Between 1 And " + maxOption);
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid number. Please try again.");
                scan.nextLine(); // consume invalid input
            }
        }
    }
    
    public int getQuantityInput(int availableStock) {
        while(true) {
            System.out.print("Please Enter Quantity: ");
            try {
                int qty = scan.nextInt();
                if(qty <= availableStock && qty > 0) {
                    return qty;
                } else {
                    System.out.println("Invalid quantity! Available stock: " + availableStock);
                }
            } catch (Exception e) {
                System.out.println("Invalid input.");
                scan.nextLine();
            }
        }
    }
    
    public void displayMessage(String msg) {
        System.out.println(msg);
    }
}