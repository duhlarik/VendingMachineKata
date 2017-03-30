package com.vendingmachine;

import java.text.DecimalFormat;

public class Display implements VendingMachineObserver {

    private final String INSERT_COIN = "INSERT COIN ";
    private final String THANK_YOU = "THANK YOU ";
    private final String PRICE = "PRICE: ";
    private final String AMOUNT_TENDERED = "AMOUNT TENDERED: ";
    private final String SOLD_OUT = "SOLD OUT: ";
    private final String EXACT_CHANGE = "EXACT CHANGE ONLY ";
    private final String ZERO = "$0.00";

    private String message = INSERT_COIN;
    private String moneyMessage = ZERO;
    private DecimalFormat currencyFormat = new DecimalFormat("$0.00");

    String getMessage() {
        return message + moneyMessage;
    }

    @Override
    public void tenderedAmountChanged(double newAmountTendered) {
        if (newAmountTendered > 0) {
            message = AMOUNT_TENDERED;
            moneyMessage = currencyFormat.format(newAmountTendered);
        } else {
            message = INSERT_COIN;
            moneyMessage = ZERO;
        }
    }

    @Override
    public void productWasDispensed(Products product) {
        message = THANK_YOU;
        moneyMessage = ZERO;
    }

    @Override
    public void notEnoughTendered(double price) {
        message = PRICE;
        moneyMessage = currencyFormat.format(price);
    }

    @Override
    public void soldOut(Products product, double amountTendered) {
        message = SOLD_OUT;
        moneyMessage = currencyFormat.format(amountTendered);
    }
}
