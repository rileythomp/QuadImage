import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Instant;

public class Blur {
    private BufferedImage img;
    private String imgName;

    public Blur(BufferedImage image, String imageName) {
        img = image;
        String[] parts = imageName.split("\\.");
        imgName = parts[0];
    }

    public void run() throws IOException {
        long start = Instant.now().toEpochMilli();

        int levelInput = Math.min(99, Math.max(1, Utils.getIntegerInput("Blur level (1 - 99): ")));
        int blurLevel = levelInput/2 + 1;

        BoxBlur blur = new BoxBlur(img);
        for (int i = 0; i < blurLevel; ++i) {
            blur.BlurImage();
        }

        String blurredName = imgName + "blurred" + levelInput + ".jpg";
        File oFile = new File(blurredName);
        ImageIO.write(blur.img, "jpg", oFile);
        System.out.println("Created " + blurredName);

        long end = Instant.now().toEpochMilli();
        System.out.println("Finished Blur in " + Utils.formatSeconds(start, end));
    }
}
