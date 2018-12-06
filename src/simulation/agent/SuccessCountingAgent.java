package simulation.agent;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import simulation.language.Language;
import simulation.environment.Thing;
import simulation.environment.Word;
import simulation.lexicon.WeightedLexicon;

import java.util.*;

@Getter @EqualsAndHashCode @ToString
public class SuccessCountingAgent implements WeightedLexiconAgent {

    private WeightedLexicon lexicon;

    private Collection<Language> languages;
    private Map<Language, Integer> successess;
    private Map<Language, Integer> lossess;

    private double acquireWeight;
    private double weightChangingValue;

    public SuccessCountingAgent(
            Collection<Language> languages,
            WeightedLexicon beginningLexicon,
            double acquireWeight,
            double weightChangingValue
    ) {
        this.languages = new HashSet<>(languages);
        this.lexicon = beginningLexicon;

        this.acquireWeight = acquireWeight;
        this.weightChangingValue = weightChangingValue;

        this.successess = new HashMap<>();
        this.lossess = new HashMap<>();

        for(Language language: languages){
            successess.put(language, 0);
            lossess.put(language, 0);
        }
    }

    @Override
    public void success(Thing thing, Word word){
        Collection<Language> wordLanguages = word.languages(languages);
        for(Language language: wordLanguages) {
            successess.replace(language, successess.get(language) + 1);
            lexicon.increaseWeight(thing, word, (weightChangingValue * proportion(language))/(double) wordLanguages.size());
            lexicon.decreaseOtherWeights(thing, word, (weightChangingValue * proportion(language))/(double) wordLanguages.size());
        }
    }

    @Override
    public void loss(Thing thing, Word word){
        lexicon.decreaseWeight(thing, word, weightChangingValue);
        for(Language language: word.languages(languages)) {
            lossess.replace(language, lossess.get(language) + 1);
        }
    }

    @Override
    public void acquire(Thing thing, Word word){
        lexicon.add(thing, word, acquireWeight);
        for(Language language: word.languages(languages))
            lossess.replace(language, lossess.get(language) + 1);
    }

    @Override
    public Word signal(Thing thing){
        if(!lexicon.contains(thing))
            lexicon.add(thing, dominatingLanguage(languages).generateWord(), acquireWeight);
        return lexicon.signal(thing);
    }

    @Override
    public Thing randomRecognizedThing() {
        Collection<Thing> things = lexicon.things();
        int num = new Random().nextInt(things.size());
        int i = 0;
        for(Thing thing: things)
            if(i++ == num) return thing;
        return null;
    }

    @Override
    public boolean recognized(Thing thing){
        return lexicon.contains(thing);
    }

    @Override
    public boolean recognized(Thing thing, Word word){
        return lexicon.contains(thing) && lexicon.contains(thing, word);
    }

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
        return lexicon.countWeights(languages);
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
        return (double) successess.get(language)/((double) (lossess.get(language) + successess.get(language)));
    }
}
