package org.ciencialabart.foodonfork;

import org.ciencialabart.foodonfork.cli.OrderingFacade;

/**
 * The FoodOnFork food ordering system
 * 
 * @author  Bartlomiej Cienciala
 * @version 1.0
 * @since   1.0
 */
public class FoodOnFork {
    
    /**
     * The main method of the FoodOnFork application
     * <p>
     * Queries the user for the order details and prints out its name and price,
     * then asks the user if he/she wants more orders.
     * 
     * @param args are ignored
     */
    public static void main(String[] args) {
        Order order;
        boolean moreOrders = true;
        OrderingFacade orderingFacade = OrderingFacade.getInstance();
        
        while (moreOrders) {
            order = OrderFactory.createOrder();                                 // ordering
            System.out.println();
            System.out.println("******************************");               // printing
            System.out.println("Your order: " + order.getName());               // resulting order name
            System.out.println("Price: " + order.getPrice() + "$");             // and its total price
            System.out.println("******************************");
            System.out.println();
            moreOrders = orderingFacade.askForMoreOrders();                     // asking for more orders
        }                                                                       // exiting if enough
    }
}
