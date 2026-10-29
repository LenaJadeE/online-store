package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class OnlineStore {

    private static final String ProductsFile = "products.csv";
    private static final String ReceiptsFolder = "Receipts";
    private static final List<Product> items = new ArrayList<>();
    private static final ShoppingCart cart = new ShoppingCart();
    private static final Scanner scanner = new  Scanner(System.in);

    public static  void main(String[] args) {
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
                case  "0" -> running = false;
                default ->  System.out.println("Please enter 1, 2, or 0.");
            }
        }
        System.out.println("Thanks for visiting.Goodbye!");
    }

    private  static void loadProducts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ProductsFile))){
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                try {
                    String[] fields = line.split("\\|");
                    products.add(new Product(fields[0].trim(), fields[1].trim(), Double.parseDouble(fields[2].trim())));
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
                case "3" -> shown = searchByDepartmen();
                case "4" -> shown = products;
                case "5" -> addToCart();
                case  "0" -> back = true;
                default ->  System.out.println("Please enter a number from the menu.");
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
            System.out.printf(Locale.US, "%-8s %-40s %10s %s%n", product.getSku(), product.getName(), money(product.getPrice(), product.getDep));

        }
    }
}

