package system.repokinjector;

import java.io.File;
import java.io.FileWriter;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class RepOkInjector {
    private File classFile;
    private String className;
    private String repOkClassString;
    
    public RepOkInjector(String classPath, String className, String repOkClassString) {
        classFile = new File(classPath);
        this.className = className;
        this.repOkClassString = repOkClassString;
    }

    public void injectRepOk() {
        try {
            CompilationUnit classCu = StaticJavaParser.parse(classFile);
            CompilationUnit repOkCu = StaticJavaParser.parse(repOkClassString);
            
            ClassOrInterfaceDeclaration classDeclaration = classCu.getClassByName(className).orElseThrow(
                () -> new RuntimeException("Class: " + className + " not found")
            );
            
            classCu.addImport("java.util.*");               // In case the repOk uses java.util.*.
            classCu.addImport("randoop.CheckRep");   // For the @CheckRep annotation.

            repOkCu.findAll(MethodDeclaration.class).forEach(method -> {
                if (method.getNameAsString().equals("repOK")) {
                    method.addAnnotation("CheckRep");
                };
                classDeclaration.addMember(method);
                System.out.println(method.toString());
            });

            FileWriter writer = new FileWriter(classFile);
            writer.write(classCu.toString());
            writer.close();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
