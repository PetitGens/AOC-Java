package adventOfCode2023.day8;

public class Node {
    private final String label;
    private Node leftChild;
    private Node rightChild;

    public Node(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }
}
