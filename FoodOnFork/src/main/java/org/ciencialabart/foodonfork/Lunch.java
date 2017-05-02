package org.ciencialabart.foodonfork;

import java.math.BigDecimal;
import org.ciencialabart.foodonfork.data.Desert;
import org.ciencialabart.foodonfork.data.MainCourse;

/**
 * Concrete ordered Lunch definition with name and price determined based on ingredients
 * 
 * @author  Bartlomiej Cienciala
 * @version 1.0
 * @since   1.0
 */
class Lunch implements Order {
    private final MainCourse mainCourse;
    private final Desert desert;
    
    /**
     * Creates the concrete Lunch
     * 
     * @param mainCourse concrete MainCourse
     * @param desert     concrete Desert
     */
    public Lunch(MainCourse mainCourse, Desert desert) {
        this.mainCourse = mainCourse;
        this.desert = desert;
    }
    
    /**
     * Returns the name of the main course based of its content
     * 
     * @return the name of the main course based of its content
     */
    @Override
    public String getName() {
        return mainCourse.name + " and " + desert.name;                         // simple joinig main course and desert names
    }
    
    /**
     * Returns the price of the main course based of its content prices
     * 
     * @return the price of the main course based of its content prices
     */
    @Override
    public BigDecimal getPrice() {
        return mainCourse.price.add(desert.price);                              // summing prices
    }
}
