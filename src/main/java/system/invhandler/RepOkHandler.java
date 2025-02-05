package system.invhandler;

import java.io.File;

public class RepOkHandler extends InvariantHandler {
    
    public RepOkHandler(File classFile, String className) {
        super(classFile, className);
    }

    public void buildRepOk() { 
        this.getVerifiedInvariants().forEach(method -> {
            if (method.getNameAsString().equals("repOk")) {
                method.getAnnotations().removeIf(a -> a.getNameAsString().equals("CheckRep"));
            }
            this.inject(method);
        });
    }   
}
