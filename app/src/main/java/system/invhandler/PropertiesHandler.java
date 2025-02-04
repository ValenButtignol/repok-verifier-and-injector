package system.invhandler;

import java.io.File;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.MethodDeclaration;

public class PropertiesHandler extends InvariantHandler {
    public PropertiesHandler(File classFile, String className) {
        super(classFile, className);
    }

    public void buildRepOk() {
        
        StringBuilder repOkBody = new StringBuilder();
        for (MethodDeclaration predicate : getVerifiedInvariants()) {
            repOkBody.append("if (!").append(predicate.getName()).append("()) return false;\n");
        }
        repOkBody.append("return true;\n");
        
        MethodDeclaration repOkMethod = new MethodDeclaration()
                .setName("repOk")
                .setType("boolean")
                .setModifiers(Modifier.Keyword.PUBLIC)
                .setBody(StaticJavaParser.parseBlock("{" + repOkBody + "}"));
                
        inject(repOkMethod);
        this.getVerifiedInvariants().forEach(method -> {
            method.getAnnotations().removeIf(a -> a.getNameAsString().equals("CheckRep"));
            inject(method);
        });
    }   
}
