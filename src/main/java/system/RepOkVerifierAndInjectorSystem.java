package system;

import java.io.File;
import java.io.IOException;

import system.editor.JavaClassEditor;

public class RepOkVerifierAndInjectorSystem {

    // private InvariantHandler invariantHandler;
    private InvariantParser invariantParser;
    private ClassProcessor classProcessor;
    private JavaClassEditor classEditor;
    private InvariantVerifier invariantVerifier;
    private String targetClassPath;
    private String targetClassName;
    private String invariantClassPath;

    public RepOkVerifierAndInjectorSystem(String targetClassPath, String targetClassName, String invariantClassPath) {
        this.targetClassPath = targetClassPath;
        this.targetClassName = targetClassName;
        this.invariantClassPath = invariantClassPath;

        classProcessor = new ClassProcessor(targetClassPath, targetClassName);
        classProcessor.processClassList();
        File targetClassCopy = classProcessor.generateCopy();

        invariantParser = new InvariantParser(invariantClassPath);
        classEditor = new JavaClassEditor(targetClassCopy.toPath(), targetClassName);
        invariantVerifier = new InvariantVerifier();
    }

    public void run() {
        invariantParser.parse();
        try {
            classEditor.injectSnippet(invariantParser.getInvariantSnippet());
            classEditor.adjustClass();
            if (!invariantVerifier.verify()) {
                cleanUp();
                return;
            }
            classEditor.createOrUpdateComposedRepOK();
        } catch (IOException e) {
            cleanUp();
            
            throw new RuntimeException("Failed creating RepOK for class " + targetClassName);
        }

        classProcessor.copyBack();
        cleanUp();
    }

    private void cleanUp() {
        classProcessor.deleteCopy();
        classProcessor.cleanClassList();
    }

}
