import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Running Quadpic...");
        long start = Instant.now().toEpochMilli();

        File dir = new File("images/");
        File[] directoryListing = dir.listFiles();

        if (directoryListing != null) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("How many iterations would you like to perform on each image? ");
            int iterations = scanner.nextInt();

            start = Instant.now().toEpochMilli();

            for (File imgFile : directoryListing) {
                BufferedImage img = ImageIO.read(imgFile);

                if (img.getWidth() > 2900 || img.getHeight() > 2900) {
                    System.err.println("Sorry, the image is too large");
                    break;
                }

                QuadTree qt = new QuadTree(img, 0, img.getWidth(), 0, img.getHeight(), true, 1);

                for (int i = 0; i < iterations; ++i) {
                    qt.splitQuadWithLargestError();
//                  qt.printChildren(new BufferedImage(qt.img.getWidth(), qt.img.getHeight(), qt.img.getType()), "image" + i + ".jpg");
                }

                qt.createQuadImage(new BufferedImage(qt.img.getWidth(), qt.img.getHeight(), qt.img.getType()), "quadimages/quad" + imgFile.getName());
            }
        }
        else {
            System.err.println("Sorry that directory does not exist");
        }

        long end = Instant.now().toEpochMilli();
        System.out.println("Created " + directoryListing.length + " images in " + formatSeconds(start, end));
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
