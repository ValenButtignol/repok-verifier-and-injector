package system;

import java.io.File;

import system.classfixer.ClassFixer;
import system.factories.HandlerAndParserAbstractFactory;
import system.factories.HandlerAndParserFactory;
import system.invhandler.InvariantHandler;
import system.invparser.InvariantParser;

public class System {

    private HandlerAndParserAbstractFactory handlerAndParserAbstractFactory;
    private HandlerAndParserFactory handlerAndParserFactory;
    private InvariantHandler invariantHandler;
    private InvariantParser invariantParser;
    private ClassFixer classFixer;
    private String classPath;
    private String className;
    private String promptType;

    public System(String classPath, String className, String promptType) {
        this.classPath = classPath;
        this.className = className;
        this.promptType = promptType;

        classFixer = new ClassFixer(classPath, className);
        classFixer.rewriteClassList();
        File classFile = classFixer.generateCopy();
        handlerAndParserAbstractFactory = new HandlerAndParserAbstractFactory(promptType, classFile, className);
        handlerAndParserFactory = handlerAndParserAbstractFactory.create();
        invariantHandler = handlerAndParserFactory.createHandler();
        invariantParser = handlerAndParserFactory.createParser();
    }

    public void run() {
        switch (promptType) {
            case StringConstants.GLOBAL_PROMPT_TYPE:
                runForRepOkClass();
                break;
            case StringConstants.FS_W_PROMPT_TYPE:
                runForRepOkClass();
                break;
            case StringConstants.FS_P_PROMPT_TYPE:
                runForRepOkClass();
                break;
            case StringConstants.DUAL_W_PROMPT_TYPE:
                runForPropertiesClass();
                break;
            case StringConstants.DUAL_P_PROMPT_TYPE:
                runForPropertiesClass();
                break;
            default:
                throw new RuntimeException("Invalid prompt type");
        }
    }

    public void runForRepOkClass() {
        invariantParser.parse();
        invariantParser.getInvariants().forEach(method -> {
            invariantHandler.inject(method);
        });
        invariantHandler.verify();
        invariantHandler.deleteLastInjections();
        invariantHandler.buildRepOk();
        classFixer.copyBack();
        classFixer.deleteCopy();
    }

    public void runForPropertiesClass() {
        invariantParser.parse();
        invariantParser.getInvariants().forEach(method -> {
            invariantHandler.inject(method);
            invariantHandler.verify();
            invariantHandler.deleteLastInjections();
        });
        invariantHandler.buildRepOk();
        classFixer.copyBack();
        classFixer.deleteCopy();
    }

    
}
