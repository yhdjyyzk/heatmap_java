import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * heatmap
 *
 * @author YuanZhaokang
 * @time 2015-12-28
 */
public class Heatmap {
    public Heatmap() {
    }
    
    /**
     * ����heatmap
     * @param width ���ɵ�ͼƬ�Ŀ��
     * @param height ���ɵ�ͼƬ�ĸ߶�
     * @param radius heatmap��İ뾶
     * @param opcatity heatmap���͸����
     * @param points heatmap������ݼ�
     * @return BufferedImage, ��ɫ��heatmap
     */
    public BufferedImage render(int width, int height, int radius, int opcatity, ArrayList<Point2D> points) {
        BufferedImage palette = createPalette(); // ��ɫ��
        BufferedImage grayHeatmap = null; // heatmap�Ҷ�ͼ
        BufferedImage colorfulHeatmap = null; // heatmap��ɫͼ

        try {
            grayHeatmap = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D graphics2d = grayHeatmap.createGraphics();

            for (Point2D p : points) {
                int x = (int) p.getX();
                int y = (int) p.getY();

                // ���ƻҶ�ͼ
                graphics2d.setPaint(new RadialGradientPaint(new Point2D.Double(x, y), radius, new float[] { 0f, 1.0f },
                        new Color[] { new Color(0, 0, 0, 120), new Color(0, 0, 0, 0) }));

                graphics2d.fillArc(x - radius, y - radius, 2 * radius, 2 * radius, 0, 360);
            }

            // ���Ʋ�ɫheatmap
            ColorModel cm = ColorModel.getRGBdefault();
            colorfulHeatmap = new BufferedImage(grayHeatmap.getWidth(), grayHeatmap.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);

            for (int i = 0; i < grayHeatmap.getWidth(); i++) {
                for (int j = 0; j < grayHeatmap.getHeight(); j++) {
                    int alpha = cm.getAlpha(grayHeatmap.getRGB(i, j));
                    if (alpha != 0) {
                        int rgba = palette.getRGB(alpha, 0);

                        int r = cm.getRed(rgba);
                        int g = cm.getGreen(rgba);
                        int b = cm.getBlue(rgba);
                        // int a = cm.getAlpha(rgba);

                        colorfulHeatmap.setRGB(i, j, new Color(r, g, b, alpha).getRGB());
                    } else
                        colorfulHeatmap.setRGB(i, j, 0);
                }
            }

            ImageIO.write(grayHeatmap, "png", new File("gray.png"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // return dst;
        }

        return colorfulHeatmap;

    }

    // ���ɵ�ɫ��
    private BufferedImage createPalette() {
        BufferedImage palette = null;
        try {
            palette = new BufferedImage(256, 1, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D graphics2d = palette.createGraphics();
            graphics2d.setPaint(new LinearGradientPaint(new Point2D.Float(1, 0), new Point2D.Float(255, 0),
                    new float[] { 0.25f, 0.55f, 0.85f, 1.0f }, new Color[] { new Color(0, 0, 255), new Color(0, 255, 0),
                            new Color(255, 255, 0), new Color(255, 0, 0) }));

            graphics2d.fillRect(0, 0, 256, 1);
            try {
                ImageIO.write(palette, "png", new File("palette.png"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        return palette;
    }
}