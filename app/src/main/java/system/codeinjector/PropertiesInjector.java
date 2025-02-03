package system.codeinjector;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class PropertiesInjector implements CodeInjector {

    private File classFile;
    private String className;
    private List<MethodDeclaration> properties;
    private CompilationUnit classCu;
    private int propToInsert;

    public PropertiesInjector(File classFile, String className, List<MethodDeclaration> methods) {
        this.classFile = classFile;
        this.className = className;
        this.properties = methods;
        this.propToInsert = 0;
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

            MethodDeclaration method = properties.get(propToInsert);
            method.addAnnotation("CheckRep");
            classDeclaration.addMember(method);
            propToInsert = propToInsert == properties.size() - 1 ? 0 : propToInsert + 1;

            FileWriter writer = new FileWriter(classFile, false);
            writer.write(classCu.toString());
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void restoreClass() {
        int lastInsertedProp = propToInsert == 0 ? properties.size() - 1 : propToInsert - 1;
        try {
            ClassOrInterfaceDeclaration classDeclaration = classCu.getClassByName(className).orElseThrow(
                () -> new RuntimeException("Class: " + className + " not found")
            );

            MethodDeclaration method = properties.get(lastInsertedProp);
            method.addAnnotation("CheckRep");
            classDeclaration.remove(method);

            FileWriter writer = new FileWriter(classFile, false);
            writer.write(classCu.toString());
            writer.close();
        
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
