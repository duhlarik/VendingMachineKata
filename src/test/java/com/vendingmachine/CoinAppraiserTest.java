package com.vendingmachine;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoinAppraiserTest {

    private static final double DELTA = 1e-15;
    private static final InsertedCoin PENNY = new InsertedCoin(2.500, 19.05);
    private static final InsertedCoin QUARTER = new InsertedCoin(5.670, 24.26);

    @Test
    public void ifInsertedCoinIsNotAValidCoinReturnFalse() {
        // ARRANGE
        boolean coinIsValid = false;
        double value = CoinAppraiser.getValue(PENNY);

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
        double value = CoinAppraiser.getValue(QUARTER);

        // ACT
        if (value != -1) {
            coinIsValid = true;
        }

        // ASSERT
        assertTrue(coinIsValid);
    }

    @Test
    public void massAndDiameterOfQuarterShouldReturnTwentyFiveCents() {
        // ACT
        double value = CoinAppraiser.getValue(QUARTER);

        // ASSERT
        assertEquals(0.25d, value, DELTA);
    }
}
