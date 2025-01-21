package system.executor;

import system.completion.RepOkCompletion;

public interface LlmExecutor {
    RepOkCompletion execute();
}
