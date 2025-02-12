package system;

import java.io.*;
import java.util.*;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class JavaClassEditor {
    private File classFile;
    private CompilationUnit classCu;
    private ClassOrInterfaceDeclaration classDeclaration;

    public JavaClassEditor(File classFile, String className) throws IOException {
        this.classFile = classFile;
        this.classCu = StaticJavaParser.parse(classFile);
        this.classDeclaration = classCu.getClassByName(className).orElseThrow(
            () -> new RuntimeException("Class: " + className + " not found")
        );
    }

    public ClassOrInterfaceDeclaration getClassDeclaration() {
        return classDeclaration;
    }

    public void addMethod(MethodDeclaration method) {
        classDeclaration.addMember(method);
    }

    public void removeMethod(MethodDeclaration method) {
        classDeclaration.remove(method);
    }

    public String getUniqueMethodName(String baseName) {
        int count = 0;
        for (MethodDeclaration method : classDeclaration.getMethods()) {
            if (method.getNameAsString().startsWith(baseName)) {
                count++;
            }
        }
        return count > 0 ? baseName + "_" + (count + 1) : baseName;
    }

    public void writeToFile() {
        try (FileWriter writer = new FileWriter(classFile)) {
            writer.write(classCu.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}