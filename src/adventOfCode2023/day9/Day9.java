package adventOfCode2023.day9;

import common.Matrix;
import common.PuzzleInputParser;
import common.PuzzleSolution;

import java.util.*;

public class Day9 extends PuzzleSolution {
    private static final int YEAR = 2023;
    private static final int DAY_NUMBER = 9;

    public static void main(String[] args) throws Exception{
        System.out.println("Tests :");
        Day9 test = new Day9(String.format("tests/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println("(1) " + test.firstStar());
        System.out.println("(2) " + test.secondStar());

        System.out.println("\nActual answers");
        Day9 actual = new Day9(String.format("inputs/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println("(1) " + actual.firstStar());
        System.out.println("(2) " + actual.secondStar());
    }

    private static void test(){
        Random random = new Random();
        int[] randoms = new int[10];

        for(int i = 0; i < randoms.length; i++){
            randoms[i] = random.nextInt(1000);
        }

        System.out.println(Arrays.toString(randoms));
    }

    public Day9(String puzzleInputPath) {
        super(puzzleInputPath);
    }

    @Override
    public String firstStar() throws Exception {
        Matrix<Integer> matrix = PuzzleInputParser.parseToIntegerMatrix(puzzleInputPath);

        int sum = 0;
        int i = 0;
        for(List<Integer> history : matrix.rowList()){
            if(history.size() == 0){
                continue;
            }

            int value = extrapolateNextValue(history.toArray(new Integer[0]));
            sum += value;
            //System.out.print(i + " : ");
            i++;
            //displayExtrapolation(history, value);
        }
        return Integer.toString(sum);
    }

    @Override
    public String secondStar() throws Exception{
        Matrix<Integer> matrix = PuzzleInputParser.parseToIntegerMatrix(puzzleInputPath);

        int sum = 0;
        int i = 0;
        for(List<Integer> history : matrix.rowList()){
            if(history.size() == 0){
                continue;
            }

            Collections.reverse(history);

            int value = extrapolateNextValue(history.toArray(new Integer[0]));
            sum += value;
            //System.out.print(i + " : ");
            i++;
            //displayExtrapolation(history, value);
        }
        return Integer.toString(sum);
    }

    int extrapolateNextValue(Integer[] sequence){
        boolean done = true;
        Integer[] differences = new Integer[sequence.length - 1];
        for(int i = 0; i < sequence.length - 1; i++){
            int difference = sequence[i + 1] - sequence[i];
            differences[i] = difference;

            if(difference != 0){
                done = false;
            }
        }

        if(done){

            return sequence[0];
        }
        int extrapolated = extrapolateNextValue(differences);
        return sequence[sequence.length - 1] + extrapolated;
    }

    void displayExtrapolation(List<Integer> history, int value){
        System.out.println(history + " -> " + value);
    }
}
