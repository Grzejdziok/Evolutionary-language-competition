package simulation.simulation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import simulation.agent.Agent;
import simulation.agent.WeightedLexiconAgent;
import simulation.agent.variant.VariantAgent;
import simulation.interaction.InteractionRunner;
import simulation.language.Language;
import simulation.environment.Thing;
import simulation.population.Population;
import simulation.environment.Environment;

import java.util.Collection;
import java.util.Map;

/**
 * A class representing the process of simulation of computer model of language competition.
 * @see SimulationConductor
 * @see simulation.simulation.creator.SimulationCreator
 * @see Environment
 * @see Language
 * @see Population
 * @see Agent
 * @see InteractionRunner
 * @see VariantAgent
 */
@Getter @EqualsAndHashCode @ToString
public class Simulation {

    private Environment environment;
    private Collection<Language> languages;
    private Population population;
    private InteractionRunner interactionRunner;
    private VariantAgent variantAgent;
    private int variantInfluence;

    private int numOfIterations = 0;
    private int numOfInteractions = 0;

    /**
     * Initializes this simulation with the given arguments.
     * @param environment the initial environment for this simulation
     * @param languages the collection of languages competing in this simulation
     * @param population the population of agents interacting in this simulation
     * @param interactionRunner the interaction runner for interactions in this simulation
     * @param variantAgent a variant agent for this simulation
     * @param variantInfluence number of interactions of the given variant agents in each simulation's step
     */
    public Simulation(
            Environment environment,
            Collection<Language> languages,
            Population population,
            InteractionRunner interactionRunner,
            VariantAgent variantAgent,
            int variantInfluence
    ) {
        this.environment = environment;
        this.languages = languages;
        this.population = population;
        this.interactionRunner = interactionRunner;
        this.variantAgent = variantAgent;
        this.variantInfluence = variantInfluence;
    }

    /**
     * Simulates a number of interactions within the population of agents equal to the size of this population.
     * If a variant agent is specified, it simulates {@code variantInfluence} interactions with the variant agent before
     * the interactions between agents.
     */
    public void simulateOneStep(){
        numOfIterations++;

        for(int i=0; i < variantInfluence;i++) {
            variantAgent.interact(population.randomAgent(), interactionRunner);
            numOfInteractions += 1;
        }

        for(int i = 0; i < population.getSize(); i++) {
            randomInteraction();
            numOfInteractions += 1;
        }

    }

    private void randomInteraction(){
        Agent speaker = population.randomAgent();
        Agent listener = population.randomNeighbour(speaker);
        Thing thing = environment.randomThing();
        interactionRunner.run(speaker, listener, thing);
    }

    /**
     * Returns {@code true} if all agents from this simulation's population are pair-wise lexically synchronized on this simulation's environment.
     * @return {@code true} if all agents from this simulation's population are pair-wise lexically synchronized on this simulation's environment; {@code false} otherwise.
     */
    public boolean agentsLexicallySynchronized(){ return Agent.lexicallySynchronized(population.getAgentsArray(), environment); }

    /**
     * Returns {@code true} if all agents from the population in this simulation are evaluated to {@code true} with oneToOneLexicon() method.
     * @see Agent
     * @return {@code true} if all agents from the population in this simulation are evaluated to {@code true} with oneToOneLexicon() method.
     */
    public boolean oneToOneLexicons() {
        return Agent.oneToOneLexicons(population.getAgentsArray());
    }

    /**
     * Returns an array containing all agents from the population in this simulation.
     * @return an array containing all agents from the population in this simulation.
     */
    public Agent[] getAgentsArray() {
        return population.getAgentsArray();
    }

    /**
     * Returns a map from this simulation's collection of languages to numbers such that each language
     * is mapped to sum of weights of words from the lexicons of the agents from this simulation's population, beloning to this language.
     * @return a map from the simulation's collection of languages to numbers such that each language
     * is mapped to sum of weights of words from the lexicons of the agents from this simulation's population, beloning to this language.
     */
    public Map<Language, Double> getWeightSums(){
        return WeightedLexiconAgent.countWeightSums(languages, population.getAgentsArray());
    }

    /**
     * Returns a map from this simulation's collection of languages to integers such that each language is mapped to
     * the number of agents from this simulation's population for which this language is dominating.
     * @return a map from this simulation's collection of languages to integers such that each language is mapped to
     * the number of agents from this simulation's population for which this language is dominating.
     */
    public Map<Language, Integer> getNumsOfUsers(){
        return Agent.countUsers(languages, population.getAgentsArray());
    }

    /**
     * Returns a map from this simulation's collection of languages to integers such that each language is mapped to
     * the number of things recognized by any agent from this simulation's population of agents in association with a word belonging to this language.
     * @return a map from this simulation's collection of languages to integers such that each language is mapped to
     * the number of things recognized by any agent from this simulation's population of agents in association with a word belonging to this language.
     */
    public Map<Language, Integer> getNumsOfRecognizedThings(){
        return Agent.countRecognizedThings(getLanguages(), population.getAgentsArray());
    }

}
