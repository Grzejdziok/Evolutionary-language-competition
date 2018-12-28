package simulation.agent.variant;

import simulation.agent.Agent;
import simulation.interaction.InteractionRunner;

/**
 * An interface for defining variant agents for computer models of language evolution.
 * Such agents can interact with other agents in accordance with specifically defined rules.
 * @see TotalSpeaker
 * @see TotalListener
 */
public interface VariantAgent extends Agent{

    /**
     * Runs interaction between this variant agent and the given agent in accordance with the given interaction runner.
     * @param a an agent to interact with this variant agent
     * @param interactionRunner an interaction runner defining the flow of the interaction
     */
    void interact(Agent a, InteractionRunner interactionRunner);

}
