package system.executor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import system.StringConstants;
import system.completion.RepOkCompletion;

public abstract class LlmExecutor {

    private static final Logger LOGGER = Logger.getLogger(LlmExecutor.class.getName());
    private static final String PYTHON_PATH = "python3";
    private static final String SCRIPT_PATH = "tools/llm-repok-generator/main.py";
    private static final String OUTPUT_TYPE = "console";

    protected String classString;
    protected String modelName;
    protected String fileName;
    protected String promptType;

    public LlmExecutor(String classString, String modelName, String fileName, String promptType) {
        this.classString = classString;
        this.modelName = modelName;
        this.fileName = fileName;
        this.promptType = promptType;
    }

    public abstract RepOkCompletion getRepOkCompletion();

    public String execute() {
        String[] command = buildCommand();
        StringBuilder output = new StringBuilder();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                int separatorCount = 0;
                String line;
                while ((line = reader.readLine()) != null) {
                    if (StringConstants.PROMPT_SEPARATOR.equals(line)) {
                        separatorCount++;
                    }
                    if (separatorCount >= 3) {
                        output.append(line).append("\n");
                    }
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                LOGGER.log(Level.WARNING, "WARNING: Process exited with non-zero code: " + exitCode);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while executing LLM script", e);
        }

        return output.toString();
    }

    private String[] buildCommand() {
        return new String[] {
            PYTHON_PATH,
            SCRIPT_PATH,
            "-mn", modelName,
            "-pc", classString,
            "-pt", promptType,
            "-ot", OUTPUT_TYPE
        };
    }
}
