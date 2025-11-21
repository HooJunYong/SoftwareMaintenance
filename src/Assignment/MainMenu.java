/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import Assignment.Product;

/**
 * ^
 *
 * @author yutic
 */
public class MainMenu {

    private static String userRole;
    private static String cashierID;

    public static void main(String[] agrs) {
        StaffManagement staff = new StaffManagement();
        staff.DisplayLogo();
        staff.Login();
    }

    public static void setRole(String role) {
        userRole = role;
    }

    public static void setCashierID(String cashierID) {
        MainMenu.cashierID = cashierID;
    }

    public static String getCashierID() {
        return cashierID;
    }

    public static void StaffMenu() {
        int opt = 0;
        int optCheck = 0;
        int choice = 0;
        StaffManagement staff = new StaffManagement();
        Scanner scan = new Scanner(System.in);
        System.out.println("");

        System.out.println("==============================");
        System.out.println("|      TECH GADGET POS       |");
        System.out.println("==============================");
        System.out.println("|    1. Product              |");
        System.out.println("|    2. Order                |");
        System.out.println("|    3. Member               |");
        System.out.println("|    4. Logout               |");
        System.out.println("==============================");
        do {
            try {
                System.out.printf("Enter your choice (1-4) > ");
                opt = scan.nextInt();

                switch (opt) {
                    case 1:
                        ProductPage(false);
                        break;
                    case 2:
                        OrderPage(false);
                        break;
                    case 3:
                        MemberPage();
                        break;
                    case 4:
                        staff.DisplayLogo();
                        staff.Login();
                        break;
                    default:
                        System.out.println("Invalid Input!!!\n");
                        choice = 1;
                        break;

                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.\n");
                choice = 1;
                scan.nextLine(); // Consume the invalid input
            }

        } while (choice == 1);

    }

    public static void AdminMenu() {
        int opt = 0;
        int optCheck = 0;
        int choice = 0;
        StaffManagement staff = new StaffManagement();

        Scanner scan = new Scanner(System.in);
        System.out.println("");

        System.out.println("==============================");
        System.out.println("|      TECH GADGET POS       |");
        System.out.println("==============================");
        System.out.println("|    1. Product              |");
        System.out.println("|    2. Order                |");
        System.out.println("|    3. Member               |");
        System.out.println("|    4. Staff                |");
        System.out.println("|    5. Logout               |");
        System.out.println("==============================");
        do {
            try {
                System.out.printf("Enter your choice (1-5) > ");
                opt = scan.nextInt();

                switch (opt) {
                    case 1: {

                        ProductPage(true);
                    }
                    break;

                    case 2:
                        OrderPage(true);
                        break;
                    case 3:
                        MemberPage();
                        break;
                    case 4:
                        StaffPage();
                        break;
                    case 5:
                        staff.DisplayLogo();
                        staff.Login();
                        break;
                    default:
                        System.out.println("Invalid Input!!!\n");
                        choice = 1;
                        break;

                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.\n");
                choice = 1;
                scan.nextLine(); // Consume the invalid input
            }
        } while (choice == 1);
    }

//----------------------------------------Member Module-------------------------------------------
    public static void MemberPage() {
        String choice;
        int reset = 0;
        Scanner sc = new Scanner(System.in);
        MemberManagement memberManage = new MemberManagement();
        ArrayList<Person> personList = Person.read();
        String filePath = "/Users/yutic/Documents/NetBeansProjects/DFT2(G1)-YuTickSang-IvanPekYuHeng-HooJunYong/person.txt";
        do {
            System.out.println("");
            System.out.println("==============================");
            System.out.println("|        Member Menu         |");
            System.out.println("==============================");
            System.out.println("|    1. Add Member           |");
            System.out.println("|    2. Search Member        |");
            System.out.println("|    3. Edit Member          |");
            System.out.println("|    4. Delete Member        |");
            System.out.println("|    5. Display Member       |");
            System.out.println("|    6. Return to Main Menu  |");
            System.out.println("==============================");
            System.out.print("Enter you choice : ");
            choice = sc.nextLine();

            switch (choice) {
                case "1":
                    memberManage.AddMember();
                    break;
                case "2":
                    memberManage.SearchMember();
                    break;
                case "3":
                    memberManage.EditMember();
                    break;
                case "4":
                    memberManage.DeleteMember(personList, filePath);
                    break;
                case "5":
                    memberManage.DisplayMember();
                    break;
                case "6":
                    // Check the user's role and call the appropriate menu
                    if ("Admin".equals(userRole)) {
                        AdminMenu();
                    } else {
                        StaffMenu();
                    }
                    break;
                default:
                    System.out.println("Invalid Input!!! Please Try Again.");
                    reset = 1;
            }
        } while (reset == 1);
    }
//----------------------------------------Member Module-------------------------------------------

//----------------------------------------Staff Module-------------------------------------------
    public static void StaffPage() {
        String choice;
        int reset = 0;
        StaffManagement manage = new StaffManagement();
        Scanner sc = new Scanner(System.in);
        ArrayList<Person> personList = Person.read();
        String filePath = "/Users/yutic/Documents/NetBeansProjects/DFT2(G1)-YuTickSang-IvanPekYuHeng-HooJunYong/person.txt";
        do {
            System.out.println("");
            System.out.println("==============================");
            System.out.println("|        Staff Menu          |");
            System.out.println("==============================");
            System.out.println("|    1. Add Staff            |");
            System.out.println("|    2. Search Staff         |");
            System.out.println("|    3. Edit Staff           |");
            System.out.println("|    4. Delete Staff         |");
            System.out.println("|    5. Display Staff        |");
            System.out.println("|    6. Return to Main Menu  |");
            System.out.println("==============================");
            System.out.print("Enter you choice : ");
            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    manage.AddStaff();
                    break;
                case "2":
                    manage.SearchStaffOrAdmin();
                    break;
                case "3":
                    manage.EditStaffOrAdmin();
                    break;
                case "4":
                    manage.DeleteStaff(personList, filePath);
                    break;
                case "5":
                    manage.DisplayStaffOrAdmin();
                    break;
                case "6":
                    if ("Admin".equals(userRole)) {
                        AdminMenu();
                    } else {
                        StaffMenu();
                    }
                    break;
                default:
                    System.out.println("Invalid Input!!! Please Try Again.");
                    reset = 1;
            }
        } while (reset == 1);

    }
//----------------------------------------Staff Module-------------------------------------------

    public static void ProductPage(boolean isAdmin) {
        ArrayList<Product> product = Product.readProduct();
        Product prod = new Product();
        String opt;
        int optCheck = 0;
        int back = 0;
        int repeat = 0;

        Scanner scan = new Scanner(System.in);
        System.out.println("");
        do {

            System.out.println("==============================");
            System.out.println("|       Product Page         |");
            System.out.println("==============================");
            System.out.println("|    1. View Products        |");
            System.out.println("|    2. Add Product          |");
            System.out.println("|    3. Modify Product       |");
            System.out.println("|    4. Delete Product       |");
            System.out.println("|    5. Exit                 |");
            System.out.println("==============================");

            do {

                System.out.printf("Enter your choice (1-5) > ");
                opt = scan.nextLine();

                switch (opt) {
                    case "1":
                        Product.VProduct();
                        repeat = 1;
                        back = 0;
                        break;
                    case "2":
                        Product.AProduct();
                        repeat = 1;
                        back = 0;
                        break;
                    case "3":
                        Product.MProduct();
                        repeat = 1;
                        back = 0;
                        break;
                    case "4":
                        Product.DProduct();
                        repeat = 1;
                        back = 0;
                        break;
                    case "5":
                        repeat = 0;
                        back = 0;
                        break;

                    default:
                        System.out.println("Invalid Input!!!");
                        back = 1;
                        break;

                }

            } while (back == 1);

            if (repeat == 0) {
                // If the user chooses to exit, go back to the appropriate menu based on the user's role
                if (isAdmin) {
                    AdminMenu();
                } else {
                    StaffMenu();
                }
            }

        } while (repeat == 1);

    }

    //order module
    public static void OrderPage(boolean isAdmin) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Product> product = Product.readProduct();
        Cart cart = new Cart();
        int select;
        int choice;
        int quantity;
        int reset = 0;
        int back = 0;
        int repeat = 0;
        int check = 0;
        char confirm;
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

                System.out.print("Please Enter Product No (0 for Exit)  :");
                try {

                    select = scan.nextInt();
                    reset = 0;
                } catch (InputMismatchException e) {
                    System.out.println("That was not a number.  Please try again.");
                    reset = 1;
                    select = -1;
                    scan.nextLine();
                }
                if (select < 0 || select > product.size()) {
                    System.out.println("Please Enter Value Between 1 And " + (product.size()));
                    System.out.println("");
                    reset = 1;
                } else if (select == 0) {

                    back = 1;
                    reset = 0;
                    // If the user chooses to exit, go back to the appropriate menu based on the user's role
                    if (isAdmin) {
                        AdminMenu();
                    } else {
                        StaffMenu();
                    }

                }

            } while (reset == 1 && select != 0);

