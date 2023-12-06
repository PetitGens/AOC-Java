package adventOfCode2022;

import common.PuzzleInputParser;
import common.PuzzleSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Day5 extends PuzzleSolution {
    private static final int YEAR = 2022;
    private static final int DAY_NUMBER = 5;

    private List<Stack<Character>> stacks = new ArrayList<>();

    public static void main(String[] args) throws Exception{
        System.out.println("Tests :");
        Day5 test = new Day5(String.format("tests/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(test.firstStar());
        System.out.println(test.secondStar());

        System.out.println("\nActual answers");
        Day5 day4 = new Day5(String.format("inputs/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(day4.firstStar());
        System.out.println(day4.secondStar());
    }

    public Day5(String puzzleInputPath) {
        super(puzzleInputPath);
    }

    @Override
    public String firstStar() throws Exception {
        List<String> puzzleInput = PuzzleInputParser.parseToStringList(puzzleInputPath);
        int stacksBaseLine = -1;
        for(String line : puzzleInput){
            if(! line.contains("[")){
                break;
            }
            stacksBaseLine++;
        }
        int stacksCount = puzzleInput.get(stacksBaseLine).split(" ").length;
        for(int i = 0; i < stacksCount; i++){
            stacks.add(new Stack<>());
        }

        for(int i = stacksBaseLine; i >= 0; i--){
            fillStacks(puzzleInput.get(i));
        }

        for(int i = stacksBaseLine + 3; i < puzzleInput.size(); i++){
            String[] splitted = puzzleInput.get(i).split(" ");
            if(splitted.length < 5){
                break;
            }
            int number = Integer.parseInt(splitted[1]);
            int src = Integer.parseInt(splitted[3]) - 1;
            int dest = Integer.parseInt(splitted[5]) - 1;
            move(src, dest, number);
        }

        StringBuilder output = new StringBuilder();
        for(Stack<Character> stack : stacks){
            output.append(stack.pop());
        }
        return output.toString();
    }

    public void fillStacks(String line){
        int stacksCount = stacks.size();
        for(int i = 0; i < stacksCount; i++){
            int index = i * 4 + 1;
            if(index >= line.length()){
                return;
            }
            char character = line.charAt(index);
            if(character != ' '){
                stacks.get(i).push(character);
            }
        }
    }

    public void move(int src, int dest, int number){
        for(int i = 0; i < number; i++){
            stacks.get(dest).push(stacks.get(src).pop());
        }
    }

    public void moveBis(int src, int dest, int number){
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < number; i++){
            stack.push(stacks.get(src).pop());
        }
        for(int i = 0; i < number; i++){
            stacks.get(dest).push(stack.pop());
        }
    }

    @Override
    public String secondStar() throws Exception{
        stacks.clear();
        List<String> puzzleInput = PuzzleInputParser.parseToStringList(puzzleInputPath);
        int stacksBaseLine = -1;
        for(String line : puzzleInput){
            if(! line.contains("[")){
                break;
            }
            stacksBaseLine++;
        }
        int stacksCount = puzzleInput.get(stacksBaseLine).split(" ").length;
        for(int i = 0; i < stacksCount; i++){
            stacks.add(new Stack<>());
        }

        for(int i = stacksBaseLine; i >= 0; i--){
            fillStacks(puzzleInput.get(i));
        }

        for(int i = stacksBaseLine + 3; i < puzzleInput.size(); i++){
            String[] splitted = puzzleInput.get(i).split(" ");
            if(splitted.length < 5){
                break;
            }
            int number = Integer.parseInt(splitted[1]);
            int src = Integer.parseInt(splitted[3]) - 1;
            int dest = Integer.parseInt(splitted[5]) - 1;
            moveBis(src, dest, number);
        }

        StringBuilder output = new StringBuilder();
        for(Stack<Character> stack : stacks){
            output.append(stack.pop());
        }
        return output.toString();
    }
}
