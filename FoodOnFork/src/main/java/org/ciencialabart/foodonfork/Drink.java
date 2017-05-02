package org.ciencialabart.foodonfork;

import java.math.BigDecimal;
import org.ciencialabart.foodonfork.data.BareDrink;
import org.ciencialabart.foodonfork.data.DrinkAdditive;

/**
 * Concrete ordered Drink definition with name and price determined based on ingredients
 * 
 * @author  Bartlomiej Cienciala
 * @version 1.0
 * @since   1.0
 */
public class Drink implements Order {
    private final BareDrink bareDrink;
    private final boolean withIceCubes;
    private final boolean withLemon;
    
    /**
     * Creates the concrete Drink
     * 
     * @param bareDrink    concrete BareDrink
     * @param withIceCubes true if the drink contains ice cubes
     * @param withLemon    true if the drink contains lemon
     */
    public Drink(BareDrink bareDrink, boolean withIceCubes, boolean withLemon) {
        this.bareDrink = bareDrink;
        this.withIceCubes = withIceCubes;
        this.withLemon = withLemon;
    }
    
    /**
     * Returns the name of the drink based of its content
     * 
     * @return the name of the drink based of its content
     */
    @Override
    public String getName() {
        String mealName = bareDrink.name;                                       // base drink name
        if (withIceCubes || withLemon) {                                        // and with subsequent
            if (withIceCubes && withLemon) {                                    // additives combinations
                mealName += " with ice cubes and lemon";
            } else {
                if (withIceCubes) {
                    mealName += " with ice cubes";
                } else {
                    mealName += " with lemon";
                }
            }
        }
        return mealName;
    }
    
    /**
     * Returns the price of the drink based of its content prices
     * 
     * @return the price of the drink based of its content prices
     */
    @Override
    public BigDecimal getPrice() {
        BigDecimal mealPrice = bareDrink.price;                                 // base drink price
        if (withIceCubes) {
            mealPrice = mealPrice.add(DrinkAdditive.ICE_CUBES.price);           // price with ice cubes
        }
        if (withLemon) {
            mealPrice = mealPrice.add(DrinkAdditive.LEMON.price);               // price with lemon
        }
        return mealPrice;
    }
}
