package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PuzzleInputParser {

    public static void main (String[] args) throws IOException {
        System.out.println(parseToIntegerList("data.txt"));
    }

    public static List<Integer> parseToIntegerList(String filePath) throws IOException {
        BufferedReader bufferedReader = Files.newBufferedReader(Path.of(filePath));
        String line;
        ArrayList<Integer> outputList = new ArrayList<>();
        while((line = bufferedReader.readLine()) != null){
            // If we encounter an empty line, we just add a null value to the list
            if(line.isEmpty()) {
                outputList.add(null);
            } else {
                outputList.add(Integer.valueOf(line));
            }
        }
        return outputList;
    }

    public static List<String> parseToStringList(String filePath) throws IOException {
        BufferedReader bufferedReader = Files.newBufferedReader(Path.of(filePath));
        String line;
        ArrayList<String> outputList = new ArrayList<>();
        while((line = bufferedReader.readLine()) != null){
            outputList.add(line);
        }
        return outputList;
    }

    public static Matrix<Integer> parseToIntegerMatrix(String filePath) throws IOException {
        BufferedReader bufferedReader = Files.newBufferedReader(Path.of(filePath));

        Matrix<Integer> integerMatrix = new Matrix<>();
        String line;
        while((line = bufferedReader.readLine()) != null){
            // If we encounter an empty line, we just add an empty row to the matrix
            if(line.isEmpty()){
                integerMatrix.addRow();
                continue;
            }
            String[] splitted = line.split(" ");
            ArrayList<Integer> integersList = new ArrayList<>();
            for(String word : splitted){
                integersList.add(Integer.valueOf(word));
            }
            integerMatrix.addRow(integersList);
        }
        return integerMatrix;
    }

    public static Matrix<String> parseToStringMatrix(String filePath) throws IOException {
        BufferedReader bufferedReader = Files.newBufferedReader(Path.of(filePath));

        Matrix<String> stringMatrix = new Matrix<>();
        String line;
        while((line = bufferedReader.readLine()) != null){
            // If we encounter an empty line, we just add an empty row to the matrix
            if(line.isEmpty()){
                stringMatrix.addRow();
                continue;
            }

            stringMatrix.addRow(line.split(" "));
        }
        return stringMatrix;
    }

    public static List<List<String>> parseSeparatedLists(String filePath) throws IOException{
        BufferedReader bufferedReader = Files.newBufferedReader(Path.of(filePath));

        List<List<String>> stringMatrix = new ArrayList<>();
        List<String> currentList = new ArrayList<>();
        stringMatrix.add(currentList);
        String line;

        while((line = bufferedReader.readLine()) != null){
            // If we encounter an empty line, add a new List
            if(line.isEmpty()){
                currentList = new ArrayList<>();
                stringMatrix.add(currentList);
                continue;
            }

            currentList.add(line);
        }
        return stringMatrix;
    }

    public static String parseToString(String filePath) throws IOException {
        BufferedReader bufferedReader = Files.newBufferedReader(Path.of(filePath));
        StringBuilder output = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine()) != null){
            output.append(line);
        }
        return output.toString();
    }

    public static char getCharacter(List<String> input, int row, int col){
        return input.get(row).charAt(col);
    }
}
