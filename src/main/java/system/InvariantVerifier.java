package system;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.MethodDeclaration;

public class InvariantVerifier {
    public List<MethodDeclaration> verify(List<MethodDeclaration> methods) {
        List<MethodDeclaration> verified = new ArrayList<>();
        ProcessBuilder processBuilder = new ProcessBuilder(StringConstants.RANDOOP_SCRIPT_PATH);
        processBuilder.redirectErrorStream(true);
        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 1) {
                verified.addAll(methods);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verified;
    }
}