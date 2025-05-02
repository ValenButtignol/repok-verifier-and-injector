package system;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;

/*
 * ClassProcessor is a class whose responsibility is to generate a copy of the original class, 
 * and make that copy part of the current package so that it is supported by Randoop.
 * Once the RepOk is generated and verified, it is added to the original class.
 */
public class ClassProcessor {

    private String className;
    private File classFile;
    private File copyFile;
    private Optional<PackageDeclaration> oldPackage;

    public ClassProcessor(String classPath, String className) {
        this.className = className;
        this.classFile = new File(classPath);
    }
    
    public File generateCopy() {
        copyFile = copyJavaFile(classFile);
        setImportsAndPackage(copyFile);
        
        return copyFile; // Return only the copy of the specified class
    }

    public void processClassList() {
        try {
            writeToFile(new File(StringConstants.CLASS_LIST_FILE_PATH),
                StringConstants.PACKAGE_DECL + "." + className + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cleanClassList() {
        try {
            writeToFile(new File(StringConstants.CLASS_LIST_FILE_PATH), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyBack() {
        if (copyFile == null) {
            throw new IllegalStateException("No copy file exists.");
        }
        try {
            CompilationUnit copyClassCu = StaticJavaParser.parse(copyFile);
            copyClassCu.setPackageDeclaration(oldPackage.orElse(null));
            copyClassCu.getImports().removeIf(i -> i.getNameAsString().equals("randoop.CheckRep"));

            writeToFile(classFile, copyClassCu.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCopy() {
        copyFile.delete();
    }

    private File copyJavaFile(File file) {
        // Generate copy on testclass folder
        File destination = new File(StringConstants.TEST_CLASS_PATH + file.getName());
        try {
            Files.copy(file.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return destination;
    }

    private void setImportsAndPackage(File destination) {
        try {
            CompilationUnit cu = StaticJavaParser.parse(destination);
            oldPackage = cu.getPackageDeclaration();
            cu.addImport("java.util.*");
            cu.addImport("randoop.CheckRep");
            cu.setPackageDeclaration(StringConstants.PACKAGE_DECL);
            writeToFile(destination, cu.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(File file, String content) throws IOException {
        FileWriter writer = new FileWriter(file, false);
        writer.write(content);
        writer.close();
    }
}
