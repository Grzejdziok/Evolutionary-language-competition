package simulation.language;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import simulation.environment.Word;

import java.util.*;

@Getter @EqualsAndHashCode @ToString
public class ModuloLanguage implements Language, Comparable<ModuloLanguage> {

    private final int equivalence;
    private final int congruency;

    public ModuloLanguage(int congruency, int equivalence){
        this.congruency = congruency;
        this.equivalence = equivalence % congruency;
    }

    @Override
    public boolean contains(Word word) {
        return (word.getId() - equivalence) % congruency == 0;
    }

    @Override
    public Word generateWord() {
        return new Word(new Random().nextInt(Integer.MAX_VALUE/(congruency + 1))*congruency + equivalence);
    }

    @Override
    public int compareTo(ModuloLanguage o) {
        if(congruency != o.getCongruency()) return Integer.compare(congruency, o.getCongruency());
        else return Integer.compare(equivalence, o.getEquivalence());
    }

}
