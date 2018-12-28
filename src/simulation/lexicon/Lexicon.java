package simulation.lexicon;

import simulation.environment.Thing;
import simulation.language.Language;
import simulation.language.Word;

import java.util.Collection;
import java.util.Map;

/**
 * An interface providing all methods expected from agents' lexicons in naming games-based computer models of language evolution.
 * In this implementation, lexicon can be seen as a set of thing-word associations.
 * @see LimitedWeightsLexicon
 * @see simulation.agent.Agent
 * @see simulation.agent.SuccessCountingAgent
 */
public interface Lexicon {

    /**
     * Adds the association between the given word and the given thing to this lexicon.
     * @param thing a thing to be added in association with the given word to this lexicon
     * @param word a word to be added in association with the given thing to this lexicon
     */
    void add(Thing thing, Word word);

    /**
     * Returns a collection of words containing all words denoting the given thing in this lexicon.
     * @param thing a thing to be checked for associated words
     * @return a collection of words containing all words denoting the given thing in this lexicon.
     */
    Collection<Word> words(Thing thing);

    /**
     * Returns a collection of things containing all things associated with any word in this lexicon.
     * @return a collection of things containing all things associated with any word in this lexicon.
     */
    Collection<Thing> things();

    /**
     * Returns the dominating word denoting the given thing in this lexicon.
     * @param thing a thing for which a word should be returned
     * @return the dominating word denoting the given thing in this lexicon.
     */
    Word signal(Thing thing);

    /**
     * Returns {@code true} if this lexicon contains the given thing associated with any word.
     * @param thing a thing to be checked for containment
     * @return {@code true} if this lexicon contains the given thing associated with any word; {@code false} otherwise.
     */
    boolean contains(Thing thing);

    /**
     * Returns {@code true} if this lexicon contains the given thing associated with the given word.
     * @param thing a thing to be checked for association with the given word
     * @param word a word to be checked for association with the given thing
     * @return {@code true} if this lexicon contains the given thing associated with the given word; {@code false} otherwise.
     */
    boolean contains(Thing thing, Word word);

    /**
     * Returns a map from the given collection of languages to integers such that each language
     * is mapped to the number of dominating words in this lexicon
     * @param languages a collection of languages to be a domain of returned map
     * @return a map between languages and numbers such that each language from the specified collection of languages
     * is mapped to the number of dominating words in this lexicon
     */
    Map<Language, Integer> countDominatingWords(Collection<Language> languages);

    /**
     * Returns a map from the given collection of languages to collections of things such that each language
     * is mapped to the collection of things recognized with the words belonging to it in this lexicon
     * @param languages a collection of languages to be a domain of returned map
     * @return a map between languages and numbers such that each language from the specified collection of languages
     * is mapped to the collection of things recognized with the words belonging to it in this lexicon
     */
    Map<Language, Collection<Thing>> recognizedThings(Collection<Language> languages);

    /**
     * Returns {@code true} if each thing contained in this lexicon is associated with only one word.
     * More formally, it returns {@code true} if this lexicon can be described as an injective function from the space of contained things to the space of words.
     * @return {@code true} if each thing contained in this lexicon is associated with only one word; {@code false} otherwise.
     */
    boolean oneToOne();
}
