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

@Builder
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
