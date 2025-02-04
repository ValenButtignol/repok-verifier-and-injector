package system.codeparser;

import java.util.List;

import com.github.javaparser.ast.body.MethodDeclaration;

public interface InvariantParser {
    
    public void parse();
    
    public List<MethodDeclaration> getInvariants();
}