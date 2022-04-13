import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Instant;

public class Twister {
    private BufferedImage img;
    private String imgName;

    public Twister(BufferedImage image, String imageName) {
        img = image;
        imgName = imageName;
    }

    public void run() throws IOException {
        long start = Instant.now().toEpochMilli();

        int iterations = Utils.getIntegerInput("How many iterations would you like to perform: ");
        System.out.println("Running TwistImage...");

        TwistTree tt = new TwistTree(img, 0, img.getWidth(), 0, img.getHeight(), 0, img.getWidth(), 0, img.getHeight());

        for (int i = 0; i < iterations; ++i) {
            tt.SplitAndRotateQuads();

        }

        BufferedImage twistedimg = tt.CreateTwisterImage(new BufferedImage(img.getWidth(), img.getHeight(), img.getType()));
        String fileName = imgName.substring(0, imgName.length() - 4) + "twist" + iterations + ".jpg";
        File ofile = new File(fileName);
        ImageIO.write(twistedimg, "jpg", ofile);
        System.out.println("Created " + fileName);

        long end = Instant.now().toEpochMilli();
        System.out.println("Finished TwistImage in " + Utils.formatSeconds(start, end));
    }
}
