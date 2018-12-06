package simulation.agent.variant;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import simulation.agent.Agent;
import simulation.agent.Listener;
import simulation.interaction.InteractionRunner;
import simulation.environment.Thing;
import simulation.environment.Word;
import simulation.language.Language;

import java.util.Collection;
import java.util.Map;

@Getter @EqualsAndHashCode @ToString
public class TotalListener implements VariantAgent {

    public TotalListener(){}

    @Override
    public void interact(Agent speaker, InteractionRunner interactionRunner) {
        interactionRunner.run(speaker, this, speaker.randomRecognizedThing());
    }

    @Override
    public void success(Thing thing, Word word) {

    }

    @Override
    public void loss(Thing thing, Word word) {

    }

    @Override
    public Word signal(Thing thing) {
        return null;
    }

    @Override
    public Thing randomRecognizedThing() {
        return null;
    }

    @Override
    public boolean recognized(Thing thing, Word word) {
        return true;
    }

    @Override
    public void acquire(Thing thing, Word word) {

    }

    @Override
    public boolean recognized(Thing thing) {
        return true;
    }

    @Override
    public boolean oneToOneLexicon() {
        return false;
    }

    @Override
    public Language dominatingLanguage(Collection<Language> languages) {
        return null;
    }

    @Override
    public Map<Language, Integer> countDominatingWords(Collection<Language> languages) {
        return null;
    }

    @Override
    public Map<Language, Collection<Thing>> recognizedThings(Collection<Language> languages) {
        return null;
    }
}
