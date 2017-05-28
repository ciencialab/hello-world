package org.ciencialabart.wordchainresolver.node;

import java.util.ArrayList;
import java.util.List;

public class FixedWordLengthWordNodeDictionary implements WordNodeDictionary {
    
    private final int wordLength;
    private final List<List<WordPrefixNode>> wordPrefixesNodesDictionaries = new ArrayList<>();
    
    public FixedWordLengthWordNodeDictionary(int wordLength) {
        this.wordLength = wordLength;
        
        buildWordPrefixesNodesDictionaries();
    }

    private void buildWordPrefixesNodesDictionaries() {
        int prefixLength = 0;
        
        while (prefixLength <= wordLength) {
            wordPrefixesNodesDictionaries.add(new ArrayList<>());
            
            prefixLength++;
        }
    }

    @Override
    public void accept(String word) {
        if (getDictionaryForPrefixLength(0).isEmpty()) {
            addFirstWord(word);
        } else {
            addNextWord(word);
        }
    }

    private void addFirstWord(String word) {
        WordPrefixNode wordPrefixNodesPath;
        int prefixLength;
        
        wordPrefixNodesPath = new WordNode(word);
        getDictionaryForPrefixLength(wordLength).add(wordPrefixNodesPath);
        
        prefixLength = wordLength - 1;
        while (prefixLength >= 0) {
            wordPrefixNodesPath = createWordPrefixNode(word, prefixLength, wordPrefixNodesPath);
            getDictionaryForPrefixLength(prefixLength).add(wordPrefixNodesPath);
            
            prefixLength--;
        }
    }

    private WordPrefixNode createWordPrefixNode(String word, int prefixLength, WordPrefixNode wordPrefixNodesPath) {
        return new WordPrefixNode(word.substring(0, prefixLength), wordPrefixNodesPath);
    }

    private void addNextWord(String word) {
        List<WordPrefixNode> dictionary;
        WordPrefixNode wordPrefixNodesPath;
        WordPrefixNode dictionaryLastNode;
        String wordPrefix;
        int prefixLength;
        
        dictionary = getDictionaryForPrefixLength(wordLength);
        dictionaryLastNode = dictionary.get(dictionary.size() - 1);
        
        if (dictionaryLastNode.getPrefix().equals(word)) {
            return;
        }
        
        wordPrefixNodesPath = new WordNode(word);
        dictionary.add(wordPrefixNodesPath);
        
        prefixLength = wordLength - 1;
        while (prefixLength >= 0) {
            wordPrefix = word.substring(0, prefixLength);
            dictionary = getDictionaryForPrefixLength(prefixLength);
            dictionaryLastNode = dictionary.get(dictionary.size() - 1);
            
            if (dictionaryLastNode.getPrefix().equals(wordPrefix)) {
                dictionaryLastNode.add(wordPrefixNodesPath);
                break;
            }
            
            wordPrefixNodesPath = new WordPrefixNode(wordPrefix, wordPrefixNodesPath);
            dictionary.add(wordPrefixNodesPath);
            
            prefixLength--;
        }
    }

    private List<WordPrefixNode> getDictionaryForPrefixLength(int prefixLength) {
        return wordPrefixesNodesDictionaries.get(prefixLength);
    }

    @Override
    public WordNode getWordNode(String word) {
        return WordNode.fromWordPrefixNode(wordPrefixesNodesDictionaries.get(0).get(0), word);
    }

    @Override
    public void resetState() {
        wordPrefixesNodesDictionaries.get(wordPrefixesNodesDictionaries.size() - 1).stream()
                .map(WordNode.class::cast)
                .forEach(wordNode -> wordNode.setVisited(false));
    }
    
}
