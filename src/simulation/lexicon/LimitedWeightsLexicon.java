package simulation.lexicon;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import simulation.environment.Thing;
import simulation.language.Language;
import simulation.language.Word;
import util.WeightPriorityQueue;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * A class of weighted lexicons in which association between things and words are weighted with values belonging to
 * a closed and bounded interval of real numbers.
 * @see simulation.agent.SuccessCountingAgent
 * @see simulation.lexicon.creator.LimitedWeightsLexiconCreator
 * @see WeightedLexicon
 * @see simulation.simulation.creator.ELCPaperSimulationCreator
 */
@EqualsAndHashCode @ToString
public class LimitedWeightsLexicon implements WeightedLexicon{

    private Map<Thing, WeightPriorityQueue<Word>> thingWordQueueMap;
    private double minWeight;
    private double maxWeight;

    /**
     * Initializes this lexicon with the given bounds of weights of associations.
     * All assocations in this lexicon are weighted with the values belonging to the interval {@code [minWeight, maxWeight]}.
     * @param minWeight the lower bound of weights in this lexicon
     * @param maxWeight the upper bound of weights in this lexicon
     */
    public LimitedWeightsLexicon(double minWeight, double maxWeight){
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.thingWordQueueMap = new HashMap<>();
    }

    private double limitWeight(double weight){
        return Math.min(weight, maxWeight);
    }

    /**
     * Adds the association between the given thing and the given word weighted with {@code maxWeight}.
     * @param thing a thing to be added in association with the given word to this lexicon
     * @param word a word to be added in association with the given thing to this lexicon
     */
    @Override
    public void add(Thing thing, Word word){
        add(thing, word, limitWeight(maxWeight));
    }

    /**
     * Adds the association between the given thing and the given word with the given weight.
     * If the given weight does not belong to {@code [minWeight, maxWeight]}, the closest value to this weight from this interval is used.
     * @param thing a thing to be added in association with the given word to this lexicon
     * @param word a word to be added in association with the given thing to this lexicon
     * @param weight a weight of the added association
     */
    @Override
    public void add(Thing thing, Word word, double weight) {
        if (weight > minWeight) {
            if (thingWordQueueMap.containsKey(thing)) {
                thingWordQueueMap.get(thing).add(word, limitWeight(weight));
            } else {
                WeightPriorityQueue<Word> queue = new WeightPriorityQueue<>();
                queue.add(word, limitWeight(weight));
                thingWordQueueMap.put(thing, queue);
            }
        }
    }

    /**
     * Increases the weight of the association between the given thing and the given word by the value of {@code by}, but no more than {@code maxWeight}.
     * @param thing a thing from the association of which the weight should be increased
     * @param word a word from the association of which the weight should be increased
     * @param by value to increase the weight of the association between the given thing and the given word
     */
    @Override
    public void increaseWeight(Thing thing, Word word, double by) {
        thingWordQueueMap.get(thing).changeWeight(word, limitWeight(weight(thing, word) + by));
    }

    /**
     * Decreases the weight of all associations of the given thing except for the association with the given word by the value of {@code by}.
     * If the weight of any association reduces to {@code minWeight}, then this association is removed from this lexicon,
     * unless it was the last association of the given thing. If so, it is left with the weight of {@code minWeight + 0.1}.
     * @param thing a thing of which associations should be included by this method
     * @param word a word from the association of which the weight should not be decreased
     * @param by value to decrease the weights of all associations of the given thing except for the association with the given word
     */
    @Override
    public void decreaseOtherWeights(Thing thing, Word word, double by) {
        WeightPriorityQueue<Word> queue = thingWordQueueMap.get(thing);
        for(Word w: queue.values())
            if(!w.equals(word))
                decreaseWeight(thing, w, by);
    }

    /**
     * Decreases the weight of the association between the given thing and the given word by the value of {@code by}.
     * If the weight of this association reduces to {@code minWeight}, then this association is removed from this lexicon,
     * unless it was the last association of the given thing. If so, it is left with the weight of {@code minWeight + 0.1}.
     * @param thing a thing from the association of which the weight should be decreased
     * @param word a word from the association of which the weight should be decreased
     * @param by value to decrease the weight of the association between the given thing and the given word
     */
    @Override
    public void decreaseWeight(Thing thing, Word word, double by) {
        WeightPriorityQueue<Word> queue = thingWordQueueMap.get(thing);
        queue.changeWeight(word, weight(thing, word) - by);
        if(weight(thing, word) <= minWeight && queue.size() == 1)
            queue.changeWeight(word, minWeight+0.1);
        else if(weight(thing, word) <= minWeight)
            queue.remove(word);
    }

    @Override
    public Collection<Word> words(Thing thing) {
        return thingWordQueueMap.get(thing).values();
    }

    @Override
    public Collection<Thing> things() {
        return thingWordQueueMap.keySet();
    }

    @Override
    public Word signal(Thing thing) {
        if(contains(thing)) return thingWordQueueMap.get(thing).peek();
        return null;
    }

    @Override
    public boolean contains(Thing thing) {
        return thingWordQueueMap.containsKey(thing);
    }

    @Override
    public boolean contains(Thing thing, Word word) {
        return thingWordQueueMap.get(thing).contains(word);
    }

    @Override
    public Map<Language, Double> countWeightSums(Collection<Language> languages){
        Map<Language, Double> weights = new HashMap<>();
        for(Language language: languages)
            weights.put(language, 0.0);

        for(WeightPriorityQueue<Word> queue: thingWordQueueMap.values())
            for(Word word: queue)
                for(Language language: word.languages(languages))
                    weights.replace(language, weights.get(language) + queue.weight(word));

        return weights;
    }

    @Override
    public Map<Language, Integer> countDominatingWords(Collection<Language> languages){
        Map<Language, Integer> dominatingWords = new HashMap<>();
        for(Language language: languages)
            dominatingWords.put(language, 0);

        for(WeightPriorityQueue<Word> queue: thingWordQueueMap.values()){
            for(Language language: queue.peek().languages(languages))
                dominatingWords.replace(language, dominatingWords.get(language)+1);
        }

        return dominatingWords;
    }

    @Override
    public Map<Language, Collection<Thing>> recognizedThings(Collection<Language> languages){
        Map<Language, Collection<Thing>> recognizedThings = new HashMap<>();

        for(Language language: languages)
            recognizedThings.put(language, new HashSet<>());

        for(Thing thing: things()){
            for(Word word: thingWordQueueMap.get(thing))
                for(Language language: languages)
                    if(language.contains(word))
                        recognizedThings.get(language).add(thing);
        }

        return recognizedThings;
    }

    @Override
    public boolean oneToOne(){
        for(Thing thing: thingWordQueueMap.keySet())
            if (thingWordQueueMap.get(thing).size() != 1 ||
                    thingWordQueueMap.get(thing).weight(signal(thing)) < maxWeight) return false;
        return true;
    }

    @Override
    public double weight(Thing thing, Word word) {
        return thingWordQueueMap.get(thing).weight(word);
    }

    public Map<Thing, WeightPriorityQueue<Word>> getThingWordQueueMap() {
        return this.thingWordQueueMap;
    }

    public double getMinWeight() {
        return this.minWeight;
    }

    public double getMaxWeight() {
        return this.maxWeight;
    }
}
