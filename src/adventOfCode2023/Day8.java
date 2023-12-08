package adventOfCode2023;
import adventOfCode2023.day8.Node;
import common.PuzzleInputParser;
import common.PuzzleSolution;

import java.util.*;

public class Day8 extends PuzzleSolution {

    private static final int YEAR = 2023;
    private static final int DAY_NUMBER = 8;

    public static void main(String[] args) throws Exception{
        System.out.println("Tests :");
        Day8 test = new Day8(String.format("tests/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println("(1) " + test.firstStar());
        test = new Day8(String.format("tests/%d/%db.txt", YEAR, DAY_NUMBER));
        System.out.println("(2) " + test.secondStar());

        System.out.println("\nActual answers");
        Day8 actual = new Day8(String.format("inputs/%d/%d.txt", YEAR, DAY_NUMBER));
        System.out.println("(1) " + actual.firstStar());
        System.out.println("(2) " + actual.secondStar());

        test();
    }

    private static void test(){
        System.out.println(Long.MAX_VALUE);
        Day8 day8 = new Day8("");
        int[] tab = {
            12,
            15,
            32,
            35
        };
        day8.simplifyBrowings(tab);
        System.out.println(Arrays.toString(tab));
        System.out.println(day8.computeStar2Solution(tab));
    }

    public Day8(String puzzleInputPath) {
        super(puzzleInputPath);
    }

    @Override
    public String firstStar() throws Exception {
        List<String> input = PuzzleInputParser.parseToStringList(puzzleInputPath);
        Node root = parseNodes(input);
        return Integer.toString(browseNodes(input.get(0), root));
    }

    @Override
    public String secondStar() throws Exception{
        List<String> input = PuzzleInputParser.parseToStringList(puzzleInputPath);
        List<Node> startingNodes = parseNodes2(input);
        int[] browsings = new int[startingNodes.size()];
        for (int i = 0; i < browsings.length; i++) {
            browsings[i] = browseNodes3(input.get(0), startingNodes.get(i));
        }

        System.out.println(verifyBrowsings(browsings, Long.parseLong("76073425487")));

        simplifyBrowings(browsings);
        return Long.toString(computeStar2Solution(browsings));
    }

    private int browseNodes(String instructionsLine, Node root){
        int lineLength = instructionsLine.length();
        Node currentNode = root;
        int steps = 0, i = 0;
        while(! currentNode.getLabel().equals("ZZZ")){
            if(i >= lineLength){
                i = 0;
            }
            char instruction = instructionsLine.charAt(i);
            currentNode = instruction == 'L' ? currentNode.getLeftChild() : currentNode.getRightChild();
            steps++;
            i++;
        }
        return steps;
    }

    private int browseNodes2(String instructionsLine, List<Node> startingNodes){
        Node[] currentNodes = startingNodes.toArray(new Node[0]);
        boolean done = false;
        int steps = -1, i = 0, lineLength = instructionsLine.length();
        while(! done && steps < Integer.MAX_VALUE){
            if(i >= lineLength){
                i = 0;
            }
            char instruction = instructionsLine.charAt(i);

            int nonZCount = 0;
            //StringBuilder logLine = new StringBuilder();
            for(int n = 0; n < currentNodes.length; n++){
               //logLine.append(currentNodes[n].getLabel()).append(" | ");
                if(currentNodes[n].getLabel().charAt(2) != 'Z'){
                    nonZCount++;
                }
                currentNodes[n] = instruction == 'L' ? currentNodes[n].getLeftChild() : currentNodes[n].getRightChild();
            }

            if(nonZCount == 0){
                done = true;
            }

            steps++;
            i++;
        }
        return steps;
    }

    private int browseNodes3(String instructionsLine, Node root){
        int lineLength = instructionsLine.length();
        Node currentNode = root;
        int steps = 0, i = 0;
        while(currentNode.getLabel().charAt(2) != 'Z'){
            if(i >= lineLength){
                i = 0;
            }
            char instruction = instructionsLine.charAt(i);
            currentNode = instruction == 'L' ? currentNode.getLeftChild() : currentNode.getRightChild();
            steps++;
            i++;
        }
        return steps;
    }

    private void simplifyBrowings(int[] browsings){
        int pgcd = pgcd(browsings);
        for(int i = 0; i < browsings.length; i++){
            browsings[i] = browsings[i] / pgcd;
        }
    }

    private int pgcd(int n1, int n2){
        int u0 = 1, u1 = 0;
        int v0 = 0, v1 = 1;

        while(n2 != 0){
            int q = n1 / n2;
            int r = n1 - n2 * q;
            int u = u0 - u1 * q;
            int v = v0 - v1 * q;
            n1 = n2;
            n2 = r;
            u0 = u1;
            u1 = u;
            v0 = v1;
            v1 = v;
        }
        return n1;
    }

    private int pgcd(int... n){
        int value = pgcd(n[0], n[1]);
        for(int i = 2; i < n.length; i++){
            value = pgcd(value, n[i]);
        }
        return value;
    }

    private long computeStar2Solution(int[] browsings){
        long product = 1;
        for(int value : browsings){
            product *= value;
        }
        return product * 293;
    }
    private Node parseNodes(List<String> input){
        HashMap<String, Node> nodesMap = new HashMap<>();

        for(int i = 2; i < input.size(); i++){
            String line = input.get(i);
            if(line.isEmpty()){
                continue;
            }

            String[] splittedLine = line.split(" = ");
            Node node =  nodesMap.containsKey(splittedLine[0]) ? nodesMap.get(splittedLine[0]) :
                    new Node(splittedLine[0]);
            // Take the right part, removing parenthesis
            String children = splittedLine[1].substring(1, splittedLine[1].length() - 1);
            String[] seperatedLabels = children.split(", ");

            Node leftChild =  nodesMap.containsKey(seperatedLabels[0]) ? nodesMap.get(seperatedLabels[0]) :
                    new Node(seperatedLabels[0]);

            Node rightChild;

            if(seperatedLabels[0].equals(seperatedLabels[1])){
                rightChild = leftChild;
            } else{
                rightChild =  nodesMap.containsKey(seperatedLabels[1]) ? nodesMap.get(seperatedLabels[1]) :
                        new Node(seperatedLabels[1]);
            }

            node.setLeftChild(leftChild);
            node.setRightChild(rightChild);

            Node[] nodes = {node, leftChild, rightChild};
            for(Node currentNode : nodes){
                if(! nodesMap.containsKey(currentNode.getLabel())){
                    nodesMap.put(currentNode.getLabel(), currentNode);
                }
            }
        }
        if(! nodesMap.containsKey("AAA")){
            throw new RuntimeException("no AAA node");
        }
        return nodesMap.get("AAA");
    }

    private List<Node> parseNodes2(List<String> input){
        HashMap<String, Node> nodesMap = new HashMap<>();
        ArrayList<Node> startingNodes = new ArrayList<>();

        for(int i = 2; i < input.size(); i++){
            String line = input.get(i);
            if(line.isEmpty()){
                continue;
            }

            String[] splittedLine = line.split(" = ");
            Node node =  nodesMap.containsKey(splittedLine[0]) ? nodesMap.get(splittedLine[0]) :
                    new Node(splittedLine[0]);
            // Take the right part, removing parenthesis
            String children = splittedLine[1].substring(1, splittedLine[1].length() - 1);
            String[] seperatedLabels = children.split(", ");

            Node leftChild =  nodesMap.containsKey(seperatedLabels[0]) ? nodesMap.get(seperatedLabels[0]) :
                    new Node(seperatedLabels[0]);

            Node rightChild;

            if(seperatedLabels[0].equals(seperatedLabels[1])){
                rightChild = leftChild;
            } else{
                rightChild =  nodesMap.containsKey(seperatedLabels[1]) ? nodesMap.get(seperatedLabels[1]) :
                        new Node(seperatedLabels[1]);
            }

            node.setLeftChild(leftChild);
            node.setRightChild(rightChild);

            Node[] nodes = {node, leftChild, rightChild};
            for(Node currentNode : nodes){
                if(! nodesMap.containsKey(currentNode.getLabel())){
                    nodesMap.put(currentNode.getLabel(), currentNode);
                }
            }

            if(node.getLabel().charAt(2) == 'A'){
                startingNodes.add(node);
            }
        }
        return startingNodes;
    }

    private boolean verifyBrowsings(int[] browsings, long value){
        for(int browsing : browsings){
            if(value % browsing != 0){
                return false;
            }
        }
        return true;
    }
}
