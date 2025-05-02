package system.editor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier.Keyword;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import system.StringConstants;

import java.util.List;
import java.util.stream.Collectors;

public class RepOkManager {
    private ClassOrInterfaceDeclaration classDeclaration;

    public RepOkManager(CompilationUnit compilationUnit, String targetClassName) {
        this.classDeclaration = compilationUnit.getClassByName(targetClassName)
                .orElseThrow(() -> new RuntimeException(
                    "Compilation error with class " + targetClassName));
    }

    public void adjustLastRepOk() {
        MethodDeclaration lastRepOk = findLastRepOkMethod();
        checkForParameters(lastRepOk);
        configureAccessModifiers(lastRepOk);
    }

    private MethodDeclaration findLastRepOkMethod() {
        List<MethodDeclaration> repoks = classDeclaration.getMethods().stream()
                .filter(this::isRepOkOrPropMethod)
                .collect(Collectors.toList());
        
        return repoks.getLast();
    }

    private boolean isRepOkOrPropMethod(MethodDeclaration method) {
        String methodName = method.getNameAsString();
        return methodName.startsWith(StringConstants.REPOK_METHOD_NAME) 
            || methodName.startsWith(StringConstants.PROP_METHOD_NAME);
    }

    private void checkForParameters(MethodDeclaration method) {
        if (!method.getParameters().isEmpty()) {
            throw new RuntimeException("Invariant can't have any parameters.");
        }
    }

    private void configureAccessModifiers(MethodDeclaration method) {
        method.addAnnotation("CheckRep");
        method.setModifier(Keyword.PRIVATE, false);
        method.setModifier(Keyword.PROTECTED, false);
        method.setModifier(Keyword.PUBLIC, true);
    }
}