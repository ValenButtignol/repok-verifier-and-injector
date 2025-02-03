package system.codeparser;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

public abstract class CodeParser {
    
    private List<MethodDeclaration> methods;
    private CompilationUnit repOkCu;

    public CodeParser(File classFile) {
        this.methods = new LinkedList<>();
        try {
            this.repOkCu = StaticJavaParser.parse(classFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parse() {
        try {
            repOkCu.findAll(MethodDeclaration.class).forEach(method -> {
                methods.add(method);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
    
    public List<MethodDeclaration> getMethods() {
        return methods;
    }
}
