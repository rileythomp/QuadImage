import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Running QuadImage...");
        long start = Instant.now().toEpochMilli();

        File imgFile = getImageInput("Image: ");
        BufferedImage img = ImageIO.read(imgFile);

        while (img.getWidth() > 2900 || img.getHeight() > 2900) {
            System.out.println("Sorry, " + imgFile.getName() + " is too large");
            imgFile = getImageInput("Image: " );
            img = ImageIO.read(imgFile);
        }

        boolean gridLines = getBooleanInput("Would you like black grid lines around each quadrant (y or n): ");

        int iterations = getIntegerInput("How many iterations would you like to perform: ");
        boolean makeGif = getBooleanInput("Would you like to create up to 300 images for a gif (y or n): ");

        start = Instant.now().toEpochMilli();

        QuadTree qt = new QuadTree(img, 0, img.getWidth(), 0, img.getHeight(), true, 1, gridLines);
        int imagesCreated = 0;

        for (int i = 0; i < iterations; ++i) {
            qt.splitQuadWithLargestError();

            if (makeGif) {
                // First 9 seconds
                if (i < 90) {
                    String fname = createImageFileName(imgFile.getName(), imagesCreated);
                    if (qt.createQuadImage(new BufferedImage(qt.img.getWidth(), qt.img.getHeight(), qt.img.getType()), fname, makeGif)) {
                        imagesCreated++;
                    }
                }
                // Next 9 seconds
                else if (i < 450 && i%4 == 0) {
                    String fname = createImageFileName(imgFile.getName(), imagesCreated);
                    if (qt.createQuadImage(new BufferedImage(qt.img.getWidth(), qt.img.getHeight(), qt.img.getType()), fname, makeGif)) {
                        imagesCreated++;
                    }
                }
                // Final 9 seconds
                else if (i >= 450 && i < 1800 && i%15 == 0) {
                    String fname = createImageFileName(imgFile.getName(), imagesCreated);

                    // createQuadImage will create an image in frames folder, and if successful will increase image count
                    if (qt.createQuadImage(new BufferedImage(qt.img.getWidth(), qt.img.getHeight(), qt.img.getType()), fname, makeGif)) {
                        imagesCreated++;
                    }
                }
            }
        }

        if (makeGif) {
            // 3 second pause before looping
            for (int i = 0; i < 30; ++i) {
                String fname = createImageFileName(imgFile.getName(), imagesCreated);
                if (qt.createQuadImage(new BufferedImage(qt.img.getWidth(), qt.img.getHeight(), qt.img.getType()), fname, makeGif)) {
                    imagesCreated++;
                }
            }
        }

        if (qt.createQuadImage(new BufferedImage(qt.img.getWidth(), qt.img.getHeight(), qt.img.getType()), "quadimages/" + iterations + imgFile.getName(), false)) {
            imagesCreated++;
        }

        long end = Instant.now().toEpochMilli();
        System.out.println("Created " +imagesCreated + " images in " + formatSeconds(start, end));
    }

    public static int getIntegerInput(String question) {
        Scanner scanner = new Scanner(System.in);
        int ans = 0;
        while (true) {
            System.out.print(question);
            String iterations = scanner.next();
            try {
                ans = Integer.parseInt(iterations);
                break;
            }
            catch (NumberFormatException e) {
                System.out.println("Please enter a positive integer");
            }
            catch (NullPointerException e) {
                System.out.println("Please enter a positive integer");
            }
        }

        return ans;
    }

    public static File getImageInput(String question) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(question);
            String imgName = scanner.next();
            File imgFile = new File(imgName);
            if (imgFile.exists()) {
                return imgFile;
            }
            else {
                System.out.println(imgFile.getName() + " does not exist");
            }
        }
    }

    public static boolean getBooleanInput(String question) {
        Scanner scanner = new Scanner(System.in);

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

    public static String createImageFileName(String name, int i) {
        String fname = "frames/" + name;
        int pos = fname.lastIndexOf(".");
        if (pos > 0) {
            fname = fname.substring(0, pos);
        }

        fname += '/';

        if (i < 100) {
            fname += '0';
        }
        if (i < 10) {
            fname += '0';
        }
        fname += i;

        fname += ".jpg";

        return fname;
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
}
