package simulation.lexicon;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import simulation.language.Language;
import simulation.environment.Thing;
import simulation.environment.Word;
import util.WeightPriorityQueue;

import java.util.*;

@Getter @EqualsAndHashCode @ToString
public class LimitedWeightsLexicon implements WeightedLexicon{

    private Map<Thing, WeightPriorityQueue<Word>> thingWordQueueMap;
    private double minWeight = 0.0;
    private double maxWeight = 3.0;

    public LimitedWeightsLexicon(double minWeight, double maxWeight){
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.thingWordQueueMap = new HashMap<>();
    }

    private double limitWeight(double weight){
        return Math.min(weight, maxWeight);
    }

    @Override
    public void add(Thing thing, Word word){
        add(thing, word, limitWeight(maxWeight));
    }

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

    @Override
    public void increaseWeight(Thing thing, Word word, double by) {
        thingWordQueueMap.get(thing).changeWeight(word, limitWeight(weight(thing, word) + by));
    }

    @Override
    public void decreaseOtherWeights(Thing thing, Word word, double by) {
        WeightPriorityQueue<Word> queue = thingWordQueueMap.get(thing);
        for(Word w: queue.values())
            if(!w.equals(word))
                decreaseWeight(thing, w, by);
    }

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
    public Map<Language, Double> countWeights(Collection<Language> languages){
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
}
