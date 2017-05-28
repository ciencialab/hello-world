package org.ciencialabart.wordchainresolver.input;

import java.util.function.Supplier;

public interface WordChainBoundariesSupplier extends Supplier<WordChainBoundaries>{
    
    WordChainBoundaries get();
    
}
