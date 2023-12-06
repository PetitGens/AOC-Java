package adventOfCode2023;

import common.PuzzleInputParser;
import common.PuzzleSolution;

import java.util.List;

public class Day1 extends PuzzleSolution {

    private static final String[] DIGIT_LITERALS = {
            "zero",
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine"
    };
    private static final int YEAR = 2023;
    private static final int DAY_NUMBER = 1;

    public static void main(String[] args) throws Exception{
        System.out.println("Tests :");
        Day1 test = new Day1(String.format("tests/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(test.firstStar());
        Day1 test2 = new Day1(String.format("tests/%d/%db.txt", YEAR, DAY_NUMBER));
        //System.out.println(test2.secondStar());

        System.out.println("\nActual answers");
        Day1 day4 = new Day1(String.format("inputs/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(day4.firstStar());
        System.out.println(day4.secondStar());

        System.out.println("\n" + day4.getFirstDigit("eightwo") + day4.getLastDigit("eightwo"));
    }

    public Day1(String puzzleInputPath) {
        super(puzzleInputPath);
    }

    @Override
    public String firstStar() throws Exception {
        List<String> input = PuzzleInputParser.parseToStringList(puzzleInputPath);
        int somme = 0;
        for(String line : input){
            int[] valeurs = new int[2];
            int nbValeurs = 0;
            for(int i = 0; i < line.length(); i++){
                try{
                    valeurs[nbValeurs] = Integer.parseInt(line.substring(i, i + 1));
                    if(nbValeurs == 0){
                        valeurs[1] = valeurs[0];
                    }
                    nbValeurs = 1;
                }
                catch(NumberFormatException ignored){}
            }
            somme += 10 * valeurs[0] + valeurs[1];
        }

        return Integer.toString(somme);
    }

    public int getFirstDigit(String line){
        int min = -1;
        int firstDigit = -1;

        for(int i = 0; i < DIGIT_LITERALS.length; i++){
            String digit = DIGIT_LITERALS[i];
            int posDigit = line.indexOf(digit);
            if(posDigit != -1){
                if(min == -1 || posDigit < min){
                    min = posDigit;
                    firstDigit = i;
                }
            }
        }

        for(int i = 0; i < line.length(); i++){
            try {
                int digit = Integer.parseInt(line.substring(i, i + 1));
                if (min == -1 || i < min) {
                    min = i;
                    firstDigit = digit;
                }
            }
            catch(NumberFormatException ignored){}
        }

        return firstDigit;
    }

    public int getLastDigit(String line){
        int max = -1;
        int lastDigit = -1;

        for(int i = 0; i < DIGIT_LITERALS.length; i++){
            String digit = DIGIT_LITERALS[i];
            int posDigit = line.lastIndexOf(digit);
            if(posDigit != -1 && posDigit > max){
                max = posDigit;
                lastDigit = i;
            }
        }

        for(int i = 0; i < line.length(); i++){
            try {
                int digit = Integer.parseInt(line.substring(i, i + 1));
                if (i > max) {
                    max = i;
                    lastDigit = digit;
                }
            }
            catch(NumberFormatException ignored){}
        }

        return lastDigit;
    }

    @Override
    public String secondStar() throws Exception{
        List<String> input = PuzzleInputParser.parseToStringList(puzzleInputPath);
        int sum = 0;
        for(String line : input){
            if(line.contains("6two6threeeightwott")){
                System.out.println("ici");
            }

            int firstDigit = getFirstDigit(line);
            int lastDigit = getLastDigit(line);

            if(firstDigit == -1 || lastDigit == -1){
                throw new RuntimeException();
            }



            sum += 10 * firstDigit + lastDigit;
        }
        return Integer.toString(sum);
    }
}
