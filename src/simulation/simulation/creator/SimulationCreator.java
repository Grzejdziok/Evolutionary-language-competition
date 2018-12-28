package simulation.simulation.creator;

import simulation.simulation.Simulation;

/**
 * An interface for factory classes for different kinds of simulations.
 * @see ELCPaperSimulationCreator
 */
public interface SimulationCreator {

    /**
     * Returns a {@code Simulation} object defined by this factory.
     * @return a {@code Simulation} object defined by this factory
     */
    Simulation create();

}
