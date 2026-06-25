package org.yearup.models;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Integer, CartItem> items = new HashMap<>();

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    public boolean contains(int productId) {
        return items.containsKey(productId);
    }

    public void add(CartItem item) {
        items.put(item.getProductId(), item);
    }

    public CartItem get(int productId) {
        return items.get(productId);
    }

    public double getTotal() {
        double total = items.values()
                .stream()
                .mapToDouble(i -> i.getLineTotal())
                .sum();

        return total;
    }

}
