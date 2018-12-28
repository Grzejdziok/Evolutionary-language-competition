package simulation.agent.variant;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import simulation.agent.Agent;
import simulation.interaction.InteractionRunner;
import simulation.environment.Thing;
import simulation.language.Word;
import simulation.language.Language;

import java.util.Collection;
import java.util.Map;

/**
 * A class of variant agents described in "Evolutionary language competition - an agent-based model" paper.
 * Such agents play always roles of listeners in interaction with other agents and their interactions are always successful.
 * Several methods of this class has no effect, but they are necessary so that total listeners can be treated as ordinary agents.
 * @see TotalSpeaker
 * @see VariantAgent
 * @see simulation.simulation.creator.ELCPaperSimulationCreator
 */
@Getter @EqualsAndHashCode @ToString
public class TotalListener implements VariantAgent {

    /**
     * Runs an interaction between the given speaker and this total listener.
     * @param speaker an agent playing the role of speaker in the interaction invoked by this method
     * @param interactionRunner an interaction runner defining the flow of the interaction
     */
    @Override
    public void interact(Agent speaker, InteractionRunner interactionRunner) {
        interactionRunner.run(speaker, this, speaker.randomRecognizedThing());
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
     * Has no effect.
     * @param thing a thing which was a topic of the interaction
     * @param word a word which was communicated of the interaction
     */
    @Override
    public void loss(Thing thing, Word word) {

    }

    /**
     * Returns {@code null}.
     * @param thing a thing for which a word should be communicated
     * @return null
     */
    @Override
    public Word signal(Thing thing) {
        return null;
    }

    /**
     * Returns {@code null}.
     * @return null
     */
    @Override
    public Thing randomRecognizedThing() {
        return null;
    }

    /**
     * Returns {@code true}.
     * @param thing A thing to be checked for recognition in connection with the specified word.
     * @param word A word to be checked for recognition in connection with the specified thing.
     * @return true.
     */
    @Override
    public boolean recognized(Thing thing, Word word) {
        return true;
    }

    /**
     * Has no effect.
     * @param thing A thing denoted by the specified word
     * @param word A word denoting the specified thing
     */
    @Override
    public void acquire(Thing thing, Word word) {

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
     * Returns {@code false}.
     * @return false
     */
    @Override
    public boolean oneToOneLexicon() {
        return false;
    }

    /**
     * Returns {@code null}
     * @param languages a collection of languages to be checked for dominating language
     * @return null
     */
    @Override
    public Language dominatingLanguage(Collection<Language> languages) {
        return null;
    }

    /**
     * Returns {@code null}.
     * @param languages a collection of languages to be a domain of returned map
     * @return null
     */
    @Override
    public Map<Language, Integer> countDominatingWords(Collection<Language> languages) {
        return null;
    }

    /**
     * Returns {@code null}.
     * @param languages a collection of languages to be a domain of returned map
     * @return null
     */
    @Override
    public Map<Language, Collection<Thing>> recognizedThings(Collection<Language> languages) {
        return null;
    }
}
