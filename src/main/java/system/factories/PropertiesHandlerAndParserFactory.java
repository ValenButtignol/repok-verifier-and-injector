package system.factories;

import java.io.File;

import system.invhandler.InvariantHandler;
import system.invhandler.PropertiesClassHandler;
import system.invparser.InvariantParser;
import system.invparser.PropertiesClassParser;

public class PropertiesHandlerAndParserFactory implements HandlerAndParserFactory {
    
    private File classFile;
    private String className;

    public PropertiesHandlerAndParserFactory(File classFile, String className) {
        this.classFile = classFile;
        this.className = className;
    }

    @Override
    public InvariantHandler createHandler() {
        return new PropertiesClassHandler(classFile, className);
    }

    @Override
    public InvariantParser createParser() {
        return new PropertiesClassParser();
    }
}
