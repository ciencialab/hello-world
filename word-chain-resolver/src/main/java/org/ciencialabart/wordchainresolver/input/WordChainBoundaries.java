package org.ciencialabart.wordchainresolver.input;

public class WordChainBoundaries {
    
    private final String startingWord;
    private final String endingWord;
    
    public WordChainBoundaries(String startingWord, String endingWord) {
        this.startingWord = startingWord;
        this.endingWord = endingWord;
    }

    public String getStartingWord() {
        return startingWord;
    }

    public String getEndingWord() {
        return endingWord;
    }
    
}
