import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Pixelate32x32 {
    public static void main(String[] args) {
        try {
           
            BufferedImage original = ImageIO.read(new File("AlphaTrimFilter.bmp"));

            // ย่อภาพลงเหลือ 32x32
            Image scaledDown = original.getScaledInstance(32, 32, Image.SCALE_REPLICATE);
            BufferedImage small = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
            Graphics2D gSmall = small.createGraphics();
            gSmall.drawImage(scaledDown, 0, 0, null);
            gSmall.dispose();

            // blocky
            BufferedImage pixelated = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D gFinal = pixelated.createGraphics();
            gFinal.drawImage(small.getScaledInstance(original.getWidth(), original.getHeight(), Image.SCALE_REPLICATE), 0, 0, null);
            gFinal.dispose();

            
            ImageIO.write(pixelated, "bmp", new File("Pixelate32x32.bmp"));
            System.out.println("Done!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}