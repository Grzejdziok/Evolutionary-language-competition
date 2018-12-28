package simulation.agent;

import simulation.environment.Thing;
import simulation.language.Word;

/**
 * An interface providing all methods expected to be defined for different kinds of listeners in naming games in computer models of language evolution.
 * @see Speaker
 * @see Agent
 * @see simulation.interaction.InteractionRunner
 */
public interface Listener {

    /**
     * Implements this listener's behaviour after a success in a naming-game based interaction.
     * @param thing a thing which was a topic of the interaction
     * @param word a word which was communicated of the interaction
     */
    void success(Thing thing, Word word);

    /**
     * Implements this listener's behaviour after a loss in a naming-game based interaction.
     * @param thing a thing which was a topic of the interaction
     * @param word a word which was communicated of the interaction
     */
    void loss(Thing thing, Word word);

    /**
     * Returns {@code true} if this listener recognizes the specified word in connection with the given thing.
     * @param thing A thing to be checked for recognition in connection with the specified word.
     * @param word A word to be checked for recognition in connection with the specified thing.
     * @return {@code true} if this listener recognizes the specified word in connection with the specified thing.
     */
    boolean recognized(Thing thing, Word word);

    /**
     * Implements this listener's acquisition of the given word in connection with the given thing.
     * @param thing A thing denoted by the specified word
     * @param word A word denoting the specified thing
     */
    void acquire(Thing thing, Word word);
}
