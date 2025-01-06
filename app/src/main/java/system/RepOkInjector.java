package system;

import java.io.File;
import java.io.FileWriter;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;


public class RepOkInjector {
    private File classFile;
    private String className;
    private String repOkString;
    
    public RepOkInjector(String classPath, String className, String repOkString) {
        classFile = new File(classPath);
        this.className = className;
        this.repOkString = repOkString;
    }

    public void injectRepOk() {
        try {
            CompilationUnit cu = StaticJavaParser.parse(classFile);
            ClassOrInterfaceDeclaration linkedListClass = cu.getClassByName(className).orElseThrow(() -> new RuntimeException("Clase LinkedList no encontrada"));

            MethodDeclaration repOKMethod = StaticJavaParser.parseMethodDeclaration(repOkString);
            System.out.println(cu.toString());
            //linkedListClass.addMember(repOKMethod);
            //FileWriter writer = new FileWriter(classFile);
            //writer.write(cu.toString());
            //writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
