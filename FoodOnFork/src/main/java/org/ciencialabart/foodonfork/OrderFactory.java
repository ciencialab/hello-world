package org.ciencialabart.foodonfork;

import org.ciencialabart.foodonfork.cli.OrderingFacade;
import org.ciencialabart.foodonfork.data.Cuisine;
import org.ciencialabart.foodonfork.data.Meal;

/**
 * Builds part of the Order choosing the Cuisine and concrete Order implementation
 * 
 * @author  Bartlomiej Cienciala
 * @version 1.0
 * @since   1.0
 */
public abstract class OrderFactory {
    
    protected static final OrderingFacade ORDERING_FACADE = OrderingFacade.getInstance();
    private static final LunchFactory LUNCH_FACTORY = new LunchFactory();
    private static final DrinkFactory DRINK_FACTORY = new DrinkFactory();
    
    /**
     * Queries the user for the common Order details and builds ready Order based on user choices made
     * 
     * @return ready Order
     */
    public static Order createOrder() {
        OrderFactory orderFactory;
        Cuisine cuisine = ORDERING_FACADE.askForCuisine();                      // choosing cuisine
        Meal mealType = ORDERING_FACADE.askForMealType();                       // choosing meal type
        if (mealType.equals(Meal.LUNCH)) {                                      // setting factory for meal choosen
            orderFactory = LUNCH_FACTORY;
            
        } else {
            orderFactory = DRINK_FACTORY;
            
        }
        return orderFactory.createOrder(cuisine);                               // creating and returning concrete meal order
    }
    
    /**
     * Queries the user for the concrete Order specific details and builds ready Order
     * 
     * @param cuisine of menu to be composed
     * @return        ready Order
     */
    protected abstract Order createOrder(Cuisine cuisine);
}
