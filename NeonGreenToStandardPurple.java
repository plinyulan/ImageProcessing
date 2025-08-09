import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class NeonGreenToStandardPurple {

    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("ChangeBackgroundToWhite32x32.bmp"));
            int width = image.getWidth();
            int height = image.getHeight();

            BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color original = new Color(image.getRGB(x, y), true);
                    int r = original.getRed();
                    int g = original.getGreen();
                    int b = original.getBlue();

                    float[] hsv = Color.RGBtoHSB(r, g, b, null);
                    float hue = hsv[0] * 360;
                    float saturation = hsv[1];
                    float brightness = hsv[2];

                    Color newColor = original;

                    // เงื่อนไข: เขียวนีออน (เช่น ผม)
                    if (hue >= 60 && hue <= 180 && saturation > 0.7f && brightness > 0.7f && g > 200) {
                        // เปลี่ยน hue เป็นม่วง 285°
                        float newHue = 285f / 360f;
                        int rgb = Color.HSBtoRGB(newHue, saturation, brightness);
                        newColor = new Color(rgb);
                    }

                    output.setRGB(x, y, newColor.getRGB());
                }
            }

            ImageIO.write(output, "bmp", new File("NeonGreenToStandardPurple.bmp"));
            System.out.println("Done!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}