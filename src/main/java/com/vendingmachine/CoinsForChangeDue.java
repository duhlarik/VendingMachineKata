package com.vendingmachine;

public enum CoinsForChangeDue {
    NICKEL(0.05),
    DIME(0.10),
    QUARTER(0.25);

    final double COIN_VALUE;

    CoinsForChangeDue(double COIN_VALUE) {
        this.COIN_VALUE = COIN_VALUE;
    }

    public static String getNameOfCoin(double changeDue){
        for(CoinsForChangeDue c : CoinsForChangeDue.values()){
            if(changeDue == c.COIN_VALUE) return c.name();
        }
        return null;
    }
}
