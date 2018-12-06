package simulation.interaction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import simulation.agent.Listener;
import simulation.agent.Speaker;
import simulation.environment.Thing;
import simulation.environment.Word;

@Getter @EqualsAndHashCode @ToString
public class StandardInteractionRunner implements InteractionRunner {

    private int counter = 0;

    public StandardInteractionRunner(){}

    @Override
    public void run(Speaker speaker, Listener listener) {
        counter++;
        Thing thing = speaker.randomRecognizedThing();
        run(speaker, listener, thing);
    }

    @Override
    public void run(Speaker speaker, Listener listener, Thing thing) {
        counter++;
        Word word = speaker.signal(thing);
        if(listener.recognized(thing, word)){
            speaker.success(thing, word);
            listener.success(thing, word);
        }
        else{
            speaker.loss(thing, word);
            listener.acquire(thing, word);
        }
    }
}
