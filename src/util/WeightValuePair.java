package util;

import java.util.Objects;

public class WeightValuePair<V> implements Comparable<WeightValuePair<V>>{

    private double weight;
    private final V value;

   public WeightValuePair(V value, double weight){
        this.value = value;
       this.weight = weight;
    }

    public WeightValuePair(V value){
        this.weight = 0;
        this.value = value;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight(){
        return weight;
    }

    public V getValue(){
        return value;
    }

    @Override
    public int compareTo(WeightValuePair<V> o) {
        return Double.compare(o.weight, this.getWeight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeightValuePair<?> that = (WeightValuePair<?>) o;
        return Double.compare(that.weight, weight) == 0 &&
                Objects.equals(value, that.value);
    }

    @Override
    public String toString() {
        return "WeightValuePair{" +
                "getWeight=" + weight +
                ", getValue=" + value +
                '}';
    }
}
