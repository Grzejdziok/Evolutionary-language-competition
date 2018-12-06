package simulation.agent.variant;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import simulation.agent.Agent;
import simulation.agent.Speaker;
import simulation.interaction.InteractionRunner;
import simulation.language.Language;
import simulation.lexicon.Lexicon;
import simulation.environment.Thing;
import simulation.environment.Word;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Getter @EqualsAndHashCode @ToString
public class TotalSpeaker implements VariantAgent {

    private Lexicon lexicon;

    public TotalSpeaker(){}

    public TotalSpeaker(Lexicon lexicon){
        this.lexicon = lexicon;
    }

    @Override
    public void interact(Agent listener, InteractionRunner interactionRunner) {
        interactionRunner.run(this, listener, randomRecognizedThing());
    }

    @Override
    public void success(Thing thing, Word word) {

    }

    @Override
    public void loss(Thing thing, Word word) {

    }

    @Override
    public boolean recognized(Thing thing, Word word) {
        return false;
    }

    @Override
    public void acquire(Thing thing, Word word) {

    }

    @Override
    public Word signal(Thing thing) {
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
    public boolean recognized(Thing thing) {
        return true;
    }

    @Override
    public boolean oneToOneLexicon() {
        return true;
    }

    @Override
    public Language dominatingLanguage(Collection<Language> languages) {
        Language maxNumOfWordsLanguage = null;
        int maxNumOfWords = 0;
        Map<Language, Integer> dominatingWords = lexicon.countDominatingWords(languages);
        for(Language language: languages) {
            if(dominatingWords.get(language) > maxNumOfWords){
                maxNumOfWords = dominatingWords.get(language);
                maxNumOfWordsLanguage = language;
            };
        }
        return maxNumOfWordsLanguage;
    }

    @Override
    public Map<Language, Integer> countDominatingWords(Collection<Language> languages) {
        return lexicon.countDominatingWords(languages);
    }

    @Override
    public Map<Language, Collection<Thing>> recognizedThings(Collection<Language> languages) {
        return lexicon.recognizedThings(languages);
    }
}
