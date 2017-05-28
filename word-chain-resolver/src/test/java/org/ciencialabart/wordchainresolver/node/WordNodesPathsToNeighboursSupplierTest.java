package org.ciencialabart.wordchainresolver.node;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class WordNodesPathsToNeighboursSupplierTest {
    
    @Test
    public void shouldReturnPathsToNeighbours() {
        WordNodeDictionary wordNodeDictionary = new FixedWordLengthWordNodeDictionary(2);
        List<WordNodesPath> pathsToNeighbours;
        
        wordNodeDictionary.accept("ab");
        wordNodeDictionary.accept("ac");
        wordNodeDictionary.accept("bc");
        pathsToNeighbours = wordNodeDictionary.getWordNode("ac").getPathsToNeighbours();
        
        assertTrue("Path returned should have ac caller node as source",
                pathsToNeighbours.get(0).toWordChain().get(0).equals("ac"));
        assertTrue("First path returned should have ab node as destination",
                pathsToNeighbours.get(0).getDestination().getPrefix().equals("ab"));
        assertTrue("Second path returned should have bc node as destination",
                pathsToNeighbours.get(1).getDestination().getPrefix().equals("bc"));
    }
    
}
