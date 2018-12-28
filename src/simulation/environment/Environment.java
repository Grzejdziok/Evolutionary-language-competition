package simulation.environment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.Random;

/**
 * A class representing an environment within which simulations of naming games can be conducted.
 * Actually, it encapsulates collection of things.
 * @see Thing
 * @see EnvironmentCreator
 * @see simulation.simulation.creator.ELCPaperSimulationCreator
 */
@Getter @EqualsAndHashCode @ToString
public class Environment {

    private Collection<Thing> things;

    /**
     * Initializes this environment with the given collection of things
     * @param things an initial collection of things of this environment
     */
    public Environment(Collection<Thing> things){
        this.things = things;
    }

    /**
     * Returns a random thing from this environment
     * @return a random thing from this environment
     */
    public Thing randomThing() {
        int elem = new Random().nextInt(things.size());
        for(Thing thing: things)
            if (elem-- == 0) return thing;
        throw new AssertionError();
    }

}
