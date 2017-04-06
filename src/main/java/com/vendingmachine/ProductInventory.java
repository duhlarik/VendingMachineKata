package com.vendingmachine;

import java.util.HashMap;
import java.util.Map;

public class ProductInventory {

    private static Map<Product, Integer> inventory = new HashMap<Product, Integer>() {
        {
            put(Product.CHIPS, 0);
            put(Product.CANDY, 0);
            put(Product.COLA, 0);
        }
    };

    static int getInventory(Product product) {
        return inventory.get(product);
    }

    static void updateInventory(Product product, Integer newInventory) {
        inventory.replace(product, newInventory);
    }
}