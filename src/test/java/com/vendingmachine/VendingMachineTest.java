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

    private static final double DELTA = 1e-15;
    private VendingMachine vendingMachine;

    @Before
    public void setUp() {
        vendingMachine = new VendingMachine(new Display());
    }

    @After
    public void tearDown() {
    List<Double> tenderedList = vendingMachine.getTendered();
    tenderedList.clear();
    }

    @Test
    public void vendingMachineCanFindTheValueOfACoin() {
        // ARRANGE
        InsertedCoin nickel = new InsertedCoin(5.000, 21.21);

        // ACT
        Double value = vendingMachine.insertCoin(nickel);

        // ASSERT
        assertEquals(.05, value, DELTA);
    }

    @Test
    public void insertingTwoCoinsReturnsTheValueOfBothCoins() {
        // ARRANGE
        InsertedCoin quarter = new InsertedCoin(5.670, 24.26);
        InsertedCoin dime = new InsertedCoin(2.268, 17.91);

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
        InsertedCoin nickel = new InsertedCoin(5.000, 21.21);
        InsertedCoin dime = new InsertedCoin(2.268, 17.91);
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
        InsertedCoin nickel = new InsertedCoin(5.000, 21.21);
        InsertedCoin dime = new InsertedCoin(2.268, 17.91);
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
        InsertedCoin nickel = new InsertedCoin(5.000, 21.21);
        InsertedCoin dime = new InsertedCoin(2.268, 17.91);
        InsertedCoin quarter = new InsertedCoin(5.670, 24.26);
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
        InsertedCoin penny = new InsertedCoin(2.500, 19.05);
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
        InsertedCoin penny = new InsertedCoin(2.500, 19.05);
        InsertedCoin nickel = new InsertedCoin(5.000, 21.21);
        InsertedCoin dime = new InsertedCoin(2.268, 17.91);
        InsertedCoin quarter = new InsertedCoin(5.670, 24.26);
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
        InsertedCoin quarter1 = new InsertedCoin(5.670, 24.26);
        InsertedCoin quarter2 = new InsertedCoin(5.670, 24.26);
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
        InsertedCoin quarter1 = new InsertedCoin(5.670, 24.26);
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
        InsertedCoin quarter1 = new InsertedCoin(5.670, 24.26);
        InsertedCoin quarter2 = new InsertedCoin(5.670, 24.26);
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
        InsertedCoin quarter1 = new InsertedCoin(5.670, 24.26);
        InsertedCoin quarter2 = new InsertedCoin(5.670, 24.26);
        InsertedCoin quarter3 = new InsertedCoin(5.670, 24.26);
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
        InsertedCoin quarter = new InsertedCoin(5.670, 24.26);
        vendingMachine.insertCoin(quarter);

        vendingMachine.done();

        assertThat(vendingMachine.amountTendered(), is(0.0));
    }
}