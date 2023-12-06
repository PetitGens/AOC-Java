package adventOfCode2022;

import common.PuzzleInputParser;
import common.PuzzleSolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 extends PuzzleSolution {
    private static final int YEAR = 2022;
    private static final int DAY_NUMBER = 1;

    public static void main(String[] args) throws Exception{
        System.out.println("Tests :");
        Day1 test = new Day1(String.format("tests/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(test.firstStar());
        System.out.println(test.secondStar());

        System.out.println("\nActual answers");
        Day1 day1 = new Day1(String.format("inputs/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(day1.firstStar());
        System.out.println(day1.secondStar());
    }

    public Day1(String puzzleInputPath) {
        super(puzzleInputPath);
    }

    @Override
    public String firstStar() throws Exception {
        List<Integer> inputList = PuzzleInputParser.parseToIntegerList(puzzleInputPath);

        int sum = 0;
        int maxFood = 0;

        for(Integer integer : inputList) {
            if (integer != null) {
                sum += integer;
                continue;
            }

            if (sum > maxFood) {
                maxFood = sum;
            }
            sum = 0;
        }

        return Integer.toString(maxFood);
    }

    @Override
    public String secondStar() throws Exception{
        List<Integer> inputList = PuzzleInputParser.parseToIntegerList(puzzleInputPath);
        List<Integer> inventories = new ArrayList<>();

        int sum = 0;

        for(Integer value : inputList){
            if(value == null){
                inventories.add(sum);
                sum = 0;
            } else{
                sum += value;
            }
        }

        Collections.sort(inventories);

        int size = inventories.size();
        sum = 0;
        for(int i = 1; i <= 3; i++){
            sum += inventories.get(size - i);
        }
        return Integer.toString(sum);
    }
}
