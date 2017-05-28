package org.ciencialabart.wordchainresolver;

import java.util.List;

import org.ciencialabart.wordchainresolver.context.WordChainResolvingContext;
import org.ciencialabart.wordchainresolver.context.factory.WordChainResolvingContextFactory;
import org.ciencialabart.wordchainresolver.context.factory.WordPrefixesManagingWordChainResolvingContextFactory;
import org.ciencialabart.wordchainresolver.input.UserWordChainBoundariesSupplier;
import org.ciencialabart.wordchainresolver.input.WordChainBoundaries;

public interface WordChainResolver {

    public static void main(String[] args) {
        WordChainResolvingContextFactory contextFactory = new WordPrefixesManagingWordChainResolvingContextFactory();
        WordChainResolvingContext context = contextFactory.createContext();
        
        WordChainBoundaries wordChainBoundaries = new UserWordChainBoundariesSupplier(context).get();
        
        context.createWordChainResolver().resolveFor(wordChainBoundaries)
        .stream().forEachOrdered(System.out::println);
    }

    List<String> resolveFor(WordChainBoundaries wordChainBoundaries);

}
