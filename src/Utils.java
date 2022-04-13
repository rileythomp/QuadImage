import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public final class Utils {
    private static Scanner scanner = new Scanner(System.in);

    private Utils() {
    }

    public static String formatSeconds(long start, long end) {
        int seconds = (int)((end - start)/1000);
        int minutes = 0;
        while (seconds >= 60) {
            minutes++;
            seconds -= 60;
        }
        return minutes + ":" + (seconds < 10 ? "0" : "") + seconds;
    }

    public static int getIntegerInput(String question) {
        int ans;
        while (true) {
            System.out.print(question);
            String iterations = scanner.next();
            try {
                ans = Integer.parseInt(iterations);
                break;
            }
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer");
            }
            catch (NullPointerException e) {
                System.out.println("Please enter a valid integer");
            }
        }
        return ans;
    }

    public static String getBinaryStringInput(String question, String option1, String option2) {
        while (true) {
            System.out.print(question + " (" + option1 + " or " + option2 + "): ");
            String input = scanner.next();
            if (input.equalsIgnoreCase(option1) || input.equalsIgnoreCase(option2)) {
                return input;
            }
            else {
                System.out.println("Please enter " + option1 + " or " + option2);
            }
        }
    }

    public static boolean getBooleanInput(String question) {
        while (true) {
            System.out.print(question);
            String bool = scanner.next();
            if (bool.equalsIgnoreCase("y")) {
                return true;
            }
            else if (bool.equalsIgnoreCase("n")) {
                return false;
            }
            else {
                System.out.println("Please enter y or n");
            }
        }
    }

    public static File getFileInput(String question, String name) throws IOException {
        while (true) {
            System.out.print(question);
            String fileName = name;
            File file = new File(fileName);
            if (file.exists()) {
                return file;
            }
            else {
                System.out.println(file.getName() + " does not exist");
            }
        }
    }
}
