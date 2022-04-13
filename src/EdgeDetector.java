import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EdgeDetector {
    public BufferedImage img;
    private int height;
    private int width;
    private ArrayList<ArrayList<Color>> colors;

    public EdgeDetector(BufferedImage image) {
        img = image;
        height = img.getHeight();
        width = img.getWidth();
        updateColors();
        System.out.println("Running EdgeDetection...");
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

    public void DetectEdges() {
        // Filter
        //  0 -1  0
        // -1  4 -1
        //  0 -1  0

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int redsum = 0;
                int greensum = 0;
                int bluesum = 0;
                int neighbours = 0;
                Color pixel;

//                if (y > 0 && x > 0) {
//                    neighbours++;
//                    pixel = colors.get(y-1).get(x-1);
//                    redsum += -1*pixel.getRed();
//                    greensum += -1*pixel.getGreen();
//                    bluesum += -1*pixel.getBlue();
//                }
                if (y > 0) {
                    neighbours++;
                    pixel = colors.get(y-1).get(x);
                    redsum += -1*pixel.getRed();
                    greensum += -1*pixel.getGreen();
                    bluesum += -1*pixel.getBlue();
                }
//                if (y > 0 && x < width - 1) {
//                    neighbours++;
//                    pixel = colors.get(y-1).get(x+1);
//                    redsum += -1*pixel.getRed();
//                    greensum += -1*pixel.getGreen();
//                    bluesum += -1*pixel.getBlue();
//                }

                if (x > 0) {
                    neighbours++;
                    pixel = colors.get(y).get(x-1);
                    redsum += -1*pixel.getRed();
                    greensum += -1*pixel.getGreen();
                    bluesum += -1*pixel.getBlue();
                }
                pixel = colors.get(y).get(x);
                redsum += 4*pixel.getRed();
                greensum += 4*pixel.getGreen();
                bluesum += 4*pixel.getBlue();
                if (x < width - 1) {
                    neighbours++;
                    pixel = colors.get(y).get(x+1);
                    redsum += -1*pixel.getRed();
                    greensum += -1*pixel.getGreen();
                    bluesum += -1*pixel.getBlue();
                }

//                if (y < height - 1 && x > 0) {
//                    neighbours++;
//                    pixel = colors.get(y+1).get(x-1);
//                    redsum += -1*pixel.getRed();
//                    greensum += -1*pixel.getGreen();
//                    bluesum += -1*pixel.getBlue();
//                }
                if (y < height - 1) {
                    neighbours++;
                    pixel = colors.get(y+1).get(x);
                    redsum += -1*pixel.getRed();
                    greensum += -1*pixel.getGreen();
                    bluesum += -1*pixel.getBlue();
                }
//                if (y < height - 1 && x < width - 1) {
//                    neighbours++;
//                    pixel = colors.get(y+1).get(x+1);
//                    redsum += -1*pixel.getRed();
//                    greensum += -1*pixel.getGreen();
//                    bluesum += -1*pixel.getBlue();
//                }

                // 2 -> 3
                // 3 -> 5
                // 4 -> 8
//                neighbours = ((neighbours*(neighbours-1))/2)+2;
//                neighbours = 0;
//                neighbours = 9;

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

