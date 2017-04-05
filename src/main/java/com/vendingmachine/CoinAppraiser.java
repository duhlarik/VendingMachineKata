package com.vendingmachine;

public enum CoinAppraiser {
    QUARTER (5.670, 24.26, 0.25), // mass in g, diameter in mm
    DIME (2.268, 17.91, 0.10),
    NICKEL (5.000, 21.21, 0.05);

    private final double MASS;
    private final double DIAMETER;
    private final double VALUE;

    CoinAppraiser(double MASS, double DIAMETER, double VALUE) {
        this.MASS = MASS;
        this.DIAMETER = DIAMETER;
        this.VALUE = VALUE;
    }

    public static double getValue(InsertedCoin insertedCoin) {
        for(CoinAppraiser c : CoinAppraiser.values()) {
            if (c.MASS == insertedCoin.getMass() && c.DIAMETER == insertedCoin.getDiameter()) {
                return c.VALUE;
            }
        }
        return -1;
    }
}
