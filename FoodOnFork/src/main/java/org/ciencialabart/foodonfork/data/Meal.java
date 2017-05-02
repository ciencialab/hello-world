package org.ciencialabart.foodonfork.data;

/**
 * Defines the two meal types to be used by OrderFactory class
 * 
 * @author  Bartlomiej Cienciala
 * @version 1.0
 * @since   1.0
 */
public enum Meal {
    LUNCH ("Lunch"),
    DRINK ("Drink");
    
    public final String name;
    
    /**
     * Creates specific meal type enum
     * 
     * @param name    name of the specific meal type
     */
    Meal (String name) {
        this.name = name;
    }
}
