package org.ciencialabart.foodonfork.data;

import java.math.BigDecimal;

/**
 * Defines bare drinks to be used with Drink class
 * <p>
 * Feel free to add/remove/arrange them in any order.
 * Application lists them in order of appearance.
 * 
 * @author  Bartlomiej Cienciala
 * @version 1.0
 * @since   1.0
 */
public enum BareDrink {
    COMPOTE (Cuisine.POLISH, new BigDecimal("0.60"), "Compote"),
    SOURED_MILK (Cuisine.POLISH, new BigDecimal("0.50"), "Soured milk"),
    VODKA (Cuisine.POLISH, new BigDecimal("1.00"), "Vodka"),
    
    HOT_CHOCOLATE (Cuisine.MEXICAN, new BigDecimal("1.00"), "Hot chocolate"),
    COLONCHE (Cuisine.MEXICAN, new BigDecimal("1.45"), "Colonche"),
    TEQUILA (Cuisine.MEXICAN, new BigDecimal("2.10"), "Tequila"),
    
    ESPRESSO (Cuisine.ITALIAN, new BigDecimal("0.95"), "Espresso"),
    CHIANTI (Cuisine.ITALIAN, new BigDecimal("0.55"), "Chianti"),
    MARTINI (Cuisine.ITALIAN, new BigDecimal("1.10"), "Martini");
    
    public final Cuisine cuisine;
    public final BigDecimal price;
    public final String name;
    
    /**
     * Creates specific bare drink enum
     * 
     * @param cuisine Cuisine which specific bare drink belongs to
     * @param price   price of the specific bare drink
     * @param name    name of the specific bare drink
     */
    BareDrink (Cuisine cuisine, BigDecimal price, String name) {
        this.cuisine = cuisine;
        this.price = price;
        this.name = name;
    }
}
