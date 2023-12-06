package adventOfCode2023;

import common.PuzzleSolution;

public class Day7 extends PuzzleSolution {
    private static final int YEAR = 2023;
    private static final int DAY_NUMBER = 7;

    public static void main(String[] args) throws Exception{
        System.out.println("Tests :");
        Day7 test = new Day7(String.format("tests/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println("(1) " + test.firstStar());
        System.out.println("(2) " + test.secondStar());

        System.out.println("\nActual answers");
        Day7 actual = new Day7(String.format("inputs/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println("(1) " + actual.firstStar());
        System.out.println("(2) " + actual.secondStar());
    }

    public Day7(String puzzleInputPath) {
        super(puzzleInputPath);
    }

    @Override
    public String firstStar() throws Exception {
        return null;
    }

    @Override
    public String secondStar() throws Exception{
        return null;
    }
}
