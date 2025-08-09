import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ChangeBeard {
    public static void main(String[] args) throws Exception {
        
        BufferedImage image = ImageIO.read(new File("ChangeShirt.bmp"));
        int width = image.getWidth();
        int height = image.getHeight();

        ImageIO.write(image, "bmp", new File("ChangeBeard.bmp"));
        System.out.println("Done!");
        
        // set gray
        Color gray = new Color(128, 128, 128);
        int changedPixels = 0;

        // check pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pixel = new Color(image.getRGB(x, y), true);
                float[] hsv = Color.RGBtoHSB(pixel.getRed(), pixel.getGreen(), pixel.getBlue(), null);
                float hue = hsv[0] * 360;
                float sat = hsv[1] * 100;
                float val = hsv[2] * 100;

                // hue recheck green rage
                if (hue >= 90 && hue <= 130 && sat >= 15 && val >= 30 && val <= 90) {
                    image.setRGB(x, y, gray.getRGB());
                    changedPixels++;
                }
            }
        }
    }
}