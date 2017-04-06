package com.vendingmachine;

import java.util.HashMap;
import java.util.Map;

public class CoinsForChangeDueInventory {

    private static Map<CoinsForChangeDue, Integer> numberOfChangeCoins = new HashMap<CoinsForChangeDue, Integer>() {
        {
            put(CoinsForChangeDue.NICKEL, 0);
            put(CoinsForChangeDue.DIME, 0);
            put(CoinsForChangeDue.QUARTER, 0);
        }
    };

    static boolean outOfChange() {
        return numberOfChangeCoins.containsValue(0);
    }

    static void updateInventory(CoinsForChangeDue changeCoin, Integer inventoryChange) {
        numberOfChangeCoins.replace(changeCoin, inventoryChange);
    }
}