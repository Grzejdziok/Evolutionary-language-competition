package simulation.environment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter @EqualsAndHashCode @ToString
public class Thing implements Comparable<Thing>{
    private final int id;

    public Thing(int id){
        this.id = id;
    };

    @Override
    public int compareTo(Thing o) {
        return Integer.compare(this.id, o.getId());
    }

}
