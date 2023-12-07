package adventOfCode2023.day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Hand implements Comparable<Hand>{
    final String label;
    final List<Card> cards;
    private final List<Card> unsortedCards;
    private final int bid;
    private HandStrategy handStrategy;

    private HandType type;

    public HandType getType() {
        return type;
    }

    public Hand(List<Card> cards, int bid, String label) {
        this.cards = cards;
        unsortedCards = new ArrayList<>(cards);
        this.handStrategy = new FirstStarStrategy(this);
        cards.sort(Collections.reverseOrder());
        this.bid = bid;
        this.label = label;
        type = computeType();
        //rearrangeCards();
    }

    public void setHandStrategy(HandStrategy handStrategy){
        this.handStrategy = handStrategy;
        type = computeType();
    }

    public int getBid() {
        return bid;
    }

    @Override
    public int compareTo(Hand hand) {
        int thisType = computeType().ordinal();
        int otherType = hand.computeType().ordinal();

        if(thisType != otherType){
            return thisType - otherType;
        }

        for(int i = 0; i < 5; i++){
            int thisHigherCard = unsortedCards.get(i).ordinal();
            int otherHigherCard = hand.unsortedCards.get(i).ordinal();

            if(thisHigherCard != otherHigherCard){
                return thisHigherCard - otherHigherCard;
            }
        }

        return 0;
    }

    public int jokerCount(){
        int count = 0;
        for(Card card : cards){
            if(card == Card.JOKER){
                count++;
            }
        }
        return count;
    }

    private HandType computeType(){
        return handStrategy.computeType();
    }

    private void rearrangeCards(){
        if(type == HandType.HIGH_CARD || type == HandType.FIVE_OF_A_KIND){
            return;
        }

        switch (type) {
            case FOUR_OF_A_KIND -> {
                int aloneIndex = 0;
                if (cards.get(0) == cards.get(1) || cards.get(0) == cards.get(2)) {
                    for (int i = 1; i < 5; i++) {
                        if (cards.get(i) != cards.get(0)) {
                            aloneIndex = i;
                        }
                    }
                    if (aloneIndex == 0) {
                        throw new RuntimeException();
                    }
                    Collections.swap(cards, aloneIndex, 4);
                }
            }
            case FULL_HOUSE, THREE_OF_A_KIND -> {
                int[] aloneIndexes = twoIsolatedCards();
                Collections.swap(cards, aloneIndexes[0], 3);
                Collections.swap(cards, aloneIndexes[1], 4);
            }
            case PAIR -> {
                int[] aloneIndexes2 = threeIsolatedCards();
                Collections.swap(cards, aloneIndexes2[0], 2);
                Collections.swap(cards, aloneIndexes2[1], 3);
                Collections.swap(cards, aloneIndexes2[2], 4);
            }
            case DOUBLE_PAIR -> {
                List<Integer> pairIndexes = doublePairsCards();
                Collections.swap(cards, pairIndexes.get(0), 0);
                Collections.swap(cards, pairIndexes.get(1), 1);
                Collections.swap(cards, pairIndexes.get(2), 2);
                Collections.swap(cards, pairIndexes.get(3), 3);
            }
        }
    }

    int getCardGroupLength(int offset){
        int groupLength;
        for(groupLength = offset + 1; groupLength < 5; groupLength ++){
            if(cards.get(groupLength - 1) != cards.get(groupLength)){
                break;
            }
        }
        return groupLength - offset;
    }

    private int[] twoIsolatedCards(){
        int[] output = new int[2];
        int found = 0;
        for(int j = 0; j < 5; j++){
            Card currentCard = cards.get(j);
            int equalityScore = 0;
            for(int i = 0; i < 5; i++){
                Card otherCard = cards.get(i);
                if(currentCard == otherCard){
                    equalityScore++;
                }
            }
            if(equalityScore < 3){
                output[found] = j;
                if(found == 1){
                    return output;
                }
                found++;
            }
        }
        throw new RuntimeException();
    }

    private int[] threeIsolatedCards(){
        int[] output = new int[3];
        int found = 0;
        for(int j = 0; j < 5; j++){
            Card currentCard = cards.get(j);
            int equalityScore = 0;
            for(int i = 0; i < 5; i++){
                Card otherCard = cards.get(i);
                if(currentCard == otherCard){
                    equalityScore++;
                }
            }
            if(equalityScore == 1){
                output[found] = j;
                if(found == 2){
                    return output;
                }
                found++;
            }
        }
        throw new RuntimeException();
    }

    private List<Integer> doublePairsCards(){
        Integer[] indexes = {0, 1, 2, 3, 4};
        ArrayList<Integer> output = new ArrayList<>(Arrays.asList(indexes));

        int isolated = -1;
        for(int j = 0; j < 5; j++){
            Card currentCard = cards.get(j);
            int equalityScore = 0;
            for(int i = 0; i < 5; i++){
                Card otherCard = cards.get(i);
                if(currentCard == otherCard){
                    equalityScore++;
                }
            }
            if(equalityScore == 1){
                isolated = j;
                break;
            }
        }
        if(isolated == -1){
            throw new RuntimeException();
        }

        output.remove(isolated);

        return output;
    }
}