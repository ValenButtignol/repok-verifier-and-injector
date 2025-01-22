package system.classparser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import system.StringConstants;

public class WholeClassParser extends ClassParser {
    
    public WholeClassParser(String classFilePath, String className) {
        super(classFilePath, className);
        this.readFile();
    }

    public String parseClass() { 
        
        CompilationUnit classCu = StaticJavaParser.parse(this.classString);
        ClassOrInterfaceDeclaration classDeclaration = classCu.getClassByName(className).orElseThrow(
            () -> new RuntimeException("Class: " + className + " not found")
        );
            
        String result = StringConstants.CLASS_TAG_PREFIX + StringConstants.START_CODE_SNIPPET + classDeclaration.toString() + StringConstants.END_CODE_SNIPPET;
        return result;
    }
}
