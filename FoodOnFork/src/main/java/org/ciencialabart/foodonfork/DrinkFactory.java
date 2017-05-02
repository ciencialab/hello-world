package org.ciencialabart.foodonfork;

import org.ciencialabart.foodonfork.data.BareDrink;
import org.ciencialabart.foodonfork.data.Cuisine;

/**
 * Builds part of the Drink order after choosing the Cuisine and concrete Order implementation
 * 
 * @author  Bartlomiej Cienciala
 * @version 1.0
 * @since   1.0
 */
class DrinkFactory extends OrderFactory {
    
    /**
     * Queries the user for the Drink specific order details and builds ready Drink
     * 
     * @param cuisine of menu to be composed
     * @return        ready Drink
     */
    @Override
    protected Order createOrder(Cuisine cuisine) {
        BareDrink drink = ORDERING_FACADE.askForDrink(cuisine);                 // ordering bare drink
        boolean addIceCubes = ORDERING_FACADE.askForIceCubes();                 // asking for ice cubes
        boolean putLemon = ORDERING_FACADE.askForLemon();                       // asking for lemon
        return new Drink (drink, addIceCubes, putLemon);                        // comprising the drink
    }
}
