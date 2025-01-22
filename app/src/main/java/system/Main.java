package system;

import system.classparser.ClassParser;
import system.classparser.WholeClassParser;
import system.executor.GlobalRepOkExecutor;
import system.executor.LlmExecutor;

public class Main {
    public static void main(String[] args) {
        String filename = "app/src/main/java/specclasses/LinkedList.java";
        String classname = "LinkedList";
        String modelname = "llama3.2-3b";
        String promptType = "global";

        ClassParser classParser = new WholeClassParser(filename, classname);  
        LlmExecutor llmExecutor = new GlobalRepOkExecutor(classParser.parseClass(), modelname, filename, promptType);
        System.out.println("\n\nOUTPUT:\n\n" + llmExecutor.execute());

        // RepOkCompletion repOkCompletion = llmExecutor.getRepOkCompletion();
        // RepOkInjector repOkInjector = new RepOkInjector(filename, classname, repOkCompletion.getRepOk());
        // repOkInjector.injectRepOk();
    }
}