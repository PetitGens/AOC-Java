package adventOfCode2023;

import common.Matrix;
import common.PuzzleInputParser;
import common.PuzzleSolution;

import java.util.HashMap;
import java.util.List;

public class Day2 extends PuzzleSolution {
    private static final int YEAR = 2023;
    private static final int DAY_NUMBER = 2;

    private class Game {
        private final HashMap<Color, Integer> cubes = new HashMap<>();

        public void setCubesQuantities(int red, int blue, int green){
            cubes.put(Color.RED, red);
            cubes.put(Color.GREEN, green);
            cubes.put(Color.BLUE, blue);
        }

        public void resetStar1(){
            setCubesQuantities(12, 14, 13);
        }

        public void resetStar2(){
            setCubesQuantities(0, 0, 0);
        }

        public boolean decrementColor(Color color, int value) {
            cubes.put(color, cubes.get(color) - value);
            if (cubes.get(color) < 0) {
                return true;
            }
            return false;
        }

        public void incrementColor(Color color, int value) {
            cubes.put(color, cubes.get(color) + value);
        }

        public boolean above(Color color, int value){
            return value < cubes.get(color);
        }

        public int get(Color color){
            return cubes.get(color);
        }

    }

    private enum Color{
        RED,
        BLUE,
        GREEN
    }

    public static void main(String[] args) throws Exception{
        System.out.println("Tests :");
        Day2 test = new Day2(String.format("tests/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(test.firstStar());
        System.out.println(test.secondStar());

        System.out.println("\nActual answers");
        Day2 day4 = new Day2(String.format("inputs/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(day4.firstStar());
        System.out.println(day4.secondStar());
    }

    public Day2(String puzzleInputPath) {
        super(puzzleInputPath);
    }

    @Override
    public String firstStar() throws Exception {
        int sum = 0;
        Matrix<String> input = PuzzleInputParser.parseToStringMatrix(puzzleInputPath);
        for(List<String> row : input.rowList()){
            String idS = row.get(1).substring(0, row.get(1).length() - 1);
            int id = Integer.parseInt(idS);
            Game game = new Game();
            game.setCubesQuantities(12, 14, 13);
            if(isPossible(game, row)){
                sum += id;
            }
        }
        return Integer.toString(sum);
    }

    private boolean isPossible(Game game, List<String> input){
        for(int i = 2; i < input.size() - 1; i += 2){
            if(input.get(i - 1).endsWith(";")){
                game.resetStar1();
            }

            Color color;
            try{
                String colorString = input.get(i + 1).substring(0, input.get(i + 1).length() - 1);
                color = Color.valueOf(colorString.toUpperCase());
            }
            catch (IllegalArgumentException e){
                color = Color.valueOf(input.get(i + 1).toUpperCase());
            }


            int count = Integer.parseInt(input.get(i));
            if(game.decrementColor(color, count)){
                return false;
            }
        }
        return true;
    }

    @Override
    public String secondStar() throws Exception{
        int sum = 0;
        Matrix<String> input = PuzzleInputParser.parseToStringMatrix(puzzleInputPath);
        for(List<String> row : input.rowList()){
            sum += minimumGame(row);
        }
        return Integer.toString(sum);
    }

    private int minimumGame(List<String> input){
        HashMap<Color, Integer> minimalGame = new HashMap<>();
        for(Color color : Color.values()){
            minimalGame.put(color, -1);
        }

        Game game = new Game();
        game.resetStar2();
        for(int i = 2; i < input.size() - 1; i += 2){
            if(input.get(i - 1).endsWith(";")){
                updateMinimalGame(minimalGame, game);
                game.resetStar2();
            }

            Color color;
            try{
                String colorString = input.get(i + 1).substring(0, input.get(i + 1).length() - 1);
                color = Color.valueOf(colorString.toUpperCase());
            }
            catch (IllegalArgumentException e){
                color = Color.valueOf(input.get(i + 1).toUpperCase());
            }


            int count = Integer.parseInt(input.get(i));
            game.incrementColor(color, count);

        }
        updateMinimalGame(minimalGame, game);

        int product = 1;
        for(Color color : Color.values()){
            product *= minimalGame.get(color);
        }

        return product;
    }

    private void updateMinimalGame(HashMap<Color, Integer> minimalGame, Game currentGame){
        for(Color color : Color.values()){
            int currentValue = minimalGame.get(color);
            if(currentGame.above(color, currentValue)){
                minimalGame.put(color, currentGame.get(color));
            }
        }
    }
}
