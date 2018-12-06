package simulation.agent;

import simulation.environment.Thing;
import simulation.environment.Word;

public interface Listener {

    void success(Thing thing, Word word);

    void loss(Thing thing, Word word);

    boolean recognized(Thing thing, Word word);

    void acquire(Thing thing, Word word);
}
