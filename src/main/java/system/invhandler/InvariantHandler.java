package system.invhandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import system.StringConstants;

public abstract class InvariantHandler {
    
    protected File classFile;
    protected String className;
    protected List<MethodDeclaration> verifiedInvariants;
    protected List<MethodDeclaration> lastInjections;
    protected CompilationUnit classCu;
    
    public InvariantHandler(File classFile, String className) {
        this.classFile = classFile;
        this.className = className;
        this.verifiedInvariants = new LinkedList<>();
        this.lastInjections = new LinkedList<>();
        try {
            this.classCu = StaticJavaParser.parse(classFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inject(MethodDeclaration method) {
        ClassOrInterfaceDeclaration classDeclaration = classCu.getClassByName(className).orElseThrow(
            () -> new RuntimeException("Class: " + className + " not found")
        );

        classDeclaration.addMember(method);
        lastInjections.add(method);
        writeClass();
    }

    public void deleteLastInjections() {
        ClassOrInterfaceDeclaration classDeclaration = classCu.getClassByName(className).orElseThrow(
            () -> new RuntimeException("Class: " + className + " not found")
        );
        
        for (MethodDeclaration method : lastInjections) {
            classDeclaration.remove(method);
        }

        lastInjections.clear();
        writeClass();
    }

    public Integer verify() { 
        Integer exitCode = -1;

        String scriptPath = StringConstants.RANDOOP_SCRIPT_PATH;
        ProcessBuilder processBuilder = new ProcessBuilder(scriptPath);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();

            // Capture script output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                output.append(line).append("\n");
            }

            exitCode = process.waitFor();
            if (exitCode == 1) {
                verifiedInvariants.addAll(lastInjections);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exitCode;
    }

    public List<MethodDeclaration> getVerifiedInvariants() {
        return verifiedInvariants;
    }

    public abstract void buildRepOk();

    private void writeClass() {
        try {
            FileWriter writer = new FileWriter(classFile, false);
            writer.write(classCu.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cleanLogs() {
        File randoopLog = new File(StringConstants.RANDOOP_LOG_PATH);
        File compileLog = new File(StringConstants.COMPILE_LOG_PATH);
        randoopLog.delete();
        compileLog.delete();
    }

}
