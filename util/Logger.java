package util;

public class Logger {

    public static void WARNING(String message, Object object) {
        System.out.println("WARNING - " + d() + " - " + message + " - " + object.getClass().getName() + " - line: " +  " - " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }

    public static void ERROR(String message, Object object) {
        System.out.println("ERROR - " + d() + " - " + message + " - " + object.getClass().getName() + " - line: " +  " - " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }

    public static void DEBUG(String message, Object object) {
        System.out.println("DEBUG - " + d() + " - " + message + " - " + object.getClass().getName() + " - line: " +  " - " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }

    public static void INFO(String message, Object object) {
        System.out.println("INFO - " + d() + " - " + message + " - " + object.getClass().getName() + " - line: " +  " - " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }

    public static String d() {
        return (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
    }
}
