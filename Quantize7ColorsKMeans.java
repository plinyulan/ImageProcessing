import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

public class Quantize7ColorsKMeans {
    public static void main(String[] args) throws Exception {
        String in  = (args.length > 0) ? args[0] : "ReplaceColorToWhite.bmp";
        String out = (args.length > 1) ? args[1] : "Quantize7ColorsKMeans.bmp";
        int K = 7;                 // 7 color
        int MAX_ITERS = 20;        //  K-Means

        BufferedImage img = ImageIO.read(new File(in));
        int w = img.getWidth(), h = img.getHeight();
        int[] pix = img.getRGB(0, 0, w, h, null, 0, w);

        
        int n = pix.length;
        int[] r = new int[n], g = new int[n], b = new int[n];
        for (int i = 0; i < n; i++) {
            int p = pix[i];
            r[i] = (p >> 16) & 0xFF;
            g[i] = (p >> 8)  & 0xFF;
            b[i] = (p)       & 0xFF;
        }

        // ===== สุ่ม centroid เริ่มต้นจากพิกเซลจริง =====
        Random rnd = new Random(0);
        double[] cr = new double[K], cg = new double[K], cb = new double[K];
        for (int k = 0; k < K; k++) {
            int idx = rnd.nextInt(n);
            cr[k] = r[idx];
            cg[k] = g[idx];
            cb[k] = b[idx];
        }

        int[] assign = new int[n];

        // ===== K-Means =====
        for (int iter = 0; iter < MAX_ITERS; iter++) {
           
            for (int i = 0; i < n; i++) {
                double bestD = Double.MAX_VALUE;
                int bestK = 0;
                for (int k = 0; k < K; k++) {
                    double dr = r[i] - cr[k];
                    double dg = g[i] - cg[k];
                    double db = b[i] - cb[k];
                    double d2 = dr*dr + dg*dg + db*db;
                    if (d2 < bestD) { bestD = d2; bestK = k; }
                }
                assign[i] = bestK;
            }

            
            double[] sumR = new double[K], sumG = new double[K], sumB = new double[K];
            int[] count = new int[K];
            for (int i = 0; i < n; i++) {
                int k = assign[i];
                sumR[k] += r[i];
                sumG[k] += g[i];
                sumB[k] += b[i];
                count[k]++;
            }
            for (int k = 0; k < K; k++) {
                if (count[k] > 0) {
                    cr[k] = sumR[k] / count[k];
                    cg[k] = sumG[k] / count[k];
                    cb[k] = sumB[k] / count[k];
                } else {
                    
                    int idx = rnd.nextInt(n);
                    cr[k] = r[idx];
                    cg[k] = g[idx];
                    cb[k] = b[idx];
                }
            }
        }

        
        for (int i = 0; i < n; i++) {
            int k = assign[i];
            int rr = clamp((int)Math.round(cr[k]));
            int gg = clamp((int)Math.round(cg[k]));
            int bb = clamp((int)Math.round(cb[k]));
            pix[i] = (0xFF << 24) | (rr << 16) | (gg << 8) | bb;
        }

        BufferedImage outImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        outImg.setRGB(0, 0, w, h, pix, 0, w);
        ImageIO.write(outImg, "bmp", new File(out));
        System.out.println("Done!");
    }

    private static int clamp(int v) { return (v < 0) ? 0 : (v > 255 ? 255 : v); }
}