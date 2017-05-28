package org.ciencialabart.wordchainresolver.node;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WordNodesPath {

    private final List<WordNode> path = new ArrayList<>();
    private int lastPassLetterIndex;
    
    public WordNodesPath(WordNode sourceWordNode, WordNode destinationWordNode, int thisPassLetterIndex) {
        path.add(sourceWordNode);
        path.add(destinationWordNode);
        this.lastPassLetterIndex = thisPassLetterIndex;
    }

    public WordNodesPath(WordNodesPath sourceWordNodesPath, WordNode destinationWordNode, int lastPassLetterIndex) {
        path.addAll(sourceWordNodesPath.path);
        path.add(destinationWordNode);
        this.lastPassLetterIndex = lastPassLetterIndex;
    }

    public WordNode getDestination() {
        return path.get(path.size() - 1);
    }

    public List<String> toWordChain() {
        return path.stream()
                .map(wordNode -> wordNode.getPrefix())
                .collect(Collectors.toList());
    }

    public List<? extends WordNodesPath> branchToDestinationNeighbours() {
        return path.get(path.size() - 1)
                .getPathsToNeighbours().stream()
                .filter(pathToNeighbour -> pathToNeighbour.getLastPassLetterIndex() != getLastPassLetterIndex())
                .map(pathToNeighbour -> new WordNodesPath(
                        this, pathToNeighbour.getDestination(), pathToNeighbour.getLastPassLetterIndex()))
                .collect(Collectors.toList());
    }
    
    public int getLastPassLetterIndex() {
        return lastPassLetterIndex;
    }
    
}
