package com.vendingmachine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProductInventoryTest {

    private static final InsertedCoin QUARTER = new InsertedCoin(5.670, 24.26);
    private static final InsertedCoin DIME = new InsertedCoin(2.268, 17.91);
    private static final InsertedCoin NICKEL = new InsertedCoin(5.000, 21.21);
    private static final InsertedCoin PENNY = new InsertedCoin(2.500, 19.05);

    private static final double DELTA = 1e-15;
    private VendingMachine vendingMachine;
    private ProductInventoryManager productInventoryManager;

    @Before
    public void setUp() {
        productInventoryManager = new ProductInventoryManager();
        vendingMachine = new VendingMachine();
        vendingMachine.addObserver(productInventoryManager);
    }

    @After
    public void tearDown() {
        ProductInventory.updateInventory(Product.CANDY, 0);
        ProductInventory.updateInventory(Product.CHIPS, 0);
        ProductInventory.updateInventory(Product.COLA, 0);
    }

    @Test
    public void addsInventoryToProductInventory() {
        // ARRANGE
        productInventoryManager.manageInventory(Product.COLA, 10);

        // ASSERT
        assertEquals(10, ProductInventory.getInventory(Product.COLA));
    }

    @Test
    public void decrementsProductInventoryByOneWhenProductIsDispensed() throws Exception {
        // ARRANGE
        productInventoryManager.manageInventory(Product.CHIPS, 5);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);

        // ACT
        vendingMachine.dispenseProduct(Product.CHIPS);

        // ASSERT
        assertEquals(4, ProductInventory.getInventory(Product.CHIPS));
    }

}