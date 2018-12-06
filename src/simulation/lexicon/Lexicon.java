package simulation.lexicon;

import simulation.language.Language;
import simulation.environment.Thing;
import simulation.environment.Word;

import java.util.Collection;
import java.util.Map;

public interface Lexicon {
    
    void add(Thing thing, Word word);

    Collection<Word> words(Thing thing);

    Collection<Thing> things();

    Word signal(Thing thing);

    boolean contains(Thing thing);

    boolean contains(Thing thing, Word word);

    Map<Language, Integer> countDominatingWords(Collection<Language> languages);

    Map<Language, Collection<Thing>> recognizedThings(Collection<Language> languages);

    boolean oneToOne();
}
