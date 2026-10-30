package com.pluralsight;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class OnlineStore {

    private static final String ProductsFile = "products.csv";
    private static final String ReceiptsFolder = "Receipts";
    private static final List<Product> products = new ArrayList<>();
    private static final ShoppingCart cart = new ShoppingCart();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadProducts();

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println(" Welcome to the Online Store ");
            System.out.println(" 1) Display Products ");
            System.out.println(" 2) Display Cart ");
            System.out.println(" 0) Exit ");

            switch (prompt("Choose an option: ")) {
                case "1" -> displayProducts();
                case "2" -> displayCart();
                case "0" -> running = false;
                default -> System.out.println("Please enter 1, 2, or 0.");
            }
        }
        System.out.println("Thanks for visiting. Goodbye!");
    }

    private static void loadProducts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ProductsFile))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                try {
                    String[] fields = line.split("\\|");
                    products.add(new Product(fields[0].trim(), fields[1].trim(), Double.parseDouble(fields[2].trim()), fields[3].trim()));
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Skipping a line that isn't in SKU|Name|Price|Department format: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR: Could not read " + ProductsFile + ". Check that it is in the project folder.");
        }
    }

    private static void displayProducts() {
        List<Product> shown = products;

        boolean back = false;
        while (!back) {
            printProducts(shown);

            System.out.println();
            System.out.println("1) Search by Product Name");
            System.out.println("2) Search by Price Range");
            System.out.println("3) Search by Department");
            System.out.println("4) Show All Products");
            System.out.println("5) Add a Product to Cart");
            System.out.println("0) Go Back");

            switch (prompt("Choose an option: ")) {
                case "1" -> shown = searchByName();
                case "2" -> shown = searchByPrice();
                case "3" -> shown = searchByDepartment();
                case "4" -> shown = products;
                case "5" -> addToCart();
                case "0" -> back = true;
                default -> System.out.println("Please enter a number from the menu.");
            }
        }
    }

    private static void printProducts(List<Product> list) {
        System.out.println();
        if (list.isEmpty()) {
            System.out.println("No products matched your search.");
            return;
        }
        System.out.printf("%-8s %-40s %10s  %s%n", "SKU", "Product", "Price", "Department");
        for (Product product : list) {
            System.out.printf(Locale.US, "%-8s %-40s %10s  %s%n",
                    product.getSku(), product.getName(), money(product.getPrice()), product.getDepartment());
        }
    }

    private static List<Product> searchByName() {
        String text = prompt("Product name contains: ").toLowerCase();

        List<Product> matches = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(text)) {
                matches.add(product);
            }
        }
        return matches;
    }

    private static List<Product> searchByPrice() {
        Double min = promptForMoney("Minimum price: ");
        Double max = promptForMoney("Maximum price: ");

        List<Product> matches = new ArrayList<>();
        if (min == null || max == null) {
            System.out.println("Prices must be numbers, like 25 or 99.99.");
            return matches;
        }

        for (Product product : products) {
            if (product.getPrice() >= min && product.getPrice() <= max) {
                matches.add(product);
            }
        }
        return matches;
    }

    private static List<Product> searchByDepartment() {
        String text = prompt("Department contains: ").toLowerCase();

        List<Product> matches = new ArrayList<>();
        for (Product product : products) {
            if (product.getDepartment().toLowerCase().contains(text)) {
                matches.add(product);
            }
        }
        return matches;
    }

    private static void addToCart() {
        String sku = prompt("Enter the SKU of the product to add: ");
        Product product = findBySku(sku);

        if (product == null) {
            System.out.println("No product has the SKU " + sku + ".");
            return;
        }

        cart.add(product);
        System.out.println("Added " + product.getName() + " to your cart.");
    }

    private static Product findBySku(String sku) {
        for (Product product : products) {
            if (product.getSku().equalsIgnoreCase(sku)) {
                return product;
            }
        }
        return null;
    }

    private static void displayCart() {
        boolean back = false;
        while (!back) {
            printCart();

            System.out.println();
            System.out.println("1) Check Out");
            System.out.println("2) Remove a Product from Cart");
            System.out.println("0) Go Back");

            switch (prompt("Choose an option: ")) {
                case "1" -> back = checkOut();
                case "2" -> removeFromCart();
                case "0" -> back = true;
                default -> System.out.println("Please enter a number from the menu.");
            }
        }
    }

    private static void printCart() {
        System.out.println();
        System.out.println(" Your Cart ");

        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        printLineItems(System.out);
        System.out.println("Total: " + money(cart.getTotal()));
    }

    private static void printLineItems(PrintStream out) {
        out.printf("%-8s %-40s %5s %10s %10s%n", "SKU", "Product", "Qty", "Price", "Subtotal");
        for (CartItem item : cart.getItems()) {
            out.printf("%-8s %-40s %5d %10s %10s%n",
                    item.getProduct().getSku(), item.getProduct().getName(), item.getQuantity(),
                    money(item.getProduct().getPrice()), money(item.getLineTotal()));
        }
    }

    private
}

