package adventOfCode2022;

import common.PuzzleInputParser;
import common.PuzzleSolution;

import java.util.List;

public class Day4 extends PuzzleSolution {
    private static final int YEAR = 2022;
    private static final int DAY_NUMBER = 4;

    public static void main(String[] args) throws Exception{
        System.out.println("Tests :");
        Day4 test = new Day4(String.format("tests/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(test.firstStar());
        System.out.println(test.secondStar());

        System.out.println("\nActual answers");
        Day4 day4 = new Day4(String.format("inputs/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(day4.firstStar());
        System.out.println(day4.secondStar());
    }

    public Day4(String puzzleInputPath) {
        super(puzzleInputPath);
    }

    @Override
    public String firstStar() throws Exception {
        List<String> puzzleInput = PuzzleInputParser.parseToStringList(puzzleInputPath);
        int sum = 0;
        for(String line : puzzleInput){
            String[] splitted = line.split(",");
            int[] values = new int[4];
            for(int i = 0; i < 2; i++){
                String[] range = splitted[i].split("-");
                values[i * 2] = Integer.parseInt(range[0]);
                values[i * 2 + 1] = Integer.parseInt(range[1]);
            }

            int range1Min = values[0];
            int range1Max = values[1];
            int range2Min = values[2];
            int range2Max = values[3];

            if(contains(range1Min, range1Max, range2Min, range2Max) ||
            contains(range2Min, range2Max, range1Min, range1Max)){
                sum++;
            }
        }
        return Integer.toString(sum);
    }

    public boolean contains(int range1Min, int range1Max, int range2Min, int range2Max){
        return range1Min <= range2Min && range1Max >= range2Max;
    }

    public boolean overlaps(int range1Min, int range1Max, int range2Min, int range2Max){
        return  range1Min <= range2Max && range1Max >= range2Min;
    }

    @Override
    public String secondStar() throws Exception{
        List<String> puzzleInput = PuzzleInputParser.parseToStringList(puzzleInputPath);
        int sum = 0;
        for(String line : puzzleInput){
            String[] splitted = line.split(",");
            int[] values = new int[4];
            for(int i = 0; i < 2; i++){
                String[] range = splitted[i].split("-");
                values[i * 2] = Integer.parseInt(range[0]);
                values[i * 2 + 1] = Integer.parseInt(range[1]);
            }

            int range1Min = values[0];
            int range1Max = values[1];
            int range2Min = values[2];
            int range2Max = values[3];

            if(overlaps(range1Min, range1Max, range2Min, range2Max)){
                sum++;
            }
        }
        return Integer.toString(sum);
    }
}
