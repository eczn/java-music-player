import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by eczn on 2016/12/3.
 */

public class Vol extends Canvas implements Runnable {
    public double now_vol;
    public Jplayer jplayer;
    public int width;
    public int height;
    public boolean isPressed;
    Thread thread_vol;

    public Vol(Jplayer JPLAYER){
        super();
        jplayer = JPLAYER;
//        mp = mediaplayer;
        now_vol = 1.0;
        height = 40;
        System.out.println("!@#$!: "+getSize().getWidth());
//        now_at = (int)(this.getSize().getWidth());
        now_at = 420;
        width = 420;
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
        repaint();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                isPressed = true;
                System.out.println("isPressed: "+isPressed);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                now_at = e.getX();
                now_vol = now_at / getSize().getWidth();

                try {
                    jplayer.setMediaPlayerVol(now_vol);
                } catch (Exception exce){
                    exce.printStackTrace();
                    System.out.println("bugbug!!!!");
                }
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionListener(){
            @Override
            public void mouseDragged(MouseEvent e){
                if (isPressed){
                    now_at = e.getX();
                    pre_at = now_at;
                    now_vol = now_at / getSize().getWidth();

                    try {
                        jplayer.setMediaPlayerVol(now_vol);
                    } catch (Exception exce){
                        exce.printStackTrace();
                        System.out.println("bugbug!!!!");
                    }
                    repaint();
                }
            }
            @Override
            public void mouseMoved(MouseEvent e){
                if (isPressed){
                    now_at = e.getX();
                    pre_at = now_at;
                    now_vol = now_at / getSize().getWidth();

                    try {
                        jplayer.setMediaPlayerVol(now_vol);
                    } catch (Exception exce){
                        exce.printStackTrace();
                        System.out.println("MediaPlayerVolSettingBUG");
                    }
                    repaint();
                }
            }
        });

        jplayer.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
//                isDown = e.getWheelRotation()
//                -1 mean UP
//                 1 mean DOWN
                System.out.println(e.getWheelRotation());
//                now_vol-=e.getWheelRotation()*2 / 100.0;

                now_at -= e.getWheelRotation() * 50;
                now_vol = now_at / getSize().getWidth();
                repaint();

                System.out.print("!!WHEEL!!    ");
                System.out.println(e.getWheelRotation());

//                shouuldGoAnimate = true;
                if (now_vol >= 1.0){
                    now_vol = 1.0;
                } else if (now_vol <= 0){
                    now_vol = 0;
                }
                try {
                    jplayer.setMediaPlayerVol(now_vol);
                } catch (Exception exce){
                    exce.printStackTrace();
                    System.out.println("bugbug!!!!");
                }

            }
        });
    }

    public int now_at=-1;
    public int pre_at;
    @Override
    public void paint(Graphics g){
        int rgb;
        if (now_at == -1){
            now_at = 160;
        }

        if (pre_at != now_at){
            System.out.println("now_at != pre_at");
            try {

                int i = 0;
                int cha = now_at - pre_at;
//                g.setColor(new Color(222,222,222));
                System.out.println("cha!!: "+cha);
                int temp = (cha>=0)?(1):(-1);


                for (i=0;(cha>=0)?(i<=cha):(i>=cha);i+=temp){
                    rgb = (int)(((pre_at+i+0.0) / width) * -40);
                    g.setColor(new Color(222+rgb,222+rgb,222+rgb));
                    g.clearRect(0, 0, 420, height);
                    System.out.println(pre_at+i);
                    g.fillRect(0, 0, pre_at+i, height);

                    g.setColor(new Color(0,0,0));
                    g.drawString( Integer.toString((int)(now_vol*100)), 10, 35);

                    Thread.sleep(1);
                }

//                double delta = cha / 100.0;
//                System.out.println(delta);
//                for (i=0;i<=100;i++){
//                    System.out.println(pre_at);
//                    g.clearRect(0, 0, 420, 160);
//                    g.fillRect(0, 0, pre_at, 160);
//                    pre_at = (int)((double)pre_at+delta);
//                    Thread.sleep(1);
//                }
            } catch (Exception e){
                e.printStackTrace();
            }
            pre_at = now_at;
            g.fillRect(0, 0, now_at, height);
            repaint();
        }

        rgb = (int)(now_vol * -40);
        g.setColor(new Color(222+rgb,222+rgb,222+rgb));

        g.fillRect(0, 0, now_at, height);
        g.setColor(new Color(0,0,0));
        g.drawString( Integer.toString((int)(now_vol*100)), 10, 35);
    }

    @Override
    public void run(){
        while (true){
//            repaint();
//            pre_at = now_at;
            try {
                Thread.sleep(1000);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
