package system;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Arguments: <classPath> <className> <promptType>");
        }

        String classPath = args[0];
        String className = args[1];
        String promptType = args[2];
        RepOkVerifierAndInjectorSystem system = new RepOkVerifierAndInjectorSystem(classPath, className, promptType);
        system.run();
    }
}