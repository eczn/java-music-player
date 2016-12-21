/**
 * Created by eczn on 2016/11/16.
 */
import javafx.util.Duration;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LiveSlider extends JPanel implements Runnable {
    // 基本参数
    private int height;
    private int width;
    public double currentAt;
    public double percentage;
    public double total;
    private boolean init;
    public Jplayer jp;
    public JStatus now_status;
    public boolean X_MAIN;
    public Thread sliderUI;

    @Override // Runnable 轮询
    public void run(){
        while (true){ // 轮询的实现
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
                        jp.mediaPlayer.pause();
                        jp.mediaPlayer.play();
                    }

                    setCurrent(now.toSeconds());
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    // 测试用的构造器
    public LiveSlider(){
        super();
        init = true;
        height = 0;
        width = 0;
        mouseInit();
    }
    // 构造器
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

    // 设置当前播放进度
    public void setCurrent(double c){
        currentAt = c;
        percentage = currentAt / total;
        repaint();
    }
    // 设置总播放进度
    public void setTotal(double t){
        total = t;
    }

    // 测试用main方法
    public static void main(String[] args){
        Frame fr = new Frame();
        LiveSlider ls = new LiveSlider();

        fr.add(ls);

        fr.setBounds(0,0,800,435);

        fr.setVisible(true);
        fr.repaint();
    }

    @Override // 重写JPanel的绘制方法
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

    // 鼠标点击事件绑定，点击某处立即跳转到该处
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
