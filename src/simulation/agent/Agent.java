package simulation.agent;

import simulation.environment.Environment;
import simulation.language.Language;
import simulation.environment.Thing;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public interface Agent extends Speaker, Listener {

    boolean recognized(Thing thing);

    boolean oneToOneLexicon();

    Language dominatingLanguage(Collection<Language> languages);

    Map<Language, Integer> countDominatingWords(Collection<Language> languages);

    Map<Language, Collection<Thing>> recognizedThings(Collection<Language> languages);

    static boolean lexicallySynchronized(Agent[] agents, Environment environment){
        for(int i = 0; i < agents.length; i++){
            for(int j = i+1; j< agents.length; j++){
                if(!lexicallySynchronized(agents[i], agents[j], environment)) return false;
            }
        }
        return true;
    }

    static boolean lexicallySynchronized(Agent a, Agent b, Environment environment){
        for(Thing thing: environment.getThings()){
            if(!(a.recognized(thing) && b.recognized(thing))) return false;
            if(!a.signal(thing).equals(b.signal(thing))) return false;
        }
        return true;
    }

    static Map<Language, Integer> countUsers(Collection<Language> languages, Agent[] agents){;
        Map<Language, Integer> users = new HashMap<>();

        for(Language language: languages)
            users.put(language, 0);

        for (Agent agent : agents) {
            Language curAgentDomLanguage = agent.dominatingLanguage(languages);
            users.replace(curAgentDomLanguage, users.get(curAgentDomLanguage) + 1);
        }

        return users;
    }

    static Map<Language, Integer> countRecognizedThings(Collection<Language> languages, Agent[] agents){
        Map<Language, Collection<Thing>> languageThings = new HashMap<>();

        for(Language language: languages)
            languageThings.put(language, new HashSet<>());

        for (Agent agent : agents) {
            Map<Language, Collection<Thing>> curAgentThingsSet = agent.recognizedThings(languages);
            for (Language language : languages)
                languageThings.get(language).addAll(curAgentThingsSet.get(language));
        }

        Map<Language, Integer> developmentDegrees = new HashMap<>();

        for(Language language: languages)
            developmentDegrees.put(language, languageThings.get(language).size());

        return developmentDegrees;
    }

    static boolean oneToOneLexicons(Agent[] agents) {
        for(Agent agent: agents)
            if(!agent.oneToOneLexicon()) return false;
        return true;
    }
}
