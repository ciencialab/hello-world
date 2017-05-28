package org.ciencialabart.wordchainresolver.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ciencialabart.wordchainresolver.WordChainResolver;
import org.ciencialabart.wordchainresolver.WordPrefixesUsingDijkstraWordChainResolver;
import org.ciencialabart.wordchainresolver.node.FixedWordLengthWordNodeDictionary;
import org.ciencialabart.wordchainresolver.node.WordNode;
import org.ciencialabart.wordchainresolver.node.WordNodeDictionary;

public class WordPrefixesManagingWordChainResolvingContext implements WordChainResolvingContext {
    
    public static final String DICTIONARY_FILE_USER_DIR_RELATIVE_PATH = "\\target\\classes\\wordlist.txt";
    
    private final List<String> dictionary;
    private final List<WordNodeDictionary> fixedWordLengthDictionaries = new ArrayList<>();
    
    public WordPrefixesManagingWordChainResolvingContext() {
        dictionary = new DictionaryLoader(DICTIONARY_FILE_USER_DIR_RELATIVE_PATH).get();
        
        buildFixedWordLengthDictionaries();
    }

    private void buildFixedWordLengthDictionaries() {
        int wordLength;
        
        for (String word : dictionary) {
            wordLength = word.length();
            while (wordLength >= fixedWordLengthDictionaries.size()) {
                fixedWordLengthDictionaries.add(new FixedWordLengthWordNodeDictionary(fixedWordLengthDictionaries.size()));
            }
            fixedWordLengthDictionaries.get(wordLength).accept(word);
        }
    }

    @Override
    public List<String> getDictionary() {
        return Collections.unmodifiableList(dictionary);
    }

    @Override
    public WordNode getWordNode(String word) {
        return fixedWordLengthDictionaries.get(word.length()).getWordNode(word);
    }

    @Override
    public WordChainResolver createWordChainResolver() {
        return new WordPrefixesUsingDijkstraWordChainResolver(this);
    }

    @Override
    public void resetState() {
        fixedWordLengthDictionaries.stream().forEach(WordNodeDictionary::resetState);
    }

}
