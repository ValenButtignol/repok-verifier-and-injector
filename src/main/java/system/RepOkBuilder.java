package system;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;

public class RepOkBuilder {
    private JavaClassEditor classEditor;
    private List<MethodDeclaration> verifiedMethods;

    public RepOkBuilder(JavaClassEditor classEditor) {
        this.classEditor = classEditor;
        this.verifiedMethods = new ArrayList<>();
    }

    public void createRepOk() {
        if (repOkExists()) {
            return;
        }
        MethodDeclaration repOkMethod = new MethodDeclaration()
            .setName(StringConstants.REPOK_METHOD_NAME)
            .setType("boolean")
            .setModifiers(com.github.javaparser.ast.Modifier.Keyword.PUBLIC)
            .setBody(StaticJavaParser.parseBlock( "{ return true; }"));
        
        classEditor.addMethod(repOkMethod);
        classEditor.writeToFile();
    }

    public void updateRepOk() {
        StringBuilder repOkBody = generateBody();
        if (repOkBody.length() == 0) {
            return;
        }
        appendToRepOk(repOkBody);
        classEditor.writeToFile();
    }

    public void addVerifiedInvariants(List<MethodDeclaration> verifiedMethods) {
        this.verifiedMethods.addAll(verifiedMethods);
    }

    private StringBuilder generateBody() {
        StringBuilder repOkBody = new StringBuilder();
        for (MethodDeclaration method : verifiedMethods) {
            if (method.getAnnotationByName("CheckRep").isPresent()) {
                method.getAnnotationByName("CheckRep").get().remove();
                repOkBody.append("if (!").append(method.getName()).append("()) return false;\n");
            }
        }
        return repOkBody;
    }

    private boolean repOkExists() {
        return classEditor.getClassDeclaration().getMethodsByName(StringConstants.REPOK_METHOD_NAME).size() > 0;
    }

    private void appendToRepOk(StringBuilder newRepOkBody) {
        MethodDeclaration repOk = classEditor.getClassDeclaration().getMethodsByName(StringConstants.REPOK_METHOD_NAME).get(0);

        deleteReturnTrue(repOk);
        
        classEditor.getClassDeclaration().getMethodsByName(StringConstants.REPOK_METHOD_NAME)
            .get(0).getBody().ifPresent(body -> body.addStatement(newRepOkBody.toString()));  
        
        classEditor.getClassDeclaration().getMethodsByName(StringConstants.REPOK_METHOD_NAME)
            .get(0).getBody().ifPresent(body -> body.addStatement("return true;"));

    }

    private void deleteReturnTrue(MethodDeclaration repOk) {
        repOk.getBody().ifPresent(body -> {
            body.getStatements().removeIf(stmt -> 
                stmt.isReturnStmt() && stmt.asReturnStmt().getExpression().isPresent() &&
                stmt.asReturnStmt().getExpression().get().toString().equals("true")
            );
        });

    }
}