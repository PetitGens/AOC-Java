package adventOfCode2023;

import common.PuzzleInputParser;
import common.PuzzleSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day4 extends PuzzleSolution {
    private static final int YEAR = 2023;
    private static final int DAY_NUMBER = 4;

    private final HashMap<Integer, Integer> multipliers = new HashMap<>();

    public static void main(String[] args) throws Exception{
        System.out.println("Tests :");
        Day4 test = new Day4(String.format("tests/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println("(1) " + test.firstStar());
        System.out.println("(2) " + test.secondStar());

        System.out.println("\nActual answers");
        Day4 actual = new Day4(String.format("inputs/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println("(1) " + actual.firstStar());
        System.out.println("(2) " + actual.secondStar());
    }

    public Day4(String puzzleInputPath) {
        super(puzzleInputPath);
    }

    @Override
    public String firstStar() throws Exception {
        List<String> input = PuzzleInputParser.parseToStringList(puzzleInputPath);
        int sum = 0;

        for(int i = 0; i < input.size(); i++){
            String line = input.get(i);
            String numbersPart = line.split(":")[1];
            String[] numerLists = numbersPart.split("\\|");
            List<Integer> winningNumbers = parseToIntList(numerLists[0]);
            List<Integer> yourNumbers = parseToIntList(numerLists[1]);
            sum += cardValue(winningNumbers, yourNumbers);
        }

        return Integer.toString(sum);
    }

    @Override
    public String secondStar() throws Exception{
        multipliers.clear();
        List<String> input = PuzzleInputParser.parseToStringList(puzzleInputPath);

        for(int i = 0; i < input.size(); i++){
            String line = input.get(i);
            String numbersPart = line.split(":")[1];
            String[] numerLists = numbersPart.split("\\|");
            List<Integer> winningNumbers = parseToIntList(numerLists[0]);
            List<Integer> yourNumbers = parseToIntList(numerLists[1]);
            cardValue2(winningNumbers, yourNumbers, i);
        }
        int sum = input.size();
        for(Integer cardCount : multipliers.values()){
            sum += cardCount;
        }

        return Integer.toString(sum);
    }

    private List<Integer> parseToIntList(String list){
        String[] splitted = list.split(" ");
        List<Integer> intList = new ArrayList<>();
        for(String part : splitted){
            try{
                intList.add(Integer.parseInt(part.trim()));
            }
            catch(NumberFormatException ignored){}

        }
        return intList;
    }

    private int cardValue(List<Integer> winnerNubmers, List<Integer> ypurNumbers){
        int maatches = matchesCount(winnerNubmers, ypurNumbers);
        if(maatches > 0){
            return (int)Math.pow(2.0, (double)maatches - 1);
        }
        return 0;
    }

    private void cardValue2(List<Integer> winnerNubmers, List<Integer> ypurNumbers, int cardNumber){
        int multiplier = 1;

        if(multipliers.containsKey(cardNumber)){
            multiplier += multipliers.get(cardNumber);
        }

        int maatches = matchesCount(winnerNubmers, ypurNumbers);

        for(int i = 1; i <= maatches; i++){
            int currentMultiplier = 0;
            if(multipliers.containsKey(i + cardNumber)){
                currentMultiplier = multipliers.get(i + cardNumber);
            }

            multipliers.put(i + cardNumber, currentMultiplier + multiplier);
        }
    }

    private int matchesCount(List<Integer> winnerNubmers, List<Integer> ypurNumbers){
        int maatches = 0;
        for(Integer integer : ypurNumbers){
            if(winnerNubmers.contains(integer)){
                maatches++;
            }
        }

        return maatches;
    }
}
