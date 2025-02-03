package system;

import java.io.File;

import system.classfixer.ClassFixer;
import system.codeinjector.CodeInjector;
import system.codeinjector.RepOkInjector;
import system.codeparser.CodeParser;
import system.codeparser.RepOkClassParser;

public class Main {
    public static void main(String[] args) {
        String classPath = "src/../../LinkedList.java";
        String className = "LinkedList";
        
        ClassFixer classFixer = new ClassFixer(classPath, className);
        File classFile = classFixer.generateCopy();
        classFixer.rewriteClassList();
        //String promptType = "";
        
        CodeParser codeParser = new RepOkClassParser();
        codeParser.parse();
        CodeInjector repOkInjector = new RepOkInjector(classFile, className, codeParser.getMethods());
        repOkInjector.inject();
        Verifier verifier = new Verifier();
        verifier.verify();

        // TODO: In some way i need to add the repOk method to the original class
        
        //classFixer.deleteCopy();

    }
}