package system;

import system.completion.RepOkCompletion;
import system.executor.GlobalRepOkExecutor;
import system.executor.LlmExecutor;
import system.repokinjector.RepOkInjector;
import system.classparser.ClassParser;
import system.classparser.WholeClassParser;

public class Main {
    public static void main(String[] args) {
        String filename = "app/src/main/java/specclasses/LinkedList.java";
        String classname = "LinkedList";
        String modelname = "Llama3.2";
        String promptType = "global";
        
        ClassParser classParser = new WholeClassParser(filename, classname);  
        LlmExecutor llmExecutor = new GlobalRepOkExecutor(classParser.parseClass(), modelname, filename, promptType);
        // llmExecutor.execute();
        // RepOkCompletion repOkCompletion = llmExecutor.getRepOkCompletion();
        // RepOkInjector repOkInjector = new RepOkInjector(filename, classname, repOkCompletion.getRepOk());
        // repOkInjector.injectRepOk();
    }
}