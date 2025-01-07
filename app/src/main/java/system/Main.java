package system;

public class Main {
    public static void main(String[] args) {
        
        CodeParser codeParser = new SimpleCodeParser("app/src/main/resources/repok1.txt");  
        RepOkInjector repOkInjector = new RepOkInjector("app/src/main/resources/LinkedList.java", "LinkedList", codeParser.parse());
        repOkInjector.injectRepOk();
    }
}