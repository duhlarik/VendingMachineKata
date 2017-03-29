package com.vendingmachine;

import java.util.Observer;

public interface Observable {
    public void addObserver (Observer o);
    public void removeObserver (Observer o);
    public void notifyObserver ();
}
