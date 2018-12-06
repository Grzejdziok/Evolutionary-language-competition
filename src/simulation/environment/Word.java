package simulation.environment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import simulation.language.Language;

import java.util.Collection;
import java.util.HashSet;

@Getter @EqualsAndHashCode @ToString
public class Word implements Comparable<Word>{

    private final int id;

    public Word(int id){
        this.id = id;
    }

    public Collection<Language> languages(Collection<Language> languages){
        Collection<Language> result = new HashSet<>();
        for(Language language : languages)
            if(language.contains(this))
                result.add(language);
        return result;
    }

    @Override
    public int compareTo(Word o) {
        return Integer.compare(id, o.getId());
    }

}
