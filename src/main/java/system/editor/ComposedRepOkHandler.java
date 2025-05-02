package system.editor;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.Modifier.Keyword;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;

import system.StringConstants;

import java.io.IOException;

public class ComposedRepOkHandler {
    private ClassOrInterfaceDeclaration classDeclaration;

    public ComposedRepOkHandler(ClassOrInterfaceDeclaration classDeclaration) {
        this.classDeclaration = classDeclaration;
    }

    public void handleComposedRepOk() throws IOException {
        MethodDeclaration lastRepOk = findLastRepOkMethod();
        
        if (!composedRepOkExists()) {
            createComposedRepOk();
        }
        updateComposedRepOk(lastRepOk);
    }

    private MethodDeclaration findLastRepOkMethod() {
        return classDeclaration.getMethods().stream()
                .filter(this::isRepOkOrPropMethod)
                .reduce((first, second) -> second)
                .orElseThrow(() -> new RuntimeException("No repOK methods found"));
    }

    private boolean isRepOkOrPropMethod(MethodDeclaration method) {
        String methodName = method.getNameAsString();
        return methodName.startsWith(StringConstants.REPOK_METHOD_NAME) 
            || methodName.startsWith(StringConstants.PROP_METHOD_NAME);
    }

    private boolean composedRepOkExists() {
        return !classDeclaration.getMethodsByName(StringConstants.COMPOSED_REPOK_METHOD_NAME).isEmpty();
    }

    private void createComposedRepOk() {
        MethodDeclaration repOkMethod = new MethodDeclaration()
                .setName(StringConstants.COMPOSED_REPOK_METHOD_NAME)
                .setType("boolean")
                .setModifiers(Keyword.PUBLIC)
                .setBody(StaticJavaParser.parseBlock("{ return true; }"));
        
        classDeclaration.addMember(repOkMethod);
    }

    private void updateComposedRepOk(MethodDeclaration lastRepOkAdded) throws IOException {
        String repOkBody = generateBody(lastRepOkAdded);
        if (repOkBody.isEmpty()) return;

        MethodDeclaration composedRepOk = classDeclaration.getMethodsByName(
            StringConstants.COMPOSED_REPOK_METHOD_NAME).get(0);
        
        cleanComposedRepOk(composedRepOk);
        appendToComposedRepOk(composedRepOk, repOkBody);
    }

    private String generateBody(MethodDeclaration lastRepOkAdded) {
        if (lastRepOkAdded.getAnnotationByName("CheckRep").isEmpty()) {
            return "";
        }
        
        lastRepOkAdded.getAnnotationByName("CheckRep").get().remove();
        return "if (!" + lastRepOkAdded.getName() + "()) return false;";
    }

    private void cleanComposedRepOk(MethodDeclaration composedRepOk) {
        composedRepOk.getBody().ifPresent(body -> 
            body.getStatements().removeIf(this::isReturnTrueStatement));
    }

    private boolean isReturnTrueStatement(Statement stmt) {
        return stmt.isReturnStmt() 
            && stmt.asReturnStmt().getExpression().isPresent() 
            && "true".equals(stmt.asReturnStmt().getExpression().get().toString());
    }

    private void appendToComposedRepOk(MethodDeclaration composedRepOk, String newStatement) {
        composedRepOk.getBody().ifPresent(body -> {
            body.addStatement(newStatement);
            body.addStatement("return true;");
        });
    }
}