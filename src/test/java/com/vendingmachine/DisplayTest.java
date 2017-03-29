package com.vendingmachine;

import org.junit.Before;
import org.junit.Ignore;
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
        vendingMachine = new VendingMachine(display);
    }

    @Test
    public void showsInsertCoinWhenNoMoneyTendered() throws Exception {
        assertThat(vendingMachine.amountTendered(), is(0.0));

        assertThat(display.getMessage(), is ("INSERT COIN"));
    }

    @Test
    @Ignore
    public void showsTheAmountTenderedAsYouPutMoneyInTheVendingMachine() throws Exception {
        vendingMachine.insertCoin(QUARTER);

        assertThat(display.getMessage(), is("AMOUNT TENDERED: $0.25"));

        vendingMachine.insertCoin(QUARTER);

        assertThat(display.getMessage(), is("AMOUNT TENDERED: $0.50"));
    }
}