package org.ciencialabart.foodonfork;

import org.ciencialabart.foodonfork.data.Cuisine;
import org.ciencialabart.foodonfork.data.Desert;
import org.ciencialabart.foodonfork.data.MainCourse;

/**
 * Builds part of the Lunch order after choosing the Cuisine and concrete Order implementation
 * 
 * @author  Bartlomiej Cienciala
 * @version 1.0
 * @since   1.0
 */
class LunchFactory extends OrderFactory {
    
    /**
     * Queries the user for the Lunch specific order details and builds ready Lunch
     * 
     * @param cuisine of menu to be composed
     * @return        ready Lunch
     */
    @Override
    protected Order createOrder(Cuisine cuisine) {
        MainCourse mainCourse = ORDERING_FACADE.askForMainCourse(cuisine);      // ordering main course
        Desert desert = ORDERING_FACADE.askForDesert(cuisine);                  // ordering desert
        return new Lunch (mainCourse, desert);                                  // comprising the lunch
    }
}
