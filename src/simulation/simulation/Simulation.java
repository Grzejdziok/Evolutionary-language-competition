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

@Getter @EqualsAndHashCode @ToString
public class Simulation {

    private Environment environment;
    private Collection<Language> languages;
    private Population population;
    private InteractionRunner interactionRunner;
    private VariantAgent variantAgent;
    private int variantInfluence;

    private int numOfIterations = 0;

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

    public void simulateOneStep(){
        numOfIterations++;

        for(int i=0; i < variantInfluence;i++)
            variantAgent.interact(population.randomAgent(), interactionRunner);

        for(int i = 0; i < population.getSize(); i++)
            randomInteraction();

    }

    private void randomInteraction(){
        Agent speaker = population.randomAgent();
        Agent listener = population.randomNeighbour(speaker);
        Thing thing = environment.randomThing();
        interactionRunner.run(speaker, listener, thing);
    }

    public boolean agentsLexicallySynchronized(){ return Agent.lexicallySynchronized(population.getAgentsArray(), environment); }

    public boolean oneToOneLexicons() {
        return Agent.oneToOneLexicons(population.getAgentsArray());
    }

    public int getNumOfInteractions(){ return interactionRunner.getCounter(); }

    public Agent[] getAgentsArray() {
        return population.getAgentsArray();
    }

    public Map<Language, Double> getWeightSums(){
        return WeightedLexiconAgent.countWeightSums(languages, population.getAgentsArray());
    }

    public Map<Language, Integer> getNumsOfUsers(){
        return Agent.countUsers(languages, population.getAgentsArray());
    }

    public Map<Language, Integer> getNumsOfRecognizedThings(){
        return Agent.countRecognizedThings(getLanguages(), population.getAgentsArray());
    }

}
