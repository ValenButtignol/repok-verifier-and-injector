package system.classparser;

import system.StringConstants;

public class GlobalRepOkParser extends ClassParser {

    public GlobalRepOkParser(String filename, String classname) {
        super(filename);
        this.readFile();
    }

    public String parseClass() {
        startIndex = inputBuilder.indexOf(StringConstants.START_CODE_SNIPPET) + StringConstants.START_CODE_SNIPPET.length();
        endIndex = inputBuilder.indexOf(StringConstants.END_CODE_SNIPPET, startIndex);

        codeSnippet = StringConstants.PREFIX_CLASS_TEMPLATE;
        codeSnippet += inputBuilder.substring(startIndex, endIndex);
        codeSnippet += StringConstants.SUFFIX_CLASS_TEMPLATE;
        return codeSnippet;
    }
}
