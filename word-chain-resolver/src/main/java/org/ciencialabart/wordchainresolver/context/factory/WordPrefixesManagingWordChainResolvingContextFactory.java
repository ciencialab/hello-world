package org.ciencialabart.wordchainresolver.context.factory;

import org.ciencialabart.wordchainresolver.context.WordChainResolvingContext;
import org.ciencialabart.wordchainresolver.context.WordPrefixesManagingWordChainResolvingContext;

public class WordPrefixesManagingWordChainResolvingContextFactory implements WordChainResolvingContextFactory {

    public WordChainResolvingContext createContext() {
        return new WordPrefixesManagingWordChainResolvingContext();
    }

}
