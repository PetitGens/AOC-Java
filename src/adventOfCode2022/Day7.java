package adventOfCode2022;

import adventOfCode2022.day7.File;
import adventOfCode2022.day7.Folder;
import adventOfCode2022.day7.IFile;
import common.PuzzleInputParser;
import common.PuzzleSolution;

import java.util.List;

public class Day7 extends PuzzleSolution {
    private static final int YEAR = 2022;
    private static final int DAY_NUMBER = 7;

    private Folder currentFolder;
    private Folder root;

    private int requiredSpace;

    public static void main(String[] args) throws Exception{
        System.out.println("Tests :");
        Day7 test = new Day7(String.format("tests/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(test.firstStar());
        System.out.println(test.secondStar());

        System.out.println("\nActual answers");
        Day7 day4 = new Day7(String.format("inputs/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println(day4.firstStar());
        System.out.println(day4.secondStar());

        System.out.println(Integer.MAX_VALUE);
    }

    public Day7(String puzzleInputPath) {
        super(puzzleInputPath);
    }

    @Override
    public String firstStar() throws Exception {
        root = new Folder("/");
        currentFolder = root;

        List<String> input = PuzzleInputParser.parseToStringList(puzzleInputPath);

        for(int i = 0; i < input.size(); i++){
            String line = input.get(i);
            if(line.charAt(0) != '$'){
                throw new RuntimeException("no dollar sign at line " + i);
            }
            String command = line.substring(2,4);
            switch (command){
                case "ls":
                    i = list(input, i + 1) - 1;
                    break;
                case "cd":
                    changeDirectory(line.substring(5));
                    break;
                default:
                    throw new RuntimeException("invalid command at line " + i);
            }
        }
        //int result = 0;


        return Integer.toString(getSizeStar1(root));
    }

    public int getSizeStar2(Folder folder){
        int min = Integer.MAX_VALUE;
        for(IFile file : folder.getFiles()){
            if(! (file instanceof Folder child)){
                continue;
            }
            int childMin = getSizeStar2(child);
            if(childMin >= requiredSpace && childMin < min){
                min = childMin;
            }

            int folderSize = child.getSize();
            if(folderSize >= requiredSpace && folderSize < min){
                min = folderSize;
            }
        }
        return min;
    }

    public int getSizeStar1(Folder folder){
        int sum = 0;
        for(IFile file : folder.getFiles()){
            if(! (file instanceof Folder child)){
                continue;
            }
            sum += getSizeStar1(child);
            int folderSize = child.getSize();
            if(folderSize < 100000){
                sum += folderSize;
            }
        }
        return sum;
    }

    @Override
    public String secondStar() throws Exception{
        root = new Folder("/");
        currentFolder = root;

        List<String> input = PuzzleInputParser.parseToStringList(puzzleInputPath);

        for(int i = 0; i < input.size(); i++){
            String line = input.get(i);
            if(line.charAt(0) != '$'){
                throw new RuntimeException("no dollar sign at line " + i);
            }
            String command = line.substring(2,4);
            switch (command){
                case "ls":
                    i = list(input, i + 1) - 1;
                    break;
                case "cd":
                    changeDirectory(line.substring(5));
                    break;
                default:
                    throw new RuntimeException("invalid command at line " + i);
            }
        }

        requiredSpace = - 40000000 + root.getSize();

        return Integer.toString(getSizeStar2(root));
    }

    public void changeDirectory(String directoryName){
        if(directoryName.equals("..")){
            currentFolder = (Folder) currentFolder.getParent();
            return;
        }

        if(directoryName.equals("/")){
            currentFolder = root;
            return;
        }

        currentFolder = (Folder) currentFolder.getFile(directoryName);
    }

    public int list(List<String> input, int startLine) {
        int i = startLine;
        while (i < input.size() && input.get(i).charAt(0) != '$'){
            String[] splitted = input.get(i).split(" ");
            if(splitted[0].equals("dir")){
                currentFolder.addFile(new Folder(splitted[1]));
            } else{
                currentFolder.addFile(new File(Integer.valueOf(splitted[0]), splitted[1]));
            }
            i++;
        }
        return i;
    }
}
