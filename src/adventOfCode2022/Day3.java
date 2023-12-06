package adventOfCode2022;

import common.Matrix;
import common.PuzzleInputParser;
import common.PuzzleSolution;

import java.util.List;

public class Day3 extends PuzzleSolution {
    private static final int YEAR = 2022;
    private static final int DAY_NUMBER = 3;

    public static void main(String[] args) throws Exception{
        System.out.println("Tests :");
        Day3 test = new Day3(String.format("tests/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(test.firstStar());
        System.out.println(test.secondStar());

        System.out.println("\nActual answers");
        Day3 day3 = new Day3(String.format("inputs/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(day3.firstStar());
        System.out.println(day3.secondStar());
        //displayPriorities();
    }

    public static void displayPriorities(){
        Day3 day3 = new Day3(String.format("tests/%d/%d.txt", YEAR, DAY_NUMBER));

        for(char c = 'a'; c <= 'z'; c++){
            System.out.println(c + " : " + day3.calculatePriority(c));
        }

        for(char c = 'A'; c <= 'Z'; c++){
            System.out.println(c + " : " + day3.calculatePriority(c));
        }
    }

    public Day3(String puzzleInputPath) {
        super(puzzleInputPath);
    }

    @Override
    public String firstStar() throws Exception {
        List<String> ruckstacks = PuzzleInputParser.parseToStringList(puzzleInputPath);

        int sum = 0;
        for(String ruckstack : ruckstacks){
            sum += duplicateItemPriority(ruckstack);
        }

        return Integer.toString(sum);
    }

    private int duplicateItemPriority(String ruckstack) {
        int size = ruckstack.length();
        String[] compartments = new String[2];
        compartments[0] = ruckstack.substring(0, size / 2);
        compartments[1] = ruckstack.substring(size / 2);
        for(int i = 0; i < size / 2; i++){
            if(compartments[1].contains(compartments[0].substring(i, i + 1))){
                char item = compartments[0].charAt(i);
                int priority = calculatePriority(item);
                if(priority < 1 || priority > 52){
                    throw new RuntimeException();
                }

                return priority;
            }
        }
        return 0;
    }

    public int calculatePriority(char item){
        return item >= 'A' && item <= 'Z' ?
                item - 'A' + 27 :
                item - 'a' + 1;
    }

    @Override
    public String secondStar() throws Exception{
        List<String> ruckstacks = PuzzleInputParser.parseToStringList(puzzleInputPath);

        int sum = 0;
        for(int i = 0; i < ruckstacks.size(); i += 3){
            String[] group = {
                    ruckstacks.get(i),
                    ruckstacks.get(i + 1),
                    ruckstacks.get(i + 2),
            };
            sum += calculatePriority(commonItem(group));
        }

        return Integer.toString(sum);
    }

    public char commonItem(String[] group){
        for(int i = 0; i < group[0].length(); i++){
            String character = group[0].substring(i, i + 1);
            if(group[1].contains(character) && group[2].contains(character)){
                return character.charAt(0);
            }
        }

        throw new RuntimeException();
    }
}
