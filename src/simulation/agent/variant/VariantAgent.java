package simulation.agent.variant;

import simulation.agent.Agent;
import simulation.interaction.InteractionRunner;

public interface VariantAgent extends Agent{

    void interact(Agent a, InteractionRunner interactionRunner);

}
