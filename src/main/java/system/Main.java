package system;

public class Main {
    public static void main(String[] args) {
        String classPath = "LinkedList.java";
        String className = "LinkedList";
        String promptType = StringConstants.GLOBAL_PROMPT_TYPE;
        System system = new System(classPath, className, promptType);
        system.run();
    }
}