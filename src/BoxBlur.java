import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class BoxBlur {
    public BufferedImage img;
    private int width;
    private int height;
    private ArrayList<ArrayList<Color>> colors;


    public BoxBlur(BufferedImage image) throws IOException {
        img = image;
        width = img.getWidth();
        height = img.getHeight();
        updateColors();
        System.out.println("Running BoxBlur...");
    }

    private void updateColors() {
        colors = new ArrayList<ArrayList<Color>>();
        for (int y = 0; y < height; ++y) {
            ArrayList<Color> colorRow = new ArrayList<Color>();
            for (int x = 0; x < width; ++x) {
                Color pixel = new Color(img.getRGB(x, y));
                colorRow.add(pixel);
            }
            colors.add(colorRow);
        }
    }

    public void BlurImage() {
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                // set the pixel colour to be the average of it and its surrounding 8 neighbours colours
                int redsum = 0;
                int greensum = 0;
                int bluesum = 0;
                int neighbours = 0;
                Color pixel;

                if (y > 0 && x > 0) {
                    neighbours++;
                    pixel = colors.get(y-1).get(x-1);
                    redsum += pixel.getRed();
                    greensum += pixel.getGreen();
                    bluesum += pixel.getBlue();
                }
                if (y > 0) {
                    neighbours++;
                    pixel = colors.get(y-1).get(x);
                    redsum += pixel.getRed();
                    greensum += pixel.getGreen();
                    bluesum += pixel.getBlue();
                }
                if (y > 0 && x < width - 1) {
                    neighbours++;
                    pixel = colors.get(y-1).get(x+1);
                    redsum += pixel.getRed();
                    greensum += pixel.getGreen();
                    bluesum += pixel.getBlue();
                }

                if (x > 0) {
                    neighbours++;
                    pixel = colors.get(y).get(x-1);
                    redsum += pixel.getRed();
                    greensum += pixel.getGreen();
                    bluesum += pixel.getBlue();
                }
                pixel = colors.get(y).get(x);
                redsum += pixel.getRed();
                greensum += pixel.getGreen();
                bluesum += pixel.getBlue();
                if (x < width - 1) {
                    neighbours++;
                    pixel = colors.get(y).get(x+1);
                    redsum += pixel.getRed();
                    greensum += pixel.getGreen();
                    bluesum += pixel.getBlue();
                }

                if (y < height - 1 && x > 0) {
                    neighbours++;
                    pixel = colors.get(y+1).get(x-1);
                    redsum += pixel.getRed();
                    greensum += pixel.getGreen();
                    bluesum += pixel.getBlue();
                }
                if (y < height - 1) {
                    neighbours++;
                    pixel = colors.get(y+1).get(x);
                    redsum += pixel.getRed();
                    greensum += pixel.getGreen();
                    bluesum += pixel.getBlue();
                }
                if (y < height - 1 && x < width - 1) {
                    neighbours++;
                    pixel = colors.get(y+1).get(x+1);
                    redsum += pixel.getRed();
                    greensum += pixel.getGreen();
                    bluesum += pixel.getBlue();
                }

                int avgRed = redsum / (neighbours + 1);
                int avgGreen = greensum / (neighbours + 1);
                int avgBlue = bluesum / (neighbours + 1);

                int avgColor = avgRed;
                avgColor = (avgColor << 8) + avgGreen;
                avgColor = (avgColor << 8) + avgBlue;

                img.setRGB(x, y, avgColor);
            }
        }

        updateColors();
    }
}
