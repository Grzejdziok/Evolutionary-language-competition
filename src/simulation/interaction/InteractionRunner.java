package simulation.interaction;

import simulation.agent.Listener;
import simulation.agent.Speaker;
import simulation.environment.Thing;

public interface InteractionRunner {

    void run(Speaker speaker, Listener listener);

    void run(Speaker speaker, Listener listener, Thing thing);

    int getCounter();

}
