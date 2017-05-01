package com.vendingmachine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
    private ProductInventoryManager productInventoryManager;
    private CoinReturn changeCoins;
    private Display display;

    @Before
    public void setUp() {
        changeCoins = new CoinReturn();
        display = new Display();
        productInventoryManager = new ProductInventoryManager();
        vendingMachine = new VendingMachine(productInventoryManager, changeCoins);
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
    public void vendingMachineCanFindTheValueOfACoin() {
        // ACT
        Double value = vendingMachine.insertCoin(NICKEL); // TODO: is this return value needed on the API?

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
        assertEquals(.15, vendingMachine.getAmountTendered(), DELTA);
    }

    @Test
    public void amountTenderedAlwaysReturnsTheSameNumber() throws Exception {
        // ARRANGE
        vendingMachine.insertCoin(NICKEL);
        vendingMachine.insertCoin(DIME);

        // ACT
        double firstAmount = vendingMachine.getAmountTendered();
        double secondAmount = vendingMachine.getAmountTendered();

        // ASSERT
        assertThat(firstAmount, is(equalTo(secondAmount)));
    }

    @Test
    public void insertingThreeCoinsReturnsTheSumOfAllCoins() {
        // ARRANGE
        vendingMachine.insertCoin(NICKEL);
        vendingMachine.insertCoin(DIME);
        vendingMachine.insertCoin(QUARTER);

        // ASSERT
        assertEquals(.40, vendingMachine.getAmountTendered(), DELTA);
    }

    @Test
    public void insertingAnInvalidCoinReturnsZero() {
        // ARRANGE
        vendingMachine.insertCoin(PENNY);

        // ASSERT
        assertEquals(.0, vendingMachine.getAmountTendered(), DELTA);
    }

    @Test
    public void insertingAnInvalidCoinWithThreeValidCoinsReturnsTheSumOfTheValidCoins() {
        // ARRANGE
        vendingMachine.insertCoin(PENNY);
        vendingMachine.insertCoin(NICKEL);
        vendingMachine.insertCoin(DIME);
        vendingMachine.insertCoin(QUARTER);

        // ASSERT
        assertEquals(.40, vendingMachine.getAmountTendered(), DELTA);
    }

    @Test
    public void insertingThreeValidCoinsWithAnInvalidCoinReturnsTheSumOfTheValidCoins() {
        // ARRANGE
        vendingMachine.insertCoin(NICKEL);
        vendingMachine.insertCoin(PENNY);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);

        // ASSERT
        assertEquals(.55, vendingMachine.getAmountTendered(), DELTA);
    }

    @Test
    public void dispensingAProductResetsAmountTenderedToZero() {
        // ARRANGE
        productInventoryManager.manageInventory(Product.CHIPS, 2);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);

        // ACT
        double actualChange = vendingMachine.dispenseProduct(Product.CHIPS);

        // ASSERT
        assertEquals(0, vendingMachine.getAmountTendered(), DELTA);
        assertEquals(0.0, actualChange, DELTA);
    }

    @Test
    public void returnChangeReturnsCorrectValueForChange() {
        // ARRANGE
        productInventoryManager.manageInventory(Product.CANDY, 2);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);

        // ASSERT
        assertEquals(.10, vendingMachine.dispenseProduct(Product.CANDY), DELTA);
        assertEquals("THANK YOU $0.00", display.getMessage());
    }

    @Test
    public void returnChangeReturnsCorrectCoin() {
        // ARRANGE
        productInventoryManager.manageInventory(Product.CANDY, 2);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);
        double change = vendingMachine.dispenseProduct(Product.CANDY);

        // ASSERT
        assertEquals("DIME", CoinsForChangeDue.getNameOfCoin(change)); // TODO: why is this test here? Move it? Refactor to be more end-to-end with the coin return?
        assertEquals("THANK YOU $0.00", display.getMessage());
    }

    @Test
    public void amountTenderedGoesBackToZeroWhenDone() throws Exception {
        // ARRANGE
        vendingMachine.insertCoin(QUARTER);

        // ACT
        vendingMachine.done();

        // ASSERT
        assertThat(vendingMachine.getAmountTendered(), is(0.0));
    }

    @Test
    public void coinsAreReturnedWhenReturnCoinsButtonIsPressed() {
        // ARRANGE
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);

        // ASSERT
        assertEquals(.50, vendingMachine.dispenseProduct(Product.NONE), DELTA);
        assertEquals("INSERT COIN $0.00", display.getMessage());
    }

    // TODO: maybe some tests for when observers get notified?
}