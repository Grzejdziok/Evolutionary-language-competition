package simulation.agent;

import simulation.language.Language;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface WeightedLexiconAgent extends Agent {

    Map<Language, Double> countWeightSums(Collection<Language> languages);

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
