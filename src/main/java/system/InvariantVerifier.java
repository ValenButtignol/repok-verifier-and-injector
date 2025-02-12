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

            if (exitCode == -1) {
                System.out.println("\u001B[31mERROR\u001B[0m: Check logs.");
            } else if (exitCode == 0) {
                System.out.println("\u001B[31mERROR\u001B[0m: Error test generated. Check repOk.");
            } else {
                System.out.println("\u001B[32mSUCCESS\u001B[0m: Class invariant verified.");
                verified.addAll(methods);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verified;
    }
}