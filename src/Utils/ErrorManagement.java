package Utils;

public class ErrorManagement {

    public static void reportError(Exception exception, String message) {
        System.err.println('\n' + "%s: %s".formatted(message, exception));
    }

}
