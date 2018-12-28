package simulation.agent.variant;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import simulation.agent.Agent;
import simulation.interaction.InteractionRunner;
import simulation.language.Language;
import simulation.lexicon.Lexicon;
import simulation.environment.Thing;
import simulation.language.Word;

import java.util.Collection;
import java.util.Map;
import java.util.Random;

/**
 * A class of variant agents described in "Evolutionary language competition - an agent-based model" paper.
 * Such agents play always roles of speakers in interaction with other agents.
 * Several methods of this class has no effect, but they are necessary so that total speakers can be treated as ordinary agents.
 * @see TotalListener
 * @see VariantAgent
 * @see simulation.simulation.creator.ELCPaperSimulationCreator
 */
@Getter @EqualsAndHashCode @ToString
public class TotalSpeaker implements VariantAgent {

    private Lexicon lexicon;

    /**
     * Initializes this agent with the given lexicon.
     * @param lexicon an initial lexicon of this agent
     */
    public TotalSpeaker(Lexicon lexicon){
        this.lexicon = lexicon;
    }

    /**
     * Runs an interaction between this total speaker and the given listener.
     * @param listener an agent playing the role of listener in the interaction invoked by this method
     * @param interactionRunner an interaction runner defining the flow of the interaction
     */
    @Override
    public void interact(Agent listener, InteractionRunner interactionRunner) {
        interactionRunner.run(this, listener, randomRecognizedThing());
    }

    /**
     * Has no effect.
     * @param thing a thing which was a topic of the interaction
     * @param word a word which was communicated of the interaction
     */
    @Override
    public void success(Thing thing, Word word) {

    }

    /**
     * Has no effect
     * @param thing a thing which was a topic of the interaction
     * @param word a word which was communicated of the interaction
     */
    @Override
    public void loss(Thing thing, Word word) {

    }

    /**
     * Returns {@code false}.
     * @param thing A thing to be checked for recognition in connection with the specified word.
     * @param word A word to be checked for recognition in connection with the specified thing.
     * @return false
     */
    @Override
    public boolean recognized(Thing thing, Word word) {
        return false;
    }

    /**
     * Has no effect.
     * @param thing A thing denoted by the specified word
     * @param word A word denoting the specified thing
     */
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

    /**
     * Returns {@code true}.
     * @param thing a thing to be checked for recognition
     * @return true
     */
    @Override
    public boolean recognized(Thing thing) {
        return true;
    }

    /**
     * Returns {@code true}.
     * @return true
     */
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
            }
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
