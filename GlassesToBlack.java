import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GlassesToBlack {

    public static void main(String[] args) throws Exception {
        BufferedImage image = ImageIO.read(new File("ChangeBackgroundToRed.bmp"));
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y), true);

                float[] hsv = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
                float value = hsv[2]; // ความสว่าง

                if (value < 0.3f) {
                    // (HSV 0°, 0%, 0%) = RGB(0,0,0)
                    image.setRGB(x, y, new Color(0, 0, 0).getRGB());
                }
            }
        }

        setWhitePixel(image, 11, 20);
        setWhitePixel(image, 12, 21);
        setWhitePixel(image, 18, 20);
        setWhitePixel(image, 19, 22);

        
        ImageIO.write(image, "bmp", new File("GlassesToBlack.bmp"));
        System.out.println("Done!");
    }

    private static void setWhitePixel(BufferedImage img, int x, int y) {
        if (x >= 0 && x < img.getWidth() && y >= 0 && y < img.getHeight()) {
            img.setRGB(x, y, new Color(255, 255, 255).getRGB());
        }
    }
}