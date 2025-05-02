package system;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InvariantVerifier {
    public boolean verify() {
        boolean passed = false;
        ProcessBuilder processBuilder = new ProcessBuilder(StringConstants.RANDOOP_SCRIPT_PATH);
        processBuilder.redirectErrorStream(true);
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();

            if (exitCode == 1) {
                passed = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return passed;
    }
}