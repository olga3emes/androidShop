package com.example.olga.shop.constant;

import com.example.olga.shop.models.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class Constant {
    public static final List<Integer> QUANTITY_LIST = new ArrayList<Integer>();

    static {
        for (int i = 1; i < 20; i++) QUANTITY_LIST.add(i);
    }

    public static final Product PRODUCT1 = new Product(1, "Pendientes Fantasía \n Color: Celeste", BigDecimal.valueOf(19.99),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua", "pendientes");
    public static final Product PRODUCT2 = new Product(2, "Mantoncillo Clásico \n Color: Verde_Pera", BigDecimal.valueOf(36.97),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua", "mantoncillo");
    public static final Product PRODUCT3 = new Product(3, "Peineta Borlas \n Color: Rojo", BigDecimal.valueOf(24.99),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua", "peineta");

    public static final Product PRODUCT4 = new Product(4, "Rosa abierta Tamaño: M \n Color: Rojo", BigDecimal.valueOf(7.59),
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua", "rosa");


    public static final List<Product> PRODUCT_LIST = new ArrayList<Product>();

    static {
        PRODUCT_LIST.add(PRODUCT1);
        PRODUCT_LIST.add(PRODUCT2);
        PRODUCT_LIST.add(PRODUCT3);
        PRODUCT_LIST.add(PRODUCT4);
    }

    public static final String CURRENCY = "€ ";
}
