package simulation.environment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.Random;

@Getter @EqualsAndHashCode @ToString
public class Environment {

    private Collection<Thing> things;

    public Environment(Collection<Thing> things){
        this.things = things;
    }

    public Thing randomThing() {
        int elem = new Random().nextInt(things.size());
        for(Thing thing: things)
            if (elem-- == 0) return thing;
        throw new AssertionError();
    }

}
