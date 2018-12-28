package simulation.agent;

import simulation.language.Language;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * An interface providing all methods expected to be defined for agents with weighted lexicons.
 * @see Agent
 * @see simulation.lexicon.WeightedLexicon
 */
public interface WeightedLexiconAgent extends Agent {

    /**
     * Returns a map from the given collection of languages to numbers such that each language
     * is mapped to sum of weights of words from this agent's lexicon belonging to this language.
     * @param languages a collection of languages to be a domain of returned map
     * @return a map from the given collection of languages to numbers such that each language
     * is mapped to sum of weights of words from this agent's lexicon belonging to this language.
     */
    Map<Language, Double> countWeightSums(Collection<Language> languages);

    /**
     * Returns a map from the given collection of languages to numbers such that each language
     * is mapped to sum of weights of words from the lexicons of the agents from the given array, beloning to this language.
     * @param languages a collection of languages to be a domain of returned map
     * @param agents an array of agents to be included in evaluation of the mappings to sums of weights
     * @return a map from the given collection of languages to numbers such that each language
     * is mapped to sum of weights of words from the lexicons of the agents from the given array, beloning to this language.
     */
    static Map<Language, Double> countWeightSums(Collection<Language> languages, Agent[] agents){

        WeightedLexiconAgent[] weightedLexiconAgents = new WeightedLexiconAgent[agents.length];
        for(int i=0;i<agents.length;i++){
            if (!(agents[i] instanceof WeightedLexiconAgent))
                throw new AssertionError("I cannot count weightsSums for getAgentsArray without weighted lexicons.");
            weightedLexiconAgents[i] = (WeightedLexiconAgent) agents[i];
        }

        Map<Language, Double> weights = new HashMap<>();

        for(Language language: languages)
            weights.put(language, 0.0);

        for(int i=0;i<agents.length;i++){
            Map<Language, Double> curAgentWeights = weightedLexiconAgents[i].countWeightSums(languages);
            for(Language language: languages)
                weights.replace(language, weights.get(language)+curAgentWeights.get(language));
        }

        return weights;
    }
}
