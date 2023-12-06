package adventOfCode2022;

import common.Matrix;
import common.PuzzleInputParser;
import common.PuzzleSolution;

import java.util.List;

public class Day2 extends PuzzleSolution {
    private static final int YEAR = 2022;
    private static final int DAY_NUMBER = 2;

    public static void main(String[] args) throws Exception{
        System.out.println("Tests :");
        Day2 test = new Day2(String.format("tests/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(test.firstStar());
        System.out.println(test.secondStar());

        System.out.println("\nActual answers");
        Day2 day2 = new Day2(String.format("inputs/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(day2.firstStar());
        System.out.println(day2.secondStar());
    }

    public Day2(String puzzleInputPath) {
        super(puzzleInputPath);
    }

    @Override
    public String firstStar() throws Exception {
        Matrix<String> puzzleInput = PuzzleInputParser.parseToStringMatrix(puzzleInputPath);
        int score = 0;

        for(List<String> row : puzzleInput.rowList()){
            int moveOppo = row.get(0).charAt(0) - 'A' + 1;
            int myMove = row.get(1).charAt(0) - 'X' + 1;
            int result = (myMove - moveOppo + 4) % 3;
            score += myMove + result * 3;
        }
        return Integer.toString(score);
    }

    @Override
    public String secondStar() throws Exception{
        Matrix<String> puzzleInput = PuzzleInputParser.parseToStringMatrix(puzzleInputPath);
        int score = 0;

        for(List<String> row : puzzleInput.rowList()){

            int moveOppo = row.get(0).charAt(0) - 'A' + 1;
            int outcome = row.get(1).charAt(0) - 'X';
            int myMove = (moveOppo + outcome + 1) % 3 + 1;
            score += myMove + outcome * 3;
        }
        return Integer.toString(score);
    }
}
