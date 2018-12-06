package util;

import java.util.*;

public class WeightPriorityQueue <V> implements Iterable<V>{

    private PriorityQueue<WeightValuePair<V>> queue;

    public WeightPriorityQueue(){
        this.queue = new PriorityQueue<>();
    }

    public void add(V value, double weight){
        if(!contains(value))
            queue.add(new WeightValuePair<>(value, weight));
        else
            changeWeight(value, weight);
    }

    public double weight(V value){
        for (WeightValuePair<V> pair : queue)
            if(pair.getValue().equals(value))
                return pair.getWeight();
        return 0;
    }

    public boolean contains(V value){
        for(WeightValuePair<V> pair: queue)
            if(pair.getValue().equals(value)) return true;
        return false;
    }

    public void clear(){
        queue.clear();
    }

    public V peek(){
        if(queue.isEmpty()) return null;
        return queue.peek().getValue();
    }

    public V poll(){
        if(queue.isEmpty()) return null;
        return queue.poll().getValue();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public void increaseWeight(V value, double by){
        if(contains(value))
            changeWeight(value, weight(value) + by);
    }

    public void decreaseWeight(V value, double by){
        if(contains(value))
            changeWeight(value, weight(value) - by);
    }

    public void decreaseOtherWeights(V value, double by){
        if(contains(value)){
            WeightValuePair<V> pair = retrieve(value);
            for(WeightValuePair<V> p: queue)
                p.setWeight(p.getWeight() - by);
            queue.add(pair);
        }
    }

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

    public boolean remove(V value){
        return queue.remove(pair(value));
    }

    public int size(){
        return queue.size();
    }

    public Collection<V> values() {
        Collection<V> values = new HashSet<>();
        for(WeightValuePair<V> pair: queue)
            values.add(pair.getValue());
        return values;
    }

    private WeightValuePair<V> retrieve(V value){
        for(WeightValuePair<V> pair: queue){
            if(pair.getValue().equals(value)){
                queue.remove(pair);
                return pair;
            }
        }
        return null;
    }

    private WeightValuePair<V> pair(V value){
        for(WeightValuePair<V> pair: queue)
            if(pair.getValue().equals(value))
                return pair;
        return null;
    }

    public PriorityQueue<WeightValuePair<V>> getQueue() {
        return queue;
    }

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

    @Override
    public String toString() {
        return "WeightPriorityQueue{" +
                "queue=" + queue +
                '}';
    }
}
