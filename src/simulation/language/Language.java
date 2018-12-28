package simulation.language;

/**
 * An interface providing methods for different kinds of language classes which can be used in computer models of language competition.
 * @see ModuloLanguage
 * @see simulation.lexicon.Lexicon
 * @see simulation.agent.Agent
 */
public interface Language {

    /**
     * Returns {@code true} if this language contains the given word.
     * @param word a word to be checked for containment
     * @return {@code true} if this language contains the given word; {@code false} otherwise.
     */
    boolean contains(Word word);

    /**
     * Returns any word belonging to this language.
     * @return any word belonging to this language
     */
    Word generateWord();

}
