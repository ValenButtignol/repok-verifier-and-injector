package system.codeparser;

import java.io.File;
import system.StringConstants;

public class PropertiesClassParser extends CodeParser {

    public PropertiesClassParser() {
        super(new File(StringConstants.PROPERTIES_CLASS_PATH));
    }
}
