package adventOfCode2022.day7;

public class File implements IFile {
    private final int size;
    private final String name;
    private Folder parent = null;

    public File(int size, String name) {
        this.size = size;
        this.name = name;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setParent(IFile folder) {
        parent = (Folder) folder;
    }

    @Override
    public IFile getParent() {
        return parent;
    }
}
