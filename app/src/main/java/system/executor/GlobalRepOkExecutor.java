package system.executor;

import system.completion.GlobalRepOkCompletion;
import system.completion.RepOkCompletion;

public class GlobalRepOkExecutor extends LlmExecutor {
    public GlobalRepOkExecutor(String classString, String modelName, String fileName, String promptType) {
        super(classString, modelName, fileName, promptType);
    }

    public RepOkCompletion getRepOkCompletion() {
        RepOkCompletion repOkCompletion = new GlobalRepOkCompletion();
        

        return repOkCompletion;
    }
}
