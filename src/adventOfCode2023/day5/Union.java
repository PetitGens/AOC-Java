package adventOfCode2023.day5;

import java.util.ArrayList;
import java.util.List;

public class Union {
    private ArrayList<Interval> intervals = new ArrayList<>();

    public void add(Interval interval){
        Interval intersecting;
        while((intersecting = seekIntersectingInterval(interval)) != null){
            interval = merge(interval, intersecting);
            intervals.remove(intersecting);
        }
        intervals.add(interval);
    }

    public Interval merge(Interval interval1, Interval interval2){
        return new Interval(Math.min(interval1.getMin(), interval2.getMin()), Math.max(interval1.getMax(), interval2.getMax()));
    }

    private Interval seekIntersectingInterval(Interval newInterval){
        for(Interval interval : intervals){
            if(interval.intersects(newInterval)){
                return interval;
            }
        }
        return null;
    }

    public boolean contains(long value){
        for(Interval interval : intervals){
            if(interval.contains(value)){
                return true;
            }
        }
        return false;
    }

    public Union clone(){
        Union newUnion = new Union();
        newUnion.intervals = (ArrayList<Interval>) intervals.clone();
        return newUnion;
    }

    public List<Interval> getIntervals(){
        return intervals;
    }

    public Union minus(Union other){
        Union newUnion = clone();
        for(Interval otherInterval : other.intervals){
            Interval intersecting;
            while((intersecting = newUnion.seekIntersectingInterval(otherInterval)) != null){
                Interval intersection = intersecting.intersection(otherInterval);
                if(intersecting.getMin() < intersection.getMin()){
                    newUnion.intervals.add(new Interval(intersecting.getMin(), intersection.getMin() - 1));
                }
                if(intersecting.getMax() > intersection.getMax()){
                    newUnion.intervals.add(new Interval(intersection.getMax() + 1, intersecting.getMax()));
                }
                newUnion.intervals.remove(intersecting);
            }
        }
        return newUnion;
    }

    public static Union fromInterval(Interval interval){
        Union union = new Union();
        union.add(interval);
        return union;
    }
}
