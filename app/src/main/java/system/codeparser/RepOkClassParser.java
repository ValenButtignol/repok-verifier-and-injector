package system.codeparser;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

import system.StringConstants;

public class RepOkClassParser implements InvariantParser {

    private List<MethodDeclaration> invariants;
    private CompilationUnit repOkCu;

    public RepOkClassParser() {
        this.invariants = new LinkedList<>();
        try {
            this.repOkCu = StaticJavaParser.parse(new File(StringConstants.REPOK_CLASS_PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse() {
        try {
            repOkCu.findAll(MethodDeclaration.class).forEach(method -> {
                if (method.getNameAsString().equals("repOK")) {
                    method.addAnnotation("CheckRep");
                }
                invariants.add(method);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MethodDeclaration> getInvariants() {
        return invariants;
    }
}
