package system;

import java.io.File;

import system.codeinjector.CodeInjector;
import system.codeinjector.RepOkInjector;
import system.codeparser.CodeParser;
import system.codeparser.RepOkClassParser;
import system.verifier.RepOkVerifier;
import system.verifier.Verifier;

public class Main {
    public static void main(String[] args) {

        File classFile = new File("src/main/java/system/LinkedList.java");
        String className = "LinkedList";
        //String promptType = "";
        
        CodeParser codeParser = new RepOkClassParser();
        codeParser.parse();
        CodeInjector repOkInjector = new RepOkInjector(classFile, className, codeParser.getMethods());
        repOkInjector.inject();

        //Verifier verifier = new RepOkVerifier(repOkInjector);
        //verifier.verify();
    }
}