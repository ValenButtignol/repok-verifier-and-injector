package system;

public class Main {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Arguments: <classPath> <className> <promptType> <specsClassPath>");
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        String classPath = args[0];
        String className = args[1];
        String promptType = args[2];
        String specsClassPath = args[3];
        RepOkVerifierAndInjectorSystem system = new RepOkVerifierAndInjectorSystem(classPath, className, promptType, specsClassPath);
        system.run();
    }
}