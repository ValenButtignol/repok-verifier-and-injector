package system;

import java.io.File;

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
        try {
            invariantParser.parse();
            classEditor.injectSnippet(invariantParser.getInvariantSnippet());
            classEditor.adjustClass();
            if (!invariantVerifier.verify()) {
                cleanUp();
                return;
            }
            classEditor.createOrUpdateComposedRepOK();
        } catch (Exception e) {
            cleanUp();
            System.err.println("\u001B[31m ERROR\u001B[0m :Failed creating RepOK for class " + targetClassName);
            return ;
        }

        classProcessor.copyBack();
        cleanUp();
    }

    private void cleanUp() {
        classProcessor.deleteCopy();
        classProcessor.cleanClassList();
    }

}
