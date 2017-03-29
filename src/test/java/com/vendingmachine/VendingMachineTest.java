package com.vendingmachine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;


public class VendingMachineTest {

    private static final InsertedCoin QUARTER = new InsertedCoin(5.670, 24.26);
    private static final InsertedCoin DIME = new InsertedCoin(2.268, 17.91);
    private static final InsertedCoin NICKEL = new InsertedCoin(5.000, 21.21);
    private static final InsertedCoin PENNY = new InsertedCoin(2.500, 19.05);

    private static final double DELTA = 1e-15;
    private VendingMachine vendingMachine;

    @Before
    public void setUp() {
        vendingMachine = new VendingMachine();
    }

    @After
    public void tearDown() {
    List<Double> tenderedList = vendingMachine.getTendered();
    tenderedList.clear();
    }

    @Test
    public void vendingMachineCanFindTheValueOfACoin() {
        // ARRANGE
        InsertedCoin nickel = NICKEL;

        // ACT
        Double value = vendingMachine.insertCoin(nickel);

        // ASSERT
        assertEquals(.05, value, DELTA);
    }

    @Test
    public void insertingTwoCoinsReturnsTheValueOfBothCoins() {
        // ARRANGE
        InsertedCoin quarter = QUARTER;
        InsertedCoin dime = DIME;

        // ACT
        Double value1 = vendingMachine.insertCoin(quarter);
        Double value2 = vendingMachine.insertCoin(dime);

        // ASSERT
        assertEquals(.25, value1, DELTA);
        assertEquals(.10, value2, DELTA);
    }

    @Test
    public void insertingTwoCoinsReturnsTheSumOfBothCoins() {
        // ARRANGE
        InsertedCoin nickel = NICKEL;
        InsertedCoin dime = DIME;
        Double amountTendered;

        // ACT
        vendingMachine.insertCoin(nickel);
        vendingMachine.insertCoin(dime);
        amountTendered = vendingMachine.amountTendered();

        // ASSERT
        assertEquals(.15, amountTendered, DELTA);
    }

    @Test
    public void amountTenderedAlwaysReturnsTheSameNumber() throws Exception {
        // ARRANGE
        InsertedCoin nickel = NICKEL;
        InsertedCoin dime = DIME;
        Double amountTendered;

        // ACT
        vendingMachine.insertCoin(nickel);
        vendingMachine.insertCoin(dime);

        // ASSERT
        double firstAmount = vendingMachine.amountTendered();
        double secondAmount = vendingMachine.amountTendered();
        assertThat(firstAmount, is(equalTo(secondAmount)));
    }

    @Test
    public void insertingThreeCoinsReturnsTheSumOfAllCoins() {
        // ARRANGE
        InsertedCoin nickel = NICKEL;
        InsertedCoin dime = DIME;
        InsertedCoin quarter = QUARTER;
        Double amountTendered;

        // ACT
        vendingMachine.insertCoin(nickel);
        vendingMachine.insertCoin(dime);
        vendingMachine.insertCoin(quarter);
        amountTendered = vendingMachine.amountTendered();

        // ASSERT
        assertEquals(.40, amountTendered, DELTA);
    }

    @Test
    public void insertingAnInvalidCoinReturnsZero() {
        // ARRANGE
        InsertedCoin penny = PENNY;
        Double amountTendered;

        // ACT
        vendingMachine.insertCoin(penny);
        amountTendered = vendingMachine.amountTendered();

        // ASSERT
        assertEquals(.0, amountTendered, DELTA);
    }

    @Test
    public void insertingAnInvalidCoinWithThreeValidCoinsReturnsTheSumOfTheValidCoins() {
        // ARRANGE
        InsertedCoin penny = PENNY;
        InsertedCoin nickel = NICKEL;
        InsertedCoin dime = DIME;
        InsertedCoin quarter = QUARTER;
        Double amountTendered;

        // ACT
        vendingMachine.insertCoin(penny);
        vendingMachine.insertCoin(nickel);
        vendingMachine.insertCoin(dime);
        vendingMachine.insertCoin(quarter);
        amountTendered = vendingMachine.amountTendered();

        // ASSERT
        assertEquals(.40, amountTendered, DELTA);
    }

    @Test
    public void selectingAProductAfterInsertingEnoughMoneyReturnsTrueForTenderedIsEnough() {

        // ARRANGE
        InsertedCoin quarter1 = QUARTER;
        InsertedCoin quarter2 = QUARTER;
        vendingMachine.insertCoin(quarter1);
        vendingMachine.insertCoin(quarter2);

        // ACT
        Double amountTendered = vendingMachine.amountTendered();
        Double price = Products.PRICE(Products.CHIPS);

        // ASSERT
        assertEquals(true, vendingMachine.tenderedIsEnough(amountTendered, price));
    }

    @Test
    public void selectingAProductWithoutInsertingEnoughMoneyReturnsFalseForTenderedIsEnough() {

        // ARRANGE
        InsertedCoin quarter1 = QUARTER;
        vendingMachine.insertCoin(quarter1);

        // ACT
        Double amountTendered = vendingMachine.amountTendered();
        Double price = Products.PRICE(Products.CHIPS);

        // ASSERT
        assertEquals(false, vendingMachine.tenderedIsEnough(amountTendered, price));
    }

    @Test
    public void dispensingAProductResetsAmountTenderedToZero() {

        // ARRANGE
        InsertedCoin quarter1 = QUARTER;
        InsertedCoin quarter2 = QUARTER;
        vendingMachine.insertCoin(quarter1);
        vendingMachine.insertCoin(quarter2);

        // ACT
        vendingMachine.dispenseProduct(Products.CHIPS);

        // ASSERT
        assertEquals(0, vendingMachine.getTendered().size());
    }

    @Test
    public void dispensingAProductReturnsChangeDue() {

        // ARRANGE
        InsertedCoin quarter1 = QUARTER;
        InsertedCoin quarter2 = QUARTER;
        InsertedCoin quarter3 = QUARTER;
        vendingMachine.insertCoin(quarter1);
        vendingMachine.insertCoin(quarter2);
        vendingMachine.insertCoin(quarter3);

        // ACT
        Double changeDue = vendingMachine.dispenseProduct(Products.CANDY);

        // ASSERT
        assertEquals(.10, changeDue, DELTA);
    }

    @Test
    public void amountTenderedGoesBackToZeroWhenDone() throws Exception {
        InsertedCoin quarter = QUARTER;
        vendingMachine.insertCoin(quarter);

        vendingMachine.done();

        assertThat(vendingMachine.amountTendered(), is(0.0));
    }


    @Test
    public void coinsAreReturnedWhenReturnCoinsButtonIsPressed() {

        // ARRANGE
        InsertedCoin quarter1 = QUARTER;
        InsertedCoin quarter2 = QUARTER;
        vendingMachine.insertCoin(quarter1);
        vendingMachine.insertCoin(quarter2);

        // ACT
        vendingMachine.returnCoins();

        // ASSERT
        assertEquals(0, vendingMachine.amountTendered(), DELTA);
    }
}