package com.vendingmachine;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DisplayTest {

    private static final InsertedCoin QUARTER = new InsertedCoin(5.670, 24.26);

    private VendingMachine vendingMachine;
    private Display display;
    private InventoryManager inventoryManager;

    @Before
    public void setUp() {
        display = new Display();
        inventoryManager = new InventoryManager();
        vendingMachine = new VendingMachine();
        vendingMachine.addObserver(display);
        vendingMachine.addObserver(inventoryManager);
        Inventory.updateInventory(Products.CANDY, 0);
        Inventory.updateInventory(Products.CHIPS, 0);
        Inventory.updateInventory(Products.COLA, 0);
    }

    @Test
    public void showsInsertCoinWhenNoMoneyTendered() throws Exception {
        assertThat(vendingMachine.getAmountTendered(), is(0.0));

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
        inventoryManager.manageInventory(Products.CHIPS, 5);
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
        inventoryManager.manageInventory(Products.CHIPS, 5);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.dispenseProduct(Products.CHIPS);

        assertThat(display.getMessage(), is("PRICE: $0.50"));
    }
    @Test
    public void showsSoldOutAndAmountTenderedWhenProductIsSoldOut() throws Exception {
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.dispenseProduct(Products.CHIPS);

        assertThat(display.getMessage(), is("SOLD OUT: $0.50"));
    }
}