package system;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Verifier {

    public Integer verify() {
        Integer exitCode = -1;

        String scriptPath = StringConstants.RANDOOP_SCRIPT_PATH;
        ProcessBuilder processBuilder = new ProcessBuilder(scriptPath);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();

            // Capture script output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                output.append(line).append("\n");
            }

            exitCode = process.waitFor();
            System.out.println("Exit code: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exitCode;
    }
}


