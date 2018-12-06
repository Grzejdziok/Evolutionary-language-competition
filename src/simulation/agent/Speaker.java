package simulation.agent;

import simulation.environment.Thing;
import simulation.environment.Word;

public interface Speaker {

    void success(Thing thing, Word word);

    void loss(Thing thing, Word word);

    Word signal(Thing thing);

    Thing randomRecognizedThing();

}
