package com.example.olga.shop.models;

import java.math.BigDecimal;

/**
 * Interface Product which can be added to shopping cart
 */
public interface Saleable {

    BigDecimal getPrice();

    String getName();
}
