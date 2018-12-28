package simulation.agent;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import simulation.language.Language;
import simulation.environment.Thing;
import simulation.language.Word;
import simulation.lexicon.WeightedLexicon;

import java.util.*;

/**
 * A class of agents described in "Evolutionary language competition - an agent-based model" paper. This type of agent
 * preserves information about all its interactions in the form of counting successes and losses of interactions concerning words
 * from a collection of languages. For more details about this kind of agent's behaviour, see the paper.
 * @see simulation.agent.Agent
 * @see simulation.agent.WeightedLexiconAgent
 * @see WeightedLexicon
 * @see simulation.simulation.creator.ELCPaperSimulationCreator
 */
@Getter @EqualsAndHashCode @ToString
public class SuccessCountingAgent implements WeightedLexiconAgent {

    private WeightedLexicon lexicon;

    private Collection<Language> languages;
    private Map<Language, Integer> successes;
    private Map<Language, Integer> losses;

    private double acquireWeight;
    private double weightChangingValue;

    /**
     * Initializes this agent with the given collection of languages, initial lexicon, and {@code acquireWeight} and {@code weightChangingValue}
     * parameters describing how this agent manages its lexicon. Languages from the given collection of languages are those of which successes
     * and losses would be counted by this agent
     * @param languages a collection of languages of which successes and losses will be counted by this agent
     * @param initialLexicon a lexicon to initialize this agent's lexicon
     * @param acquireWeight initial weight of new words in this agent's lexicon
     * @param weightChangingValue basic value by which weight changes in this agent's lexicon are made; note that it is
     *                            always multiplied by a factor depending on this agent's counters of losses and successes;
     *                            more details can be found in the paper
     */
    public SuccessCountingAgent(
            Collection<Language> languages,
            WeightedLexicon initialLexicon,
            double acquireWeight,
            double weightChangingValue
    ) {
        this.languages = new HashSet<>(languages);
        this.lexicon = initialLexicon;

        this.acquireWeight = acquireWeight;
        this.weightChangingValue = weightChangingValue;

        this.successes = new HashMap<>();
        this.losses = new HashMap<>();

        for(Language language: languages){
            successes.put(language, 0);
            losses.put(language, 0);
        }
    }

    /**
     * Increases weight of the given word in connection with the given thing and decreases weights of other words denoting
     * this thing in this agent's lexicon. Updates counters of successes of the languages to which the given word belongs.
     * @param thing a thing which was a topic of the interaction
     * @param word a word which was communicated of the interaction
     */
    @Override
    public void success(Thing thing, Word word){
        Collection<Language> wordLanguages = word.languages(languages);
        for(Language language: wordLanguages) {
            successes.replace(language, successes.get(language) + 1);
            lexicon.increaseWeight(thing, word, (weightChangingValue * proportion(language))/(double) wordLanguages.size());
            lexicon.decreaseOtherWeights(thing, word, (weightChangingValue * proportion(language))/(double) wordLanguages.size());
        }
    }

    /**
     * Decreases weight of the given word in connection with the given thing and updates counters of losses of the languages to which the given word belongs.
     * @param thing a thing which was a topic of the interaction
     * @param word a word which was communicated of the interaction
     */
    @Override
    public void loss(Thing thing, Word word){
        lexicon.decreaseWeight(thing, word, weightChangingValue);
        for(Language language: word.languages(languages)) {
            losses.replace(language, losses.get(language) + 1);
        }
    }

    /**
     * Adds the given word in connection with the given thing to this agent's lexicon.
     * Updates counters of losses of the languages to which the given word belongs.
     * @param thing A thing denoted by the specified word
     * @param word A word denoting the specified thing
     */
    @Override
    public void acquire(Thing thing, Word word){
        lexicon.add(thing, word, acquireWeight);
        for(Language language: word.languages(languages))
            losses.replace(language, losses.get(language) + 1);
    }

    /**
     * If this agent knows a word for the given thing, returns the dominating word denoting this thing in this agent's lexicon.
     * If this agent does not know any word denoting the given thing, generates random word of this agent's dominating language,
     * adds it to this agent's lexicon with {@code acquireWeight} and returns it.
     * @param thing a thing for which a word should be communicated
     * @return the dominating word denoting this thing in this agent's lexicon;
     */
    @Override
    public Word signal(Thing thing){
        if(!lexicon.contains(thing))
            lexicon.add(thing, dominatingLanguage(languages).generateWord(), acquireWeight);
        return lexicon.signal(thing);
    }

    /**
     * Returns a random thing recognized by this agent
     * @return a random thing recognized by this agent
     */
    @Override
    public Thing randomRecognizedThing() {
        Collection<Thing> things = lexicon.things();
        int num = new Random().nextInt(things.size());
        int i = 0;
        for(Thing thing: things)
            if(i++ == num) return thing;
        return null;
    }

    /**
     * Returns {@code true} if this agent recognizes the specified thing with a word.
     * @param thing a thing to be checked for recognition
     * @return {@code true} if this agent recognizes the specified thing with a word; {@code false} otherwise.
     */
    @Override
    public boolean recognized(Thing thing){
        return lexicon.contains(thing);
    }

    /**
     * Returns {@code true} if this agent recognizes the specified word in connection with the given thing.
     * @param thing A thing to be checked for recognition in connection with the specified word.
     * @param word A word to be checked for recognition in connection with the specified thing.
     * @return {@code true} if this listener recognizes the specified word in connection with the specified thing.
     */
    @Override
    public boolean recognized(Thing thing, Word word){
        return lexicon.contains(thing) && lexicon.contains(thing, word);
    }

    /**
     * Returns {@code this.lexicon.oneToOne()}.
     * @return {@code this.lexicon.oneToOne()}.
     */
    @Override
    public boolean oneToOneLexicon(){
        return lexicon.oneToOne();
    }

    @Override
    public Language dominatingLanguage(Collection<Language> languages) {
        Map<Language, Integer> domWords = countDominatingWords(languages);
        Map<Language, Double> weights = countWeightSums(languages);

        int dominatingLanguageWords = 0;
        double dominatingLanguageWeights = 0;
        Language dominatingLanguage = null;

        for(Language language: languages){
            int curDomWords = domWords.get(language);
            double curWeights = weights.get(language);

            if(dominatingLanguageWords < curDomWords || (dominatingLanguageWords == curDomWords && dominatingLanguageWeights < curWeights)){
                dominatingLanguageWords = curDomWords;
                dominatingLanguageWeights = curWeights;
                dominatingLanguage = language;
            }
        }

        return dominatingLanguage;
    }

    @Override
    public Map<Language, Double> countWeightSums(Collection<Language> languages){
        return lexicon.countWeightSums(languages);
    }

    @Override
    public Map<Language, Integer> countDominatingWords(Collection<Language> languages){
        return lexicon.countDominatingWords(languages);
    }

    @Override
    public Map<Language, Collection<Thing>> recognizedThings(Collection<Language> languages){
        return lexicon.recognizedThings(languages);
    }

    private double proportion(Language language){
        return (double) successes.get(language)/((double) (losses.get(language) + successes.get(language)));
    }
}
