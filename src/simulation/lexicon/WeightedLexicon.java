package simulation.lexicon;

import simulation.environment.Thing;
import simulation.environment.Word;
import simulation.language.Language;

import java.util.Collection;
import java.util.Map;

public interface WeightedLexicon extends Lexicon {

    void add(Thing thing, Word word, double weight);

    double weight(Thing thing, Word word);

    void increaseWeight(Thing thing, Word word, double by);

    void decreaseOtherWeights(Thing thing, Word word, double by);

    void decreaseWeight(Thing thing, Word word, double by);

    Map<Language, Double> countWeights(Collection<Language> languages);
}
