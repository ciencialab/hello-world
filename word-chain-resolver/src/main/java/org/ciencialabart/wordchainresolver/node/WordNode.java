package org.ciencialabart.wordchainresolver.node;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class WordNode extends WordPrefixNode implements WordNodesPathsToNeighboursSupplier {
    
    private boolean isVisited;

    public WordNode(String word) {
        super(word);
    }
    
    public static WordNode fromWordPrefixNode(final WordPrefixNode initialWordPrefixNode, final String word) {
        WordPrefixNode wordPrefixNode;
        int letterIndex;
        
        wordPrefixNode = initialWordPrefixNode;
        letterIndex = wordPrefixNode.getPrefix().length();
        while (letterIndex < word.length()) {
            try {
                wordPrefixNode = wordPrefixNode.getChildForLetter(word.charAt(letterIndex));
            } catch (NoSuchElementException e) {
                wordPrefixNode = new WordNode("");
            }
            
            letterIndex++;
        }
        
        return (WordNode) wordPrefixNode;
    }

    @Override
    public List<WordNodesPath> getPathsToNeighbours() {
        List<WordNodesPath> pathsToNeighbours = new ArrayList<>();
        Optional<WordPrefixNode> optionalAncestorNode;
        WordPrefixNode ancestorNode;
        WordPrefixNode ancestorNodeChildAboveThis;
        
        ancestorNodeChildAboveThis = this;
        optionalAncestorNode = getParentNode();
        
        while (optionalAncestorNode.isPresent()) {
            ancestorNode = optionalAncestorNode.get();
            
            pathsToNeighbours.addAll(ancestorNode
                    .getWordNodesPathsForChildNodesExcluding(ancestorNodeChildAboveThis, this));
            
            ancestorNodeChildAboveThis = ancestorNode;
            optionalAncestorNode = ancestorNodeChildAboveThis.getParentNode();
        }
            
        return pathsToNeighbours;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

}
