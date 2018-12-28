package simulation.simulation.creator;

import com.rits.cloning.Cloner;
import lombok.Builder;
import simulation.agent.Agent;
import simulation.agent.SuccessCountingAgent;
import simulation.agent.variant.TotalListener;
import simulation.agent.variant.TotalSpeaker;
import simulation.agent.variant.VariantAgent;
import simulation.environment.Environment;
import simulation.environment.EnvironmentCreator;
import simulation.interaction.InteractionRunner;
import simulation.interaction.StandardInteractionRunner;
import simulation.language.Language;
import simulation.language.ModuloLanguage;
import simulation.lexicon.WeightedLexicon;
import simulation.lexicon.creator.LimitedWeightsLexiconCreator;
import simulation.population.Population;
import simulation.population.SquareLatticePopulation;
import simulation.simulation.Simulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * A factory class for creating simulations of computer model described in "Evolutionary language competition - an agent-based model" paper.
 * <p>
 * This class can be cunstructed with provided builder class.
 * <p>
 * The following parameters can be defined for this kind of simulation:
 * <ul>
 *  <li> {@code side) defines the side of the agents' lattice graph (default: {@code 4}})</li>
 *  <li> {@code numOfAgents} defines the number of agents (default: {@code side * side})</li>
 *  <li> {@code numsOfUsers[]} defines the initial sizes of the 1-lingual and 2-lingual populations (default: {@code {numOfAgents/2, numOfAgents/2}}) </li>
 *  <li> {@code numOfThings} defines the number of objects (default: {@code 5})</li>
 *  <li> {@code devdegrees[]} defines the initial environment of the 1-lingual and 2-lingual populations (default: {@code {numOfThings, numOfThings}})</li>
 *  <li> {@code epsilon} defines the epsilon parameter (default: {@code 0.05})</li>
 *  <li> {@code variant} defines the model variant as a {@code String} (default: {@code "zero"})</li>
 *  <li> {@code variantInfluence} defines the variant influence (default: {@code 0}) </li>
 *  <li> {@code variantLanguage} defines the variant language (default: {@code 0})</li>
 * </ul>
 * The {@code create()} method constructs and returns an appropriate simulation object.
 * <p>
 * For more details, please see the paper.
 */
@Builder
public class ELCPaperSimulationCreator implements SimulationCreator {

    private int numOfThings = 5;
    private int[] devdegrees = {numOfThings, numOfThings};
    private int side = 4;
    private int numOfAgents = side * side;
    private int[] numsOfUsers = {numOfAgents/2, numOfAgents/2};
    private double epsilon = 0.05;
    private String variant = "zero";
    private int variantInfluence = 0;
    private int variantLanguage = 0;

    /**
     * Returns a {@code Simulation} object created in accordance with this factory's parameters.
     * @return a {@code Simulation} object created in accordance with this factory's parameters.
     */
    @Override
    public Simulation create() {
        Environment environment = createEnvironment(numOfThings);

        Language[] languages = createLanguages();
        WeightedLexicon[] lexicons = createLexicons(environment, languages, devdegrees);

        Agent[] agents = createAgents(numOfAgents, numsOfUsers, lexicons, languages);
        Population population = createPopulation(agents, side, epsilon);

        InteractionRunner interactionRunner = new StandardInteractionRunner();

        VariantAgent variantAgent = createVariantAgent(variant, variantLanguage, lexicons);

        return new Simulation(
                environment,
                Arrays.asList(languages),
                population,
                interactionRunner,
                variantAgent,
                variantInfluence);
    }

    private VariantAgent createVariantAgent(String variant, int variantLanguage, WeightedLexicon[] lexicons) {
        switch(variant){
            case "tl":
                return new TotalListener();
            case "ts":
                return new TotalSpeaker(new Cloner().deepClone(lexicons[variantLanguage]));
        }
        return null;
    }

    private Population createPopulation(Agent[] agents, int side, double epsilon) {
        return new SquareLatticePopulation(agents, side, epsilon);
    }

    private Agent[] createAgents(int numOfAgents, int[] numsOfUsers, WeightedLexicon[] lexicons, Language[] languages) {
        Agent[] agents = new Agent[numOfAgents];

        Collection<Language> languageCollection = new ArrayList<>(Arrays.asList(languages));
        Cloner cloner = new Cloner();
        for(int i=0;i<numsOfUsers[0];i++)
            agents[i] = new SuccessCountingAgent(languageCollection, cloner.deepClone(lexicons[0]), 1.0, 0.3);

        for(int i=numsOfUsers[0]; i<numOfAgents;i++)
            agents[i] = new SuccessCountingAgent(languageCollection, cloner.deepClone(lexicons[1]), 1.0, 0.3);

        return agents;
    }

    private WeightedLexicon[] createLexicons(Environment environment, Language[] languages, int[] devdegrees) {
        WeightedLexicon[] lexicons = new WeightedLexicon[2];
        lexicons[0] = new LimitedWeightsLexiconCreator(0.0, 3.0).create(languages[0], environment, devdegrees[0], 3.0);
        lexicons[1] = new LimitedWeightsLexiconCreator(0.0, 3.0).create(languages[1], environment, devdegrees[1], 3.0);
        return lexicons;
    }

    private Environment createEnvironment(int numOfThings){
        return new EnvironmentCreator(numOfThings).create();
    }

    private Language[] createLanguages(){
        Language[] languages = new Language[2];
        languages[0] = new ModuloLanguage(2,0);
        languages[1] = new ModuloLanguage(2,1);
        return languages;
    }

}
