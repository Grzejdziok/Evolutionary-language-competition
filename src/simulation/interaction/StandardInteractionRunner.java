package simulation.interaction;

import simulation.agent.Listener;
import simulation.agent.Speaker;
import simulation.environment.Thing;
import simulation.language.Word;

/**
 * A class defining the flow of interactions described in "Evolutionary language competition - an agent-based model" paper.
 * Given an interaction, its flow is as follows. First, the interaction's speaker signals a word denoting given thing (random thing if not given).
 * <ul>
 *     <li>If the interaction's listener recognizes this word for the given thing, {@code success} method is invoked for both the speaker and the listener.</li>
 *     <li>If the interaction's listener does not recognize this word for the given thing, {@code loss} method is invoked for the speaker,
 *     and {@code acquire} method is invoked for the listener </li>
 * </ul>
 * @see InteractionRunner
 * @see simulation.simulation.creator.ELCPaperSimulationCreator
 */
public class StandardInteractionRunner implements InteractionRunner {

    /**
     * Runs an interaction for a random thing recognized by the given speaker.
     * @param speaker a speaker in the interaction
     * @param listener a listener in the interaction
     */
    @Override
    public void run(Speaker speaker, Listener listener) {
        Thing thing = speaker.randomRecognizedThing();
        run(speaker, listener, thing);
    }

    /**
     * Runs an interaction between the given speaker and the given listener regarding the given thing.
     * Its flow is as follows. First, the speaker signals a word denoting the given thing.
     * <ul>
     *     <li>If the listener recognizes this word for the given thing, {@code success} method is invoked for both the speaker and the listener.</li>
     *     <li>If the listener does not recognize this word for the given thing, {@code loss} method is invoked for the speaker,
     *     and {@code acquire} method is invoked for the listener </li>
     * </ul>
     * @param speaker a speaker in the interaction
     * @param listener a listener in the interaction
     * @param thing a thing to be the interaction's topic
     */
    @Override
    public void run(Speaker speaker, Listener listener, Thing thing) {
        Word word = speaker.signal(thing);
        if(listener.recognized(thing, word)){
            speaker.success(thing, word);
            listener.success(thing, word);
        }
        else{
            speaker.loss(thing, word);
            listener.acquire(thing, word);
        }
    }
}
