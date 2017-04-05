package com.vendingmachine;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private static Map<Products, Integer> inventory = new HashMap<Products, Integer>() {
        {
            put(Products.CHIPS, 0);
            put(Products.CANDY, 0);
            put(Products.COLA, 0);
        }
    };

    static int getInventory(Products product) {
        return inventory.get(product);
    }

    static void updateInventory(Products product, Integer newInventory) {
        inventory.replace(product, newInventory);
    }
}