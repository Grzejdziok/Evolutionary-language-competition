package util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * An auxiliary class which serves as a priority queue whose values are sorted in accordance with their weights
 * - values with higher weights are at the beginning of the queue.
 * <p>
 * The implementation is based mainly on the PriorityQueue class from Java Collections Framework and the interface is inspired by it.
 * In this case, inheritance of PriorityQueue class would not agree with Liskov Substition Principle, because the WeightPriorityQueue class keeps a collection of value-weight pairs, not a collection of values.
 * @see simulation.lexicon.LimitedWeightsLexicon
 * @see WeightValuePair
 * @param <V> class of values to be kept in the queue
 */
public class WeightPriorityQueue <V> implements Iterable<V>{

    /**
     * A PriorityQueue object which keeps the pairs of values and weights
     */
    private PriorityQueue<WeightValuePair<V>> queue;

    /**
     * Initilizes an empty queue
     */
    public WeightPriorityQueue(){
        this.queue = new PriorityQueue<>();
    }

    /**
     * Rdds the specified value with the specified weight into this queue
     * @param value the value to be added to the queue
     * @param weight the weight of the value to be added to the queue
     */
    public void add(V value, double weight){
        if(!contains(value))
            queue.add(new WeightValuePair<>(value, weight));
        else
            changeWeight(value, weight);
    }

    /**
     * Returns the weight of the specified value in this queue
     * @param value value of which weight in this queue should be returned
     * @return the weight of the value in this queue; {@code 0} if the value is not kept in the queue
     */
    public double weight(V value){
        for (WeightValuePair<V> pair : queue)
            if(pair.getValue().equals(value))
                return pair.getWeight();
        return 0;
    }

    /**
     * Returns {@code true} if this WeightPriorityQueue contains the specified value; {@code false} otherwise
     * @param value value to be checked for containment in this queue
     * @return {@code true} if this WeightPriorityQueue contains the specified value; {@code false} otherwise
     */
    public boolean contains(V value){
        for(WeightValuePair<V> pair: queue)
            if(pair.getValue().equals(value)) return true;
        return false;
    }

    /**
     * Removes all of the values from this queue
     */
    public void clear(){
        queue.clear();
    }

    /**
     * Retrieves, but does not remove, the value with the highest weight in this queue, or returns null if this queue is empty.
     * @return the value with the highest weight in this queue, or null if this queue is empty
     */
    public V peek(){
        if(queue.isEmpty()) return null;
        return queue.peek().getValue();
    }

    /**
     * Retrieves and removes the value with the highest weight in this queue, or returns null if this queue is empty.
     * @return the value with the highest weight in this queue, or null if this queue is empty
     */
    public V poll(){
        if(queue.isEmpty()) return null;
        return queue.poll().getValue();
    }

    /**
     * Returns {@code true} if this queue contains no values; {@code false} otherwise
     * @return {@code true} if this queue contains no values; {@code false} otherwise
     */
    public boolean isEmpty(){
        return queue.isEmpty();
    }

    /**
     * Changes the specified {@code value}'s weight to the number specified by the {@code to} argument. Has no effect, if this queue does not contain the {@code value}.
     * @param value value of which weight should be changed
     * @param to new weight for the specified value
     */
    public void changeWeight(V value, double to) {
        WeightValuePair<V> valuePair = null;
        for(WeightValuePair<V> pair: queue)
            if(pair.getValue().equals(value)) {
                valuePair = pair;
                break;
            }
        if(valuePair != null) {
            queue.remove(valuePair);
            valuePair.setWeight(to);
            queue.add(valuePair);
        }
    }

    /**
     * Removes a single instance of the specified value from this queue, if it is present.
     * Returns {@code true} if and only if this queue contained the specified value (or equivalently, if this queue changed as a result of the call).
     * @param value value to be removed
     * @return {@code true} if this queue contained the specified value; {@code false} otherwise
     */
    public boolean remove(V value){
        return queue.remove(pair(value));
    }

    /**
     * Returns the number of values in this queue. If this queue contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
     * @return the number of elements in this queue
     */
    public int size(){
        return queue.size();
    }

    /**
     * Returns a collection of all the values contained in this queue
     * @return a collection of all the values contained in this queue
     */
    public Collection<V> values() {
        Collection<V> values = new HashSet<>();
        for(WeightValuePair<V> pair: queue)
            values.add(pair.getValue());
        return values;
    }

    private WeightValuePair<V> pair(V value){
        for(WeightValuePair<V> pair: queue)
            if(pair.getValue().equals(value))
                return pair;
        return null;
    }

    /**
     * Returns an iterator over the values in this queue. The iterator does not return the values in any particular order.
     * @return an iterator over the values in this queue
     */
    @Override
    public Iterator<V> iterator() {

        return new Iterator<V>() {

            Iterator<WeightValuePair<V>> iterator = queue.iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public V next() {
                return iterator.next().getValue();
            }
        };
    }
}
