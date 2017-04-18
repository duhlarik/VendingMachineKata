package com.vendingmachine;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChangeDueTest {
    private static final InsertedCoin QUARTER = new InsertedCoin(5.670, 24.26);
    private static final InsertedCoin DIME = new InsertedCoin(2.268, 17.91);

    private VendingMachine vendingMachine;
    private ProductInventoryManager productInventoryManager;

    // TODO: maybe these tests could pay into the broader concept of a "coin return"?

    @Before
    public void setUp() {
        vendingMachine = new VendingMachine(productInventoryManager);
        productInventoryManager = new ProductInventoryManager();
        Display display = new Display();
        vendingMachine.addObserver(display);
        vendingMachine.addObserver(productInventoryManager);
    }

    @After
    public void tearDown() {
        productInventoryManager.updateInventory(Product.CANDY, 0);
        productInventoryManager.updateInventory(Product.CHIPS, 0);
        productInventoryManager.updateInventory(Product.COLA, 0);
    }

    @Test
    public void changeDueWillReturnANickelWhenChangeDueIsFiveCents () {
        // ARRANGE
        productInventoryManager.manageInventory(Product.CANDY, 2);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(DIME);
        vendingMachine.insertCoin(DIME);

        // ACT
        double change = vendingMachine.returnChange(Product.CANDY.PRICE);

        // ASSERT
        Assert.assertEquals("NICKEL", CoinsForChangeDue.getNameOfCoin(change));
    }

    @Test
    public void changeDueWillReturnADimeWhenChangeDueIsTenCents () {
        // ARRANGE
        productInventoryManager.manageInventory(Product.CANDY, 2);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);

        // ACT
        double change = vendingMachine.returnChange(Product.CANDY.PRICE);

        // ASSERT
        Assert.assertEquals("DIME", CoinsForChangeDue.getNameOfCoin(change));
    }
}