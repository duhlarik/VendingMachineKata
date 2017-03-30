package com.vendingmachine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VendingMachineTest {

    private static final InsertedCoin QUARTER = new InsertedCoin(5.670, 24.26);
    private static final InsertedCoin DIME = new InsertedCoin(2.268, 17.91);
    private static final InsertedCoin NICKEL = new InsertedCoin(5.000, 21.21);
    private static final InsertedCoin PENNY = new InsertedCoin(2.500, 19.05);

    private static final double DELTA = 1e-15;
    private VendingMachine vendingMachine;
    private Inventory inventory;

    @Before
    public void setUp() {
        vendingMachine = new VendingMachine();
        Display display = new Display();
        inventory = new Inventory();
        vendingMachine.addObserver(display);
        vendingMachine.addObserver(inventory);
    }

    @After
    public void tearDown() {
    List<Double> tenderedList = vendingMachine.getTendered();
    tenderedList.clear();
    }

    @Test
    public void vendingMachineCanFindTheValueOfACoin() {
        // ACT
        Double value = vendingMachine.insertCoin(NICKEL);

        // ASSERT
        assertEquals(.05, value, DELTA);
    }

    @Test
    public void insertingTwoCoinsReturnsTheValueOfBothCoins() {
        // ACT
        Double value1 = vendingMachine.insertCoin(QUARTER);
        Double value2 = vendingMachine.insertCoin(DIME);

        // ASSERT
        assertEquals(.25, value1, DELTA);
        assertEquals(.10, value2, DELTA);
    }

    @Test
    public void insertingTwoCoinsReturnsTheSumOfBothCoins() {
        // ACT
        vendingMachine.insertCoin(NICKEL);
        vendingMachine.insertCoin(DIME);

        // ASSERT
        assertEquals(.15, vendingMachine.amountTendered(), DELTA);
    }

    @Test
    public void amountTenderedAlwaysReturnsTheSameNumber() throws Exception {
        // ACT
        vendingMachine.insertCoin(NICKEL);
        vendingMachine.insertCoin(DIME);
        double firstAmount = vendingMachine.amountTendered();
        double secondAmount = vendingMachine.amountTendered();

        // ASSERT
        assertThat(firstAmount, is(equalTo(secondAmount)));
    }

    @Test
    public void insertingThreeCoinsReturnsTheSumOfAllCoins() {
        // ACT
        vendingMachine.insertCoin(NICKEL);
        vendingMachine.insertCoin(DIME);
        vendingMachine.insertCoin(QUARTER);

        // ASSERT
        assertEquals(.40, vendingMachine.amountTendered(), DELTA);
    }

    @Test
    public void insertingAnInvalidCoinReturnsZero() {
        // ACT
        vendingMachine.insertCoin(PENNY);

        // ASSERT
        assertEquals(.0, vendingMachine.amountTendered(), DELTA);
    }

    @Test
    public void insertingAnInvalidCoinWithThreeValidCoinsReturnsTheSumOfTheValidCoins() {
        // ACT
        vendingMachine.insertCoin(PENNY);
        vendingMachine.insertCoin(NICKEL);
        vendingMachine.insertCoin(DIME);
        vendingMachine.insertCoin(QUARTER);

        // ASSERT
        assertEquals(.40, vendingMachine.amountTendered(), DELTA);
    }

    @Test
    public void selectingAProductAfterInsertingEnoughMoneyReturnsTrueForTenderedIsEnough() {
        // ARRANGE
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);

        // ACT
        Double amountTendered = vendingMachine.amountTendered();
        Double price = Products.PRICE(Products.CHIPS);
        boolean tenderedIsEnough = vendingMachine.tenderedIsEnough(amountTendered, price);

        // ASSERT
        assertTrue(tenderedIsEnough);
    }

    @Test
    public void selectingAProductWithoutInsertingEnoughMoneyReturnsFalseForTenderedIsEnough() {
        // ARRANGE
        vendingMachine.insertCoin(QUARTER);

        // ACT
        Double amountTendered = vendingMachine.amountTendered();
        Double price = Products.PRICE(Products.CHIPS);
        boolean tenderedIsEnough = vendingMachine.tenderedIsEnough(amountTendered, price);

        // ASSERT
        assertFalse(tenderedIsEnough);
    }

    @Test
    public void dispensingAProductResetsAmountTenderedToZero() {
        // ARRANGE
        inventory.increaseInventory(Products.CHIPS, 5);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);

        // ACT
        vendingMachine.dispenseProduct(Products.CHIPS);

        // ASSERT
        assertEquals(0, vendingMachine.getTendered().size());
    }

    @Test
    public void dispensingAProductReturnsChangeDue() {
        // ARRANGE
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);

        // ASSERT
        assertEquals(.10, vendingMachine.dispenseProduct(Products.CANDY), DELTA);
    }

    @Test
    public void amountTenderedGoesBackToZeroWhenDone() throws Exception {
        // ARRANGE
        vendingMachine.insertCoin(QUARTER);

        // ACT
        vendingMachine.done();

        // ASSERT
        assertThat(vendingMachine.amountTendered(), is(0.0));
    }

    @Test
    public void coinsAreReturnedWhenReturnCoinsButtonIsPressed() {
        // ARRANGE
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);

        // ASSERT
        assertEquals(.50, vendingMachine.returnCoins(), DELTA);
    }
}