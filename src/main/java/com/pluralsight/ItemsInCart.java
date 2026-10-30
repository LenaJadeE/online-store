package com.pluralsight;

public class ItemsInCart {

    private final Product product;
    private int quantity;

    public ItemsInCart(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }
    public int getQuantity() {
        return quantity;
    }

    public void addOne() {
        quantity++;
    }

    public double getLineTotal() {
        return product.getPrice() * quantity;
    }
}
