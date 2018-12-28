package simulation.lexicon.creator;

import simulation.environment.Environment;
import simulation.language.Language;
import simulation.lexicon.Lexicon;

/**
 * An interface for factory classes for lexicons.
 * @see Lexicon
 * @see WeightedLexiconCreator
 * @see LimitedWeightsLexiconCreator
 */
public interface LexiconCreator {

    /**
     * Returns an empty lexicon.
     * @return an empty lexicon.
     */
    Lexicon create();

    /**
     * Returns a lexicon containing associations between {@code numOfAssociations} things from the given environment with random words from the given language.
     * @param language a language to generate words for the created lexicon
     * @param environment an environment containing things to be included in the created lexicon
     * @param numOfAssociations number of associations to be initially added to the created lexicon
     * @return a lexicon containing {@code numOfAssociations} associations of the things from the given environment with random words from the given language.
     */
    Lexicon create(Language language, Environment environment, int numOfAssociations);

}
