package org.ciencialabart.foodonfork.data;

import java.math.BigDecimal;

/**
 * Defines deserts to be used with Lunch class
 * <p>
 * Feel free to add/remove/arrange them in any order.
 * Application lists them in order of appearance.
 * 
 * @author  Bartlomiej Cienciala
 * @version 1.0
 * @since   1.0
 */
public enum Desert {
    DOUGHNUT (Cuisine.POLISH, new BigDecimal("0.50"), "Doughnut"),
    PASTRY_TWISTERS (Cuisine.POLISH, new BigDecimal("1.10"), "Pastry twisters"),
    CHEESE_CAKE (Cuisine.POLISH, new BigDecimal("1.00"), "Cheese cake"),
    
    EMPANADAS (Cuisine.MEXICAN, new BigDecimal("1.75"), "Empanadas"),
    COCADAS (Cuisine.MEXICAN, new BigDecimal("0.45"), "Cocadas"),
    BIONICO (Cuisine.MEXICAN, new BigDecimal("0.89"), "Bionico"),
    
    TIRAMISU (Cuisine.ITALIAN, new BigDecimal("1.25"), "Tiramisu"),
    PANNA_COTA (Cuisine.ITALIAN, new BigDecimal("0.30"), "Panna cota"),
    ZABAIONE (Cuisine.ITALIAN, new BigDecimal("0.80"), "Zabaione");
    
    public final Cuisine cuisine;
    public final BigDecimal price;
    public final String name;
    
    /**
     * Creates specific desert enum
     * 
     * @param cuisine Cuisine which specific desert belongs to
     * @param price   price of the specific desert
     * @param name    name of the specific desert
     */
    Desert (Cuisine cuisine, BigDecimal price, String name) {
        this.cuisine = cuisine;
        this.price = price;
        this.name = name;
    }
}
