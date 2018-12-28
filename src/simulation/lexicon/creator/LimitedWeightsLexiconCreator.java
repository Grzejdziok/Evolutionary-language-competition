package simulation.lexicon.creator;

import simulation.environment.Environment;
import simulation.environment.Thing;
import simulation.language.Language;
import simulation.lexicon.LimitedWeightsLexicon;
import simulation.lexicon.WeightedLexicon;

/**
 * A factory class for creating {@code LimitedWeightsLexicon} objects conveniently.
 * @see LimitedWeightsLexicon
 * @see WeightedLexiconCreator
 * @see LexiconCreator
 */
public class LimitedWeightsLexiconCreator implements WeightedLexiconCreator {

    private double minWeight;
    private double maxWeight;

    /**
     * Initializes this factory with the given {@code minWeight} and the given {@code maxWeight}.
     * All lexicons created by this factory will be initialized with these parameters.
     * @param minWeight the lower bound of weights in lexicons created by this factory
     * @param maxWeight the upper bound of weights in lexicons created by this factory
     */
    public LimitedWeightsLexiconCreator(double minWeight, double maxWeight) {
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
    }

    @Override
    public WeightedLexicon create() {
        return new LimitedWeightsLexicon(minWeight, maxWeight);
    }

    @Override
    public WeightedLexicon create(Language language, Environment environment, int numOfAssociations) {
        WeightedLexicon lexicon = create();
        int i=0;
        for(Thing thing: environment.getThings()){
            lexicon.add(thing, language.generateWord());
            if(++i == numOfAssociations) break;
        }
        return lexicon;
    }

    @Override
    public WeightedLexicon create(Language language, Environment environment, int numOfAssociations, double weight) {
        WeightedLexicon lexicon = create();
        int i=0;
        for(Thing thing: environment.getThings()){
            lexicon.add(thing, language.generateWord(), weight);
            if(++i == numOfAssociations) break;
        }
        return lexicon;
    }
}
