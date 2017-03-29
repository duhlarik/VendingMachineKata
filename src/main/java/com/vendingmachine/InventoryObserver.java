package com.vendingmachine;

public class InventoryObserver implements Observer {

    private Observable observable = null;

    public InventoryObserver(Observable observable) {
        this.observable = observable;
    }

    @Override
    public void update() {
    }

    @Override
    public void reset() {
    }
}