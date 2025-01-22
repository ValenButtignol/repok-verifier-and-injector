package system.executor;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import system.completion.RepOkCompletion;

public abstract class LlmExecutor {

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

    // TODO: VER si hacen falta solo dos métodos y en algunos se implementan unos y en otros no.
    // Porque acá habría mucho código repetido.
    public abstract RepOkCompletion getRepOkCompletion();

    public String execute() {
        System.out.println("Ejecutando LlmExecutor");
        String pythonPath = "python3";
        String scriptPath = "llm-repok-generator/main.py"; 

        // Script args
        String[] command = {
            pythonPath,
            scriptPath,
            "-mn", modelName,
            "-pc", classString, 
            "-pt", promptType,
            "-ot", "console"     //TODO implement a "return" type of prompt.
        };
        
        String output = "";
        try {
            System.out.println("Ejecutando comando: ");
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                output += line + "\n";
            }

            int exitCode = process.waitFor();
            System.out.println("El proceso terminó con código: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    };
}
