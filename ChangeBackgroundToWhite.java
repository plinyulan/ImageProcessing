import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ChangeBackgroundToWhite {

    public static void main(String[] args) throws Exception {
        
        BufferedImage image = ImageIO.read(new File("Pixelate32x32.bmp"));

        int width = image.getWidth();
        int height = image.getHeight();

        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color original = new Color(image.getRGB(x, y));

                // change RGB → HSB
                float[] hsb = Color.RGBtoHSB(original.getRed(), original.getGreen(), original.getBlue(), null);
                float hue = hsb[0] * 360;        // degree 0–360
                float sat = hsb[1];              // 0–1
                float bri = hsb[2];              // 0–1

                // เงื่อนไขพื้นหลัง: เบจอมเขียว → hue ~ 45–90°, sat ต่ำ, bright สูง
                if (hue >= 45 && hue <= 90 && sat < 0.5 && bri > 0.5) {
                    image.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }

        ImageIO.write(image, "bmp", new File("ChangeBackgroundToWhite32x32.bmp"));
        System.out.println("Done!");
    }
}
