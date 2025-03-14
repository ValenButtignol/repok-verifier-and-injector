package system.classfixer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;

import system.StringConstants;

/*
 * ClassFixer is a class whose responsibility is to generate a copy of the original class, 
 * and make that copy part of the current package so that it is supported by Randoop.
 * Once the RepOk is generated and verified, it is added to the original class.
 */
public class ClassFixer {

    private String className;
    private File classFile;
    private File copyFile;
    private Optional<PackageDeclaration> oldPackage;
    private List<File> copiedFiles;

    public ClassFixer(String classPath, String className) {
        this.className = className;
        this.classFile = new File(classPath);
        this.copiedFiles = new ArrayList<>();
    }
    
    public File generateCopy() {
        File directory = classFile.getParentFile(); // Get package directory
        if (directory == null || !directory.isDirectory()) {
            throw new IllegalArgumentException("Invalid package directory: " + directory);
        }

        for (File file : directory.listFiles()) {
            if (file.getName().endsWith(".java")) {
                copyJavaFile(file);
            }
        }
        updatePackageDeclarations();
        
        return copyFile; // Return only the copy of the specified class
    }

    public void writeClassList() {
        try {
            writeToFile(new File(StringConstants.CLASS_LIST_FILE_PATH),
                StringConstants.PACKAGE_DECL + "." + className + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteClassList() {
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

    public void deleteCopies() {
        for (File file : copiedFiles) {
            file.delete();
        }
        copiedFiles.clear();
    }

    private void copyJavaFile(File file) {
        try {
            File destination = new File(StringConstants.CLASS_FIXER_PATH + file.getName());
            Files.copy(file.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            copiedFiles.add(destination);

            if (file.getName().equals(className + ".java")) {
                modifyCopy(destination);
                copyFile = destination; // Save reference to the modified file
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void modifyCopy(File destination) {
        try {
            CompilationUnit cu = StaticJavaParser.parse(destination);
            oldPackage = cu.getPackageDeclaration();
            cu.addImport("java.util.*");
            cu.addImport("randoop.CheckRep");
            
            writeToFile(destination, cu.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePackageDeclarations() {
        for (File file : copiedFiles) {
            try {
                CompilationUnit cu = StaticJavaParser.parse(file);
                cu.setPackageDeclaration(StringConstants.PACKAGE_DECL);
                writeToFile(file, cu.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }

    private void writeToFile(File file, String content) throws IOException {
        FileWriter writer = new FileWriter(file, false);
        writer.write(content);
        writer.close();
    }
}
