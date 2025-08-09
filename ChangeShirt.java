
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ChangeShirt {

    public static void main(String[] args) throws Exception {
        BufferedImage image = ImageIO.read(new File("NeonGreenToStandardPurple.bmp"));

        int width = image.getWidth();
        int height = image.getHeight();

        Color newColor = new Color(0, 102, 204); // น้ำเงินสด

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y), true);
                float[] hsv = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
                float hue = hsv[0] * 360;
                float sat = hsv[1];
                float bri = hsv[2];

                // เงื่อนไขเสื้อ: hue ช่วงสีเขียว (ประมาณ 100-160), ความสว่างและความอิ่มตัวพอสมควร
                boolean isGreenOrPurple = ((hue >= 100 && hue <= 160) || (hue >= 270 && hue <= 310))
                        && sat >= 0.4 && bri >= 0.2;

                if (isGreenOrPurple && y >= height * 0.84) {
                    image.setRGB(x, y, newColor.getRGB());
                }
                // detect purple RGB (200, 0, 200)
                int tolerance = 30;
                boolean isApproxPurple = Math.abs(color.getRed() - 200) < tolerance
                        && Math.abs(color.getGreen() - 0) < tolerance
                        && Math.abs(color.getBlue() - 200) < tolerance;
            }
        }

        ImageIO.write(image, "bmp", new File("ChangeShirt.bmp"));
        System.out.println("Done!");
    }
}
