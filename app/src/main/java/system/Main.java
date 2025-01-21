package system;

import system.completion.RepOkCompletion;
import system.executor.GlobalRepOkExecutor;
import system.executor.LlmExecutor;
import system.repokinjector.RepOkInjector;
import system.classparser.ClassParser;
import system.classparser.GlobalRepOkParser;

public class Main {
    public static void main(String[] args) {

        String filename = "app/src/main/java/specclasses/LinkedList.java";
        String classname = "LinkedList";
        String modelname = "Llama3.2";
        String promptType = "GlobalRepOk";
        
        ClassParser classParser = new GlobalRepOkParser(filename, classname);    
        LlmExecutor llmExecutor = new GlobalRepOkExecutor(classParser.parseClass(), modelname, filename, promptType);
        RepOkCompletion repOkCompletion = llmExecutor.execute();
        RepOkInjector repOkInjector = new RepOkInjector(filename, classname, repOkCompletion.getRepOk());
        repOkInjector.injectRepOk();
    }
}