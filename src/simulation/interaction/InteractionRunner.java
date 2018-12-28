package simulation.interaction;

import simulation.agent.Listener;
import simulation.agent.Speaker;
import simulation.environment.Thing;

/**
 * An interface for classes which define flows of naming game interactions.
 * @see Speaker
 * @see Listener
 */
public interface InteractionRunner {

    /**
     * Runs an interaction between the given speaker and the given listener.
     * @param speaker a speaker in the interaction
     * @param listener a listener in the interaction
     */
    void run(Speaker speaker, Listener listener);

    /**
     * Runs an interaction between the given speaker and the given listener concerning the given thing.
     * @param speaker a speaker in the interaction
     * @param listener a listener in the interaction
     * @param thing a thing to be the interaction's topic
     */
    void run(Speaker speaker, Listener listener, Thing thing);

}
