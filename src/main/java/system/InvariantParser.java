package system;

import java.nio.file.Files;
import java.nio.file.Path;

public class InvariantParser {
    
    private String invariantClassPath;
    private String invariantSnippet;
    
    public InvariantParser(String invariantClassPath) {
        this.invariantSnippet = "";
        this.invariantClassPath = invariantClassPath;
    }

    public void parse() {
        try {
            invariantSnippet = Files.readString(Path.of(invariantClassPath));
        } catch (Exception e) {
            throw new RuntimeException("Error reading RepOK file");
        }
    }

    public String getInvariantSnippet() {
        return invariantSnippet;
    } 
}