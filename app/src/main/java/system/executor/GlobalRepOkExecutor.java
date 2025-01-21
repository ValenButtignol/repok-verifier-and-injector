package system.executor;

import system.completion.GlobalRepOkCompletion;
import system.completion.RepOkCompletion;

public class GlobalRepOkExecutor implements LlmExecutor {
    public GlobalRepOkExecutor(String classString, String modelName, String fileName, String promptType) {
        
    }

    public RepOkCompletion execute() {
        RepOkCompletion repOkCompletion = new GlobalRepOkCompletion();
        return repOkCompletion;
    }
}
