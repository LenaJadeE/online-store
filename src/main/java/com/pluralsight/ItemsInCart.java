package com.pluralsight;

public class ItemsInCart {

    private final Product product;
    private int quantity;

    private ItemsInCart(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    private Product getProduct() {
        return product;
    }
    private int getQuantity() {
        return quantity;
    }

    private void addOne() {
        quantity++;
    }

    private double getLineTotal() {
        return product.getPrice() * quantity;
    }
}
