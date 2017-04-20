package com.vendingmachine;

import java.util.HashMap;
import java.util.Map;

class CoinsForChangeDueInventory {

    private Map<CoinsForChangeDue, Integer> numberOfChangeCoins = new HashMap<CoinsForChangeDue, Integer>() {
        {
            put(CoinsForChangeDue.NICKEL, 0);
            put(CoinsForChangeDue.DIME, 0);
        }
    };

    boolean outOfChange() {
        return (numberOfChangeCoins.get(CoinsForChangeDue.DIME) == 0 && numberOfChangeCoins.get(CoinsForChangeDue.NICKEL) < 2);
    }

    String changeCoin(double changeDue) {
        String changeCoin = "";
        if(!outOfChange()) {
            changeCoin = CoinsForChangeDue.getNameOfCoin(changeDue);
        }
        return changeCoin;
    }

    void updateCoinInventory(CoinsForChangeDue changeCoin, Integer inventoryChange) {
        numberOfChangeCoins.replace(changeCoin, inventoryChange);
    }
}