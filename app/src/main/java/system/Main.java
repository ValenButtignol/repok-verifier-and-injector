package system;

import java.io.File;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

public class Main {
    public static void main(String[] args) {
        
        CodeParser codeParser = new SimpleCodeParser("app/src/main/resources/repok1.txt");
        try {
            // Ruta al archivo Java (el archivo puede no tener una clase)
            File file = new File("Example.java");

            // Analiza el archivo y crea un CompilationUnit
            CompilationUnit compilationUnit = StaticJavaParser.parse(file);

            // Encuentra todos los métodos en el archivo, incluso si no están dentro de una clase explícita
            compilationUnit.findAll(MethodDeclaration.class).forEach(method -> {
                System.out.println("Method Name: " + method.getName());
                System.out.println("Return Type: " + method.getType());
                System.out.println("Parameters: " + method.getParameters());
                System.out.println();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //RepOkInjector repOkInjector = new RepOkInjector("app/src/main/resources/LinkedList.java", "LinkedList", codeParser.parse());
        //repOkInjector.injectRepOk();
    }
}