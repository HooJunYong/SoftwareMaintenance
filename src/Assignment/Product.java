/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author yutic
 */
public class Product {

    private String ProductID;
    private String ProductName;
    private String Price;
    private String quanity;
    private String warrantyYear;

    private static int counter;

    public Product(String ProductID, String ProductName, String Price, String quanity, String warrantyYear) {
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.Price = Price;
        this.quanity = quanity;
        this.warrantyYear = warrantyYear;
    }

    public Product(String ProductName, String Price, String quanity, String warrantyYear) {
        this.ProductName = ProductName;
        this.Price = Price;
        this.quanity = quanity;
        this.warrantyYear = warrantyYear;
    }

    public Product() {

    }

    private String generateProductCode() {// start the counter based on the highest existing product ID to generate new id

        String formattedCounter = String.format("%03d", counter);
        counter++;
        return "P" + formattedCounter;
    }

    public String getProductID() {
        return ProductID;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getPrice() {
        return Price;
    }

    public String getQuanity() {
        return quanity;
    }

    public String getWarrantyYear() {
        return warrantyYear;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public void setProductID(String ProductID) {
        this.ProductID = ProductID;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public void setQuanity(String quanity) {
        this.quanity = quanity;
    }

    public void setWarrantyYear(String warrantyYear) {
        this.warrantyYear = warrantyYear;
    }

    static {
        // Initialize the counter based on the highest existing product ID
        ArrayList<Product> productList = readProduct();
        if (!productList.isEmpty()) {
            int maxId = 0;
            for (Product product : productList) {
                String productId = product.getProductID().substring(1); // Remove 'P' to parse as integer
                int id = Integer.parseInt(productId);
                if (id > maxId) {
                    maxId = id;
                }
            }
            counter = maxId + 1;
        } else {
            counter = 1; // If no products exist, start from 1
        }
    }

    public static ArrayList<Product> readProduct() {
        ArrayList<Product> productList = new ArrayList<>();

        try {
            File productfile = new File("C:\\Users\\Acer\\Downloads\\DFT2(G1)-YuTickSang-IvanPekYuHeng-HooJunYong\\product.txt");
            Scanner scan = new Scanner(productfile);
            Product product;
            while (scan.hasNextLine()) {
                String[] data = scan.nextLine().split(",");
                if (data.length >= 5) {
                    product = new Product(data[0], data[1], data[2], data[3], data[4]);
                    productList.add(product);

                }

            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error had occured");
        }

        return productList;
    }

    public static void writeProduct(Product product) {
        String newProductID = "P" + String.format("%03d", counter);
        counter++;
        try {
            File productfile = new File("C:\\Users\\Acer\\Downloads\\DFT2(G1)-YuTickSang-IvanPekYuHeng-HooJunYong\\product.txt");
            FileWriter writer = new FileWriter(productfile, true);

            writer.write(newProductID + "," + product.getProductName() + "," + product.getPrice() + "," + product.getQuanity() + "," + product.getWarrantyYear() + "\n");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error had occurred");
        }
    }

    public static void writeArray(ArrayList<Product> prodList) {
        try {
            File productFile = new File("C:\\Users\\Acer\\Downloads\\DFT2(G1)-YuTickSang-IvanPekYuHeng-HooJunYong\\product.txt");
            FileWriter writer = new FileWriter(productFile);
            PrintWriter productPrint = new PrintWriter(writer);
            for (Product product : prodList) {
                productPrint.print(product.getProductID() + "," + product.getProductName() + "," + product.getPrice() + "," + product.getQuanity() + "," + product.getWarrantyYear() + "\n");
            }
            productPrint.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error had occured");
        }
    }

    public static void updateProductQuantity(ArrayList<Product> productList, String productID, int purchasedQuantity) {
        for (Product product : productList) {
            if (product.getProductID().equals(productID)) {
                int currentQuantity = Integer.parseInt(product.getQuanity());
                if (currentQuantity >= purchasedQuantity) {
                    product.setQuanity(Integer.toString(currentQuantity - purchasedQuantity));
                    break; // Exit the loop once the product is found and updated
                } else {
                    System.out.println("Error: Insufficient stock for product ID " + productID);
                }
            }
        }
    }

    public static void AProduct() {
        int reset = 0;
        int again = 0;
        int opt = 0;
        String ProName;
        String ProPrice;
        double userDouble;
        int userQuantity;
        int userWarranty;
        String ProQuantity;
        String ProWarranty;
        char chr;
        Scanner scan = new Scanner(System.in);
        do {

            do {
               
                System.out.printf("Product Name (X for exit) :");
              
                ProName = scan.nextLine();

                if (ProName.toUpperCase().charAt(0) == 'X') {
                    return;
                } 
                   
                
                if (ProName.isEmpty()) {
                    System.out.println("Product Name cannot be empty!!\n");
                    reset = 1;
                } else {
                    reset = 0;
                    break;
                }

            } while (reset == 1);

            do {
                System.out.printf("Product Price :");
             
                ProPrice = scan.nextLine();

                try {
                    userDouble = Double.parseDouble(ProPrice);

                    // Check if the input is empty (after trimming leading/trailing whitespace)
                    if (ProPrice.trim().isEmpty()) {
                        reset = 1;
                        System.out.println("Input is empty. Please enter a double value\n.");
                    } else {
                        reset = 0;
                        break;
                    }
                    // Handle the case where the input cannot be parsed as a double  
                    //the input contains characters that cannot be converted to a double
                } catch (NumberFormatException e) {
                    reset = 1;
                    System.out.println("Invalid input. Please enter a valid double value.\n");
                }
            } while (reset == 1);

            do {
                System.out.printf("Product Quantity:");
                ProQuantity = scan.nextLine();

                try {
                    userQuantity = Integer.parseInt(ProQuantity);

                    if (ProQuantity.trim().isEmpty()) {
                        reset = 1;
                        System.out.println("Input is empty. Please enter a  value.\n");
                    } else {
                        reset = 0;
                        break;
                    }
                    // Handle the case where the input cannot be parsed as a double  
                    //the input contains characters that cannot be converted to a double
                } catch (NumberFormatException e) {
                    reset = 1;
                    System.out.println("Invalid input. Please enter a valid  value.\n");
                }

            } while (reset == 1);

            do {
                System.out.printf("Product Warranty:");
                ProWarranty = scan.nextLine();

                try {
                    userWarranty = Integer.parseInt(ProWarranty);

                    if (ProWarranty.trim().isEmpty()) {
                        reset = 1;
                        System.out.println("Input is empty. Please enter a value\n.");
                    } else {
                        reset = 0;
                        break;
                    }
                    // Handle the case where the input cannot be parsed as a double  
                    //the input contains characters that cannot be converted to a double
                } catch (NumberFormatException e) {
                    reset = 1;
                    System.out.println("Invalid input. Please enter a valid  value.\n");
                }
            } while (reset == 1);

            Product productlist = new Product(ProName, ProPrice, ProQuantity, ProWarranty);

            Product.writeProduct(productlist);
            System.out.println("\n");
            System.out.println("============================================");
            System.out.println("|       Product Added Successfully  !!     |");
            System.out.println("============================================\n");

            do {
                System.out.printf("Do you want to continue to add item (Yes - Y / No - N) ? >");
                chr = scan.nextLine().toUpperCase().charAt(0);
                if (Character.toUpperCase(chr) == 'Y') {
                    again = 1;
                    opt = 0;
                }

                if (Character.toUpperCase(chr) == 'N') {
                    again = 0;
                    opt = 0;
                }
                if (Character.toUpperCase(chr) != 'Y' && Character.toUpperCase(chr) != 'N') {
                    System.out.println("Invalid Input ! Please Try Again\n");
                    opt = 1;
                }
            } while (opt == 1);

        } while (again == 1);
    }

    public static void VProduct() {

        System.out.println("==================================================================================================");
        System.out.printf("|%-4s %-17s %-29s %-15s %-13s %s|", "No.", "Product ID", "Product Name", "Price", "Quantity", "Warranty Year");
        System.out.printf("\n");
        System.out.println("==================================================================================================");
        ArrayList<Product> product = Product.readProduct();
        for (int i = 0; i < product.size(); i++) {
            System.out.print("|");
            System.out.print((i + 1) + "   " + product.get(i).toString());
            System.out.print("|");
            System.out.print("\n");

        }
        System.out.println("==================================================================================================");
        System.out.println("Press Enter to return to exit...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // Wait for the user to press Enter      s00
    }

    public static void MProduct() {
        Scanner scan = new Scanner(System.in);
        String tempID;
        String ID;
        String Name;
        String Price;
        String Quantity;
        String Warranty;
        Double ToDouble;
        char con;
        int ToInt;
        int ToNum;
        int choice;
        boolean found = false;
        boolean validInput = false;
        int reset = 0;
        int repeat = 0;
        int index = 0;
        int opt = 0;
        int cho = 0;
        int conti = 0;
        int app = 0;
        ArrayList<Product> product = Product.readProduct();

        do {
            System.out.println("**********************");
            System.out.println("|   MODIFY PRODUCT   |");
            System.out.println("**********************");
            System.out.println("");
            do {

                System.out.printf("Please Enter Product ID (X for exit): ");
                tempID = scan.next();

                if (tempID.toUpperCase().charAt(0) == 'X') {
                    return;
                }
                if (!tempID.isEmpty()) {  // Check if input is not empty
                    tempID = tempID.toUpperCase();
                    validInput = true;
                    for (int i = 0; i < product.size(); i++) {
                        if (product.get(i).getProductID().equals(tempID)) {
                            reset = 0;
                            index = i;
                            found = true;
                            validInput = true;
                            break;
                        } else {
                            reset = 1;
                            validInput = false;
                        }
                    }

                    if (reset == 1) {
                        System.out.println("Product ID did not exist !!\n");
                    }
                } else {
                    System.out.println("Invalid input! Product ID cannot be empty.\n");
                    reset = 1;
                    validInput = false;
                }
            } while (!validInput);

            if (found) {
                System.out.println("");
                System.out.println("======================================");
                System.out.println("  Product ID      : " + product.get(index).getProductID());
                System.out.println("1.Name            : " + product.get(index).getProductName());
                System.out.println("2.Price           : RM" + product.get(index).getPrice());
                System.out.println("3.Quantity        : " + product.get(index).getQuanity());
                System.out.println("4.Warranty Year   : " + product.get(index).getWarrantyYear() + " Year");
                System.out.println("======================================");
                System.out.println("Remarks: Product ID cannot be modify !!");
                System.out.println("");
                do {
                    System.out.print("Enter An Option to Modify (0 to exit modify):");
                    choice = scan.nextInt();

                    if (choice > 4) {
                        opt = 1;
                        System.out.println("Invalid Input! Please Enter Again !\n");
                    }else{
                        opt = 0;
                    }

                    if (choice == 0) {
                        cho = 0;
                        opt = 0;
                        break;
                    }

                } while (opt == 1);

                switch (choice) {

                    case 1:
                         
                        do {
                            System.out.print("\n");
                            System.out.printf("Enter Product Name to change :");
                            scan.nextLine(); 
                            Name = scan.nextLine();

                            if (Name.trim().isEmpty()) {
                                System.out.println("Product Name cannot be empty!!\n");
                                repeat = 1;
                            } else {
                                repeat = 0;
                                break;
                            }

                        } while (repeat == 1);

                        if (repeat == 0) {
                            product.get(index).setProductName(Name);
                            System.out.println("");
                            System.out.println("--Successfully Modify Product Information--");

                            do {
                                System.out.print("\nDo You Want To Modify Another Product ? (Y/N) >");
                                con = scan.next().charAt(0);
                                if (Character.toUpperCase(con) == 'Y') {
                                    cho = 1;
                                    conti = 0;
                                }

                                if (Character.toUpperCase(con) == 'N') {
                                    cho = 0;
                                    conti = 0;
                                }
                                if (Character.toUpperCase(con) != 'Y' && Character.toUpperCase(con) != 'N') {
                                    conti = 1;
                                }
                            } while (conti == 1);
                        }

                        break;

                    case 2:

                        do {
                            System.out.printf("Enter Product Price To Modify :");
                            Price = scan.next();

                            try {
                                ToDouble = Double.parseDouble(Price);

                                // Check if the input is empty (after trimming leading/trailing whitespace)
                                if (Price.trim().isEmpty()) {
                                    repeat = 1;
                                    System.out.println("Input is empty. Please enter a double value.\n");
                                } else {
                                    repeat = 0;
                                    break;
                                }
                                // Handle the case where the input cannot be parsed as a double  
                                //the input contains characters that cannot be converted to a double
                            } catch (NumberFormatException e) {
                                repeat = 1;
                                System.out.println("Invalid input. Please enter a valid double value.\n");
                            }
                        } while (repeat == 1);

                        if (repeat == 0) {
                            product.get(index).setPrice(Price);
                            System.out.println("");
                            System.out.println("--Successfully Modify Product Information--");

                            do {
                                System.out.print("\nDo You Want To Modify Another Product ? (Y/N) >");
                                con = scan.next().charAt(0);
                                if (Character.toUpperCase(con) == 'Y') {
                                    cho = 1;
                                    conti = 0;
                                }

                                if (Character.toUpperCase(con) == 'N') {
                                    cho = 0;
                                    conti = 0;
                                }
                                if (Character.toUpperCase(con) != 'Y' && Character.toUpperCase(con) != 'N') {
                                    conti = 1;
                                }
                            } while (conti == 1);
                        }
                        break;
                    case 3:

                        do {
                            System.out.printf("Enter Product Quantity To Modify :");
                            Quantity = scan.next();

                            try {
                                ToInt = Integer.parseInt(Quantity);

                                // Check if the input is empty (after trimming leading/trailing whitespace)
                                if (Quantity.trim().isEmpty()) {
                                    repeat = 1;
                                    System.out.println("Input is empty. Please enter a integer value.\n");
                                } else {
                                    repeat = 0;
                                    break;
                                }
                                // Handle the case where the input cannot be parsed as a double  
                                //the input contains characters that cannot be converted to a double
                            } catch (NumberFormatException e) {
                                repeat = 1;
                                System.out.println("Invalid input. Please enter a valid integer value.\n");
                            }
                        } while (repeat == 1);

                        if (repeat == 0) {
                            product.get(index).setQuanity(Quantity);
                            System.out.println("");
                            System.out.println("--Successfully Modify Product Information--");

                            do {
                                System.out.print("\nDo You Want To Modify Another Product ? (Y/N) >");
                                con = scan.next().charAt(0);
                                if (Character.toUpperCase(con) == 'Y') {
                                    cho = 1;
                                    conti = 0;
                                }

                                if (Character.toUpperCase(con) == 'N') {
                                    cho = 0;
                                    conti = 0;
                                }
                                if (Character.toUpperCase(con) != 'Y' && Character.toUpperCase(con) != 'N') {
                                    conti = 1;
                                }
                            } while (conti == 1);
                        }

                        break;
                    case 4:

                        do {
                            System.out.printf("Enter Product Warranty Year To Modify :");
                            Warranty = scan.next();

                            try {
                                ToNum = Integer.parseInt(Warranty);

                                // Check if the input is empty (after trimming leading/trailing whitespace)
                                if (Warranty.trim().isEmpty()) {
                                    repeat = 1;
                                    System.out.println("Input is empty. Please enter a integer value.\n");
                                } else {
                                    repeat = 0;
                                    break;
                                }
                                // Handle the case where the input cannot be parsed as a double  
                                //the input contains characters that cannot be converted to a double
                            } catch (NumberFormatException e) {
                                repeat = 1;
                                System.out.println("Invalid input. Please enter a valid integer value.\n");
                            }
                        } while (repeat == 1);

                        if (repeat == 0) {
                            product.get(index).setWarrantyYear(Warranty);
                            System.out.println("");
                            System.out.println("============================================");
                            System.out.println("|Successfully Modify Product Information !!|");
                            System.out.println("============================================");

                            do {
                                System.out.print("\nDo You Want To Modify Another Product ? (Y/N) >");
                                con = scan.next().charAt(0);
                                if (Character.toUpperCase(con) == 'Y') {
                                    cho = 1;
                                    conti = 0;
                                }

                                if (Character.toUpperCase(con) == 'N') {
                                    cho = 0;
                                    conti = 0;
                                }
                                if (Character.toUpperCase(con) != 'Y' && Character.toUpperCase(con) != 'N') {
                                    conti = 1;
                                }
                            } while (conti == 1);
                        }

                }
                //overwriting the updated product information in text file
                Product.writeArray(product);

            }

        } while (cho == 1);
    }

    public static void DProduct() {
        Scanner scan = new Scanner(System.in);
        ArrayList<Product> product = Product.readProduct();
        String delID;
        boolean found = false;
        int reset = 0;
        int opt = 0;
        int repeat = 0;
        int index = 0;
        char chr;
        char cha;

        do {

            System.out.println("==================================================================================================");
            System.out.printf("|%-4s %-17s %-29s %-15s %-13s %s|", "No.", "Product ID", "Product Name", "Price", "Quantity", "Warranty Year");
            System.out.printf("\n");
            System.out.println("==================================================================================================");

            for (int i = 0; i < product.size(); i++) {
                System.out.print("|");
                System.out.print((i + 1) + "   " + product.get(i).toString());
                System.out.print("|");
                System.out.print("\n");

            }
            System.out.println("==================================================================================================");
            do {
                System.out.printf("Please Enter Product ID To Delete (X for exit) > ");
                delID = scan.next();

                if (delID.isEmpty()) {  // Check if input is not empty\
                    reset = 1;
                    System.out.println("Invalid input! Product ID cannot be empty.\n");

                } else if (delID.toUpperCase().charAt(0) == 'X') {
                    return;
                } else {
                    delID = delID.toUpperCase();
                    for (int i = 0; i < product.size(); i++) {
                        if (product.get(i).getProductID().equals(delID)) {
                            reset = 0;
                            index = i;
                            found = true;

                            break;
                        } else {
                            reset = 1;
                            found = false;
                        }
                    }

                    if (!found) {
                        System.out.println("Product ID did not exist !!\n");
                    }
                }

            } while (reset == 1);

            if (found) {
                do {

                    System.out.println("\n");
                    System.out.printf("Are You Confirm to Delete Product %s (Y/N) > ", delID);
                    chr = scan.next().charAt(0);

                    if (Character.toUpperCase(chr) == 'Y') {
                        product.remove(index);//reomve product
                        counter--;
                        for (int i = 0; i < product.size(); i++) {
                            product.get(i).setProductID("P" + String.format("%03d", i + 1));
                        }
                        Product.writeArray(product);//overwrite the product text file 

                        repeat = 0;

                        System.out.println("============================================");
                        System.out.printf("|     Successfully Delete Product %s !!  |\n", delID);
                        System.out.println("============================================");
                        System.out.print("\n");
                        do {
                            System.out.printf("Are You Want To Delete Another Product Again ? (Y/N) > ");
                            cha = scan.next().charAt(0);

                            if (Character.toUpperCase(cha) == 'Y') {
                                repeat = 0;
                                opt = 1;
                            }

                            if (Character.toUpperCase(cha) == 'N') {
                                repeat = 0;
                                opt = 0;
                                break;
                            }

                            if (Character.toUpperCase(cha) != 'Y' && Character.toUpperCase(cha) != 'N') {
                                System.out.println("Invalid Input ! Please Try Again\n");
                                repeat = 1;
                            }
                        } while (repeat == 1);

                    }

                    if (Character.toUpperCase(chr) == 'N') {
                        repeat = 0;
                        opt = 1;
                    }

                    if (Character.toUpperCase(chr) != 'Y' && Character.toUpperCase(chr) != 'N') {
                        repeat = 1;
                        System.out.println("Invalid Input ! Please Try Again\n");
                    }
                } while (repeat == 1);
            }
        } while (opt == 1);
    }

    public String toString() {
        return String.format("%3s %-12s %-31s %-18s %-16s %-7s", "", ProductID, ProductName, Price, quanity, warrantyYear);
    }
}
