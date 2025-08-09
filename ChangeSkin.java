import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ChangeSkin {
    
    public static void main(String[] args) {
       
  try {
            BufferedImage image = ImageIO.read(new File("ChangeBeard.bmp"));
            BufferedImage processedImage = changeColors(image);
            ImageIO.write(processedImage, "bmp", new File("ChangeSkin.bmp"));
            System.out.println("Done!");
        } catch (IOException e) {
            System.err.println("Error processing image: " + e.getMessage());
        }
    }

    public static BufferedImage changeColors(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        int[] targetRGBs = {
            new Color(0x71D8A4).getRGB(),
            new Color(0x7DCFA2).getRGB(),
            new Color(0x8CCFAD).getRGB(),
            new Color(0x6EBE93).getRGB()
        };
        
        
        Color singleBrownColor = new Color(210, 180, 140); 
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = originalImage.getRGB(x, y);
                Color originalColor = new Color(rgb, true);
                
                // Skip transparent pixels
                if (originalColor.getAlpha() == 0) {
                    newImage.setRGB(x, y, rgb);
                    continue;
                }
                
                
                boolean replaced = false;
                for (int target : targetRGBs) {
                    if ((rgb & 0xFFFFFF) == (target & 0xFFFFFF)) {
                        newImage.setRGB(x, y, singleBrownColor.getRGB());
                        replaced = true;
                        break;
                    }
                }
                
                if (replaced) continue;
                
                
                float[] hsv = Color.RGBtoHSB(
                    originalColor.getRed(),
                    originalColor.getGreen(),
                    originalColor.getBlue(),
                    null
                );
                
                float hue = hsv[0] * 360;
                float saturation = hsv[1] * 100;
                float value = hsv[2] * 100;
                
                if (isInTargetRange(hue, saturation, value)) {
                    // ใช้สีเดียวแทนการคำนวณใหม่
                    newImage.setRGB(x, y, singleBrownColor.getRGB());
                } else {
                    newImage.setRGB(x, y, rgb);
                }
            }
        }
        
        return newImage;
    }
    
    private static boolean isInTargetRange(float hue, float saturation, float value) {
        // ช่วง HSV สำหรับโทนผิว
        return (hue >= 150 && hue <= 180) &&
               (saturation >= 18 && saturation <= 65) &&
               (value >= 60 && value <= 95);
    }
    
    
    private static Color getLightBrownColor(float originalValue) {
        float brownHue = 35f / 360f;
        float brownSaturation = 0.4f;
        float brownValue = originalValue / 100f;
        brownValue = Math.max(0.6f, Math.min(0.9f, brownValue));
        return Color.getHSBColor(brownHue, brownSaturation, brownValue);
    }
    
    public static BufferedImage changeColorsWithPresetBrowns(BufferedImage originalImage) {
        return originalImage;
    }
    
    public static void analyzeColors(BufferedImage image) {
    }
}
