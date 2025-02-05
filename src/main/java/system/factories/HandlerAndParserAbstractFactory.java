package system.factories;

import java.io.File;

import system.StringConstants;

public class HandlerAndParserAbstractFactory {
    
    private String promptType;
    private File classFile;
    private String className;

    public HandlerAndParserAbstractFactory(String promptType, File classFile, String className) {
        this.promptType = promptType;
        this.classFile = classFile;
        this.className = className;
    }

    public HandlerAndParserFactory create() {
        switch (promptType) {
            case StringConstants.GLOBAL_PROMPT_TYPE:
                return new RepOkHandlerAndParserFactory(classFile, className);
            case StringConstants.FS_W_PROMPT_TYPE:
                return new RepOkHandlerAndParserFactory(classFile, className);
            case StringConstants.FS_P_PROMPT_TYPE:
                return new RepOkHandlerAndParserFactory(classFile, className);
            case StringConstants.DUAL_W_PROMPT_TYPE:
                return new PropertiesHandlerAndParserFactory(classFile, className);
            case StringConstants.DUAL_P_PROMPT_TYPE:
                return new PropertiesHandlerAndParserFactory(classFile, className);
            default:
                throw new RuntimeException("Invalid prompt type");
        }
    }
}
