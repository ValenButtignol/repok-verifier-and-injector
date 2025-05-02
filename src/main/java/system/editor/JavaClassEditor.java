package system.editor;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JavaClassEditor {
    private Path targetClassPath;
    private String targetClassName;
    private SnippetInjector snippetInjector;

    public JavaClassEditor(Path targetClassPath, String targetClassName) {
        this.targetClassPath = targetClassPath;
        this.targetClassName = targetClassName;
        this.snippetInjector = new SnippetInjector(targetClassPath);
    }

    public void injectSnippet(String invariantSnippet) throws IOException {
        snippetInjector.inject(invariantSnippet);
    }

    public void adjustClass() throws IOException {
        CompilationUnit compilationUnit = StaticJavaParser.parse(targetClassPath.toFile());
        RepOkManager repOkManager = new RepOkManager(compilationUnit, targetClassName);
        repOkManager.adjustLastRepOk();
        Files.writeString(targetClassPath, compilationUnit.toString());
    }

    public void createOrUpdateComposedRepOK() throws IOException {
        CompilationUnit compilationUnit = StaticJavaParser.parse(targetClassPath.toFile());
        ClassOrInterfaceDeclaration classDeclaration = compilationUnit.getClassByName(targetClassName)
                .orElseThrow(() -> new RuntimeException(
                    "Compilation error with class " + targetClassName));

        ComposedRepOkHandler handler = new ComposedRepOkHandler(classDeclaration);
        handler.handleComposedRepOk();
        Files.writeString(targetClassPath, compilationUnit.toString());
    }
}