package system.editor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SnippetInjector {
    private final Path targetClassPath;

    public SnippetInjector(Path targetClassPath) {
        this.targetClassPath = targetClassPath;
    }

    public void inject(String invariantSnippet) throws IOException {
        String classCode = Files.readString(targetClassPath);
        int lastBraceIndex = classCode.lastIndexOf('}');
        
        if (lastBraceIndex == -1) {
            throw new RuntimeException("Incomplete Class: '}' not found.");
        }

        String updatedCode = classCode.substring(0, lastBraceIndex) 
                           + "\n" + invariantSnippet + "\n" 
                           + classCode.substring(lastBraceIndex);
        
        Files.writeString(targetClassPath, updatedCode);
    }
}