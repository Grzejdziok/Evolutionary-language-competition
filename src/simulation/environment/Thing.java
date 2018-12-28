package simulation.environment;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A class representing things in environments in which agents are situated in computer models of language evolution.
 * In this implementation, things are identified and distinguished only with integer IDs.
 */
@EqualsAndHashCode @ToString
public class Thing implements Comparable<Thing>{
    private final int id;

    /**
     * Initializes this thing with the given id.
     * @param id an initial id for this thing
     */
    public Thing(int id){
        this.id = id;
    }

    /**
     * Compares this thing with the given thing in accordance with their ID.
     * This method is equivalent to returning {@code Integer.compare(this.getId(), o.getId()}.
     * @param o the thing to be compared
     * @return Integer.compare(this.getId(), o.getId()
     */
    @Override
    public int compareTo(Thing o) {
        return Integer.compare(this.id, o.getId());
    }

    public int getId() {
        return this.id;
    }
}
