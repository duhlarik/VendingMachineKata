package com.vendingmachine;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoinsTest {

    private static final double DELTA = 1e-15;
    private static final InsertedCoin PENNY = new InsertedCoin(2.500, 19.05);
    private static final InsertedCoin QUARTER = new InsertedCoin(5.670, 24.26);

    @Test
    public void ifInsertedCoinIsNotAValidCoinReturnFalse() {
        // ARRANGE
        boolean coinIsValid = false;
        InsertedCoin penny = PENNY;
        double value = CoinAppraiser.VALUE(penny);

        // ACT
        if (value != -1) {
            coinIsValid = true;
        }

        // ASSERT
        assertFalse(coinIsValid);
    }

    @Test
    public void ifInsertedCoinIsAValidCoinReturnTrue() {
        // ARRANGE
        boolean coinIsValid = false;
        InsertedCoin quarter = QUARTER;
        double value = CoinAppraiser.VALUE(quarter);

        // ACT
        if (value != -1) {
            coinIsValid = true;
        }

        // ASSERT
        assertTrue(coinIsValid);
    }

    @Test
    public void massAndDiameterOfQuarterShouldReturnTwentyFiveCents() {
        // ARRANGE
        InsertedCoin quarter = QUARTER;

        // ACT
        double value = CoinAppraiser.VALUE(quarter);


        // ASSERT
        assertEquals(0.25d, value, DELTA);
    }
}
