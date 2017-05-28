package org.ciencialabart.wordchainresolver.context;

import java.util.List;

import org.ciencialabart.wordchainresolver.WordChainResolver;
import org.ciencialabart.wordchainresolver.node.WordNode;

public interface WordChainResolvingContext {

    List<String> getDictionary();

    WordNode getWordNode(String word);

    WordChainResolver createWordChainResolver();

    void resetState();

}
