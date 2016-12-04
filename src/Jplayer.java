import com.sun.awt.AWTUtilities;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javax.swing.*;
import java.awt.*;

import java.awt.datatransfer.*;
import java.awt.dnd.*;

import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.net.URI;
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.List;

public class Jplayer extends JFrame {
    // main container
    private ImgPanel contentPane;
    public Livelist livelist;
    public Vol volBar;
    public Droparea droparea;

    // 顶部条
    private JPanel theHead;
    private JLabel title;

    // 按钮
    private Btns playBtn;
    private Btns nextBtn;
    private Btns preBtn;
    private Btns listBtn;

    // 播放状态
    public JStatus jStatus;
    // 进度
    public LiveSlider ls;
    // 音量

    // 播放器
    public String path;
    public MediaPlayer mediaPlayer;
    private Media media;


    public Point loc = null;
    public Point tmp = null;
    public boolean isDragged = false;




    public static void main(String[] args){
        Jplayer myP =  new Jplayer();
    }

    private void JP_View_init(){
        setTitle("JPlayer");
        // 程序图标
        setIconImage(
                Toolkit.getDefaultToolkit().getImage(
                        Jplayer.class.getResource("images/main-theme.png")
                )
        );
        setResizable(false);
        // 设置背景颜色
        setBackground(Color.WHITE);
        // 设置位置
        setBounds(200, 100, 855, 435);
        // 点右上角按钮的时候的默认操作，这里设置为  EXIT_ON_CLOSE 也就是直接退出
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setDefaultLookAndFeelDecorated(true);

        AWTUtilities.setWindowShape(this,
                new RoundRectangle2D.Double(2.0D, 2.0D, this.getWidth(),
                        this.getHeight(), 24.0D, 24.0D));

        contentPane = new ImgPanel();



        Container jp = getContentPane();
        jp.add(contentPane);

        contentPane.setLayout(null);
        contentPane.setBackground(new Color(244, 244, 244));





        theHead = new JPanel();
        theHead.setBounds(0, 0, 855, 50);
        theHead.setBackground(new Color(0, 0, 0, 183));
        theHead.setLayout(null);

        title = new JLabel("welcome, Click the list and choose music");
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Microsoft Yahei", Font.BOLD , 22));
        title.setBounds(0, 0, 855, 50);
        title.setVisible(true);
        title.repaint();
        theHead.add(title);

        theHead.setVisible(true);
        theHead.repaint();


        URL[] closeIcons = {
                Jplayer.class.getResource("images/close_icon.png"),
                Jplayer.class.getResource("images/close-pressed.png"),
                Jplayer.class.getResource("images/close-pressed.png")
        };
        Btns toClose = new Btns(closeIcons, "close");
        toClose.setBounds(805, 0, 50, 50);
        toClose.setBorder(null);
        toClose.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        theHead.add(toClose);

        contentPane.add(theHead);



        Jplayer jptemp = this;
        theHead.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                isDragged = false;
                jptemp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            public void mousePressed(MouseEvent e) {
                tmp = new Point(e.getX(), e.getY());
                isDragged = true;
                jptemp.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }
        });
        theHead.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if(isDragged) {
                    loc = new Point(jptemp.getLocation().x + e.getX() - tmp.x,
                            jptemp.getLocation().y + e.getY() - tmp.y);

                    jptemp.setLocation(loc);
                }
            }
        });














        URL[] playIcons = {
            Jplayer.class.getResource("images/play_icon.png"),
            Jplayer.class.getResource("images/play-pressed.png"),
            Jplayer.class.getResource("images/play-pressed.png")
        };

        playBtn = new Btns(playIcons,"play");
        playBtn.setBorder(null);
        playBtn.setSize(60, 60);
        playBtn.setVisible(true);
        playBtn.setBounds(615, 130, 60, 60);
        playBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ButtonModel model = playBtn.getModel();
                if (model.isPressed()){
                    System.out.print("!!");
                }
                if (jStatus.isPlay){
                    thePlay(1); // pause;
                } else {
                    thePlay(0); // playByPath
                }

            }

        });
        playBtn.setVisible(true);
        playBtn.repaint();

        contentPane.add(playBtn);

        URL[] nextTemp = {
                Jplayer.class.getResource("images/next_icon.png"),
                Jplayer.class.getResource("images/next-pressed.png"),
                Jplayer.class.getResource("images/next-pressed.png")
        };
        nextBtn = new Btns(nextTemp,"next");
        nextBtn.setBorder(null);
        nextBtn.setBounds(735, 130, 60, 60);
        nextBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                livelist.toNext();
            }
        });
        contentPane.add(nextBtn);

        URL[] preTemp = {
                Jplayer.class.getResource("images/pre_icon.png"),
                Jplayer.class.getResource("images/pre-pressed.png"),
                Jplayer.class.getResource("images/pre-pressed.png")
        };
        preBtn = new Btns(preTemp,"pre");
        preBtn.setBorder(null);
        preBtn.setBounds(495, 130, 60, 60);
        preBtn.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                livelist.toPre();
            }
        });
        contentPane.add(preBtn);

        URL[] listTemp = {
                Jplayer.class.getResource("images/list_icon.png"),
                Jplayer.class.getResource("images/list-pressed.png"),
                Jplayer.class.getResource("images/list-pressed.png")
        };
        listBtn = new Btns(listTemp,"list");
        listBtn.setBorder(null);
        listBtn.setBounds(745, 320, 60, 30);
        listBtn.setBackground(null);
        listBtn.setOpaque(false);

        listBtn.setVisible(true);
        listBtn.repaint();
        contentPane.add(listBtn);
        listBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                jStatus.isListOpen = !jStatus.isListOpen;
                livelist.setVisible(jStatus.isListOpen);
            }
        });

        volBar = new Vol(this);
        volBar.setBounds(435, 355, 420, 160);
        contentPane.add(volBar);
        droparea = new Droparea(this);
        droparea.setBounds(0,0,855,435);

        contentPane.add(droparea);

    }

    public Jplayer(){
        // default path
        path = null;
        livelist = new Livelist(this);
        jStatus = new JStatus();

        JP_View_init();

        ls = new LiveSlider(300, 60, true);
        ls.setBorder(null);
        ls.setBounds(495, 200, 300, 60);

        contentPane.repaint();
        contentPane.add(ls);

        setVisible(true);
    }

