package simulation.agent;

import simulation.environment.Environment;
import simulation.language.Language;
import simulation.environment.Thing;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * An interface providing all methods expected to be defined for different kinds of agents in naming games in computer models of language evolution.
 * @see Listener
 * @see Speaker
 * @see simulation.agent.variant.VariantAgent
 * @see simulation.lexicon.Lexicon
 */
public interface Agent extends Speaker, Listener {

    /**
     * Returns {@code true} if this agent recognizes the specified thing with a word.
     * @param thing a thing to be checked for recognition
     * @return {@code true} if this agent recognizes the specified thing with a word; {@code false} otherwise.
     */
    boolean recognized(Thing thing);

    /**
     * Returns {@code true} if each thing recognized by this agent is denoted by only one word.
     * More formally, it returns {@code true} if this agent's lexicon can be described as an injective function from the space of recognized things to the space of words.
     * @return {@code true} if each thing recognized by this agent is denoted by only one word; {@code false} otherwise.
     */
    boolean oneToOneLexicon();

    /**
     * Returns the dominating language of this agent from among the specified collection of languages.
     * @param languages a collection of languages to be checked for dominating language
     * @return the dominating language of this agent from among the specified collections of languages.
     */
    Language dominatingLanguage(Collection<Language> languages);

    /**
     * Returns a map from the given collection of languages to integers such that each language
     * is mapped to the number of dominating words in this agent's lexicon
     * @param languages a collection of languages to be a domain of returned map
     * @return a map between languages and numbers such that each language from the specified collection of languages
     * is mapped to the number of dominating words in this agent's lexicon
     */
    Map<Language, Integer> countDominatingWords(Collection<Language> languages);

    /**
     * Returns a map from the given collection of languages to collections of things such that each language
     * is mapped to the collection of things recognized with the words belonging to it in this agent's lexicon
     * @param languages a collection of languages to be a domain of returned map
     * @return a map between languages and numbers such that each language from the specified collection of languages
     * is mapped to the collection of things recognized with the words belonging to it in this agent's lexicon
     */
    Map<Language, Collection<Thing>> recognizedThings(Collection<Language> languages);

    /**
     * Returns {@code true} if all agents from the given array are pair-wise lexically synchronized on the given environment.
     * @param agents an array of agents to be checked for lexical synchronization on the given environment
     * @param environment an environment on which the given array of agents should be checked for lexical synchronization
     * @return {@code true} if all agents from the given array are pair-wise lexically synchronized on the things from the given environment; {@code false} otherwise.
     */
    static boolean lexicallySynchronized(Agent[] agents, Environment environment){
        for(int i = 0; i < agents.length; i++){
            for(int j = i+1; j< agents.length; j++){
                if(!lexicallySynchronized(agents[i], agents[j], environment)) return false;
            }
        }
        return true;
    }

    /**
     * Returns {@code true} if the two specified agents are lexically synchronized on the given environment.
     * @param a the first agent to be checked for lexical synchronization
     * @param b the second agent to be checked for lexical synchronization
     * @param environment an environment on which the two specified agents should be checked for lexical synchronization
     * @return {@code true} if the two specified agents are lexically synchronized on the given environment; {@code false} otherwise.
     */
    static boolean lexicallySynchronized(Agent a, Agent b, Environment environment){
        for(Thing thing: environment.getThings()){
            if(!(a.recognized(thing) && b.recognized(thing))) return false;
            if(!a.signal(thing).equals(b.signal(thing))) return false;
        }
        return true;
    }

    /**
     * Returns a map from the given collection of languages to integers such that each language is mapped to
     * the number of agents from the given array for which this language is dominating.
     * @param languages a collection of languages to be a domain of returned map
     * @param agents an array of agents from which numbers of users of languages from the given collection should be counted
     * @return a map from the given collection of languages to integers such that each language is mapped to
     * the number of agents from the given array for which this language is dominating.
     */
    static Map<Language, Integer> countUsers(Collection<Language> languages, Agent[] agents){
        Map<Language, Integer> users = new HashMap<>();

        for(Language language: languages)
            users.put(language, 0);

        for (Agent agent : agents) {
            Language curAgentDomLanguage = agent.dominatingLanguage(languages);
            users.replace(curAgentDomLanguage, users.get(curAgentDomLanguage) + 1);
        }

        return users;
    }

    /**
     * Returns a map from the given collection of languages to integers such that each language is mapped to
     * the number of things recognized by any agent from the given array of agents in association with a word belonging to this language.
     * @param languages a collection of languages to be a domain of returned map
     * @param agents an array of agents to be included in evaluation of the mappings to collections of recognized things
     * @return a map from the given collection of languages to integers such that each language is mapped to
     * the number of things recognized by any agent from the given array of agents in association with a word belonging to this language.
     */
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

    /**
     * Returns {@code true} if all agents from the given array are evaluated to {@code true} with oneToOneLexicon() method.
     * @param agents an array of agents to be checked for evaluation to {@code true} with oneToOneLexicon() method
     * @return {@code true} if all agents from the given array are evaluated to {@code true} with oneToOneLexicon() method.
     */
    static boolean oneToOneLexicons(Agent[] agents) {
        for(Agent agent: agents)
            if(!agent.oneToOneLexicon()) return false;
        return true;
    }
}
