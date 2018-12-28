package simulation.lexicon;

import simulation.environment.Thing;
import simulation.language.Word;
import simulation.language.Language;

import java.util.Collection;
import java.util.Map;

/**
 * An interface of lexicons in which each thing-word association is given a double-valued weight.
 * @see LimitedWeightsLexicon
 * @see simulation.agent.WeightedLexiconAgent
 * @see simulation.agent.SuccessCountingAgent
 */
public interface WeightedLexicon extends Lexicon {

    /**
     * Adds the association between the given thing and the given word with the given weight to this lexicon.
     * @param thing a thing to be added in association with the given word to this lexicon
     * @param word a word to be added in association with the given thing to this lexicon
     * @param weight a weight of the added association
     */
    void add(Thing thing, Word word, double weight);

    /**
     * Returns the weight of the association between the given thing and the given word in this lexicon.
     * @param thing a thing to be checked for the weight of association with the given word
     * @param word a word to be checked for the weight of association with the given thing
     * @return the weight of the association between the given thing and the given word in this lexicon.
     */
    double weight(Thing thing, Word word);

    /**
     * Increases the weight of the association between the given thing and the given word by the value of {@code by}.
     * @param thing a thing from the association of which the weight should be increased
     * @param word a word from the association of which the weight should be increased
     * @param by value to increase the weight of the association between the given thing and the given word
     */
    void increaseWeight(Thing thing, Word word, double by);

    /**
     * Decreases the weight of all associations of the given thing except for the association with the given word by the valu of {@code by}.
     * @param thing a thing of which associations should be included by this method
     * @param word a word from the association of which the weight should not be decreased
     * @param by value to decrease the weights of all associations of the given thing except for the association with the given word
     */
    void decreaseOtherWeights(Thing thing, Word word, double by);

    /**
     * Decreases the weight of the association between the given thing and the given word by the value of {@code by}.
     * @param thing a thing from the association of which the weight should be decreased
     * @param word a word from the association of which the weight should be decreased
     * @param by value to decrease the weight of the association between the given thing and the given word
     */
    void decreaseWeight(Thing thing, Word word, double by);

    /**
     * Returns a map from the given collection of languages to numbers such that each language
     * is mapped to sum of weights of words from this lexicon belonging to this language.
     * @param languages a collection of languages to be a domain of returned map
     * @return a map from the given collection of languages to numbers such that each language
     * is mapped to sum of weights of words from this lexicon belonging to this language.
     */
    Map<Language, Double> countWeightSums(Collection<Language> languages);
}