//    public boolean playNew;
    public void thePlay(int status){
        JFXPanel fxPanel;
        fxPanel = new JFXPanel();

        playBtn.imgSrc[0] = Jplayer.class.getResource("images/pause_icon.png");
        playBtn.imgSrc[1] = Jplayer.class.getResource("images/pause_pressed.png");
        playBtn.imgSrc[2] = Jplayer.class.getResource("images/pause_pressed.png");

        try {

            if (status == 0) {
                if (jStatus.isPlay) {
                    mediaPlayer.pause();
                    jStatus.isPlay = false;
                } else if (jStatus.nowPlay == null) {
                    File Song = new File(path);
                    URI uri = Song.toURI();
                    String thePath = uri.toASCIIString();
                    media = new Media(thePath);
                    mediaPlayer = new MediaPlayer(media);
                    contentPane.flashImage(path, title);

                    jStatus.isPlay = true;
                    jStatus.nowPlay = mediaPlayer;
                    mediaPlayer.play();
                    jStatus.isPlay = true;
                } else if (jStatus.nowPlay != null) {
                    mediaPlayer.play();
                    jStatus.isPlay = true;
                }
            } else if (status == 1) {
                mediaPlayer.pause();
                playBtn.imgSrc[0] = Jplayer.class.getResource("images/play_icon.png");
                playBtn.imgSrc[1] = Jplayer.class.getResource("images/play-pressed.png");
                playBtn.imgSrc[2] = Jplayer.class.getResource("images/play-pressed.png");

                jStatus.isPlay = false;
            } else if (status == 10) {
                if (jStatus.nowPlay != null) {
                    mediaPlayer.stop();
                }

                File Song = new File(path);
                URI uri = Song.toURI();
                String thePath = uri.toASCIIString();
                media = new Media(thePath);
                mediaPlayer = new MediaPlayer(media);
                contentPane.flashImage(path, title);

                jStatus.isPlay = true;
                jStatus.nowPlay = mediaPlayer;
                mediaPlayer.play();

            }

        } catch (Exception e){
            e.printStackTrace();
            playBtn.imgSrc[0] = Jplayer.class.getResource("images/play_icon.png");
            playBtn.imgSrc[1] = Jplayer.class.getResource("images/play-pressed.png");
            playBtn.imgSrc[2] = Jplayer.class.getResource("images/play-pressed.png");
            this.repaint();
            // 出错结束 thePlay()
            return;
        }

        mediaPlayer.setOnPlaying(new Runnable(){
            @Override
            public void run() {
                Duration time_left;

                ls.setCurrent(0.0);
                ls.setTotal(media.getDuration().toSeconds());
                ls.setPlayer(mediaPlayer);
                ls.now_status = jStatus;

                mediaPlayer.setVolume(volBar.now_vol);
                // 播放的时候 进度条要用到这个循环
                while (mediaPlayer.getCurrentTime().toSeconds() < media.getDuration().toSeconds()) {
                    ls.setCurrent(mediaPlayer.getCurrentTime().toSeconds());
                    time_left = mediaPlayer.getTotalDuration().subtract(mediaPlayer.getCurrentTime());
//                    System.out.println("Vol: "+ mediaPlayer.getVolume());
//                    mediaPlayer.setVolume(volBar.now_vol);
//                    if (time_left.toSeconds() < 0.00001){
//                        System.out.println("EXIT!");
//                        break;
//                    }

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        mediaPlayer.setOnEndOfMedia(new Runnable(){
            public void run(){
                System.out.println("END!");
                livelist.toNext();
            }
        });

        this.repaint();
    }

    public void setMediaPlayerVol(Double vol){
        mediaPlayer.setVolume(vol);
    }

}
