package org.ciencialabart.wordchainresolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.ciencialabart.wordchainresolver.context.WordChainResolvingContext;
import org.ciencialabart.wordchainresolver.context.factory.WordChainResolvingContextFactory;
import org.ciencialabart.wordchainresolver.context.factory.WordPrefixesManagingWordChainResolvingContextFactory;
import org.ciencialabart.wordchainresolver.input.AgainstDictionaryWordValidator;
import org.ciencialabart.wordchainresolver.input.WordChainBoundaries;
import org.ciencialabart.wordchainresolver.testutil.NanoClock;
import org.ciencialabart.wordchainresolver.testutil.SingleLetterWordsDifferenceValidator;
import org.junit.Before;
import org.junit.Test;

public class WordChainResolverTest {

    private WordChainResolvingContext context;

    @Before
    public void initializeApplication() {
        WordChainResolvingContextFactory contextFactory = new WordPrefixesManagingWordChainResolvingContextFactory();
        context = contextFactory.createContext();
    }
    
    @Test
    public void shouldBuildCorrectChainOfWordsWithGivenBoundaries() {
        WordChainBoundaries wordChainBoundaries = new WordChainBoundaries("cat", "dog");
        List<String> result = context.createWordChainResolver().resolveFor(wordChainBoundaries);
        String previousWord;
        boolean allWordsDiffersFromPrevious1By1Letter;
        
        assertTrue("Chain of words built should be minimum 2 words long", result.size() >= 2);
        assertEquals("Chain of words built should start with cat", "cat", result.get(0));
        assertEquals("Chain of words built should end with dog", "dog", result.get(result.size() - 1));
        assertTrue("Every inner entry in the chain should be 3 characters long",
                result.subList(1, result.size() - 1).stream()
                    .allMatch(word -> word.length() == 3));
        assertTrue("Every inner entry in the chain should be dictionary word",
                result.subList(1, result.size() - 1).stream()
                    .allMatch(word -> 
                        new AgainstDictionaryWordValidator(context.getDictionary(), word)
                            .isValid()));
        
        previousWord = result.get(0);
        allWordsDiffersFromPrevious1By1Letter = true;
        for (String word : result.subList(1, result.size())) {
            if (!new SingleLetterWordsDifferenceValidator(previousWord, word).isValid()) {
                allWordsDiffersFromPrevious1By1Letter = false;
                break;
            }
            
            previousWord = word;
        }
        assertTrue("Every entry but first in the chain should differ from previous word by one letter",
                allWordsDiffersFromPrevious1By1Letter);
    }
    
    @Test
    public void shouldReturnTheShortestPossibleCorrectWordChain() {
        WordChainBoundaries wordChainBoundaries = new WordChainBoundaries("lead", "gold");
        List<String> result = context.createWordChainResolver().resolveFor(wordChainBoundaries);
        List<String> expectedResult = Arrays.asList(new String[] {"lead", "load", "goad", "gold"});
        
        assertEquals("Chain of words built should be the shortest correct one", expectedResult, result);
    }
    
    @Test
    public void shouldReturnTheShortestPossibleCorrectWordChainForSwitchedOrder() {
        WordChainBoundaries wordChainBoundaries = new WordChainBoundaries("gold", "lead");
        List<String> result = context.createWordChainResolver().resolveFor(wordChainBoundaries);
        List<String> expectedResult = Arrays.asList(new String[] {"gold", "goad", "load", "lead"});
        
        assertEquals("Chain of words built for switched order should be the shortest correct one",
                expectedResult, result);
    }
    
    @Test(timeout=1000)
    public void shouldReturnTheShortestPossibleCorrectWordChainInLessThenSecond() {
        shouldReturnTheShortestPossibleCorrectWordChain();
    }
    
    @Test
    public void shouldCompletionTimeBeTheSameForNormalAndSwitchedArgumentsOrder() {
        Clock clock = new NanoClock();
        Instant normalOrderStartInstant;
        Instant normalOrderEndInstant;
        Instant switchedOrderStartInstant;
        Instant switchedOrderEndInstant;
        Duration normalOrderDuration;
        Duration switchedOrderDuration;
        
        normalOrderStartInstant = clock.instant();
        shouldReturnTheShortestPossibleCorrectWordChain();
        normalOrderEndInstant = clock.instant();
        context.resetState();
        switchedOrderStartInstant = clock.instant();
        shouldReturnTheShortestPossibleCorrectWordChainForSwitchedOrder();
        switchedOrderEndInstant = clock.instant();
        
        normalOrderDuration = Duration.between(normalOrderStartInstant, normalOrderEndInstant);
        switchedOrderDuration = Duration.between(switchedOrderStartInstant, switchedOrderEndInstant);
        System.out.println("Normal order duration: " + normalOrderDuration);
        System.out.println("Switched order duration: " + switchedOrderDuration);
        assertTrue("Completion time should be nearly the same for normal and switched arguments order",
                normalOrderDuration.minus(switchedOrderDuration).abs().toMillis() < 100);
    }
    
}
