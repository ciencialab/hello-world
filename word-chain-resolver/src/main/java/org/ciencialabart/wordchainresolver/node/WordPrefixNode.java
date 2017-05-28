package org.ciencialabart.wordchainresolver.node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class WordPrefixNode implements Comparable<WordPrefixNode> {

    protected final String prefix;
    private final Character letter;
    private final List<WordPrefixNode> childNodes = new ArrayList<>();
    private WordPrefixNode parentNode;

    public WordPrefixNode(String prefix) {
        this.prefix = prefix;
        if (prefix.length() > 0) {
            this.letter = prefix.charAt(prefix.length() - 1);
        } else {
            this.letter = ' ';
        }
    }

    public WordPrefixNode(String prefix, WordPrefixNode childNode) {
        this(prefix);
        add(childNode);
    }
    
    public WordPrefixNode(char letter) {
        this.prefix = null;
        this.letter = letter;
    }

    public void add(WordPrefixNode childNode) {
        childNodes.add(childNode);
        childNode.setParentNode(this);
    }

    private void setParentNode(WordPrefixNode parentNode) {
        this.parentNode = parentNode;
    }

    public Optional<WordPrefixNode> getParentNode() {
        return Optional.ofNullable(parentNode);
    }

    public String getPrefix() {
        return prefix;
    }

    public char getLetter() {
        return letter;
    }

    public List<WordPrefixNode> getChildNodes() {
        return Collections.unmodifiableList(childNodes);
    }

    public WordPrefixNode getChildForLetter(char letter) {
        try {
            return childNodes.get(Collections.binarySearch(childNodes, new WordPrefixNode(letter)));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new NoSuchElementException(new StringBuilder()
                    .append("There is no dictionary word with ")
                    .append(prefix)
                    .append(letter)
                    .append(" prefix.")
                    .toString());
        }
    }

    @Override
    public int compareTo(WordPrefixNode other) {
        return letter.compareTo(other.letter);
    }

    protected Collection<? extends WordNodesPath> getWordNodesPathsForChildNodesExcluding(
            WordPrefixNode excludedChild, WordNode sourceWordNode) {
        String wordPattern = sourceWordNode.getPrefix();
        int thisPassLetterIndex = excludedChild.getPrefix().length();
        
        return getChildNodes().stream()
                .filter(wordPrefixNode -> !wordPrefixNode.equals(excludedChild))
                .map(wordPrefixNode -> WordNode.fromWordPrefixNode(wordPrefixNode, wordPattern))
                .filter(wordNode -> !wordNode.getPrefix().isEmpty())
                .map(wordNode -> new WordNodesPath(sourceWordNode, wordNode, thisPassLetterIndex))
                .collect(Collectors.toList());
    }

}
