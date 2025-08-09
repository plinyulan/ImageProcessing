
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Checkhis {

    private BufferedImage img;
    private int width;
    private int height;

    public static void main(String[] args) {
        Checkhis im = new Checkhis("image/Quantize7ColorsKMeans.bmp");

        int[] histogram = im.getGrayscaleHistogram();
        if (histogram != null) {
            int count = 0;
            for (int v : histogram) {
                if (v != 0) {
                    count++;
                }
            }

            System.out.println("จำนวนระดับสีที่พบ: " + count);
            im.writeHistogramToCSV(histogram, "histogram.csv");
        }
    }

    public Checkhis(String imagePath) {
        try {
            img = ImageIO.read(new File(imagePath));
            width = img.getWidth();
            height = img.getHeight();
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }

    public void writeHistogramToCSV(int[] histogram, String fileName) {
        try (FileWriter fw = new FileWriter(fileName)) {
            for (int i = 0; i < histogram.length; i++) {
                fw.write(histogram[i] + (i < histogram.length - 1 ? "," : ""));
            }
            fw.write(System.lineSeparator());
            System.out.println("Histogram saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing CSV: " + e.getMessage());
        }
    }

    public int[] getGrayscaleHistogram() {
        if (img == null) {
            return null;
        }

        int[] histogram = new int[256];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;
                int gray = (int) Math.round(0.299 * r + 0.587 * g + 0.114 * b);
                histogram[gray]++;
            }
        }
        return histogram;
    }

}
