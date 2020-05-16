import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class QuadTree {
    BufferedImage img;

    private int level;

    public int xStart;
    public int xEnd;
    public int yStart;
    public int yEnd;

    private int numPixels;

    public QuadTree northWest;
    public QuadTree northEast;
    public QuadTree southWest;
    public QuadTree southEast;

    private boolean isSouthEastMostQuad;

    public int avgRed;
    public int avgGreen;
    public int avgBlue;
    public double colorError;

    boolean gridLines;

    public QuadTree(BufferedImage image, int x0, int x1, int y0, int y1, boolean se, int l, boolean gl) {
        img = image;

        gridLines = gl;

        level = l;

        xStart = x0;
        xEnd = x1;
        yStart = y0;
        yEnd = y1;

        numPixels = (x1 - x0) * (y1 - y0);

        northWest = null;
        northEast = null;
        southWest = null;
        southEast = null;

        isSouthEastMostQuad = se;

        // Assigns to avgRed, avgGreen and avgBlue
        calculateRGBAverages();

        colorError = calculateColorError();
    }

    public void splitQuadWithLargestError() {
        if (northWest == null && numPixels != 0) {
            int xHalf = xStart + (xEnd - xStart)/2;
            int yHalf = yStart + (yEnd - yStart)/2;

            northWest = new QuadTree(img, xStart, xHalf, yStart, yHalf, false, level+1, gridLines);
            northEast = new QuadTree(img, xHalf, xEnd, yStart, yHalf, false, level+1, gridLines);
            southWest = new QuadTree(img, xStart, xHalf, yHalf, yEnd, false, level+1, gridLines);
            southEast = new QuadTree(img, xHalf, xEnd, yHalf, yEnd, isSouthEastMostQuad, level+1, gridLines);

            isSouthEastMostQuad = false;
        }
        else {
            if (hasLargestError(northWest)) {
                northWest.splitQuadWithLargestError();
            }
            else if (hasLargestError(northEast)) {
                northEast.splitQuadWithLargestError();
            }
            else if (hasLargestError(southWest)) {
                southWest.splitQuadWithLargestError();
            }
            else if (hasLargestError(southEast)) {
                southEast.splitQuadWithLargestError();
            }
        }

        colorError = Math.max(Math.max(northWest.colorError, northEast.colorError), Math.max(southWest.colorError, southEast.colorError));
    }

    public boolean createQuadImage(BufferedImage oImg, String imgName, boolean gif) throws IOException {
        if (northWest == null) {
            for (int y = yStart; y < yEnd; ++y) {
                for (int x = xStart; x < xEnd; ++x) {
                    if (gridLines && ((xEnd - xStart) > 2) && ((yEnd - yStart) > 2) && (x == xStart || x == xEnd - 1 || y == yStart || y == yStart - 1)) {
                        int avgColor = 0;
                        avgColor = (avgColor << 8) + 0;
                        avgColor = (avgColor << 8) + 0;
                        oImg.setRGB(x, y, avgColor);
                    }
                    else {
                        int avgColor = avgRed;
                        avgColor = (avgColor << 8) + avgGreen;
                        avgColor = (avgColor << 8) + avgBlue;
                        oImg.setRGB(x, y, avgColor);
                    }
                }
            }

            if (isSouthEastMostQuad) {
                File frameDir = new File(imgName.substring(0, imgName.length() - 7));
                if ((gif && frameDir.exists()) || !gif) {
                    File ofile = new File(imgName);
                    ImageIO.write(oImg, "jpg", ofile);
                    System.out.println("Created " + imgName);
                    return true;
                }
                else {
                    System.out.println(imgName.substring(0, imgName.length() - 7) + " doesn't exist");
                }
            }
        }
        else {
            return northWest.createQuadImage(oImg, imgName, gif) ||
            northEast.createQuadImage(oImg, imgName, gif) ||
            southWest.createQuadImage(oImg, imgName, gif) ||
            southEast.createQuadImage(oImg, imgName, gif);
        }
        return false;
    }

    private boolean hasLargestError(QuadTree qt) {
        double stdDev = qt.colorError;
        return stdDev >= northWest.colorError && stdDev >= northEast.colorError && stdDev >= southWest.colorError && stdDev >= southEast.colorError;
    }

    private void calculateRGBAverages() {
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                Color pixel = new Color(img.getRGB(x, y));

                int  red   = pixel.getRed();
                int  green = pixel.getGreen();
                int  blue  = pixel.getBlue();

                redSum += red;
                greenSum += green;
                blueSum += blue;
            }
        }

        if (numPixels != 0) {
            avgRed = redSum/numPixels;
            avgGreen = greenSum/numPixels;
            avgBlue = blueSum/numPixels;
        }
        else {
            avgRed = 0;
            avgGreen = 0;
            avgBlue = 0;
        }
    }

    private double calculateColorError() {
        int redSqaureSum = 0;
        int greenSquareSum = 0;
        int blueSquareSum = 0;

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                Color pixel = new Color(img.getRGB(x, y));

                int  red   = pixel.getRed();
                int  green = pixel.getGreen();
                int  blue  = pixel.getBlue();

                redSqaureSum += Math.abs(red - avgRed);
                greenSquareSum += Math.abs(green - avgGreen);
                blueSquareSum += Math.abs(blue - avgBlue);
            }
        }

        double redStdDev;
        double greenStdDev;
        double blueStdDev;

        if (numPixels != 0) {
            redStdDev = Math.sqrt(redSqaureSum/numPixels);
            greenStdDev = Math.sqrt(greenSquareSum/numPixels);
            blueStdDev = Math.sqrt(blueSquareSum/numPixels);
        }
        else {
            redStdDev = 0;
            greenStdDev = 0;
            blueStdDev = 0;
        }

        return (redStdDev + blueStdDev + greenStdDev)/level;
    }
}