            // Retrieve the selected product
            Product selectedProduct = product.get(select - 1);
            do {
                System.out.print("Please Enter Quantity :");

                quantity = scan.nextInt();
                 int availableStock = Integer.parseInt(selectedProduct.getQuanity());

                if ( quantity > availableStock) {
                    System.out.println("Insufficient Stock! Available quantity: " + availableStock);
                    System.out.println("");
                    reset = 1;
                } else {
                    
                    cart.addToCart(selectedProduct, quantity);
                    reset = 0;
                    System.out.println("");
                    System.out.println("============================================");
                    System.out.println("|            Add Successfully !!           |");
                    System.out.println("============================================");
                    System.out.println("");
                }

            } while (reset == 1);

            if (reset == 0) {
                do {
                    try {
                        System.out.print("Proceed To Cart or Add More Order (1 - Cart / 2 - Add Order) >");
                        choice = scan.nextInt();

                        if (choice == 1) {
                            repeat = 0;
                            check = 1;
                            cart.viewCart();

                        }

                        if (choice == 2) {
                            repeat = 0;
                            back = 1;
                            check = 0;
                        }

                        if (choice > 2) {
                            System.out.println("Invalid Input ! Please Try Again\n");
                            repeat = 1;
                        }
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Invalid input! Please enter a valid number.\n");
                        repeat = 1;
                        scan.nextLine(); // Consume the invalid input
                    }
                } while (repeat == 1);
            }

