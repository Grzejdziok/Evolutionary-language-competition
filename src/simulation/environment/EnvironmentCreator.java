package simulation.environment;

import java.util.ArrayList;
import java.util.Collection;

public class EnvironmentCreator {

    private int numOfThings;

    public EnvironmentCreator(int numOfThings){
        this.numOfThings = numOfThings;
    }

    public Environment create(){
        Collection<Thing> things = new ArrayList<>();
        for(int i=0;i<numOfThings;i++){
            things.add(new Thing(i));
        }
        return new Environment(things);
    }

}
