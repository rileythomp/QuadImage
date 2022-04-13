import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Instant;

public class QuadImage {
    private BufferedImage img;
    private String imgName;

    public QuadImage(BufferedImage image, String imageName) {
        img = image;
        imgName = imageName;
    }

    public void run() throws IOException {
        long start = Instant.now().toEpochMilli();

        boolean gridLines = Utils.getBooleanInput("Would you like black grid lines around each quadrant (y or n): ");
        int iterations = Utils.getIntegerInput("How many iterations would you like to perform: ");
        boolean makeGif = Utils.getBooleanInput("Would you like to create up to 300 images for a gif (y or n): ");
        System.out.println("Running QuadImage...");

        QuadTree qt = new QuadTree(img, 0, img.getWidth(), 0, img.getHeight(), true, 1, gridLines);

        int imagesCreated = 0;

        for (int i = 0; i < iterations; ++i) {
            qt.splitQuadWithLargestError();

            if (makeGif) {
                // First 9 seconds
                // createQuadImage will create an image in frames folder, and if successful will increase image count
                if (i < 90) {
                    String fname = createImageFileName(imgName, imagesCreated);
                    if (qt.createQuadImage(new BufferedImage(qt.img.getWidth(), qt.img.getHeight(), qt.img.getType()), fname, makeGif)) {
                        imagesCreated++;
                    }
                }
                // Next 9 seconds
                else if (i < 450 && i%4 == 0) {
                    String fname = createImageFileName(imgName, imagesCreated);
                    if (qt.createQuadImage(new BufferedImage(qt.img.getWidth(), qt.img.getHeight(), qt.img.getType()), fname, makeGif)) {
                        imagesCreated++;
                    }
                }
                // Final 9 seconds
                else if (i >= 450 && i < 1800 && i%15 == 0) {
                    String fname = createImageFileName(imgName, imagesCreated);
                    if (qt.createQuadImage(new BufferedImage(qt.img.getWidth(), qt.img.getHeight(), qt.img.getType()), fname, makeGif)) {
                        imagesCreated++;
                    }
                }
            }
        }

        if (makeGif) {
            // 3 second pause before looping
            for (int i = 0; i < 30; ++i) {
                String fname = createImageFileName(imgName, imagesCreated);
                if (qt.createQuadImage(new BufferedImage(qt.img.getWidth(), qt.img.getHeight(), qt.img.getType()), fname, makeGif)) {
                    imagesCreated++;
                }
            }
        }

        String fileName = imgName.substring(0, imgName.length() - 4) + "quad" + iterations + ".jpg";
        if (qt.createQuadImage(new BufferedImage(qt.img.getWidth(), qt.img.getHeight(), qt.img.getType()), fileName, false)) {
            imagesCreated++;
        }

        long end = Instant.now().toEpochMilli();
        System.out.println("Finished QuadImage in " + Utils.formatSeconds(start, end));
    }

    private String createImageFileName(String name, int i) {
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
}
