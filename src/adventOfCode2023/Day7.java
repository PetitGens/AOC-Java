package adventOfCode2023;

import adventOfCode2023.day7.*;
import common.PuzzleInputParser;
import common.PuzzleSolution;

import java.util.*;

public class Day7 extends PuzzleSolution{
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
        List<String> input = PuzzleInputParser.parseToStringList(puzzleInputPath);
        List<Hand> handList = parseHands(input);
        Collections.sort(handList);

        int sum = 0;
        for(int i = 0; i < handList.size(); i++){
            sum += handList.get(i).getBid() * (i + 1);
        }

        return Integer.toString(sum);
    }

    @Override
    public String secondStar() throws Exception{
        List<String> input = PuzzleInputParser.parseToStringList(puzzleInputPath);
        List<Hand> handList = parseHands2(input);
        Collections.sort(handList);

        int sum = 0;
        for(int i = 0; i < handList.size(); i++){
            Hand currentHand = handList.get(i);
            sum += currentHand.getBid() * (i + 1);

            if(currentHand.jokerCount() > 0){
                HandType newType = currentHand.getType();
                currentHand.setHandStrategy(new FirstStarStrategy(currentHand));
                HandType oldType = currentHand.getType();
                if(newType == oldType && oldType != HandType.FIVE_OF_A_KIND){
                    throw new RuntimeException();
                }
            }
        }

        return Integer.toString(sum);
    }

    private List<Hand> parseHands(List<String> input){
        ArrayList<Hand> hands = new ArrayList<>();
        for(String line : input){
            if(line.isEmpty()){
                continue;
            }
            ArrayList<Card> cards = new ArrayList<>();
            for(int i = 0; i < 5; i++){
                cards.add(Card.chatToCard(line.charAt(i)));
            }

            int bid = Integer.parseInt(line.split(" ")[1]);
            Hand hand = new Hand(cards, bid, line.split(" ")[0]);

            hands.add(hand);
        }
        return hands;
    }

    private List<Hand> parseHands2(List<String> input){
        ArrayList<Hand> hands = new ArrayList<>();
        for(String line : input){
            if(line.isEmpty()){
                continue;
            }
            ArrayList<Card> cards = new ArrayList<>();
            for(int i = 0; i < 5; i++){
                if(line.charAt(i) == 'J'){
                    cards.add(Card.JOKER);
                    continue;
                }
                cards.add(Card.chatToCard(line.charAt(i)));
            }

            int bid = Integer.parseInt(line.split(" ")[1]);
            Hand hand = new Hand(cards, bid, line.split(" ")[0]);
            hand.setHandStrategy(new SecondStarStrategy(hand));

            hands.add(hand);
        }
        return hands;
    }
}
