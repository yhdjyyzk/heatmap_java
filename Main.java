import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by admin on 2016/4/8 0008.
 */
public class Main {
    public static void main(String[] args){
        Heatmap render=new Heatmap();

        ArrayList<Point> points=new ArrayList<Point>();
        for(int i=0;i<50;i++){
            points.add(new Point((int)(Math.random()*255),(int)(Math.random()*255)));
        }
        BufferedImage im=render.render(256,256,20,180,points);
        try{
            ImageIO.write(im,"png",new File("a.png"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
