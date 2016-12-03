import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by eczn on 2016/12/3.
 */
//implements Runnable
public class Vol extends Canvas {
    public double now_vol;

    public int width;
    public int height;

    Thread thread_vol;

    public Vol(){
        super();
//        mp = mediaplayer;

        setBackground(new Color(255,255,255));
//        volcanvas = new Volcanvas();
//        volcanvas.repaint();

//        add(volcanvas);
//        volcanvas.setBounds(0,0,160,160);


//        thread_vol = new Thread(this);
//        thread_vol.start();

//        this.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                now_vol = e.getX() / (double)width;
//                System.out.print("now_vol: ");
//                System.out.println(now_vol);
//            }
//        });
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.print("now_vol!!!");
            }
        });
    }

    public void paint(Graphics g){
//        g.clearRect(0, 0, total, height);
        g.setColor(new Color(222,222,222));
        g.fillRect(0,0,80,160);
    }

//    @Override
//    public void run(){
//        while (true){
//            System.out.println("Hello, Vol!");
//            try {
//                Thread.sleep(300);
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }
}
