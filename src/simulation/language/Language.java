package simulation.language;

import simulation.environment.Word;

public interface Language {

    boolean contains(Word word);

    Word generateWord();

    boolean equals(Object o);

    int hashCode();

}
