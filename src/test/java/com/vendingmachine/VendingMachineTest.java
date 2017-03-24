package com.vendingmachine;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendingMachineTest {

    private static final double DELTA = 1e-15;
    private VendingMachine vendingMachine;

    @Before
    public void setUp() {
        vendingMachine = new VendingMachine();
    }

    @Test
    public void vendingMachineCanFindTheValueOfACoin() {
        // ARRANGE
        InsertedCoin nickel = new InsertedCoin(5.000, 21.21);

        // ACT
        Double value = vendingMachine.findValue(nickel);

        // ASSERT
        assertEquals(.05, value, DELTA);
    }

    @Test
    public void insertingTwoCoinsReturnsTheValueOfBothCoins() {
        // ARRANGE
        InsertedCoin quarter = new InsertedCoin(5.670, 24.26);
        InsertedCoin dime = new InsertedCoin(2.268, 17.91);

        // ACT
        Double value1 = vendingMachine.findValue(quarter);
        Double value2 = vendingMachine.findValue(dime);

        // ASSERT
        assertEquals(.25, value1, DELTA);
        assertEquals(.10, value2, DELTA);

    }

    @Test
    public void insertingTwoCoinsReturnsTheSumOfBothCoins() {
        // ARRANGE
        InsertedCoin nickel = new InsertedCoin(5.000, 21.21);
        InsertedCoin dime = new InsertedCoin(2.268, 17.91);
        Double sumOfValues;

        // ACT
        vendingMachine.findValue(nickel);
        vendingMachine.findValue(dime);
        sumOfValues = vendingMachine.sumOfInsertedCoins();

        // ASSERT
        assertEquals(.15, sumOfValues, DELTA);

    }

    @Test
    public void insertingThreeCoinsReturnsTheSumOfAllCoins() {
        // ARRANGE
        InsertedCoin nickel = new InsertedCoin(5.000, 21.21);
        InsertedCoin dime = new InsertedCoin(2.268, 17.91);
        InsertedCoin quarter = new InsertedCoin(5.670, 24.26);
        Double sumOfValues;

        // ACT
        vendingMachine.findValue(nickel);
        vendingMachine.findValue(dime);
        vendingMachine.findValue(quarter);
        sumOfValues = vendingMachine.sumOfInsertedCoins();

        // ASSERT
        assertEquals(.40, sumOfValues, DELTA);

    }

    @Test
    public void insertingAnInvalidCoinReturnsZero() {
        // ARRANGE
        InsertedCoin penny = new InsertedCoin(2.500, 19.05);
        Double sumOfValues;

        // ACT
        vendingMachine.findValue(penny);
        sumOfValues = vendingMachine.sumOfInsertedCoins();

        // ASSERT
        assertEquals(.0, sumOfValues, DELTA);

    }

    @Test
    public void insertingAnInvalidCoinWithThreeValidCoinsReturnsTheSumOfTheValidCoins() {
        // ARRANGE
        InsertedCoin penny = new InsertedCoin(2.500, 19.05);
        InsertedCoin nickel = new InsertedCoin(5.000, 21.21);
        InsertedCoin dime = new InsertedCoin(2.268, 17.91);
        InsertedCoin quarter = new InsertedCoin(5.670, 24.26);
        Double sumOfValues;

        // ACT
        vendingMachine.findValue(penny);
        vendingMachine.findValue(nickel);
        vendingMachine.findValue(dime);
        vendingMachine.findValue(quarter);
        sumOfValues = vendingMachine.sumOfInsertedCoins();

        // ASSERT
        assertEquals(.40, sumOfValues, DELTA);
    }

    @Test
    public void insertingLessThanPriceOfProductReturnsInsertCoinsMessage() {
        // ARRANGE
        InsertedCoin nickel = new InsertedCoin(5.000, 21.21);
        InsertedCoin dime = new InsertedCoin(2.268, 17.91);
        InsertedCoin quarter = new InsertedCoin(5.670, 24.26);

        // ACT
        vendingMachine.findValue(nickel);
        vendingMachine.findValue(dime);
        vendingMachine.findValue(quarter);
        vendingMachine.sumOfInsertedCoins();
        String message = vendingMachine.isItEnough(Products.CANDY);

        // ASSERT
        assertEquals("Insert Coins", message);

    }

    @Test
    public void insertingExactPriceOfProductReturnsCorrectMessage() {
        // ARRANGE
        InsertedCoin quarter1 = new InsertedCoin(5.670, 24.26);
        InsertedCoin quarter2 = new InsertedCoin(5.670, 24.26);

        // ACT
        vendingMachine.findValue(quarter2);
        vendingMachine.findValue(quarter1);
        vendingMachine.sumOfInsertedCoins();
        String message = vendingMachine.isItEnough(Products.CHIPS);

        // ASSERT
        assertEquals("Thank you", message);
    }

    @Test
    public void insertingExactPriceOfProductReturnsCorrectChangeDueMessage() {
        // ARRANGE
        InsertedCoin quarter1 = new InsertedCoin(5.670, 24.26);
        InsertedCoin quarter2 = new InsertedCoin(5.670, 24.26);

        // ACT
        vendingMachine.findValue(quarter2);
        vendingMachine.findValue(quarter1);
        vendingMachine.sumOfInsertedCoins();
        String message = vendingMachine.thankYouAndChange(Products.CHIPS);

        // ASSERT
        assertEquals("Change due: 0.0", message);
    }

}