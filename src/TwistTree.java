import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TwistTree {
    public BufferedImage img;

    private int readx0;
    private int ready0;
    private int readx1;
    private int ready1;
    private int writex0;
    private int writey0;
    private int writex1;
    private int writey1;

    private int numPixels;

    public TwistTree northWest;
    public TwistTree northEast;
    public TwistTree southWest;
    public TwistTree southEast;

    private int width;
    private int height;

    private boolean isLeaf;

    // rx0, ry0
    //  +-------+-------+
    //  |       |       |
    //  |  nw   |  ne   |
    //  |       |       |
    //  |-------|-------|
    //  |       |       |
    //  |  sw   |  se   |
    //  |       |       |
    //  +-------+-------+
    //                  rx1, ry1

    public TwistTree(BufferedImage image, int rx0, int rx1, int ry0, int ry1, int wx0, int wx1, int wy0, int wy1) {
        img = image;

        readx0 = rx0;
        readx1 = rx1;
        ready0 = ry0;
        ready1 = ry1;

        writex0 = wx0;
        writex1 = wx1;
        writey0 = wy0;
        writey1 = wy1;

        width = rx1 - rx0;
        height = ry1 - ry0;
        numPixels = width*height;

        northWest = null;
        northEast = null;
        southWest = null;
        southEast = null;

        isLeaf = true;
    }

    public void SplitAndRotateQuads() {
        if (isLeaf) {
            isLeaf = false;
            // y write shifts down by height/2
            northWest = new TwistTree(img, readx0, readx0 + width/2, ready0, ready0 + height/2, readx0, readx0 + width/2, ready0 + height/2, ready0 + height/2 + height/2);

            // x write shifts left by width/2
            northEast = new TwistTree(img, readx0 + width/2, readx1, ready0, ready0 + height/2, readx0 + width/2 - width/2, readx1 - width/2, ready0, ready0 + height/2);

            // x write shifts right by width/2
            southWest = new TwistTree(img, readx0, readx0 + width/2, ready0 + height/2, ready1, readx0 + width/2, readx0 + width/2 + width/2, ready0 + height/2, ready1);

            // y write shifts up by height/2
            southEast = new TwistTree(img, readx0 + width/2, readx1, ready0 + height/2, ready1, readx0 + width/2, readx1, ready0 + height/2 - height/2, ready1 - height/2);
        }
        else {
            northWest.SplitAndRotateQuads();
            northEast.SplitAndRotateQuads();
            southWest.SplitAndRotateQuads();
            southEast.SplitAndRotateQuads();
        }
    }

    public BufferedImage CreateTwisterImage(BufferedImage oImg) {
        if (isLeaf) {
            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    Color pixel = new Color(img.getRGB(readx0 + x, ready0 + y));

                    int  red   = pixel.getRed();
                    int  green = pixel.getGreen();
                    int  blue  = pixel.getBlue();

                    int rgb = red;
                    rgb = (rgb << 8) + green;
                    rgb = (rgb << 8) + blue;

                    oImg.setRGB(writex0 + x, writey0 + y, rgb);
                }
            }
        }
        else {
            northWest.CreateTwisterImage(oImg);
            northEast.CreateTwisterImage(oImg);
            southWest.CreateTwisterImage(oImg);
            southEast.CreateTwisterImage(oImg);
        }
        return oImg;
    }
}
