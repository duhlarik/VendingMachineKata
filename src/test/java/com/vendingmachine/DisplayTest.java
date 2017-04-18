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

    @Before
    public void setUp() {
        display = new Display();
        productInventoryManager = new ProductInventoryManager(); // TODO: needed in this test class?
        vendingMachine = new VendingMachine(productInventoryManager);
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
    public void showsInsertCoinWhenNoMoneyTendered() throws Exception {
        assertThat(vendingMachine.getAmountTendered(), is(0.0));

        assertThat(display.getMessage(), is("INSERT COIN $0.00"));
    }

    @Test
    public void showsTheAmountTenderedAsYouPutMoneyInTheVendingMachine() throws Exception {
        vendingMachine.insertCoin(QUARTER);

        assertThat(display.getMessage(), is("AMOUNT TENDERED: $0.25"));

        vendingMachine.insertCoin(QUARTER);

        assertThat(display.getMessage(), is("AMOUNT TENDERED: $0.50"));
    }

    @Test
    public void showsThankYouWhenProductIsDispensed() throws Exception {
        productInventoryManager.manageInventory(Product.CHIPS, 5);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.dispenseProduct(Product.CHIPS);

        assertThat(display.getMessage(), is("THANK YOU $0.00"));
    }

    @Test
    public void goesBackToInsertCoinWhenTransactionIsComplete() throws Exception {
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.dispenseProduct(Product.CHIPS);
        vendingMachine.done();

        assertThat(display.getMessage(), is("INSERT COIN $0.00"));
    }

    @Test
    public void showsThePriceWhenInsufficientTenderedAmount() throws Exception {
        productInventoryManager.manageInventory(Product.CHIPS, 5);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.dispenseProduct(Product.CHIPS);

        assertThat(display.getMessage(), is("PRICE: $0.50"));
    }

    @Test
    public void showsSoldOutAndAmountTenderedWhenProductIsSoldOut() throws Exception {
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.insertCoin(QUARTER);
        vendingMachine.dispenseProduct(Product.CHIPS);

        assertThat(display.getMessage(), is("SOLD OUT: $0.50"));
    }

    @Test
    public void showsExactChangeOnlyWhenChangeCoinsAreEmpty() {
        vendingMachine.checkForExactChange();

        assertThat(display.getMessage(), is("EXACT CHANGE ONLY"));
    }
}