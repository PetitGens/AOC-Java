package adventOfCode2023;

import common.PuzzleInputParser;
import common.PuzzleSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day3 extends PuzzleSolution {
    private static final int YEAR = 2023;
    private static final int DAY_NUMBER = 3;

    private final HashMap<Coordonates, List<Integer>> gears = new HashMap<>();

    private record Coordonates(int x, int y) {}

    public static void main(String[] args) throws Exception{
        System.out.println("Tests :");
        Day3 test = new Day3(String.format("tests/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println("(1)" + test.firstStar());
        System.out.println("(2)" + test.secondStar());

        System.out.println("\nActual answers");
        Day3 day4 = new Day3(String.format("inputs/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println("(1)" + day4.firstStar());
        System.out.println("(2)" + day4.secondStar());
    }

    public Day3(String puzzleInputPath) {
        super(puzzleInputPath);
    }

    @Override
    public String firstStar() throws Exception {
        List<String> input = PuzzleInputParser.parseToStringList(puzzleInputPath);
        int sum = 0;

        for(int line = 0; line < input.size(); line++){
            sum += treatLine(input, line);
        }

        return Integer.toString(sum);
    }

    @Override
    public String secondStar() throws Exception{
        List<String> input = PuzzleInputParser.parseToStringList(puzzleInputPath);
        int sum = 0;

        for(int line = 0; line < input.size(); line++){
            treatLine2(input, line);
        }

        for(List<Integer> list: gears.values()){
            if(list.size() < 2){
                continue;
            }

            int product = 1;
            for(int value : list){
                product *= value;
            }

            sum += product;
        }

        return Integer.toString(sum);
    }

    private int treatLine(List<String> input, int lineNumber){
        String line = input.get(lineNumber);
        int sum = 0;
        for(int col = 0; col < line.length(); col++){
            if(! Character.isDigit(line.charAt(col))){
                continue;
            }
            int length = 1;
            while(col + length < line.length() && Character.isDigit(line.charAt(col + length))){
                length++;
            }

            String number = line.substring(col, col + length);

            if(isPart(input, lineNumber, col, length)){
                sum += Integer.parseInt(number);
            }
            col += length;
        }
        return sum;
    }

    private void treatLine2(List<String> input, int lineNumber){
        String line = input.get(lineNumber);
        for(int col = 0; col < line.length(); col++){
            if(! Character.isDigit(line.charAt(col))){
                continue;
            }
            int length = 1;
            while(col + length < line.length() && Character.isDigit(line.charAt(col + length))){
                length++;
            }

            String number = line.substring(col, col + length);

            updateNearbyGear(input, lineNumber, col, length);
            col += length;
        }
    }

    private boolean isSymbol(char character){
        return ! Character.isDigit(character) && character != '.';
    }

    private char getCharacter(List<String> input, int row, int col){
        return input.get(row).charAt(col);
    }

    private boolean isPart(List<String> input, int row, int col, int length){
        for(int dy = -1; dy <= 1; dy ++){
            for(int dx = -1;  dx < length + 1; dx++){
                try{
                    if(isSymbol(getCharacter(input, row + dy, col + dx))){
                        return true;
                    }
                }
                catch (IndexOutOfBoundsException ignored){}
            }
        }
        return false;
    }

    private void updateNearbyGear(List<String> input, int row, int col, int length){
        int number = Integer.parseInt(input.get(row).substring(col, col + length));
        for(int dy = -1; dy <= 1; dy ++){
            for(int dx = -1;  dx < length + 1; dx++){
                try{
                    if(getCharacter(input, row + dy, col + dx) == '*'){
                        Coordonates coordonates = new Coordonates(row + dy, col + dx);
                        gears.computeIfAbsent(coordonates, k -> new ArrayList<>());
                        gears.get(coordonates).add(number);
                    }
                }
                catch (IndexOutOfBoundsException ignored){}
            }
        }
    }
}
