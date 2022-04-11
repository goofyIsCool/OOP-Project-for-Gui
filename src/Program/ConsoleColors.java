package Program;

public class ConsoleColors {

    public static final String RESET = "\033[0m";  // Text Reset

    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE

    public static void printInBlue(String str) {
        System.out.println(ConsoleColors.BLUE + str + ConsoleColors.RESET);
    }

    public static void printInGreen(String str) {
        System.out.println(ConsoleColors.GREEN + str + ConsoleColors.RESET);
    }

    public static void printInRed(String str) {
        System.out.println(ConsoleColors.RED + str + ConsoleColors.RESET);
    }

    public static void printInYellow(String str) {
        System.out.println(ConsoleColors.YELLOW + str + ConsoleColors.RESET);
    }
}