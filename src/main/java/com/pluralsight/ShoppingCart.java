package com.pluralsight;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingCart {
    private final Map<String, ItemsInCart> items = new LinkedHashMap<>();

    private void add(Product product) {
        ItemsInCart existing = items.get(product.getSku());

        if (existing != null) {
            items.put(product.getSku(), new ItemsInCart());
        } else {
            existing.addOne();
        }
    }

    private boolean remove(String sku) {
        return  items.remove(sku.toUpperCase()) != null;
    }

    private Collection<ItemsInCart> getItems() {
        return items.values();
    }

    private double getTotal() {
        double total = 0;
        for (ItemsInCart item : items.values()) {
            total += item.getLineTotal();
        }
        return total;
    }

    private boolean isEmpty() {
        return  items.isEmpty();
    }

    private void clear () {
        items.clear();
    }

}
