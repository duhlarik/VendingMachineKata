package com.vendingmachine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DisplayTest {

    private static final InsertedCoin QUARTER = new InsertedCoin(5.670, 24.26);

    private VendingMachine vendingMachine;
    private Display display;
    private ProductInventoryManager productInventoryManager;
    private CoinReturn changeCoins;

    @Before
    public void setUp() {
        display = new Display();
        productInventoryManager = new ProductInventoryManager(); // TODO: needed in this test class? --Yes, it's needed to add to the observer
        changeCoins = new CoinReturn();
        vendingMachine = new VendingMachine(productInventoryManager, changeCoins);
        vendingMachine.addObserver(display);
        vendingMachine.addObserver(productInventoryManager);
    }

    @After
    public void tearDown() {
        productInventoryManager.updateInventory(Product.CANDY, 0);
        productInventoryManager.updateInventory(Product.CHIPS, 0);
        productInventoryManager.updateInventory(Product.COLA, 0);
        changeCoins.updateCoinInventory(CoinsForChangeDue.DIME, 0);
        changeCoins.updateCoinInventory(CoinsForChangeDue.NICKEL, 0);
    }

    @Test
    public void displayShowsInsertCoinWhenNoMoneyTenderedAndThereAreCoinsForChange() throws Exception {
        assertThat(vendingMachine.getAmountTendered(), is(0.0));

        assertThat(display.getMessage(), is("INSERT COIN $0.00"));
    }

    @Test
    public void displayShowsExactChangeOnlyWhenNoMoneyTenderedAndThereAreNoCoinsForChange() {
        vendingMachine.checkForExactChange();

        assertThat(display.getMessage(), is( "EXACT CHANGE ONLY"));
    }

    @Test
    public void displayShowsTheAmountTenderedAsYouPutMoneyInTheVendingMachine() throws Exception {
        vendingMachine.insertCoin(QUARTER);

        assertThat(display.getMessage(), is("AMOUNT TENDERED: $0.25"));

        vendingMachine.insertCoin(QUARTER);

        assertThat(display.getMessage(), is("AMOUNT TENDERED: $0.50"));
    }

    @Test
    public void displayShowsThankYouWhenProductIsDispensed() throws Exception {
        productInventoryManager.manageInventory(Product.CHIPS, 5);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.dispenseProduct(Product.CHIPS);

        assertThat(display.getMessage(), is("THANK YOU $0.00"));
    }

    @Test
    public void displayGoesBackToInsertCoinWhenTransactionIsComplete() throws Exception {
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.dispenseProduct(Product.CHIPS);
        vendingMachine.done();

        assertThat(display.getMessage(), is("INSERT COIN $0.00"));
    }

    @Test
    public void displayShowsThePriceWhenInsufficientTenderedAmount() throws Exception {
        productInventoryManager.manageInventory(Product.CHIPS, 5);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.dispenseProduct(Product.CHIPS);

        assertThat(display.getMessage(), is("PRICE: $0.50"));
    }

    @Test
    public void displayShowsSoldOutAndAmountTenderedWhenProductIsSoldOut() throws Exception {
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.dispenseProduct(Product.CHIPS);

        assertThat(display.getMessage(), is("SOLD OUT: $0.50"));
    }

    @Test
    public void displayShowsExactChangeOnlyWhenChangeCoinsAreEmpty() {
        vendingMachine.checkForExactChange();

        assertThat(display.getMessage(), is("EXACT CHANGE ONLY"));
    }
}