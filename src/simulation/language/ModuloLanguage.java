package simulation.language;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

/**
 * A class of languages based on modulo congruence relation.
 * A ModuloLanguage object has two parameters: {@code congruency} and {@code modulus}.
 * A word belongs to this language if its id is congruent modulo {@code modulus} to {@code congruency}.
 * @see simulation.simulation.creator.ELCPaperSimulationCreator
 * @see Language
 */
@Getter @EqualsAndHashCode @ToString
public class ModuloLanguage implements Language, Comparable<ModuloLanguage> {

    private final int congruency;
    private final int modulus;

    /**
     * Initializes this language with the given {@code modulus} and {@code congruency} parameters.
     * A word belongs to this language if its id is congruent modulo {@code modulus} to {@code congruency}.
     * @param modulus the initial {@code modulus} parameter for this language
     * @param congruency the initial {@code congruency} parameter for this language
     */
    public ModuloLanguage(int modulus, int congruency){
        this.modulus = modulus;
        this.congruency = congruency % modulus;
    }

    /**
     * Returns {@code true} if the given word's id is congruent to {@code congruency} modulo {@code modulus}.
     * @param word a word to be checked for containment
     * @return {@code true} if the given word's id is congruent to {@code congruency} modulo {@code modulus}; {@code} false otherwise.
     */
    @Override
    public boolean contains(Word word) {
        return (word.getId() - congruency) % modulus == 0;
    }

    /**
     * Returns a random word with id congruent to {@code congruency} modulo {@code modulus}.
     * @return a random word with id congruent to {@code congruency} modulo {@code modulus}.
     */
    @Override
    public Word generateWord() {
        return new Word(new Random().nextInt(Integer.MAX_VALUE/(modulus + 1))* modulus + congruency);
    }

    /**
     * Compares this language with the given {@code ModuloLanguage} object.
     * If the {@code modulus} parameters are not equal, returns {@code Integer.compare(this.getModulus(), o.getModulus())}.
     * Otherwise, returns {@code Integer.compare(this.getCongruency(), o.getCongruency())}.
     * @param o a language to be compared
     * @return if the {@code modulus} parameters are not equal, returns {@code Integer.compare(this.getModulus(), o.getModulus())};
     * otherwise, returns {@code Integer.compare(this.getCongruency(), o.getCongruency())}
     */
    @Override
    public int compareTo(ModuloLanguage o) {
        if(modulus != o.getModulus()) return Integer.compare(modulus, o.getModulus());
        else return Integer.compare(congruency, o.getCongruency());
    }

}
