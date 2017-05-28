package org.ciencialabart.wordchainresolver.input;

import java.util.Scanner;

import org.ciencialabart.wordchainresolver.context.WordChainResolvingContext;

public class UserWordChainBoundariesSupplier implements WordChainBoundariesSupplier {

    private static final String STARTING_WORD_PROMPT = "Enter chain starting word: ";
    private static final String ENDING_WORD_PROMPT = "Enter chain ending word: ";
    
    private final WordChainResolvingContext context;
    private Scanner scanner;

    public UserWordChainBoundariesSupplier(WordChainResolvingContext context) {
        this.context = context;
    }

    public WordChainBoundaries get() {
        String startingWord;
        String endingWord;
        
        scanner = new Scanner(System.in);
        
        startingWord = getWordFromUser(STARTING_WORD_PROMPT);
        do {
            endingWord = getWordFromUser(ENDING_WORD_PROMPT);
        } while (startingWord.length() != endingWord.length());
        
        scanner.close();
        
        return new WordChainBoundaries(startingWord, endingWord);
    }

    private String getWordFromUser(String prompt) {
        String word;
        
        do {
            System.out.print(prompt);
            word = scanner.nextLine();
        } while (!new AgainstDictionaryWordValidator(context.getDictionary(), word).isValid());
        
        return word;
    }

}
