package adventOfCode2022;

import common.Matrix;
import common.PuzzleInputParser;
import common.PuzzleSolution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day8 extends PuzzleSolution {
    private static final int YEAR = 2022;
    private static final int DAY_NUMBER = 8;

    public static void main(String[] args) throws Exception{
        System.out.println("Tests :");
        Day8 test = new Day8(String.format("tests/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(test.firstStar());
        System.out.println(test.secondStar());

        System.out.println("\nActual answers");
        Day8 day4 = new Day8(String.format("inputs/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(day4.firstStar());
        System.out.println(day4.secondStar());
    }

    public Day8(String puzzleInputPath) {
        super(puzzleInputPath);
    }

    @Override
    public String firstStar() throws Exception {
        Matrix<Integer> input = parseTreeHeights();

        int count = 0;
        for(int row = 0; row < input.rowList().size(); row++){
            for(int col = 0; col < input.rowList().get(0).size(); col++){
                if(isVisible(input, row, col)){
                    count++;
                }
            }
        }

        return Integer.toString(count);
    }

    @Override
    public String secondStar() throws Exception{
        Matrix<Integer> input = parseTreeHeights();

        int max = 0;
        for(int row = 0; row < input.rowList().size(); row++){
            for(int col = 0; col < input.rowList().get(0).size(); col++){
                int view = scenicScore(input, row, col);
                if(view > max){
                    max = view;
                }
            }
        }

        return Integer.toString(max);
    }

    private Matrix<Integer> parseTreeHeights() throws IOException {
        List<String> input = PuzzleInputParser.parseToStringList(puzzleInputPath);
        Matrix<Integer> output = new Matrix<>();
        for(String line : input){
            if(line.isEmpty()){
                continue;
            }
            ArrayList<Integer> row = new ArrayList<>();
            for(int i = 0; i < line.length(); i++){
                row.add((int)line.charAt(i) - '0');
            }
            output.addRow(row);
        }
        return output;
    }

    private boolean isVisible(Matrix<Integer> map, int row, int col){
        int height = map.get(col, row);

        boolean visible = true;
        //UP
        for(int currentRow = row - 1; currentRow >= 0; currentRow--){
            if(map.get(col, currentRow) >= height){
                visible = false;
                break;
            }
        }

        if(visible){
            return true;
        }

        visible = true;
        //DOWN
        for(int currentRow = row + 1; currentRow < map.rowList().size(); currentRow++){
            if(map.get(col, currentRow) >= height){
                visible = false;
                break;
            }
        }

        if(visible){
            return true;
        }

        visible = true;
        //LEFT
        for(int currentColumn = col - 1; currentColumn >= 0; currentColumn--){
            if(map.get(currentColumn, row) >= height){
                visible = false;
                break;
            }
        }

        if(visible){
            return true;
        }

        visible = true;
        //RIGHT
        for(int currentColumn = col + 1; currentColumn < map.rowList().get(0).size(); currentColumn++){
            if(map.get(currentColumn, row) >= height){
                visible = false;
                break;
            }
        }

        return visible;
    }

    private int scenicScore(Matrix<Integer> map, int row, int col){
        int height = map.get(col, row);

        int product = 1;

        int count = 0;
        //UP
        for(int currentRow = row - 1; currentRow >= 0; currentRow--){
            count++;
            if(map.get(col, currentRow) >= height) {
                break;
            }
        }
        if(count >= 0){
            product *= count;
        }


        count = 0;
        //DOWN
        for(int currentRow = row + 1; currentRow < map.rowList().size(); currentRow++){
            count++;
            if(map.get(col, currentRow) >= height){
                break;
            }
        }
        if(count >= 0){
            product *= count;
        }

        count = 0;
        //LEFT
        for(int currentColumn = col - 1; currentColumn >= 0; currentColumn--){
            count++;
            if(map.get(currentColumn, row) >= height){
                break;
            }
        }
        if(count >= 0){
            product *= count;
        }

        count = 0;
        //RIGHT
        for(int currentColumn = col + 1; currentColumn < map.rowList().get(0).size(); currentColumn++){
            count++;
            if(map.get(currentColumn, row) >= height){
                break;
            }
        }
        if(count >= 0){
            product *= count;
        }

        return product;
    }
}
