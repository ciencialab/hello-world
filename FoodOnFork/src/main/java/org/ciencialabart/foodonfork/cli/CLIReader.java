package org.ciencialabart.foodonfork.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Deals with querying the user with common query format types based queries
 * 
 * @author  Bartlomiej Cienciala
 * @version 1.0
 * @since   1.0
 */
public class CLIReader {
    private static final String RENEWED_PROMPT = "Choose correct answer please: ";
    private final BufferedReader INPUT = new BufferedReader (new InputStreamReader (System.in));
    private String prompt;
    
    /**
     * Queries the user with listed options based query
     * 
     * @param instruction that tells the user what to choose from
     * @param options     list of options the user chooses from in order of appearance
     * @return            valid options list index of user choice
     */
    public int getAnswerIndex(String instruction, List<String> options) {
        int optionNumber = 0;
        int answer = 0;                                                         // here answer is a list index

        System.out.println();                                                   // separation from previous question-answer pair
        System.out.println(instruction + ": ");                                 // instruction what to choose from
        for (String option : options) {                                         // for every option from the list
            optionNumber++;                                                     // starting from 1)
            System.out.println(optionNumber + ") " + option);                   // print numbered option
        }

        prompt = "";                                                            // initially without prompt
        while (true) {                                                          // insist to answer question
            try {                                                               // with correct number ...
                answer = Integer.parseInt(askAndGetAnswer(prompt));

                if (answer > 0 && answer <= options.size()) {                   // ... contained in options range
                    return answer - 1;                                          // and return answer index of the options list
                }
            } catch (NumberFormatException ex) {}
            
            prompt = RENEWED_PROMPT;                                            // prompt in case of incorrect answer
        }
    }
    
    /**
     * Queries the user with yes/no based query
     * 
     * @param question "?" terminated question to ask the user
     * @return         true in case of "yes" or "y" answer, false in case of "no" or "n" answer
     */
    public boolean getAnswer(String question) {
        String answer;                                                          // here answer is a yes/no/y/n String
        
        System.out.println();                                                   // separation from previous question-answer pair
        
        prompt = question + "(Yes/No): ";                                       // initial prompt is question with answer options
        while (true) {                                                          // insist to answer question
            answer = askAndGetAnswer(prompt)
                    .toLowerCase();                                             // case is not important
            
            if (answer.equals("yes") || answer.equals("y")) {                   // you can choose yes or y
                return true;
            }
            if (answer.equals("no") || answer.equals("n")) {                    // or you can choose no or n
                return false;
            }
            
            prompt = RENEWED_PROMPT;                                            // prompt in case of incorrect answer
        }
    }
    
    /**
     * Queries the user with one line prompt given
     * <p>
     * In case of disability to read user inputs exits application with code -1 returned
     * 
     * @param prompt to appear in front of user input
     * @return       not validated user input
     */
    private String askAndGetAnswer(String prompt) {
        String answer = "";

        System.out.print(prompt);                                               // print the prompt
        try {
            answer = INPUT.readLine();                                          // get answer from std in
        } catch (IOException ex) {
            Logger.getLogger(CLIReader.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        return answer;                                                          // return answer
    }
}
