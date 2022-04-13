import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: $ ./run.sh {program_letter} {image_path}");
            System.out.println("+-----+------------------+");
            System.out.println("|  q  |    QuadImage     |");
            System.out.println("|  b  |       Blur       |");
            System.out.println("|  e  |  Edge Detection  |");
            System.out.println("|  t  |   Twist Image    |");
            System.out.println("+-----+------------------+");
            System.out.println("See USAGE.md for more detail.");

            return;
        }

        File imgFile;
        imgFile = new File(args[1]);
        if (!imgFile.exists()) {
            System.out.println(imgFile.getName() + " does not exist");
            return;
        }

        BufferedImage img = ImageIO.read(imgFile);
        while (img.getWidth() > 2900 || img.getHeight() > 2900) {
            System.out.println("Sorry, " + imgFile.getName() + " is too large");
            return;
        }

        String program = args[0];
        if (program.equalsIgnoreCase("q")) {
            QuadImage qi = new QuadImage(img, imgFile.getName());
            qi.run();
        }
        else if (program.equalsIgnoreCase("e")) {
            EdgeDetection ed = new EdgeDetection(img, imgFile.getName());
            ed.run();
        }
        else if (program.equalsIgnoreCase("t")) {
            Twister t = new Twister(img, imgFile.getName());
            t.run();
        }
        else if (program.equalsIgnoreCase("b")){
            Blur blur = new Blur(img, imgFile.getName());
            blur.run();
        }
        else {
            System.out.println("Must enter a valid program, see USAGE.md");
            return;
        }
    }
}
