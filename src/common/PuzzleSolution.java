package common;

import java.io.IOException;

public abstract class PuzzleSolution {
    protected String puzzleInputPath;

    public PuzzleSolution(String puzzleInputPath){
        this.puzzleInputPath = puzzleInputPath;
    }

    public abstract String firstStar() throws Exception;
    public abstract String secondStar() throws Exception;
}
