package adventOfCode2023.day7;

public enum Card implements Comparable<Card>{
    JOKER,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    T,
    J,
    Q,
    K,
    A;

    public static Card chatToCard(char character){
        switch (character) {
            case '2' -> {
                return TWO;
            }
            case '3' -> {
                return THREE;
            }
            case '4' -> {
                return FOUR;
            }
            case '5' -> {
                return FIVE;
            }
            case '6' -> {
                return SIX;
            }
            case '7' -> {
                return SEVEN;
            }
            case '8' -> {
                return EIGHT;
            }
            case '9' -> {
                return NINE;
            }
        }
        for(Card card : values()){
            if(card.name().length() == 1 && card.name().charAt(0) == character){
                return card;
            }
        }
        throw new IllegalArgumentException("illegal card");
    }
}
