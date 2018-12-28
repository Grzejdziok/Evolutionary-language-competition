package simulation.population;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import simulation.agent.Agent;

import java.util.Random;

/**
 * A class structuring agents in a square lattice-like graph described in "Evolutionary language competition - an agent-based model" paper.
 * <p>
 * In this kind of population, agents can be seen as though they were structured in a two dimensional array of dimensions {@code n x m}.
 * Let an agent {@code a} be in position {@code [i, j]} (i-th row and j-th column).
 * An edge connecting this agent with another agent {@code b} is of weight {@code 1.0 + epsilon}
 * if and only if {@code b}'s position is {@code [i+1, j]}, {@code [i-1, j]}, {@code [i, j+1]} or {@code [i, j-1]};
 * otherwise it is of weight {@code epsilon}.
 * In other words, an edge connecting any two neighbouring in the array agents is of weight {@code 1.0 + epsilon}, and of weight {@code epsilon} otherwise.
 * @see simulation.simulation.creator.ELCPaperSimulationCreator
 */
@EqualsAndHashCode @ToString
public class SquareLatticePopulation implements Population {

    private Agent[][] agents;
    private double epsilon;

    /**
     * Initializes this population with the given parameters.
     * @param agents an array of agents to be structured in square lattice
     * @param side the first dimension of this population's lattice, the second is calculated as {@code agents.length/side}
     * @param epsilon an additional weight added to weight of edge between any two agents
     */
    public SquareLatticePopulation(Agent[] agents, int side, double epsilon) {
        this.epsilon = epsilon;
        this.agents = new Agent[side][agents.length/side];

        for(int i=0;i<this.agents.length;i++)
            for(int j=0;j<this.agents[0].length;j++)
                this.agents[i][j] = agents[i * this.agents[0].length + j];

    }

    @Override
    public int getSize() {
        return agents.length * agents[0].length;
    }

    @Override
    public Agent randomAgent(){
        return agents[new Random().nextInt(agents.length)][new Random().nextInt(agents[0].length)];
    }

    @Override
    public Agent randomNeighbour(Agent agent) {
        int x, y;

        x = y = -1;
        for(int i=0;i<agents.length;i++)
            for(int j=0;j<agents[0].length;j++)
                if(agent == agents[i][j]){
                    x = i;
                    y = j;
                }

        return randomNeighbour(x, y);
    }

    @Override
    public Agent[] getAgentsArray() {
        Agent result[] = new Agent[getSize()];
        for(int i=0;i<agents.length;i++)
            for(int j=0;j<agents[0].length;j++)
                result[i*agents[0].length+j] = agents[i][j];
        return result;
    }

    private Agent randomNeighbour(int x, int y){
        if(x == 0 && y == 0) return upperLeftCornerRandomNeighbour();
        else if(x == 0 && y == agents[0].length-1) return upperRightCornerRandomNeighbour();
        else if(x == agents.length-1 && y == 0) return lowerLeftCornerRandomNeighbour();
        else if(x == agents.length-1 && y == agents[0].length-1) return lowerRightCornerRandomNeighbour();
        else if(x == 0) return upperRowRandomNeighbour(y);
        else if(x == agents.length-1) return lowerRowRandomNeighbour(y);
        else if(y == 0) return leftColumnRandomNeighbour(x);
        else if(y == agents[0].length-1) return rightColumnRandomNeighbour(x);
        else return insideRandomNeighbour(x, y);
    }

    private Agent insideRandomNeighbour(int x, int y) {
        double proba = (getSize()-1) * epsilon / (4.0 + (getSize()-1) * epsilon);
        if(new Random().nextDouble() < proba)
            return randomAgentWithout(x, y);
        else {
            int direction = new Random().nextInt(4);
            if(direction == 0)
                return agents[x+1][y];
            else if(direction == 1)
                return agents[x][y+1];
            else if(direction == 2)
                return agents[x-1][y];
            else
                return agents[x][y-1];
        }
    }

