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
        String content = "";
        try {
            content = Files.readString(Path.of(invariantClassPath));
        } catch (Exception e) {
            throw new RuntimeException("Error reading RepOK file");
        }

        int start = content.indexOf('{');
        int end = content.lastIndexOf('}');

        if (start != -1 && end != -1 && start < end) {
            invariantSnippet = content.substring(start + 1, end).trim(); // Excluye las llaves
        } else {
            throw new IllegalArgumentException("RepOK file format is not correct.");
        }
    }

    public String getInvariantSnippet() {
        return invariantSnippet;
    } 
}