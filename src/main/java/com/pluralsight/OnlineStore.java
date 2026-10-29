package com.pluralsight;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OnlineStore {

    private static final String ProductsFile = "products.csv";
    private static final String ReceiptsFolder = "Receipts";
    private static final List<Product> items = new ArrayList<>();
    private static final ShoppingCart cart = new ShoppingCart();
    private static final Scanner scanner = new  Scanner(System.in);

    public static  void main(String[] args) {
        loadProducts();

    }


}
