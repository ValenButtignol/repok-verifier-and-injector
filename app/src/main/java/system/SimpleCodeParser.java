package system;

import java.io.File;
import java.util.Scanner;

public class SimpleCodeParser implements CodeParser {
    private String filepath;
    private StringBuilder inputBuilder;
    private String codeSnippet;
    private Integer startIndex;
    private Integer endIndex;


    public SimpleCodeParser(String filepath) {
        this.filepath = filepath;
        inputBuilder = new StringBuilder();
        this.codeSnippet = "";
    }

    public String parse() {
        this.readFile();

        startIndex = inputBuilder.indexOf(Delimiters.START_CODE_SNIPPET) + Delimiters.START_CODE_SNIPPET.length();
        endIndex = inputBuilder.indexOf(Delimiters.END_CODE_SNIPPET, startIndex);

        codeSnippet = inputBuilder.substring(startIndex, endIndex);
        return codeSnippet;
    }
    
    private void readFile() {
        try {
            File file = new File(filepath);
            Scanner scanner = new Scanner(file);
            inputBuilder.delete(0, inputBuilder.length());
            while (scanner.hasNextLine()) {
                inputBuilder.append(scanner.nextLine() + "\n");
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
