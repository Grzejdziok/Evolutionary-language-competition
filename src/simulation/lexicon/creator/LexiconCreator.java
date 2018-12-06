package simulation.lexicon.creator;

import simulation.environment.Environment;
import simulation.language.Language;
import simulation.lexicon.Lexicon;

import java.util.Collection;

public interface LexiconCreator {

    Lexicon create();

    Lexicon create(Language language, Environment environment, int numOfWords);

}
