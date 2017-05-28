package org.ciencialabart.wordchainresolver.node;

import java.util.function.Consumer;

public interface WordNodeDictionary extends Consumer<String> {

    WordNode getWordNode(String word);

    void resetState();

}
