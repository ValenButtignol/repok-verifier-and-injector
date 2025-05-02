package system;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class InvariantParser {
    
    private File invariantClass;
    private String invariantSnippet;
    
    public InvariantParser(String invariantClassPath) {
        this.invariantSnippet = "";
        this.invariantClass = new File(invariantClassPath);
    }

    public void parse() {
        String content = "";
        try {
            content = Files.readString(invariantClass.toPath());
        } catch (IOException e) {
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