package org.ciencialabart.wordchainresolver.testutil;

import org.ciencialabart.wordchainresolver.common.Validator;

public class SingleLetterWordsDifferenceValidator implements Validator {

    private String word1;
    private String word2;

    public SingleLetterWordsDifferenceValidator(String word1, String word2) {
        this.word1 = word1;
        this.word2 = word2;
    }

    @Override
    public boolean isValid() {
        char[] characters1 = word1.toCharArray();
        char[] characters2 = word2.toCharArray();
        int characterIndex = 0;
        int differencesCount = 0;
        
        while (characterIndex < word1.length()) {
            if (characters1[characterIndex] != characters2[characterIndex]) {
                differencesCount++;
            }
            
            characterIndex++;
        }
        
        return differencesCount == 1;
    }

}
