package com.vendingmachine;

public class Display {

    final String INSERT_COIN = "INSERT COIN";
    final String THANK_YOU = "THANK YOU";
    final String PRICE = "PRICE";
    final String AMOUNT_TENDERED = "AMOUNT TENDERED: ";
    final String CHANGE_DUE = "CHANGE DUE: ";
    final String SOLD_OUT = "SOLD OUT";
    final String EXACT_CHANGE = "EXACT CHANGE ONLY";

    String message = INSERT_COIN;

    String getMessage() {
        return message;
    }



}
