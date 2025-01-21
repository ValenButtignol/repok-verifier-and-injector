package system.classparser;

import java.io.File;
import java.util.Scanner;

public abstract class ClassParser {

    protected String filepath;
    protected StringBuilder inputBuilder;
    protected String codeSnippet;
    protected Integer startIndex;
    protected Integer endIndex;

    public ClassParser(String filepath) {
        this.filepath = filepath;
        inputBuilder = new StringBuilder();
        this.codeSnippet = "";
    }

    protected void readFile() {
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

    public abstract String parseClass();
}
