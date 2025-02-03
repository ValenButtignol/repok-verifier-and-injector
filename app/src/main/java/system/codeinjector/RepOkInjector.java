package system.codeinjector;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class RepOkInjector implements CodeInjector {
    private File classFile;
    private String className;
    private List<MethodDeclaration> repOkMethods;
    private CompilationUnit classCu;
    
    public RepOkInjector(File classFile, String className, List<MethodDeclaration> repOkMethods) {
        this.classFile = classFile;
        this.className = className;
        this.repOkMethods = repOkMethods;
        try {
            this.classCu = StaticJavaParser.parse(classFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inject() {
        try {
            ClassOrInterfaceDeclaration classDeclaration = classCu.getClassByName(className).orElseThrow(
                () -> new RuntimeException("Class: " + className + " not found")
            );

            classCu.addImport("java.util.*");
            classCu.addImport("randoop.CheckRep");

            repOkMethods.forEach(method -> {
                if (method.getNameAsString().equals("repOK")) {
                    method.addAnnotation("CheckRep");
                };
                classDeclaration.addMember(method);
            });

            FileWriter writer = new FileWriter(classFile);
            writer.write(classCu.toString());
            writer.close();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