            if (check == 1) {
                System.out.println("");
                do {
                    reset = 0;
                    System.out.print("Confirm To Make Order ? (Y = Confirm / N = No) >");
                    confirm = scan.next().charAt(0);

                    if (Character.toUpperCase(confirm) == 'Y') {
                        ProcessOrder(cart);
                        reset = 0;
                        break;
                    }

                    if (Character.toUpperCase(confirm) == 'N') {
                        back = 0;
                        reset = 0;
                        if ("Admin".equals(userRole)) {
                            AdminMenu();
                        } else {
                            StaffMenu();
                        }
                        break;
                    }

                    if (Character.toUpperCase(confirm) != 'Y' && Character.toUpperCase(confirm) != 'N') {
                        System.out.println("Invalid Input ! Please Try Again\n");
                        reset = 1;
                    }

                } while (reset == 1);

            }

        } while (back == 1);

    }

    public static void ProcessOrder(Cart cart) {
        Scanner scan = new Scanner(System.in);
        Member member = new Member();
        Payment payment = new Payment(cart);
        int confirm;
        int again = 0;
        int invalid = 0;
        String tempId;
        do {
            invalid = 0;
            System.out.println("----------------------");
            System.out.println("|      PAYMENT       |");
            System.out.println("----------------------");
            System.out.println("1. Member");
            System.out.println("2. Non-member");
            System.out.print("Enter Your Selection > ");
            try {

                confirm = scan.nextInt();
                if (confirm == 1) {
                    invalid = 1;
                    do {
                        again = 0;
                        System.out.print("Enter Member ID > ");
                        tempId = scan.next().toUpperCase();

                        if (!member.validateID(tempId)) {
                            again = 1;
                            System.out.println("Invalid Member ID !! Please Try Again\n");
                        } else {
                            member.setID(tempId);

                            payment.setDiscount(0.05);
                            payAmount(payment, cart, member);
                        }

                    } while (again == 1);

                }

                if (confirm == 2) {
                    invalid = 1;
                    member.setID("-");
                    payment.setDiscount(0.00);
                    payAmount(payment, cart, member);
                }

                if (confirm != 1 && confirm != 2) {
                    System.out.println("Invalid Input! Please Try Again\n");
                    invalid = 0;
                }
            } catch (InputMismatchException e) {
                // Handle the exception when a non-integer input is entered
                System.out.println("Invalid Input! Please Enter a Valid Integer.\n");
                scan.nextLine(); // Clear the input buffer
            }
        } while (invalid == 0);

    }

    public static void payAmount(Payment payment, Cart cart, Member member) {
        cart.viewPayment();
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("|                                               Member ID = %10s|\n", member.getID());
        System.out.printf("|                                         Member Discount = RM%8.2f|\n", payment.calDiscount());
        System.out.printf("|                                              Tax Amount = RM%8.2f|\n", payment.calTaxRate());
        System.out.printf("|                                                Subtotal = RM%8.2f|\n", cart.CalcSubtotal());
        System.out.printf("|                                             Total Price = RM%8.2f|\n", payment.calTotal());
        System.out.println("-----------------------------------------------------------------------");

        double total = payment.calTotal();
        paymentMethod(cart, payment, total, member);
    }

    public static void paymentMethod(Cart cart, Payment payment, Double total, Member member) {
        Scanner scan = new Scanner(System.in);
        int choice;
        int reset = 0;
        int repeat = 0;
        double money;
        double change = 0;
        String method;
        System.out.println("Please Select the Payment Method");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        do {
            try {
                System.out.print("Enter Your Choice >");
                choice = scan.nextInt();

                if (choice == 1) {
                    repeat = 0;
                    Payment cashpayment = new Cash(cart);// Polymorphic assignment
                    do {
                        try {

                            System.out.print("Enter Amount Given : RM");
                            money = scan.nextDouble();
                            if (cashpayment instanceof Cash) { //Checked if cashpayment is an instance of the Cash
                                Cash cash = (Cash) cashpayment;//Cast cashpayment to a Cash object to access the setAmount() method.
                                cash.setAmount(money);// Set the amount in the Cash object

                                if (money >= total) {
                                    change = cash.change(total);
                                    reset = 0;

                                    cashReceipt(cart, cash, change, payment, member);
                                    return;
                                } else {
                                    System.out.println("Not Enough Amount !!\n");
                                    reset = 1;
                                }
                            }
                        } catch (java.util.InputMismatchException e) {
                            System.out.println("Invalid input! Please enter a valid number.\n");
                            reset = 1;
                            scan.nextLine(); // Consume the invalid input
                        }
                    } while (reset == 1);

                }

                if (choice == 2) {
                    repeat = 0;
                    String cardNumber;
                    String cvv;
                    int error = 0;
                    Payment cardpayment = new Card(cart);// Polymorphic assignment
                    do {

                        System.out.print("Enter Card No. >");
                        cardNumber = scan.next();
                        if (cardpayment instanceof Card) {
                            Card card = (Card) cardpayment;
                            if (card.isValidCardNumber(cardNumber)) {
                                reset = 0;
                                do {

                                    System.out.print("Enter CVV >");
                                    cvv = scan.next();

                                    if (card.isValidCVV(cvv)) {
                                        reset = 0;
                                        error = 0;

                                        cardReceipt(cart, payment, card, member);
                                    } else {
                                        System.out.println("Invalid CVV !!\n");
                                        error = 1;
                                    }

                                } while (error == 1);

                            } else {
                                System.out.println("Invalid Card Number !!\n");
                                reset = 1;
                            }

                        }
                    } while (reset == 1);

                }
                if (choice >= 3) {
                    System.out.println("Invalid input! Please enter a valid number.\n");
                    repeat = 1;
                }

            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.\n");
                repeat = 1;
                scan.nextLine(); // Consume the invalid input
            }
        } while (repeat == 1);
    }

    public static void cashReceipt(Cart cart, Cash cash, double change, Payment payment, Member member) {
        Receipt rep = new Receipt();
        MainMenu menu = new MainMenu();
        Scanner scan = new Scanner(System.in);
        double totalBeforeDiscount = cart.CalcSubtotal(); // Calculate total before discount
        double totalAfterDiscount = payment.calTotal(); // Calculate total after discount

        System.out.println("");
        System.out.println("=======================================================================");
        System.out.println("|                              RECEIPT                                | ");
        System.out.println("=======================================================================");
        System.out.printf("|Invoice no : %5s                                                   |\n", rep.getInvoiceID());
        System.out.printf("|Date       : %5s                                     |\n", rep.getCreatedDateTime());
        System.out.printf("|Staff ID   : %5s                                                   |\n", menu.getCashierID());
        cart.viewReceipt();
        System.out.println("=======================================================================");
        System.out.printf("|Total Quantity                                                    %3s|\n", cart.CalcTotalQty());
        System.out.printf("|Member ID                                                  %10s|\n", member.getID());
        System.out.printf("|Member Discount                                            RM%8.2f|\n", payment.calDiscount());
        System.out.printf("|Tax Amount                                                 RM%8.2f|\n", payment.calTaxRate());
        System.out.printf("|Subtotal                                                   RM%8.2f|\n", totalBeforeDiscount);
        System.out.println("=======================================================================");
        System.out.printf("|Total (MYR)                                                RM%8.2f|\n", totalAfterDiscount);
        System.out.printf("|%s                                                       RM%8.2f|\n", cash.getPaymentMethod(), cash.getAmount());
        System.out.printf("|Change                                                    -RM%8.2f|\n", cash.change(totalAfterDiscount));
        System.out.println("=======================================================================");
        System.out.println("");
        System.out.println("============================================");
        System.out.println("|             Order Complete !!            |");
        System.out.println("============================================");
        // Update product quantities based on the items in the cart
        ArrayList<Product> productList = Product.readProduct();
        for (CartItem cartItem : cart.getCartlist()) {
            Product.updateProductQuantity(productList, cartItem.getCartProd().getProductID(), cartItem.getQuantity());
        }
        // Write the updated product list back to the text file
        Product.writeArray(productList);
        System.out.println("Press Enter to return to exit...");
        scan.nextLine(); // Wait for the user to press Enter      s00
        if ("Admin".equals(userRole)) {
            AdminMenu();
        } else {
            StaffMenu();
        }

    }

    public static void cardReceipt(Cart cart, Payment payment, Card card, Member member) {
        Receipt rep = new Receipt();
        MainMenu menu = new MainMenu();
        Scanner scan = new Scanner(System.in);
        System.out.println("");
        System.out.println("=======================================================================");
        System.out.println("|                              RECEIPT                                | ");
        System.out.println("=======================================================================");
        System.out.printf("|Invoice no : %5s                                                   |\n", rep.getInvoiceID());
        System.out.printf("|Date       : %5s                                     |\n", rep.getCreatedDateTime());
        System.out.printf("|Staff ID   : %5s                                                   |\n", menu.getCashierID());
        cart.viewReceipt();
        System.out.println("=======================================================================");
        System.out.printf("|Total Quantity                                                    %3s|\n", cart.CalcTotalQty());
        System.out.printf("|Member ID                                                  %10s|\n", member.getID());
        System.out.printf("|Member Discount                                            RM%8.2f|\n", payment.calDiscount());
        System.out.printf("|Tax Amount                                                 RM%8.2f|\n", payment.calTaxRate());
        System.out.printf("|Subtotal                                                   RM%8.2f|\n", cart.CalcSubtotal());
        System.out.println("=======================================================================");
        System.out.printf("|Total (MYR)                                                RM%8.2f|\n", payment.calTotal());
        System.out.printf("|%s                                                          |\n", card.getPaymentMethod());
        System.out.println("=======================================================================");
        System.out.println("");
        System.out.println("============================================");
        System.out.println("|             Order Complete !!            |");
        System.out.println("============================================");

        // Update product quantities based on the items in the cart
        ArrayList<Product> productList = Product.readProduct();
        for (CartItem cartItem : cart.getCartlist()) {
            Product.updateProductQuantity(productList, cartItem.getCartProd().getProductID(), cartItem.getQuantity());
        }
        // Write the updated product list back to the text file
        Product.writeArray(productList);
        System.out.println("Press Enter to return to exit...");
        scan.nextLine(); // Wait for the user to press Enter      s00
        if ("Admin".equals(userRole)) {
            AdminMenu();
        } else {
            StaffMenu();
        }

    }

}
