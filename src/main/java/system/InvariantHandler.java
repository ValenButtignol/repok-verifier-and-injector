package system;

import java.io.*;
import java.util.*;
import com.github.javaparser.ast.body.MethodDeclaration;

public class InvariantHandler {
    private JavaClassEditor classEditor;
    private InvariantVerifier verifier;
    private RepOkBuilder repOkBuilder;
    private List<MethodDeclaration> lastInjections = new ArrayList<>();

    public InvariantHandler(File classFile, String className) throws IOException {
        this.classEditor = new JavaClassEditor(classFile, className);
        this.verifier = new InvariantVerifier();
        this.repOkBuilder = new RepOkBuilder(classEditor);
    }

    public void inject(MethodDeclaration method) {
        String uniqueName = classEditor.getUniqueMethodName(method.getNameAsString());
        method.setName(uniqueName);
        classEditor.addMethod(method);
        lastInjections.add(method);
        classEditor.writeToFile();
    }

    public void deleteLastInjections() {
        lastInjections.forEach(classEditor::removeMethod);
        lastInjections.clear();
        classEditor.writeToFile();
    }

    public void verify() {
        List<MethodDeclaration> validInvariants = verifier.verify(lastInjections);
        if (validInvariants.isEmpty()) {
            deleteLastInjections();
        }
        repOkBuilder.addVerifiedInvariants(validInvariants);
    }

    public void updateRepOk() {
        repOkBuilder.updateRepOk();
    }

    public void createRepOk() {
        repOkBuilder.createRepOk();
    }

    public void cleanLogs() {
        new File(StringConstants.RANDOOP_LOG_PATH).delete();
        new File(StringConstants.COMPILE_LOG_PATH).delete();
    }
}
