package adventOfCode2023;

import adventOfCode2023.day5.Interval;
import adventOfCode2023.day5.Union;
import common.PuzzleInputParser;
import common.PuzzleSolution;

import java.util.*;

public class Day5 extends PuzzleSolution {
    private static final int YEAR = 2023;
    private static final int DAY_NUMBER = 5;

    private final HashSet<Long> seeds = new HashSet<>();

    private final List<MapEntry> seedMap = new ArrayList<>();

    private final List<List<MapEntry>> maps = new ArrayList<>();

    private final List<Interval> locationRanges = new ArrayList<>();

    private static record MapEntry(long destination, long source, long range) {}

    public static void main(String[] args) throws Exception{
        System.out.println("Tests :");
        Day5 test = new Day5(String.format("tests/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println("(1) " + test.firstStar());
        System.out.println("(2) " + test.secondStar());

        System.out.println("\nActual answers");
        Day5 actual = new Day5(String.format("inputs/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println("(1) " + actual.firstStar());
        System.out.println("(2) " + actual.secondStar());
    }

    public Day5(String puzzleInputPath) {
        super(puzzleInputPath);
    }

    @Override
    public String firstStar() throws Exception {
        List<List<String>> input = PuzzleInputParser.parseSeparatedLists(puzzleInputPath);
        parseSeeds(input.get(0).get(0));

        for(int i = 1; i < input.size(); i++){
            maps.add(parseMap(input.get(i)));
        }


        long minimum = Long.MAX_VALUE;

        for(long seed : seeds){
            long location = matchLocation(seed);
            if(location < minimum){
                minimum = location;
            }
        }

        return Long.toString(minimum);
    }

    private void clean(){
        maps.clear();
    }

    @Override
    public String secondStar() throws Exception{
        List<List<String>> input = PuzzleInputParser.parseSeparatedLists(puzzleInputPath);
        parseSeedMap(input.get(0).get(0));

        for(int i = 1; i < input.size(); i++){
            maps.add(parseMap(input.get(i)));
        }

        for(MapEntry entry : seedMap){
            /*for(long seed = entry.source; seed < entry.source + entry.range; seed++){
                long location = matchLocation(seed);
                if(location < minimum){
                    minimum = location;
                }
            }*/

            Interval seedInterval = new Interval(entry.source, entry.source + entry.range - 1);
            matchlocation2(seedInterval);
        }

        long minimum = Long.MAX_VALUE;
        for(Interval locationRange : locationRanges){
            if(locationRange.getMin() < minimum){
                minimum = locationRange.getMin();
            }
        }

        return Long.toString(minimum);
    }

    public void parseSeeds(String line){
        String[] splitted = line.split(" ");

        for(int i = 1; i < splitted.length; i++){
            seeds.add(Long.parseLong(splitted[i]));
        }

        //seeds.addAll(Arrays.asList(splitted).subList(1, splitted.length));
    }

    public void parseSeedMap(String line){
        String[] splitted = line.split(" ");

        for(int i = 1; i + 1 < splitted.length; i += 2){
            seedMap.add(new MapEntry(0, Long.parseLong(splitted[i]), Long.parseLong(splitted[i + 1])));
        }

        //seeds.addAll(Arrays.asList(splitted).subList(1, splitted.length));
    }

    private ArrayList<MapEntry> parseMap(List<String> source){
        ArrayList<MapEntry> destination = new ArrayList<>();

        for(int i = 1; i < source.size(); i++){
            String[] splitted = source.get(i).split(" ");

            long destinationRange = Long.parseLong(splitted[0]);
            long sourceRange = Long.parseLong(splitted[1]);
            long rangeLength = Integer.parseInt(splitted[2]);

            destination.add(new MapEntry(destinationRange, sourceRange, rangeLength));
        }
        return destination;
    }

    private long matchLocation(long seed){
//        return matchRecur(seed, 0);
        return matchNonRecur(seed);
    }

    private void matchlocation2(Interval seedRange){
        matchRecur2(seedRange, 0);
    }

    private void matchRecur2(Interval interval, int level){
        Union handledRanges = new Union();

        for(MapEntry entry : maps.get(level)){
            Interval entrySourceInterval = new Interval(entry.source, entry.source + entry.range - 1);
            if(interval.intersects(entrySourceInterval)){
                Interval intersection = interval.intersection(entrySourceInterval);
                Interval destination = new Interval(entry.destination + intersection.getMin() - entry.source,
                        entry.destination + entry.range - 1 - entrySourceInterval.getMax() + intersection.getMax());
                handledRanges.add(intersection);
                if(level == 6){
                    locationRanges.add(destination);
                } else{
                    matchRecur2(destination, level + 1);
                }
            }
        }

        Union unhandledRanges = Union.fromInterval(interval).minus(handledRanges);

        for(Interval range : unhandledRanges.getIntervals()){
            if(level == 6) {
                locationRanges.add(range);
            } else{
                matchRecur2(range, level + 1);
            }
        }
    }

    private boolean isSeed(long input){
        for(MapEntry entry : seedMap){
            if(input >= entry.source && input <= entry.source + entry.range){
                return true;
            }
        }
        return false;
    }

    private long matchNonRecur(long source){
        for(int level = 0; level <= 6; level++){
            for(MapEntry entry : maps.get(level)){
                if(source >= entry.source && source <= entry.source + entry.range){
                    long delta = source - entry.source;
                    source = entry.destination + delta;
                    break;
                }
            }
        }

        return source;
    }

    private long matchRecur(long source, int level){
        for(MapEntry entry : maps.get(level)){
            if(source >= entry.source && source <= entry.source + entry.range){
                long delta = source - entry.source;
                long destination = entry.destination + delta;
                if(level == 6){
                    return destination;
                }
                return matchRecur(destination, level + 1);
            }
        }
        if(level == 6){
            return source;
        }
        return matchRecur(source, level + 1);
    }
}
