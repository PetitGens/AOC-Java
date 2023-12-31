package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Matrix<T> {
    private final ArrayList<List<T>> matrix;

    public Matrix(){
        matrix = new ArrayList<>();
    }
    public void addRow(){
        matrix.add(new ArrayList<>());
    }

    public void addRow(T[] array){
        ArrayList<T> newRow = new ArrayList<>(Arrays.asList(array));
        matrix.add(newRow);
    }

    public void addRow(List<T> list){
        matrix.add(list);
    }

    public void removeRow(int index){
        matrix.remove(index);
    }

    public T get(int column, int row){
        return matrix.get(row).get(column);
    }

    public T set(int column, int row, T value){
        return matrix.get(row).set(column, value);
    }

    public List<List<T>> rowList(){
        return matrix;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     * @apiNote In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * The string output is not necessarily stable over time or across
     * JVM invocations.
     * @implSpec The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for(List<T> line : matrix){
            for (T element : line){
                output.append(element).append(" ");
            }
            output.append('\n');
        }
        return output.toString();
    }
}
