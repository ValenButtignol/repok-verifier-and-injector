package system;

import java.io.File;

import system.classfixer.ClassFixer;
import system.codeparser.InvariantParser;
import system.codeparser.PropertiesClassParser;
import system.codeparser.RepOkClassParser;
import system.invhandler.InvariantHandler;
import system.invhandler.PropertiesHandler;
import system.invhandler.RepOkHandler;

public class Main {
    public static void main(String[] args) {
        String classPath = "src/../../LinkedList.java";
        String className = "LinkedList";
        

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

        System.out.println(invariantHandler.getVerifiedInvariants());
        


        // TODO: In some way i need to add the repOk method to the original class
        
        //classFixer.deleteCopy();

    }
}