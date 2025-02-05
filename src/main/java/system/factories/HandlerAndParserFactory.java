package system.factories;

import system.invhandler.InvariantHandler;
import system.invparser.InvariantParser;

public interface HandlerAndParserFactory {
    public InvariantHandler createHandler();
    public InvariantParser createParser();
}
