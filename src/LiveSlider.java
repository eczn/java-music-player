/**
 * Created by eczn on 2016/11/16.
 */
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LiveSlider extends JPanel implements Runnable {
    private int height;
    private int width;
    public double currentAt;
    public double percentage;
    public double total;
    private boolean init;
//    public MediaPlayer now_playing;
    public Jplayer jp;
    public JStatus now_status;
    public boolean X_MAIN;
    public Thread sliderUI;

    @Override
    public void run(){
//        while(true){
            System.out.println(total);
            System.out.println("currentAt: "+currentAt);
            System.out.println("percentage: "+percentage);
            System.out.println();

            try {
                Thread.sleep(500);
                if (jp.mediaPlayer != null){
                    Duration now = jp.mediaPlayer.getCurrentTime();
                    Duration total = jp.mediaPlayer.getTotalDuration();

                    if (now.toString() == "NaN"){
//                        System.out.println("now == total!, 2stop");
//                        jp.mediaPlayer.stop();
                        jp.mediaPlayer.pause();
                        jp.mediaPlayer.play();
                    }

                    setCurrent(now.toSeconds());
                }
            } catch (Exception e){
                e.printStackTrace();
            }
//        }

        run();
    }

    public LiveSlider(){
        super();
        init = true;
        height = 0;
        width = 0;
        mouseInit();
    }
    public LiveSlider(int W, int H, boolean X, Jplayer JP){
        super();
        jp = JP;
        init = true;
        height = H;
        width = W;
        X_MAIN = X;
        mouseInit();

        sliderUI = new Thread(this);
        sliderUI.start();
    }

//    public void setPlayer(MediaPlayer P){
//        now_playing = P;
//    }

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

        if (X_MAIN){
            g.fillRect(0,20,width,height-50);
        } else {
            g.fillRect(0,20,width,height-50);
        }

        g.setColor(new Color(173,180,194));

        double temp = percentage * width;
//        System.out.println(temp);

        if (X_MAIN){
            g.fillRect(0,20,(int)temp,height-50);
        } else {
            g.fillRect(0,20,(int)temp,height-50);
        }

        if (init){
            System.out.println(width);
            repaint();
            init = false;
        }

    }

    public boolean onEvent;
    private void mouseInit(){
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (now_status.isPlay){
                    percentage = (e.getX() / (double)width);
                    jp.mediaPlayer.seek(new Duration(total * percentage * 1000));
                }
                repaint();
            }
        });
    }
}
