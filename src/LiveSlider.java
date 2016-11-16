/**
 * Created by eczn on 2016/11/16.
 */

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LiveSlider extends JPanel {
    private int height;
    private int width;
    public double currentAt;
    public double percentage;
    public double total;
    private boolean init;
    private MediaPlayer now_playing;
    public JStatus now_status;

    public void setPlayer(MediaPlayer P){
        now_playing = P;
    }

    public void setCurrent(double c){
        currentAt = c;
        percentage = currentAt / total;
        repaint();
    }
    public void setTotal(double t){
        total = t;
    }

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

        g.setColor(new Color(173,180,194));

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

    public boolean onEvent;
    private void mouseInit(){
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
//                onEvent = true;

                if (now_status.isPlay){
                    percentage = (e.getX() / (double)width);
                    now_playing.seek(new Duration(total * percentage * 1000));
                }
                // Jp
//                onEvent = false;
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
