package com.vendingmachine;

import java.text.DecimalFormat;

public class Display {

    final String INSERT_COIN = "INSERT COIN";
    final String THANK_YOU = "THANK YOU";
    final String PRICE = "PRICE";
    final String AMOUNT_TENDERED = "AMOUNT TENDERED: ";
    final String CHANGE_DUE = "CHANGE DUE: ";
    final String SOLD_OUT = "SOLD OUT";
    final String EXACT_CHANGE = "EXACT CHANGE ONLY";

    String message = INSERT_COIN;
    DecimalFormat currencyFormat = new DecimalFormat("$0.00");

    String getMessage() {
        return message;
    }


    public void tenderedAmountChanged(double newAmountTendered) {
        if (newAmountTendered > 0) {
            message = AMOUNT_TENDERED + currencyFormat.format(newAmountTendered);
        } else {
            message = INSERT_COIN;
        }
    }

    public void productWasDispensed() {
        message = THANK_YOU;
    }
}
