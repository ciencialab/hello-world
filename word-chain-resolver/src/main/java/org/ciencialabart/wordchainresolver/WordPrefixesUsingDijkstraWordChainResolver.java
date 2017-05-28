package org.ciencialabart.wordchainresolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.ciencialabart.wordchainresolver.context.WordChainResolvingContext;
import org.ciencialabart.wordchainresolver.input.WordChainBoundaries;
import org.ciencialabart.wordchainresolver.node.WordNode;
import org.ciencialabart.wordchainresolver.node.WordNodesPath;

public class WordPrefixesUsingDijkstraWordChainResolver implements WordChainResolver {

    private final WordChainResolvingContext context;
    private WordNode startingWordNode;
    private WordNode endingWordNode;
    private boolean isOrderReversed;

    public WordPrefixesUsingDijkstraWordChainResolver(WordChainResolvingContext context) {
        this.context = context;
    }

    @Override
    public List<String> resolveFor(WordChainBoundaries wordChainBoundaries) {
        List<WordNodesPath> paths;
        List<WordNodesPath> branchedPaths;
        
        fixWordChainBoundariesOrder(wordChainBoundaries);
        
        paths = startingWordNode.getPathsToNeighbours();
        
        startingWordNode.setVisited(true);
        setDestinationsVisited(paths);
        
        while (paths.size() > 0) {
            branchedPaths = new ArrayList<>();
            
            for (WordNodesPath path : paths) {
                if (path.getDestination().equals(endingWordNode)) {
                    return getOriginalOrderWordChain(path);
                }
                
                branchedPaths.addAll(getPathsProlongedToNextUnvisitedNodes(path));
            }
            
            paths = branchedPaths;
            setDestinationsVisited(paths);
        }
        
        throw new RuntimeException(new StringBuilder()
                .append("Words ")
                .append(wordChainBoundaries.getStartingWord())
                .append(" and ")
                .append(wordChainBoundaries.getEndingWord())
                .append(" are not connected.")
                .toString());
    }

    private void fixWordChainBoundariesOrder(WordChainBoundaries wordChainBoundaries) {
        List<String> wordChainBoundariesList = new ArrayList<>();
        String originalEndingWord;
        
        originalEndingWord = wordChainBoundaries.getEndingWord();
        
        wordChainBoundariesList.add(wordChainBoundaries.getStartingWord());
        wordChainBoundariesList.add(originalEndingWord);
        Collections.sort(wordChainBoundariesList);
        
        startingWordNode = context.getWordNode(wordChainBoundariesList.get(0));
        endingWordNode = context.getWordNode(wordChainBoundariesList.get(1));
        isOrderReversed = startingWordNode.getPrefix().equals(originalEndingWord);
    }

    private void setDestinationsVisited(List<WordNodesPath> paths) {
        paths.stream()
                .map(WordNodesPath::getDestination)
                .forEach(node -> node.setVisited(true));
    }

    private List<WordNodesPath> getPathsProlongedToNextUnvisitedNodes(WordNodesPath path) {
        return path.branchToDestinationNeighbours().stream()
                .filter(nodePath -> !nodePath.getDestination().isVisited())
                .collect(Collectors.toList());
    }

    private List<String> getOriginalOrderWordChain(WordNodesPath path) {
        List<String> wordChain = path.toWordChain();
        
        if (isOrderReversed) {
            Collections.reverse(wordChain);
        }
        
        return wordChain;
    }

}
