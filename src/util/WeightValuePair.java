package util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A class that couples together a value and a double-valued weight connected with this value.
 * @param <V> class of a value in this pair
 * @see WeightPriorityQueue
 */
@Setter @Getter @EqualsAndHashCode @ToString
public class WeightValuePair<V> implements Comparable<WeightValuePair<V>>{

    private double weight;
    private final V value;

    /**
     * Initializes a WeightValuePair object with the specified value and weight
     * @param value the initial value of this pair
     * @param weight the initial weight of this pair
     */
    public WeightValuePair(V value, double weight){
       this.value = value;
       this.weight = weight;
    }

    /**
     * Initializes a WeightValuePair object with the specified value and weight equal to {@code 0}
     * @param value the initial value of this pair
     */
    public WeightValuePair(V value){
        this(value, 0);
    }

    /**
     * Compares two WeightValuePair objects in accordance with their weights in reversed order. This function is equivalent to {@code Double.compare(o.getWeight, this.getWeight())}.
     * @param o the WeightValuePair object to be compared
     * @return the value 0 if {@code o.getWeight()} is numerically equal to {@code this.getWeight()};
     * a value less than 0 if {@code o.getWeight()} is numerically less than {@code this.getWeight()};
     * and a value greater than 0 if {@code o.getWeight()} is numerically greater than {@code this.getWeight()}.
     */
    @Override
    public int compareTo(WeightValuePair<V> o) {
        return Double.compare(o.weight, this.getWeight());
    }
}
