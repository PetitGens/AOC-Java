package adventOfCode2023.day7;

public class FirstStarStrategy implements HandStrategy{
    private final Hand hand;

    public FirstStarStrategy(Hand hand) {
        this.hand = hand;
    }

    @Override
    public HandType computeType() {
        int firstGroupLength = hand.getCardGroupLength(0);

        HandType state = null;

        switch (firstGroupLength) {
            case 5 -> {
                return HandType.FIVE_OF_A_KIND;
            }
            case 4 -> {
                return HandType.FOUR_OF_A_KIND;
            }
            case 3 -> {
                if (hand.cards.get(3) == hand.cards.get(4)) {
                    return HandType.FULL_HOUSE;
                }
                return HandType.THREE_OF_A_KIND;
            }
            case 2 -> state = HandType.PAIR;
            case 1 -> state = HandType.HIGH_CARD;
        }

        if(state == HandType.PAIR){
            int secondGroup = hand.getCardGroupLength(2);
            switch (secondGroup) {
                case 3 -> {
                    return HandType.FULL_HOUSE;
                }
                case 2 -> {
                    return HandType.DOUBLE_PAIR;
                }
            }
            if(hand.cards.get(3) == hand.cards.get(4)){
                return HandType.DOUBLE_PAIR;
            }
            return HandType.PAIR;
        }

        int secondGroup = hand.getCardGroupLength(1);

        switch (secondGroup) {
            case 4 -> {
                return HandType.FOUR_OF_A_KIND;
            }
            case 3 -> {
                return HandType.THREE_OF_A_KIND;
            }
            case 2 -> {
                if (hand.cards.get(3) == hand.cards.get(4)) {
                    return HandType.DOUBLE_PAIR;
                }
                return HandType.PAIR;
            }
        }

        int thirdGroup = hand.getCardGroupLength(2);

        switch (thirdGroup) {
            case 3 -> {
                return HandType.THREE_OF_A_KIND;
            }
            case 2 -> {
                return HandType.PAIR;
            }
        }

        if(hand.cards.get(3) == hand.cards.get(4)){
            return HandType.PAIR;
        }

        return HandType.HIGH_CARD;
    }
}
