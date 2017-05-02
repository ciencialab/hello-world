package org.ciencialabart.foodonfork.data;

import java.math.BigDecimal;

/**
 * Defines main courses to be used with Lunch class
 * <p>
 * Feel free to add/remove/arrange them in any order.
 * Application lists them in order of appearance.
 * 
 * @author  Bartlomiej Cienciala
 * @version 1.0
 * @since   1.0
 */
public enum MainCourse {
    PORK_CHOP (Cuisine.POLISH, new BigDecimal("4.89"), "Pork chop with cabbage and potatos"),
    BOILED_BEEF (Cuisine.POLISH, new BigDecimal("3.85"), "Boiled chunk of beef in horseradish sauce"),
    DUMPLINGS (Cuisine.POLISH, new BigDecimal("2.50"), "Dumplings with sauerkraut"),
    
    TORTILLA (Cuisine.MEXICAN, new BigDecimal("2.75"), "Tortilla"),
    BURRITO (Cuisine.MEXICAN, new BigDecimal("2.30"), "Burrito"),
    QUESADILLA (Cuisine.MEXICAN, new BigDecimal("3.25"), "Quesadilla"),
    
    PIZZA (Cuisine.ITALIAN, new BigDecimal("5.25"), "Pizza"),
    PASTA (Cuisine.ITALIAN, new BigDecimal("3.20"), "Spaghetti bolognese"),
    LASAGNE (Cuisine.ITALIAN, new BigDecimal("2.35"), "Lasagne");
    
    public final Cuisine cuisine;
    public final BigDecimal price;
    public final String name;
    
    /**
     * Creates specific main course enum
     * 
     * @param cuisine Cuisine which specific main course belongs to
     * @param price   price of the specific main course
     * @param name    name of the specific main course
     */
    MainCourse (Cuisine cuisine, BigDecimal price, String name) {
        this.cuisine = cuisine;
        this.price = price;
        this.name = name;
    }
}
