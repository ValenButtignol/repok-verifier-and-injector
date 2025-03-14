package system;

import java.io.File;

import system.classfixer.ClassFixer;

public class RepOkVerifierAndInjectorSystem {

    private InvariantHandler invariantHandler;
    private InvariantParser invariantParser;
    private ClassFixer classFixer;
    private String classPath;
    private String className;
    private String invariantClassPath;

    public RepOkVerifierAndInjectorSystem(String classPath, String className, String invariantClassPath){
        this.classPath = classPath;
        this.className = className;
        this.invariantClassPath = invariantClassPath;

        classFixer = new ClassFixer(classPath, className);
        classFixer.writeClassList();
        File classFile = classFixer.generateCopy();
        invariantParser = new InvariantParser(invariantClassPath);
        try {
            invariantHandler = new InvariantHandler(classFile, className);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        invariantParser.parse();
        invariantHandler.createRepOk();
        invariantParser.getParsedMethods().forEach(method -> {
            invariantHandler.inject(method);
        });
        invariantHandler.verify();
        invariantHandler.updateRepOk();
        classFixer.copyBack();
        classFixer.deleteCopies();
        classFixer.deleteClassList();
    }
}
