package simulation.lexicon.creator;

import simulation.environment.Environment;
import simulation.language.Language;
import simulation.lexicon.WeightedLexicon;

/**
 * An interface for factory classes for weighted lexicons.
 * @see WeightedLexicon
 * @see LimitedWeightsLexiconCreator
 */
public interface WeightedLexiconCreator extends LexiconCreator {

    /**
     * Returns an empty lexicon.
     * @return an empty lexicon.
     */
    WeightedLexicon create();

    /**
     * Returns a lexicon containing associations between {@code numOfAssociations} things from the given environment with random words from the given language.
     * All associations are weighted with a default weight.
     * @param language a language to generate words for the created lexicon
     * @param environment an environment containing things to be included in the created lexicon
     * @param numOfAssociations number of associations to be initially added to the created lexicon
     * @return a lexicon containing {@code numOfAssociations} associations of the things from the given environment with random words from the given language.
     */
    WeightedLexicon create(Language language, Environment environment, int numOfAssociations);

    /**
     * Returns a lexicon containing associations between {@code numOfAssociations} things from the given environment with random words from the given language.
     * All associations are weighted with the given weight.
     * @param language a language to generate words for the created lexicon
     * @param environment an environment containing things to be included in the created lexicon
     * @param numOfAssociations number of associations to be initially added to the created lexicon
     * @param weight the initial weight for the associations in the created lexicon
     * @return a lexicon containing {@code numOfAssociations} associations of the things from the given environment with random words from the given language.
     */
    WeightedLexicon create(Language language, Environment environment, int numOfAssociations, double weight);
}
