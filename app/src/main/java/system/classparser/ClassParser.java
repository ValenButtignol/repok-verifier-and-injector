package system.classparser;

import java.io.File;
import java.util.Scanner;

public abstract class ClassParser {

    protected String classFilePath;
    protected String className;
    protected String classString;

    public ClassParser(String classFilePath, String className) {
        this.classFilePath = classFilePath;
        this.className = className;
    }
    
    protected void readFile() {
        StringBuilder classBuilder = new StringBuilder();
        try {
            File file = new File(classFilePath);
            Scanner scanner = new Scanner(file);
            classBuilder.delete(0, classBuilder.length());
            while (scanner.hasNextLine()) {
                classBuilder.append(scanner.nextLine() + "\n");
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.classString = classBuilder.toString();
    }

    public abstract String parseClass();
}
