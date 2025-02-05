package system.invhandler;

import java.io.File;

public class RepOkClassHandler extends InvariantHandler {
    
    public RepOkClassHandler(File classFile, String className) {
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
