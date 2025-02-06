package system.factories;

import java.io.File;

import system.StringConstants;

public class HandlerAndParserAbstractFactory {
    
    private String promptType;
    private File classFile;
    private String className;
    private String specsClassPath;

    public HandlerAndParserAbstractFactory(String promptType, File classFile, String className, String specsClassPath) {
        this.promptType = promptType;
        this.classFile = classFile;
        this.className = className;
        this.specsClassPath = specsClassPath;
    }

    public HandlerAndParserFactory create() {
        switch (promptType) {
            case StringConstants.GLOBAL_PROMPT_TYPE:
                return new RepOkHandlerAndParserFactory(classFile, className, specsClassPath);
            case StringConstants.FS_W_PROMPT_TYPE:
                return new RepOkHandlerAndParserFactory(classFile, className, specsClassPath);
            case StringConstants.FS_P_PROMPT_TYPE:
                return new RepOkHandlerAndParserFactory(classFile, className, specsClassPath);
            case StringConstants.DUAL_W_PROMPT_TYPE:
                return new PropertiesHandlerAndParserFactory(classFile, className, specsClassPath);
            case StringConstants.DUAL_P_PROMPT_TYPE:
                return new PropertiesHandlerAndParserFactory(classFile, className, specsClassPath);
            default:
                throw new RuntimeException("Invalid prompt type");
        }
    }
}
