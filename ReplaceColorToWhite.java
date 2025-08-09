import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ReplaceColorToWhite {
    public static void main(String[] args) throws Exception {
        BufferedImage image = ImageIO.read(new File("GlassesToBlack.bmp"));

        int width = image.getWidth();
        int height = image.getHeight();

        
        float targetHue = 165f;
        float targetSat = 0.02f;
        float targetVal = 0.75f;
        float hueTolerance = 10f;     // องศา
        float satTolerance = 0.05f;   // 5%
        float valTolerance = 0.05f;   // 5%

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y), true);
                float[] hsv = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
                float hue = hsv[0] * 360;
                float sat = hsv[1];
                float val = hsv[2];

                if (Math.abs(hue - targetHue) < hueTolerance &&
                    Math.abs(sat - targetSat) < satTolerance &&
                    Math.abs(val - targetVal) < valTolerance) {
                    image.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }

        ImageIO.write(image, "bmp", new File("ReplaceColorToWhite.bmp"));
        System.out.println("Done!");
    }
} 

