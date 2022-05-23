package com.github.mihnead12.currrencycovert.exceptions;

public class CurrencyNotValid extends RuntimeException {
    public CurrencyNotValid() {
        super("The currency you provided is not valid");
    }
}
