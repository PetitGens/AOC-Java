package adventOfCode2022;

import common.PuzzleInputParser;
import common.PuzzleSolution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Day6 extends PuzzleSolution {
    private static final int YEAR = 2022;
    private static final int DAY_NUMBER = 6;

    public static void main(String[] args) throws Exception{
        System.out.println("Tests :");
        Day6 test = new Day6(String.format("tests/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(test.firstStar());
        System.out.println(test.secondStar());

        System.out.println("\nActual answers");
        Day6 day4 = new Day6(String.format("inputs/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(day4.firstStar());
        System.out.println(day4.secondStar());
    }

    public Day6(String puzzleInputPath) {
        super(puzzleInputPath);
    }

    @Override
    public String firstStar() throws Exception {
        String input = PuzzleInputParser.parseToString(puzzleInputPath);
        LinkedList<Character> buffer = new LinkedList<>();
        for(int i = 0; i < input.length(); i++){
            buffer.add(input.charAt(i));
            if(i >= 4){
                buffer.removeFirst();
                if(! containsDuplicates(buffer)){
                    return Integer.toString(i + 1);
                }
            }

        }
        throw new RuntimeException();
    }

    public boolean containsDuplicates(List<Character> list){
        List<Character> buffer = new ArrayList<>(list);
        for(int i = 0; i < list.size(); i++){
            Character element = buffer.remove(0);
            if(buffer.contains(element)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String secondStar() throws Exception{
        String input = PuzzleInputParser.parseToString(puzzleInputPath);
        LinkedList<Character> buffer = new LinkedList<>();
        for(int i = 0; i < input.length(); i++){
            buffer.add(input.charAt(i));
            if(i >= 14){
                buffer.removeFirst();
                if(! containsDuplicates(buffer)){
                    return Integer.toString(i + 1);
                }
            }
        }
        throw new RuntimeException();
    }
}
