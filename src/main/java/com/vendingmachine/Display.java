package com.vendingmachine;

import java.text.DecimalFormat;

public class Display implements VendingMachineObserver {

    private String message = "INSERT COIN ";
    private String moneyMessage = "$0.00";
    private DecimalFormat currencyFormat = new DecimalFormat("$0.00");

    String getMessage() {
        return message + moneyMessage;
    }

//    public String defaultMessage() {
//        if(coinsForChangeDue.outOfChange()) {
//            message = "EXACT CHANGE ONLY";
//        } else message = "INSERT COIN ";
//        return message;
//    }

    @Override
    public void tenderedAmountChanged(double newAmountTendered) {
        if (newAmountTendered > 0) {
            message = "AMOUNT TENDERED: ";
            moneyMessage = currencyFormat.format(newAmountTendered);
        } else {
            message = "INSERT COIN ";
            moneyMessage = "$0.00";
        }
    }

    @Override
    public void productWasDispensed(Product product, int inventoryChange) {
        message = "THANK YOU ";
        moneyMessage = "$0.00";
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

    @Override
    public void outOfChange() {
        message = "EXACT CHANGE ONLY";
        moneyMessage = "";
    }
}