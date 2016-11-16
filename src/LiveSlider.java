/**
 * Created by eczn on 2016/11/16.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LiveSlider extends JPanel {
    private int height;
    private int width;
    public int currentAt;
    public double percentage;
    public int total;
    private boolean init;

    public static void main(String[] args){
        Frame fr = new Frame();
        LiveSlider ls = new LiveSlider();

        fr.add(ls);

        fr.setBounds(0,0,800,435);

        fr.setVisible(true);
        fr.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        g.clearRect(0,0,width,height);

        g.setColor(new Color(233,233,233));
        g.fillRect(0,20,width,height-50);

//        g.setColor(new Color(244, 244, 244));
        g.setColor(new Color(200,200,204));



        double temp = percentage * width;
        System.out.println(temp);

        g.fillRect(0,20,(int)temp,height-50);

        if (init){
            System.out.println(width);
            repaint();
            init = false;
        }

        System.out.println("");
    }

    private void mouseInit(){
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println("where: "+e.getX());
                System.out.println("Test :"+ (1/3));
                System.out.println("% :"+ (e.getX() / (double)width));
                percentage = (e.getX() / (double)width);

                // Jp
                repaint();

            }
        });
    }

    public LiveSlider(){
        super();
        init = true;
        height = 0;
        width = 0;
        mouseInit();
    }
    public LiveSlider(int W, int H){
        super();
        init = true;
        height = H;
        width = W;
        mouseInit();
    }
}
