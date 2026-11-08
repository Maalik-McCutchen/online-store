package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Starter code for the Online Store workshop.
 * Learners will complete the TODO sections to make the program work.
 * <p>
 * What is already done for you: the two lists, the Home Screen loop and
 * the method headers. Each TODO says what that method has to do.
 * Product.java is complete; products.csv sits at the project root.
 * You will need extra imports (for example java.io.BufferedReader,
 * java.io.FileReader, java.io.IOException) as you fill the TODOs in.
 */
public class Store {

    public static void main(String[] args) {

        // Create lists for inventory and the shopping cart
        ArrayList<Product> inventory = new ArrayList<>();
        ArrayList<Product> cart = new ArrayList<>();

        // Load inventory from the data file (pipe-delimited: SKU|Product Name|Price|Department)
        loadInventory("products.csv", inventory);

        // Main menu loop
        Scanner myScanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 3) {
            System.out.println("\nWelcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            System.out.println();
            System.out.print("Your choice: ");

            if (!myScanner.hasNextInt()) {
                System.out.println("Please enter 1, 2, or 3.");
                myScanner.nextLine();                 // discard bad input
                continue;
            }
            choice = myScanner.nextInt();
            myScanner.nextLine();                     // clear newline

            switch (choice) {
                case 1 -> displayProducts(inventory, cart, myScanner);
                case 2 -> displayCart(cart, myScanner);
                case 3 -> System.out.println("Thank you for shopping with us!");
                default -> System.out.println("Invalid choice!");
            }
        }
        myScanner.close();
    }

    /**
     * Reads product data from a file and populates the inventory list.
     * File format (pipe-delimited), the first line is a header row:
     * SKU|Product Name|Price|Department
     * <p>
     * Example line:
     * AV1051|JBL Bluetooth Speaker|89.95|Audio Video
     */

    //Load product information from the CSV file into the inventory list.

    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            reader.readLine();
            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split("\\|");
                String iD = tokens[0];
                String name = tokens[1];
                double price = Double.parseDouble(tokens[2]);
                String department = tokens[3];

                Product product = new Product(iD, name, price, department);

                inventory.add(product);

            }
            reader.close();
            System.out.println();
            System.out.println("Products loaded: " + inventory.size());

        } catch (Exception e) {
            System.err.println("Something went wrong.");
        }

    }

    /**
     * Displays all products and lets the user add one to the cart.
     * Typing X returns to the main menu.
     */
    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner myScanner) {

        boolean shopping = true;
        while (shopping) {


            for (Product product : inventory) {
                System.out.println(product.getSku() + " | " + product.getProductName() + " | " + product.getPrice() + " | " + product.getDepartment());
            }
            System.out.println("------------------------------------------------");
            System.out.println("Enter product ID to add to cart");
            System.out.println("X) Return Home ");
            System.out.println();
            System.out.print("Your choice: ");
            String productId = myScanner.nextLine();

            if (productId.equalsIgnoreCase("X")) {
                return;
            }

            Product product = findProductById(productId, inventory);

            if (product != null) {
                cart.add(product);
                System.out.println("\u001B[32m" + "[" + product.getSku() + "]" + " Was added to the cart!" + "\u001B[0m");
                System.out.println("------------------------------------------------");
            }
        }
    }

    /**
     * Shows the contents of the cart, calculates the total,
     * and offers the option to check out.
     */
    public static void displayCart(ArrayList<Product> cart, Scanner myScanner) {
        // TODO:
        //   • list each product in the cart
        //   • compute the total cost
        //   • ask the user whether to check out (C) or return (X)
        //   • if C, call checkOut(cart, totalAmount, scanner)

        if (cart.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }
        double totalAmount = 0.0;

        for (Product product : cart) {
            System.out.println(product.getSku() + " | " + product.getProductName() + " | " + product.getPrice() + " | " + product.getDepartment());

            totalAmount = totalAmount + product.getPrice();

        }
        System.out.println("------------------------------------------------");
        System.out.println("Total: $" + totalAmount);
        System.out.println();
        System.out.println("C) Checkout ");
        System.out.println("X) Return Home ");
        System.out.println();
        System.out.print("Your choice: ");
        String userCart = myScanner.nextLine();

        if (userCart.equalsIgnoreCase("X")) {
            return;
        }
        if (userCart.equalsIgnoreCase("C")) {
            checkOut(cart, totalAmount, myScanner);
        }

    }

    /**
     * Handles the checkout process:
     * 1. Confirm that the user wants to buy.
     * 2. Accept payment and calculate change.
     * 3. Display a simple receipt.
     * 4. Clear the cart.
     */
    public static void checkOut(ArrayList<Product> cart, double totalAmount, Scanner myScanner) {
        // TODO: implement steps listed above

        System.out.println("Your total is: $" + totalAmount);
        System.out.println();
        System.out.println("Are you sure you want to checkout? " + "(Y/N)");
        System.out.print("Your choice: ");
        String userCheckOut = myScanner.nextLine();

        if (userCheckOut.equalsIgnoreCase("Y")) {
            double paymentAmount = 0.0;

            while (true) {
                System.out.print("Enter your payment amount: $");

                if (!myScanner.hasNextDouble()) {
                    System.out.println("Invalid payment. Please enter a number.");
                    myScanner.nextLine();
                    continue;
                }

                paymentAmount = myScanner.nextDouble();
                myScanner.nextLine();

                if (paymentAmount < totalAmount) {
                    System.out.println("Payment amount not enough.");
                    continue;
                }

                break;
            }

            double userChange = paymentAmount - totalAmount;
            System.out.printf("Your change: $%.2f\n", userChange);

            System.out.println("========== RECEIPT ==========");
            for (Product product : cart) {
                System.out.println(product.getSku() + " | " + product.getProductName() + " | " + product.getPrice() + " | " + product.getDepartment());
            }
            System.out.println("------------------------------------------------");
            System.out.printf("Total: $%.2f\n", totalAmount);
            System.out.printf("Payment: $%.2f\n", paymentAmount);
            System.out.printf("Change: $%.2f\n", userChange);
            System.out.println("=============================");

            cart.clear();
        }
        if (userCheckOut.equalsIgnoreCase("N")) {
            return;
        }
    }

    /**
     * Searches a list for a product by its id.
     *
     * @return the matching Product, or null if not found
     */
    public static Product findProductById(String id, ArrayList<Product> inventory) {
        // TODO: loop over the list and compare ids

        for (Product product : inventory) {
            if (product.getSku().equalsIgnoreCase(id)) {
                return product;
            }
        }
        System.out.println("Could not find that product.");
        return null;
    }
}
