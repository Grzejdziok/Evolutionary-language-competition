package simulation.simulation.creator;

import com.rits.cloning.Cloner;
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
 * This class can be cunstructed only with provided builder class.
 * <p>
 * The following parameters can be defined for this kind of simulation:
 * <ul>
 *  <li> {@code side} defines the side of the agents' lattice graph</li>
 *  <li> {@code numOfAgents} defines the number of agents</li>
 *  <li> {@code numsOfUsers[]} defines the initial sizes of the 1-lingual and 2-lingual populations</li>
 *  <li> {@code numOfThings} defines the number of objects</li>
 *  <li> {@code devdegrees[]} defines the initial environment of the 1-lingual and 2-lingual populations</li>
 *  <li> {@code epsilon} defines the epsilon parameter</li>
 *  <li> {@code variant} defines the model variant as a {@code String} (available {@code "tl", "ts", "zero"})</li>
 *  <li> {@code variantInfluence} defines the variant influence </li>
 *  <li> {@code variantLanguage} defines the variant language </li>
 * </ul>
 * The {@code create()} method constructs and returns an appropriate simulation object.
 * <p>
 * For more details, please see the paper.
 * @see simulation.lexicon.LimitedWeightsLexicon
 * @see SuccessCountingAgent
 * @see ModuloLanguage
 * @see Environment
 * @see VariantAgent
 * @see Simulation
 * @see ELCPaperSimulationCreatorBuilder
 */
public class ELCPaperSimulationCreator implements SimulationCreator {

    private int numOfThings;
    private int[] devdegrees;
    private int side;
    private int numOfAgents;
    private int[] numsOfUsers;
    private double epsilon;
    private String variant;
    private int variantInfluence;
    private int variantLanguage;

    @java.beans.ConstructorProperties({"numOfThings", "devdegrees", "side", "numOfAgents", "numsOfUsers", "epsilon", "variant", "variantInfluence", "variantLanguage"})
    ELCPaperSimulationCreator(int numOfThings, int[] devdegrees, int side, int numOfAgents, int[] numsOfUsers, double epsilon, String variant, int variantInfluence, int variantLanguage) {
        this.numOfThings = numOfThings;
        this.devdegrees = devdegrees;
        this.side = side;
        this.numOfAgents = numOfAgents;
        this.numsOfUsers = numsOfUsers;
        this.epsilon = epsilon;
        this.variant = variant;
        this.variantInfluence = variantInfluence;
        this.variantLanguage = variantLanguage;
    }

    public static ELCPaperSimulationCreatorBuilder builder() {
        return new ELCPaperSimulationCreatorBuilder();
    }

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

    public static class ELCPaperSimulationCreatorBuilder {
        private int numOfThings;
        private int[] devdegrees;
        private int side;
        private int numOfAgents;
        private int[] numsOfUsers;
        private double epsilon;
        private String variant;
        private int variantInfluence;
        private int variantLanguage;

        ELCPaperSimulationCreatorBuilder() {
        }

        public ELCPaperSimulationCreatorBuilder numOfThings(int numOfThings) {
            this.numOfThings = numOfThings;
            return this;
        }

        public ELCPaperSimulationCreatorBuilder devdegrees(int[] devdegrees) {
            this.devdegrees = devdegrees;
            return this;
        }

        public ELCPaperSimulationCreatorBuilder side(int side) {
            this.side = side;
            return this;
        }

        public ELCPaperSimulationCreatorBuilder numOfAgents(int numOfAgents) {
            this.numOfAgents = numOfAgents;
            return this;
        }

        public ELCPaperSimulationCreatorBuilder numsOfUsers(int[] numsOfUsers) {
            this.numsOfUsers = numsOfUsers;
            return this;
        }

        public ELCPaperSimulationCreatorBuilder epsilon(double epsilon) {
            this.epsilon = epsilon;
            return this;
        }

        public ELCPaperSimulationCreatorBuilder variant(String variant) {
            this.variant = variant;
            return this;
        }

        public ELCPaperSimulationCreatorBuilder variantInfluence(int variantInfluence) {
            this.variantInfluence = variantInfluence;
            return this;
        }

        public ELCPaperSimulationCreatorBuilder variantLanguage(int variantLanguage) {
            this.variantLanguage = variantLanguage;
            return this;
        }

        public ELCPaperSimulationCreator build() {
            return new ELCPaperSimulationCreator(numOfThings, devdegrees, side, numOfAgents, numsOfUsers, epsilon, variant, variantInfluence, variantLanguage);
        }

        public String toString() {
            return "ELCPaperSimulationCreator.ELCPaperSimulationCreatorBuilder(numOfThings=" + this.numOfThings + ", devdegrees=" + Arrays.toString(this.devdegrees) + ", side=" + this.side + ", numOfAgents=" + this.numOfAgents + ", numsOfUsers=" + Arrays.toString(this.numsOfUsers) + ", epsilon=" + this.epsilon + ", variant=" + this.variant + ", variantInfluence=" + this.variantInfluence + ", variantLanguage=" + this.variantLanguage + ")";
        }
    }
}
