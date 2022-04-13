import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Instant;

public class EdgeDetection {
    private BufferedImage img;
    private String imgName;

    public EdgeDetection(BufferedImage image, String imageName) {
        img = image;
        String[] parts = imageName.split("\\.");
        imgName = parts[0];
    }

    public void run() throws IOException {
        long start = Instant.now().toEpochMilli();

        EdgeDetector edgeDetector = new EdgeDetector(img);
        edgeDetector.DetectEdges();

        String blurredName = imgName + "edges.jpg";
        File oFile = new File(blurredName);
        ImageIO.write(edgeDetector.img, "jpg", oFile);
        System.out.println("Created " + blurredName);

        long end = Instant.now().toEpochMilli();
        System.out.println("Finished Blur in " + Utils.formatSeconds(start, end));
    }
}