    private Agent rightColumnRandomNeighbour(int x) {
        double proba = (getSize()-1) * epsilon / (3.0 + (getSize()-1) * epsilon);
        if(new Random().nextDouble() < proba)
            return randomAgentWithout(x, agents[0].length-1);
        else {
            int direction = new Random().nextInt(3);
            if(direction == 0)
                return agents[x+1][agents[0].length-1];
            else if(direction == 1)
                return agents[x][agents[0].length-2];
            else
                return agents[x-1][agents[0].length-1];
        }
    }

    private Agent leftColumnRandomNeighbour(int x) {
        double proba = (getSize()-1) * epsilon / (3.0 + (getSize()-1) * epsilon);
        if(new Random().nextDouble() < proba)
            return randomAgentWithout(x, 0);
        else {
            int direction = new Random().nextInt(3);
            if(direction == 0)
                return agents[x+1][0];
            else if(direction == 1)
                return agents[x][1];
            else
                return agents[x-1][0];
        }
    }

    private Agent lowerRowRandomNeighbour(int y) {
        double proba = (getSize()-1) * epsilon / (3.0 + (getSize()-1) * epsilon);
        if(new Random().nextDouble() < proba)
            return randomAgentWithout(agents.length-1, y);
        else {
            int direction = new Random().nextInt(3);
            if(direction == 0)
                return agents[agents.length-1][y+1];
            else if(direction == 1)
                return agents[agents.length-2][y];
            else
                return agents[agents.length-1][y-1];
        }
    }

    private Agent upperRowRandomNeighbour(int y) {
        double proba = (getSize()-1) * epsilon / (3.0 + (getSize()-1) * epsilon);
        if(new Random().nextDouble() < proba)
            return randomAgentWithout(0, y);
        else {
            int direction = new Random().nextInt(3);
            if(direction == 0)
                return agents[0][y+1];
            else if(direction == 1)
                return agents[1][y];
            else
                return agents[0][y-1];
        }
    }

    private Agent lowerRightCornerRandomNeighbour() {
        double proba = (getSize()-1) * epsilon / (2.0 + (getSize()-1) * epsilon);
        if(new Random().nextDouble() < proba)
            return randomAgentWithout(agents.length-1, agents[0].length-1);
        else if(new Random().nextInt(2) == 0)
            return agents[agents.length-1][agents[0].length-2];
        else
            return agents[agents.length-2][agents[0].length-1];
    }

    private Agent lowerLeftCornerRandomNeighbour() {
        double proba = (getSize()-1) * epsilon / (2.0 + (getSize()-1) * epsilon);
        if(new Random().nextDouble() < proba)
            return randomAgentWithout(agents.length-1, 0);
        else if(new Random().nextInt(2) == 0)
            return agents[agents.length-1][1];
        else
            return agents[agents.length-2][0];
    }

    private Agent upperLeftCornerRandomNeighbour(){
        double proba = (getSize()-1) * epsilon / (2.0 + (getSize()-1) * epsilon);
        if(new Random().nextDouble() < proba)
            return randomAgentWithout(0, 0);
        else if(new Random().nextInt(2) == 0)
            return agents[0][1];
        else
            return agents[1][0];
    }

    private Agent upperRightCornerRandomNeighbour(){
        double proba = (getSize()-1) * epsilon / (2.0 + (getSize()-1) * epsilon);
        if(new Random().nextDouble() < proba)
            return randomAgentWithout(0, agents[0].length-1);
        else if(new Random().nextInt(2) == 0)
            return agents[0][agents[0].length-2];
        else
            return agents[1][agents[0].length-1];
    }

    private Agent randomAgentWithout(int i, int j) {
        Agent agent = randomAgent();
        while(agents[i][j] == agent)
            agent = randomAgent();
        return agent;
    }

    public Agent[][] getAgents() {
        return this.agents;
    }

    public double getEpsilon() {
        return this.epsilon;
    }
}
