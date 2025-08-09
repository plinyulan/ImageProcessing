import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class AlphaTrimFilter {

    public static void main(String[] args) throws Exception {
     
        BufferedImage image = ImageIO.read(new File("AlphaTrimFilter.bmp"));

       
        BufferedImage filtered = applyAlphaTrimFilter(image, 9, 50); // windowSize=3, d=4

       
        ImageIO.write(filtered, "bmp", new File("AlphaTrimFilter.bmp"));
        System.out.println("Done!");
    }

    public static BufferedImage applyAlphaTrimFilter(BufferedImage image, int windowSize, int d) {
        int width = image.getWidth();
        int height = image.getHeight();
        int radius = windowSize / 2;
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int[] rList = new int[windowSize * windowSize];
                int[] gList = new int[windowSize * windowSize];
                int[] bList = new int[windowSize * windowSize];
                int count = 0;

                for (int dy = -radius; dy <= radius; dy++) {
                    for (int dx = -radius; dx <= radius; dx++) {
                        int nx = clamp(x + dx, 0, width - 1);
                        int ny = clamp(y + dy, 0, height - 1);
                        Color color = new Color(image.getRGB(nx, ny));
                        rList[count] = color.getRed();
                        gList[count] = color.getGreen();
                        bList[count] = color.getBlue();
                        count++;
                    }
                }

                Arrays.sort(rList);
                Arrays.sort(gList);
                Arrays.sort(bList);

                int trim = d / 2;
                int sumR = 0, sumG = 0, sumB = 0;
                for (int i = trim; i < rList.length - trim; i++) {
                    sumR += rList[i];
                    sumG += gList[i];
                    sumB += bList[i];
                }
                int div = rList.length - d;
                int r = sumR / div;
                int g = sumG / div;
                int b = sumB / div;

                Color avgColor = new Color(r, g, b);
                result.setRGB(x, y, avgColor.getRGB());
            }
        }

        return result;
    }

    private static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }
}
