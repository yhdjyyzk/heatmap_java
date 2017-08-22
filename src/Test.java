import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Created by yuanzhaokang on 2016/4/8 0008.
 */
public class Test {
    public static void main(String[] args) {
        Heatmap render = new Heatmap();

        ArrayList<Point2D> points = new ArrayList<Point2D>();
        int width = 500;
        int height = 500;
        int radius = 20;
        int opacity = 255;
        String heatmapName = "heatmap.png";
        
        // generate data set.
        for (int i = 0; i < 10000; i++) {
            double x = new Random().nextGaussian() * width;
            double y = new Random().nextGaussian() * height;
            Point2D.Double p = new Point2D.Double(x, y);

            points.add(p);
        }

        BufferedImage im = render.render(width, height, radius, points);

        try {
            ImageIO.write(im, "png", new File(heatmapName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
