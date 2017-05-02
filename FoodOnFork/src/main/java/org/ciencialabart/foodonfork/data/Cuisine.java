package org.ciencialabart.foodonfork.data;

/**
 * Defines cuisines to be used with MainCourse, Desert and BareDrink enums
 * <p>
 * Feel free to add/remove/arrange them in any order.
 * Application lists them in order of appearance.
 * 
 * @author  Bartlomiej Cienciala
 * @version 1.0
 * @since   1.0
 */
public enum Cuisine {
    POLISH ("Polish"),
    MEXICAN ("Mexican"),
    ITALIAN ("Italian");
    
    public final String name;
    
    /**
     * Creates specific cuisine enum
     * 
     * @param name name of the specific cuisine
     */
    Cuisine (String name) {
        this.name = name;
    }
}
