package com.vendingmachine;

import java.util.HashMap;
import java.util.Map;

class CoinReturn {

    private Map<CoinsForChangeDue, Integer> changeCoinsInventory = new HashMap<CoinsForChangeDue, Integer>() {
        {
            put(CoinsForChangeDue.NICKEL, 0);
            put(CoinsForChangeDue.DIME, 0);
        }
    };

    boolean outOfChange() {
        return (changeCoinsInventory.get(CoinsForChangeDue.DIME) == 0 && changeCoinsInventory.get(CoinsForChangeDue.NICKEL) < 2);
    }

    String nameOfCoinForChange(double changeDue) {
        String changeCoin = "";
        if(!outOfChange()) {
            changeCoin = CoinsForChangeDue.getNameOfCoin(changeDue);
        }
        return changeCoin;
    }

    void updateCoinInventory(CoinsForChangeDue changeCoin, Integer inventoryChange) {
        changeCoinsInventory.replace(changeCoin, inventoryChange);
    }
}