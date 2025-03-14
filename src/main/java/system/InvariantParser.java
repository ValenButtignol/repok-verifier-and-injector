package system;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier.Keyword;
import com.github.javaparser.ast.body.MethodDeclaration;

public class InvariantParser {
    
    private List<MethodDeclaration> parsedMethods;
    private CompilationUnit repOkCu;

    public InvariantParser(String invariantClassPath) {
        this.parsedMethods = new LinkedList<>();
        try {
            this.repOkCu = StaticJavaParser.parse(new File(invariantClassPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parse() {
        try {
            repOkCu.findAll(MethodDeclaration.class).forEach(method -> {
                if (method.getNameAsString().equals(StringConstants.REPOK_METHOD_NAME)
                    || method.getNameAsString().startsWith(StringConstants.PROP_METHOD_NAME)) {
                    method.addAnnotation("CheckRep");
                    if (!method.getParameters().isEmpty())
                        throw new IllegalArgumentException("Invariant can't have any parameters.");

                    method.setModifier(Keyword.PRIVATE, false);
                    method.setModifier(Keyword.PROTECTED, false);
                    method.setModifier(Keyword.PUBLIC, true);
                }
                parsedMethods.add(method);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MethodDeclaration> getParsedMethods() {
        return parsedMethods;
    }
}