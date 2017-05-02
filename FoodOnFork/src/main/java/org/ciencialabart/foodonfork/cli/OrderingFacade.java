package org.ciencialabart.foodonfork.cli;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.ciencialabart.foodonfork.data.BareDrink;
import org.ciencialabart.foodonfork.data.Cuisine;
import org.ciencialabart.foodonfork.data.Desert;
import org.ciencialabart.foodonfork.data.DrinkAdditive;
import org.ciencialabart.foodonfork.data.MainCourse;
import org.ciencialabart.foodonfork.data.Meal;

/**
 * Facade querying the user with concrete gastronomic domain specific order queries
 * 
 * @author  Bartlomiej Cienciala
 * @version 1.0
 * @since   1.0
 */
public class OrderingFacade {
    private static final OrderingFacade ORDERING_FACADE = new OrderingFacade(); // OrderingFacade singleton
    private final CLIReader CLI_READER = new CLIReader();                       // wrapping System.in
    
    /**
     * Constructs the only instance of ordering Facade
     */
    private OrderingFacade() {}
    
    /**
     * Gets the OrderingFacade singleton
     * 
     * @return the OrderingFacade singleton
     */
    public static OrderingFacade getInstance() {
        return ORDERING_FACADE;
    }
    
    /**
     * Asks the user to choose cuisine from Cuisine enums options list
     * 
     * @return the choosen Cuisine enum value
     */
    public Cuisine askForCuisine() {
        List<Cuisine> cuisinesList = Arrays.asList(Cuisine.values());           // list of cuisines
        List<String> optionsList = cuisinesList.stream()                        // list of cuisine names
                .map(cuisine -> cuisine.name)
                .collect(Collectors.toList());
        int cuisineIndex = CLI_READER.getAnswerIndex("Choose cuisine", optionsList);    // choosing cuisine from the list
        return cuisinesList.get(cuisineIndex);                                  // returning choosen cuisine enum value
    }
    
    /**
     * Asks the user to choose meal type from Meal enums options list
     * 
     * @return the choosen Meal enum value
     */
    public Meal askForMealType() {
        List<Meal> mealsList = Arrays.asList(Meal.values());                    // list of meals
        List<String> optionsList = mealsList.stream()                           // list of meal names
                .map(meal -> meal.name)
                .collect(Collectors.toList());
        int mealIndex = CLI_READER.getAnswerIndex("Choose your meal", optionsList);    // choosing meal from the list
        return mealsList.get(mealIndex);                                        // returning choosen meal enum value
    }
    
    /**
     * Asks the user to choose main course from MainCourse enums options list narrowed to given Cuisine
     * 
     * @param cuisine to which main course choice has to be narrowed
     * @return        the choosen MainCourse enum value
     */
    public MainCourse askForMainCourse(Cuisine cuisine) {
        List<MainCourse> mainCoursesList = Arrays.asList(MainCourse.values()).stream()  // list of main courses
                .filter(mainCourse -> mainCourse.cuisine.equals(cuisine))               // filtered by cuisine
                .collect(Collectors.toList());
        int maxNameSize = Math.max(20, mainCoursesList.stream()                 // determainig not less than 20
                .mapToInt(mainCourse -> mainCourse.name.length())               // maximal main course name
                .max().getAsInt() + 3);                                         // + index string size
        String formatString = "%-" + String.valueOf(maxNameSize) + "s %s$";     // to pad with spaces main course price
        List<String> optionsList = mainCoursesList.stream()                     // list of main course names and their prices
                .map(mainCourse -> String.format(formatString, mainCourse.name, mainCourse.price))
                .collect(Collectors.toList());
        int mainCourseIndex = CLI_READER.getAnswerIndex("Choose your main course", optionsList);    // choosing main course from the list
        return mainCoursesList.get(mainCourseIndex);                            // returning choosen main course enum value
    }
    
    /**
     * Asks the user to choose desert from Desert enums options list narrowed to given Cuisine
     * 
     * @param cuisine to which desert choice has to be narrowed
     * @return        the choosen Desert enum value
     */
    public Desert askForDesert(Cuisine cuisine) {
        List<Desert> desertsList = Arrays.asList(Desert.values()).stream()      // list of deserts
                .filter(desert -> desert.cuisine.equals(cuisine))               // filtered by cuisine
                .collect(Collectors.toList());
        int maxNameSize = Math.max(20, desertsList.stream()                     // determainig not less than 20
                .mapToInt(desert -> desert.name.length())                       // maximal desert name
                .max().getAsInt() + 3);                                         // + index string size
        String formatString = "%-" + String.valueOf(maxNameSize) + "s %s$";     // to pad with spaces desert price
        List<String> optionsList = desertsList.stream()                         // list of desert names and their prices
                .map(desert -> String.format(formatString, desert.name, desert.price))
                .collect(Collectors.toList());
        int desertIndex = CLI_READER.getAnswerIndex("Choose your desert", optionsList);    // choosing desert from the list
        return desertsList.get(desertIndex);                                    // returning choosen desert enum value
    }
    
    /**
     * Asks the user to choose desert from BareDrink enums options list narrowed to given Cuisine
     * 
     * @param cuisine to which bare drink choice has to be narrowed
     * @return        the choosen BareDrink enum value
     */
    public BareDrink askForDrink(Cuisine cuisine) {
        List<BareDrink> drinksList = Arrays.asList(BareDrink.values()).stream() // list of drinks
                .filter(drink -> drink.cuisine.equals(cuisine))                 // filtered by cuisine
                .collect(Collectors.toList());
        int maxNameSize = Math.max(20, drinksList.stream()                      // determainig not less than 20
                .mapToInt(drink -> drink.name.length())                         // maximal drink name
                .max().getAsInt() + 3);                                         // + index string size
        String formatString = "%-" + String.valueOf(maxNameSize) + "s %s$";     // to pad with spaces drink price
        List<String> optionsList = drinksList.stream()                          // list of drink names and their prices
                .map(drink -> String.format(formatString, drink.name, drink.price))
                .collect(Collectors.toList());
        int drinkIndex = CLI_READER.getAnswerIndex("Choose your drink", optionsList);    // choosing drink from the list
        return drinksList.get(drinkIndex);                                      // returning choosen drink enum value
    }
    
    /**
     * Asks the user if he/she wish ice cubes
     * 
     * @return true in case of "yes" or "y" answer, false in case of "no" or "n" answer
     */
    public boolean askForIceCubes() {
        return CLI_READER.getAnswer("Do you wish ice cubes (It's " + DrinkAdditive.ICE_CUBES.price + "$)?");
    }
    
    /**
     * Asks the user if he/she wish lemon
     * 
     * @return true in case of "yes" or "y" answer, false in case of "no" or "n" answer
     */
    public boolean askForLemon() {
        return CLI_READER.getAnswer("Do you wish lemon (It's " + DrinkAdditive.LEMON.price + "$)?");
    }
    
    /**
     * Asks the user if he/she wish more orders
     * 
     * @return true in case of "yes" or "y" answer, false in case of "no" or "n" answer
     */
    public boolean askForMoreOrders() {
        return CLI_READER.getAnswer("Do you wish more orders?");
    }
}
