package adventOfCode2022.day7;

public interface IFile {
    int getSize();
    String getName();
    void setParent(IFile folder);

    IFile getParent();
}
