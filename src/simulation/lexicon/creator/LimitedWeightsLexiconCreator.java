package simulation.lexicon.creator;

import simulation.environment.Environment;
import simulation.environment.Thing;
import simulation.language.Language;
import simulation.lexicon.Lexicon;
import simulation.lexicon.LimitedWeightsLexicon;
import simulation.lexicon.WeightedLexicon;

import java.util.Collection;

public class LimitedWeightsLexiconCreator implements WeightedLexiconCreator {

    private double minWeight;
    private double maxWeight;

    public LimitedWeightsLexiconCreator(double minWeight, double maxWeight) {
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
    }

    @Override
    public WeightedLexicon create() {
        return new LimitedWeightsLexicon(minWeight, maxWeight);
    }

    @Override
    public WeightedLexicon create(Language language, Environment environment, int numOfWords) {
        WeightedLexicon lexicon = create();
        int i=0;
        for(Thing thing: environment.getThings()){
            lexicon.add(thing, language.generateWord());
            if(++i == numOfWords) break;
        }
        return lexicon;
    }

    @Override
    public WeightedLexicon create(Language language, Environment environment, int numOfWords, double weight) {
        WeightedLexicon lexicon = create();
        int i=0;
        for(Thing thing: environment.getThings()){
            lexicon.add(thing, language.generateWord(), weight);
            if(++i == numOfWords) break;
        }
        return lexicon;
    }
}
