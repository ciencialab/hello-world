package org.ciencialabart.wordchainresolver.input;

import java.util.List;

import org.ciencialabart.wordchainresolver.common.Validator;

public class AgainstDictionaryWordValidator implements Validator {
    
    private final List<String> dictionary;
    private final String word;

    public AgainstDictionaryWordValidator(List<String> dictionary, String word) {
        this.dictionary = dictionary;
        this.word = word;
    }

    public boolean isValid() {
        
        
        return dictionary.contains(word);
    }

}
