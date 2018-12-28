package simulation.agent;

import simulation.environment.Thing;
import simulation.language.Word;

/**
 * An interface providing all methods expected to be defined for different kinds of speakers in naming games in computer models of language evolution.
 * @see Listener
 * @see Agent
 * @see simulation.interaction.InteractionRunner
 */
public interface Speaker {

    /**
     * Implements this speaker's behaviour after a success in a naming-game based interaction.
     * @param thing a thing which was a topic of the interaction
     * @param word a word which was communicated of the interaction
     */
    void success(Thing thing, Word word);

    /**
     * Implements this speaker's behaviour after a loss in a naming-game based interaction.
     * @param thing a thing which was a topic of the interaction
     * @param word a word which was communicated of the interaction
     */
    void loss(Thing thing, Word word);

    /**
     * Returns a {@code Word} object to be communicated by this speaker to denote the given thing.
     * @param thing a thing for which a word should be communicated
     * @return a word denoting the specified thing in this speaker's lexicon
     */
    Word signal(Thing thing);

    /**
     * Returns a random thing recognized by this speaker
     * @return a random thing recognized by this speaker
     */
    Thing randomRecognizedThing();

}
