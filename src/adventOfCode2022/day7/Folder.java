package adventOfCode2022.day7;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Folder implements IFile{
    private final ArrayList<IFile> files;
    private final String name;
    private Folder parent;

    public Folder(String name) {
        files = new ArrayList<>();
        this.name = name;
    }

    public void addFile(IFile file){
        try{
            getFile(file.getName());
            return;
        }
        catch (NoSuchElementException ignored){}
        files.add(file);
        file.setParent(this);
    }

    public List<IFile> getFiles(){
        return files;
    }

    public IFile getFile(String name){
        for(IFile file : files){
            if(file.getName().equals(name)){
                return file;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public int getSize() {
        int sum = 0;
        for(IFile file : files){
            sum += file.getSize();
        }
        return sum;
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
