package system.factories;

import java.io.File;

import system.invhandler.InvariantHandler;
import system.invhandler.PropertiesClassHandler;
import system.invparser.InvariantParser;
import system.invparser.PropertiesClassParser;

public class PropertiesHandlerAndParserFactory implements HandlerAndParserFactory {
    
    private File classFile;
    private String className;
    private String specsClassPath;

    public PropertiesHandlerAndParserFactory(File classFile, String className, String specsClassPath) {
        this.classFile = classFile;
        this.className = className;
        this.specsClassPath = specsClassPath;
    }

    @Override
    public InvariantHandler createHandler() {
        return new PropertiesClassHandler(classFile, className);
    }

    @Override
    public InvariantParser createParser() {
        return new PropertiesClassParser(specsClassPath);
    }
}
