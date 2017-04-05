package com.vendingmachine;

import java.text.DecimalFormat;

public class Display implements VendingMachineObserver {

    private final String INSERT_COIN = "INSERT COIN ";
    private final String ZERO = "$0.00";
    private final String EXACT_CHANGE = "EXACT CHANGE ONLY ";

    private String message = INSERT_COIN;
    private String moneyMessage = ZERO;
    private DecimalFormat currencyFormat = new DecimalFormat("$0.00");

    String getMessage() {
        return message + moneyMessage;
    }

    @Override
    public void tenderedAmountChanged(double newAmountTendered) {
        if (newAmountTendered > 0) {
            message = "AMOUNT TENDERED: ";
            moneyMessage = currencyFormat.format(newAmountTendered);
        } else {
            message = INSERT_COIN;
            moneyMessage = ZERO;
        }
    }

    @Override
    public void productWasDispensed(Products product, int inventoryChange) {
        message = "THANK YOU ";
        moneyMessage = ZERO;
    }

    @Override
    public void notEnoughTendered(double price) {
        message = "PRICE: ";
        moneyMessage = currencyFormat.format(price);
    }

    @Override
    public void soldOut(double amountTendered) {
        message = "SOLD OUT: ";
        moneyMessage = currencyFormat.format(amountTendered);
    }
}