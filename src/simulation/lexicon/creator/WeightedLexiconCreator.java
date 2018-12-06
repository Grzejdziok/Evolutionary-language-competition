package simulation.lexicon.creator;

import simulation.environment.Environment;
import simulation.language.Language;
import simulation.lexicon.WeightedLexicon;

import java.util.Collection;

public interface WeightedLexiconCreator extends LexiconCreator {

    WeightedLexicon create();

    WeightedLexicon create(Language language, Environment environment, int numOfWords);

    WeightedLexicon create(Language language, Environment environment, int numOfWords, double weight);
}
