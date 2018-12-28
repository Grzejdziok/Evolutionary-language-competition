package simulation.population;

import simulation.agent.Agent;

/**
 * An interface for defining different of populations of agents which are structured in a weighted graph of some kind.
 * @see SquareLatticePopulation
 */
public interface Population {

    /**
     * Returns the number of agents in this population.
     * @return the number of agents in this population.
     */
    int getSize();

    /**
     * Returns a random agent from this population.
     * @return a random agent from this population
     */
    Agent randomAgent();

    /**
     * Returns a random agent neighbouring the given agent in this population.
     * @param agent an agent of which a neighbour shold returned.
     * @return a random agent neighbouring the given agent in this population.
     */
    Agent randomNeighbour(Agent agent);

    /**
     * Returns an array containing all the agents from this population.
     * @return an array containing all the agents from this population.
     */
    Agent[] getAgentsArray();

}
