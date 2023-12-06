package adventOfCode2023;

import common.PuzzleInputParser;
import common.PuzzleSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6 extends PuzzleSolution {
    private static final int YEAR = 2023;
    private static final int DAY_NUMBER = 6;

    public static void main(String[] args) throws Exception{
        System.out.println("Tests :");
        Day6 test = new Day6(String.format("tests/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println("(1) " + test.firstStar());
        System.out.println("(2) " + test.secondStar());

        System.out.println("\nActual answers");
        Day6 actual = new Day6(String.format("inputs/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println("(1) " + actual.firstStar());
        System.out.println("(2) " + actual.secondStar());
    }

    public Day6(String puzzleInputPath) {
        super(puzzleInputPath);
    }

    @Override
    public String firstStar() throws Exception {
        List<String> input = PuzzleInputParser.parseToStringList(puzzleInputPath);

        String timeLine = input.get(0);
        String distanceLine = input.get(1);



        Pattern pattern = Pattern.compile("[0-9]+");

        Matcher matcher = pattern.matcher(timeLine);

        ArrayList<Long> times = new ArrayList<>();
        while (matcher.find()) {
            times.add(Long.valueOf(matcher.group()));
        }

        matcher = pattern.matcher(distanceLine);

        ArrayList<Long> distances = new ArrayList<>();
        while (matcher.find()) {
            distances.add(Long.parseLong(matcher.group()));
        }

        int product = 1;
        for(int i = 0; i < times.size(); i++){
            long count = waysToWinCount(times.get(i), distances.get(i));
            if(count > 0){
                product *= count;
            }
        }

        return Long.toString(product);
    }

    private long waysToWinCount(long time, long distance){
        long count = 0;
        for(long speed = 1; speed < time; speed++){
            long actualDistance = speed * (time - speed);
            if(actualDistance > distance){
                count++;
            }
        }
        return count;
    }

    @Override
    public String secondStar() throws Exception{
        List<String> input = PuzzleInputParser.parseToStringList(puzzleInputPath);

        String timeLine = input.get(0);
        String distanceLine = input.get(1);



        Pattern pattern = Pattern.compile("[0-9]+");

        Matcher matcher = pattern.matcher(timeLine);

        StringBuilder timeS = new StringBuilder();
        while (matcher.find()) {
            timeS.append(matcher.group());
        }

        matcher = pattern.matcher(distanceLine);

        StringBuilder distanceS = new StringBuilder();
        while (matcher.find()) {
            distanceS.append(matcher.group());
        }

        return Long.toString(waysToWinCount(Long.parseLong(timeS.toString()), Long.parseLong(distanceS.toString())));
    }
}
