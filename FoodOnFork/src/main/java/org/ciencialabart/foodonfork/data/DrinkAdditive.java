package org.ciencialabart.foodonfork.data;

import java.math.BigDecimal;

/**
 * Defines drink additives to be used with Drink class
 * <p>
 * Feel free to modify the prices only
 * 
 * @author  Bartlomiej Cienciala
 * @version 1.0
 * @since   1.0
 */
public enum DrinkAdditive {
    ICE_CUBES (new BigDecimal("0.05")),
    LEMON (new BigDecimal("0.15"));
    
    public final BigDecimal price;
    
    /**
     * Creates specific drink additive enum
     * 
     * @param price price of the specific additive enum
     */
    DrinkAdditive (BigDecimal price) {
        this.price = price;
    }
}
