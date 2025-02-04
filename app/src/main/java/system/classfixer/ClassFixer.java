package system.classfixer;

import java.io.File;
import java.io.FileWriter;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import system.StringConstants;

/*
 * ClassFixer is a class whose responsibility is to generate a copy of the original class, 
 * and make that copy part of the current package so that it is supported by Randoop.
 * Once the RepOk is generated and verified, it is added to the original class.
 */
public class ClassFixer {

    private String classPath;
    private String className;
    private File classFile;
    private CompilationUnit classCu;
    private String classCopyPath;
    private File copyFile;

    public ClassFixer(String classPath, String className) {
        this.classPath = classPath;
        this.className = className;
        this.classCopyPath = StringConstants.CLASS_FIXER_PATH + className + ".java";
        this.classFile = new File(classPath);
        try {
            this.classCu = StaticJavaParser.parse(classFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public File generateCopy() {
        copyFile = new File(classCopyPath);
        
        try {
            classCu.setPackageDeclaration(StringConstants.PACKAGE_DECL);
            
            FileWriter writer = new FileWriter(copyFile);
            writer.write(classCu.toString());
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return copyFile;
    }

    public void rewriteClassList() {
        File classListFile = new File(StringConstants.CLASS_LIST_FILE_PATH);
        try {
            FileWriter writer = new FileWriter(classListFile);
            writer.write(StringConstants.PACKAGE_DECL + "." + className + "\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
