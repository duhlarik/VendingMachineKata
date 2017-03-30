package com.vendingmachine;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DisplayTest {

    private static final InsertedCoin QUARTER = new InsertedCoin(5.670, 24.26);

    private VendingMachine vendingMachine;
    private Display display;

    @Before
    public void setUp() {
        display = new Display();
        vendingMachine = new VendingMachine();
        vendingMachine.addObserver(display);
    }

    @Test
    public void showsInsertCoinWhenNoMoneyTendered() throws Exception {
        assertThat(vendingMachine.amountTendered(), is(0.0));

        assertThat(display.getMessage(), is ("INSERT COIN $0.00"));
    }

    @Test
    public void showsTheAmountTenderedAsYouPutMoneyInTheVendingMachine() throws Exception {
        vendingMachine.insertCoin(QUARTER);

        assertThat(display.getMessage(), is("AMOUNT TENDERED: $0.25"));

        vendingMachine.insertCoin(QUARTER);

        assertThat(display.getMessage(), is("AMOUNT TENDERED: $0.50"));
    }

    @Test
    public void showsThankYouWhenProductIsDispensed() throws Exception {
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.dispenseProduct(Products.CHIPS);

        assertThat(display.getMessage(), is("THANK YOU $0.00"));
    }

    @Test
    public void goesBackToInsertCoinWhenTransactionIsComplete() throws Exception {
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.dispenseProduct(Products.CHIPS);
        vendingMachine.done();

        assertThat(display.getMessage(), is("INSERT COIN $0.00"));
    }

    @Test
    public void showsThePriceWhenInsufficientTenderedAmount() throws Exception {
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.dispenseProduct(Products.CHIPS);

        assertThat(display.getMessage(), is("PRICE: $0.50"));
    }
}