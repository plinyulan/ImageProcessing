import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ChangeBackgroundToRed {
    public static void main(String[] args) {
        try {
            
            File input = new File("ChangeSkin.bmp"); 
            BufferedImage image = ImageIO.read(input);

            int width = image.getWidth();
            int height = image.getHeight();

            // check pixel
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color c = new Color(image.getRGB(x, y), true);

                    // ตรวจสอบว่าพื้นหลังเป็นสีขาวสนิท (รวม alpha)
                    if (c.getRed() == 255 && c.getGreen() == 255 && c.getBlue() == 255 && c.getAlpha() == 255) {
                        image.setRGB(x, y, new Color(255, 0, 0).getRGB()); // red
                    }
                }
            }

         
            File output = new File("ChangeBackgroundToRed.bmp");
            ImageIO.write(image, "bmp", output);

            System.out.println("Done!");

        } catch (Exception e) {
            System.err.println("เกิดข้อผิดพลาด: " + e.getMessage());
        }
    }
}