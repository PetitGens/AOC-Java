package adventOfCode2023.day7;

public class SecondStarStrategy implements HandStrategy{
    private final Hand hand;

    public SecondStarStrategy(Hand hand) {
        this.hand = hand;
    }

    /*
    Après réflexion, une solution bien meilleure était de gérer un cas pour chaque combinaison
    possible de Type de Main et de nombre de joker, ce qui donnerais :

    FOUR + J -> FIVE
    THREE + J -> FOUR
    THREE + 2J -> FIVE
    DP + J -> FULL
    DP + 2J -> FOUR
    PAIR + J -> THREE
    PAIR + 2J -> THREE
    PAIR + 3J -> FIVE
    H + J -> PAIR
     */
    @Override
    public HandType computeType() {
        if(hand.label.equals("T55J5")){
            //System.out.println("ici");
        }

        int jokerCount = hand.jokerCount();

        int firstGroupLength = hand.getCardGroupLength(0);

        HandType state = null;

        switch (firstGroupLength) {
            case 5 -> {
                return HandType.FIVE_OF_A_KIND;
            }
            case 4 -> {
                if(jokerCount >= 1){
                    return HandType.FIVE_OF_A_KIND;
                }
                return HandType.FOUR_OF_A_KIND;
            }
            case 3 -> {
                switch (jokerCount) {
                    case 1 -> {
                        return HandType.FOUR_OF_A_KIND;
                    }
                    case 2 -> {
                        return HandType.FIVE_OF_A_KIND;
                    }
                }

                if (hand.cards.get(3) == hand.cards.get(4)) {
                    return HandType.FULL_HOUSE;
                }
                return HandType.THREE_OF_A_KIND;
            }
            case 2 -> state = HandType.PAIR;
            case 1 -> state = HandType.HIGH_CARD;
        }

        if(state == HandType.PAIR){
            switch (jokerCount) {
                case 3 -> {
                    return HandType.FIVE_OF_A_KIND;
                }
                case 2 -> {
                    return HandType.FOUR_OF_A_KIND;
                }
            }

            int secondGroup = hand.getCardGroupLength(2);
            switch (secondGroup) {
                case 3 -> {
                    return HandType.FULL_HOUSE;
                }
                case 2 -> {
                    if(jokerCount == 1){
                        return HandType.FULL_HOUSE;
                    }
                    return HandType.DOUBLE_PAIR;
                }
            }
            if(hand.cards.get(3) == hand.cards.get(4)){
                if(jokerCount == 1){
                    return HandType.FULL_HOUSE;
                }
                return HandType.DOUBLE_PAIR;
            }
            if(jokerCount == 1){
                return HandType.THREE_OF_A_KIND;
            }
            return HandType.PAIR;
        }

        int secondGroup = hand.getCardGroupLength(1);

        switch (secondGroup) {
            case 4 -> {
                if(jokerCount == 4){
                    return HandType.FIVE_OF_A_KIND;
                }
                return HandType.FOUR_OF_A_KIND;
            }
            case 3 -> {
                if(jokerCount == 1){
                    return HandType.FOUR_OF_A_KIND;
                }
                return HandType.THREE_OF_A_KIND;
            }
            case 2 -> {
                switch (jokerCount) {
                    case 2 -> {
                        return HandType.FOUR_OF_A_KIND;
                    }
                    case 1 -> {
                        return HandType.THREE_OF_A_KIND;
                    }
                }

                if (hand.cards.get(3) == hand.cards.get(4)) {
                    return HandType.DOUBLE_PAIR;
                }
                return HandType.PAIR;
            }
        }

        // AB???
        int thirdGroup = hand.getCardGroupLength(2);

        switch (thirdGroup) {
            case 3 -> {
                if(jokerCount == 3){
                    return HandType.FOUR_OF_A_KIND;
                }
                return HandType.THREE_OF_A_KIND;
            }
            case 2 -> {
                if(jokerCount == 1){
                    return HandType.THREE_OF_A_KIND;
                }
                return HandType.PAIR;
            }
        }

        if(hand.cards.get(3) == hand.cards.get(4)){
            if(jokerCount == 2){
                return HandType.THREE_OF_A_KIND;
            }
            return HandType.PAIR;
        }

        if(jokerCount == 1){
            return HandType.PAIR;
        }
        return HandType.HIGH_CARD;
    }
}
