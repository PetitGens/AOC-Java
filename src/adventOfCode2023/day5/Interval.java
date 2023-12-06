package adventOfCode2023.day5;

import java.util.HashMap;

public class Interval{
    private final long min;
    private final long max;
    private final HashMap<Interval, Interval> bindings = new HashMap<>();


    public Interval(long min, long max) {
        if(min > max){
            throw new IllegalArgumentException("illegal interval");
        }
        this.min = min;
        this.max = max;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

    public boolean contains(long value){
        return value >= min && value <= max;
    }

    public boolean contains(Interval other){
        return other.min >= min && other.max <= max;
    }

    public boolean intersects(Interval other){
        if (this.max < other.min) return false;

        return other.max >= this.min;
    }

    public void bind(Interval source, Interval destination){
        if(! contains(source)){
            throw new IllegalArgumentException("the specified interval must be contained in this one");
        }

        bindings.put(source, destination);
    }

    public Interval intersection(Interval other){
        if(! intersects(other)){
            return null;
        }
        return new Interval(Math.max(min, other.min), Math.min(max, other.max));
    }
}
