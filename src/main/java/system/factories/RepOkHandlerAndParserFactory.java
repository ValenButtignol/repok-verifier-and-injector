package system.factories;

import java.io.File;

import system.invhandler.InvariantHandler;
import system.invhandler.RepOkClassHandler;
import system.invparser.InvariantParser;
import system.invparser.RepOkClassParser;

public class RepOkHandlerAndParserFactory implements HandlerAndParserFactory {
    
    private File classFile;
    private String className;

    public RepOkHandlerAndParserFactory(File classFile, String className) {
        this.classFile = classFile;
        this.className = className;
    }

    @Override
    public InvariantHandler createHandler() {
        return new RepOkClassHandler(classFile, className);
    }

    @Override
    public InvariantParser createParser() {
        return new RepOkClassParser();
    }
    
}
