package system;

import java.io.File;

import system.classfixer.ClassFixer;
import system.invhandler.InvariantHandler;
import system.invhandler.PropertiesHandler;
import system.invhandler.RepOkHandler;
import system.invparser.InvariantParser;
import system.invparser.PropertiesClassParser;
import system.invparser.RepOkClassParser;

public class Main {
    public static void main(String[] args) {
        String classPath = "src/../../LinkedList.java";
        String className = "LinkedList";
        //String promptType = "";

        ClassFixer classFixer = new ClassFixer(classPath, className);
        classFixer.rewriteClassList();
        File classFile = classFixer.generateCopy();
        
        // InvariantHandler invariantHandler = new RepOkHandler(classFile, className);
        // InvariantParser codeParser = new RepOkClassParser();
        // codeParser.parse();
        // codeParser.getInvariants().forEach(method -> {
        //     invariantHandler.inject(method);
        // });
        // invariantHandler.verify();
        // invariantHandler.deleteLastInjections();
        // invariantHandler.buildRepOk();
        // classFixer.copyBack();
        // // invariantHandler.deleteLastInjections();
        // classFixer.deleteCopy();
        

        InvariantHandler invariantHandler = new PropertiesHandler(classFile, className);
        InvariantParser codeParser = new PropertiesClassParser();
        codeParser.parse();
        codeParser.getInvariants().forEach(method -> {
            invariantHandler.inject(method);
            invariantHandler.verify();
            invariantHandler.deleteLastInjections();
        });
        invariantHandler.buildRepOk();
        classFixer.copyBack();
        classFixer.deleteCopy();
    }
}