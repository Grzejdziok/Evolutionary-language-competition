package simulation.population;

import simulation.agent.Agent;

public interface Population {

    int getSize();

    Agent randomAgent();

    Agent randomNeighbour(Agent agent);

    Agent[] getAgentsArray();

}
