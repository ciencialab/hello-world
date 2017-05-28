package org.ciencialabart.wordchainresolver.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.ciencialabart.wordchainresolver.context.factory.WordChainResolvingContextFactory;
import org.ciencialabart.wordchainresolver.context.factory.WordPrefixesManagingWordChainResolvingContextFactory;
import org.junit.Before;
import org.junit.Test;

public class WordChainResolvingContextTest {

    private WordChainResolvingContext context;

    @Before
    public void initializeApplication() {
        WordChainResolvingContextFactory contextFactory = new WordPrefixesManagingWordChainResolvingContextFactory();
        context = contextFactory.createContext();
    }
    
    @Test
    public void shouldReturnSortedDictionary() {
        List<String> dictionary = context.getDictionary();
        
        assertTrue("Dictionary item at index 0 should be earlier then one at index 1",
                dictionary.get(0).compareTo(dictionary.get(1)) < 0);
        assertTrue("Dictionary item at index 1 should be earlier then one at index 2",
                dictionary.get(1).compareTo(dictionary.get(2)) < 0);
    }
    
    @Test
    public void shouldReturnSupportedObjects() {
        context.getWordNode("gold").getPrefix();
        
        assertEquals("Should return gold node", "gold", context.getWordNode("gold").getPrefix());
        assertTrue("Should return WordChainResolver", context.createWordChainResolver() != null);
    }
    
}
