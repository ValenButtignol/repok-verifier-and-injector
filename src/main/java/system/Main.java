package system;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Arguments: <classPath> <className> <invariantClassPath>");
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        String classPath = args[0];
        String className = args[1];
        String invariantClassPath = args[2];

        RepOkVerifierAndInjectorSystem system = new RepOkVerifierAndInjectorSystem(classPath, className, invariantClassPath);
        system.run();
    }
}