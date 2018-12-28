package simulation.language;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.HashSet;

/**
 * A class representing words in computer models of language evolution.
 * In this implementation, words are identified and distinguished only with integer IDs.
 */
@Getter @EqualsAndHashCode @ToString
public class Word implements Comparable<Word>{

    private final int id;

    /**
     * Initializes this word with the given id.
     * @param id an initial id for this word
     */
    public Word(int id){
        this.id = id;
    }

    /**
     * Returns a collection of all languages from the given collection of languages which contain this word.
     * @param languages a collection of languages to be checked for containing this word
     * @return a collection of all languages from the given collection of languages which contain this word
     */
    public Collection<Language> languages(Collection<Language> languages){
        Collection<Language> result = new HashSet<>();
        for(Language language : languages)
            if(language.contains(this))
                result.add(language);
        return result;
    }

    /**
     * Compares this word with the given word in accordance with their ID.
     * This method is equivalent to returning {@code Integer.compare(this.getId(), o.getId()}.
     * @param o the thing to be compared
     * @return Integer.compare(this.getId(), o.getId()
     */
    @Override
    public int compareTo(Word o) {
        return Integer.compare(id, o.getId());
    }

}
