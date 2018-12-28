package simulation.environment;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A factory class to create random {@code Environment} objects with a given number of things.
 * @see Environment
 * @see Thing
 */
public class EnvironmentCreator {

    private int numOfThings;

    /**
     * Initializes this creator with the given number of things.
     * @param numOfThings a number of things to initialize this creator
     */
    public EnvironmentCreator(int numOfThings){
        this.numOfThings = numOfThings;
    }

    /**
     * Returns a new {@code Environment} object with randomly generated things.
     * @return a new {@code Environment} object with randomly generated things.
     */
    public Environment create(){
        Collection<Thing> things = new ArrayList<>();
        for(int i=0;i<numOfThings;i++){
            things.add(new Thing(i));
        }
        return new Environment(things);
    }

}
