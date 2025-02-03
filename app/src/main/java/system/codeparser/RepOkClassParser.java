package system.codeparser;

import java.io.File;

import system.StringConstants;

public class RepOkClassParser extends CodeParser {

    public RepOkClassParser() {
        super(new File(StringConstants.REPOK_CLASS_PATH));
    }
}
