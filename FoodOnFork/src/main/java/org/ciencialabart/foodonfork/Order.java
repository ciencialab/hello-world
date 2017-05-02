package org.ciencialabart.foodonfork;

import java.math.BigDecimal;

/**
 * Order definition with name and price determined
 * 
 * @author  Bartlomiej Cienciala
 * @version 1.0
 * @since   1.0
 */
public interface Order {
    
    /**
     * Returns the name of the order based of its content
     * 
     * @return the name of the order based of its content
     */
    String getName();
    
    /**
     * Returns the price of the order based of its content prices
     * 
     * @return the price of the order based of its content prices
     */
    BigDecimal getPrice();
}
