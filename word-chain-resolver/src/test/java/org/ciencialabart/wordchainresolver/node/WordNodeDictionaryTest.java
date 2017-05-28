package org.ciencialabart.wordchainresolver.node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WordNodeDictionaryTest {
    
    @Test
    public void shouldAcceptWordsAndReturnTheirNodes() {
        WordNodeDictionary wordNodeDictionary = new FixedWordLengthWordNodeDictionary(5);
        
        wordNodeDictionary.accept("apple");
        assertEquals("Should accept apple word and return its node", "apple",
                wordNodeDictionary.getWordNode("apple").getPrefix());
        wordNodeDictionary.accept("appli");
        assertEquals("Should accept appli word and return its node", "appli",
                wordNodeDictionary.getWordNode("appli").getPrefix());
        wordNodeDictionary.accept("arras");
        assertEquals("Should accept arras word and return its node", "arras",
                wordNodeDictionary.getWordNode("arras").getPrefix());
        wordNodeDictionary.accept("zebra");
        assertEquals("Should accept zebra word and return its node", "zebra",
                wordNodeDictionary.getWordNode("zebra").getPrefix());
        
        assertEquals("Should return apple word node again", "apple",
                wordNodeDictionary.getWordNode("apple").getPrefix());
    }
    
    @Test
    public void shouldReturnedNodesHaveCommonAncestors() {
        WordNodeDictionary wordNodeDictionary = new FixedWordLengthWordNodeDictionary(2);
        
        wordNodeDictionary.accept("ab");
        wordNodeDictionary.accept("ac");
        wordNodeDictionary.accept("bc");
        
        assertTrue("ab node should have common parent with ac node",
                wordNodeDictionary.getWordNode("ab").getParentNode().get().equals(
                        wordNodeDictionary.getWordNode("ac").getParentNode().get()));
        assertTrue("ab node should have common ancestor with bc node",
                wordNodeDictionary.getWordNode("ab").getParentNode().get().getParentNode().get().equals(
                        wordNodeDictionary.getWordNode("bc").getParentNode().get().getParentNode().get()));
    }
    
    @Test
    public void shouldResetVisitedStateOfAllNodes() {
        WordNodeDictionary wordNodeDictionary = new FixedWordLengthWordNodeDictionary(5);
        
        wordNodeDictionary.accept("stone");
        wordNodeDictionary.getWordNode("stone").setVisited(true);
        wordNodeDictionary.accept("water");
        wordNodeDictionary.getWordNode("water").setVisited(true);
        wordNodeDictionary.resetState();
        assertFalse("stone node should have unvisited state", wordNodeDictionary.getWordNode("stone").isVisited());
        assertFalse("water node should have unvisited state", wordNodeDictionary.getWordNode("water").isVisited());
    }
    
}
