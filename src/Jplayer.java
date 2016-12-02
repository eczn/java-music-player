import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.util.*;
import java.io.*;
import java.net.*;

public class Jplayer extends JFrame {
    // main container
    private ImgPanel contentPane;
    public Livelist livelist;

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

    public static void main(String[] args){
        Jplayer myP =  new Jplayer();
    }

    private void JP_View_init(){
        setTitle("JPlayer");
        // 程序图标
        setIconImage(
                Toolkit.getDefaultToolkit().getImage(
                        Jplayer.class.getResource("images/clio-emt-speaker.png")
                )
        );
        setResizable(false);
        // 设置背景颜色
        setBackground(Color.WHITE);
        // 设置位置
        setBounds(200, 100, 855, 435);
        // 点右上角按钮的时候的默认操作，这里设置为  EXIT_ON_CLOSE 也就是直接退出
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

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
        contentPane.add(theHead);

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
        listBtn.setBounds(745, 350, 60, 60);
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
    }

    public Jplayer(){
        // default path
        path = "E:/CloudMusic/1.mp3"; // the fir
        livelist = new Livelist(this);
        jStatus = new JStatus();

        JP_View_init();

        ls = new LiveSlider(300, 60, true);
        ls.setBorder(null);
        ls.setBounds(495, 200, 300, 60);

        contentPane.repaint();
        contentPane.add(ls);

//        SwingUtilities.updateComponentTreeUI(this);
        setVisible(true);
    }

//    public boolean playNew;

    public void thePlay(int status){
        JFXPanel fxPanel;
        fxPanel = new JFXPanel();


        if (status == 0){
            if (jStatus.isPlay){
                mediaPlayer.pause();
                jStatus.isPlay = false;
            } else if (jStatus.nowPlay == null){
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
        } else if (status == 1){
            mediaPlayer.pause();
            jStatus.isPlay = false;
        } else if (status == 10){
            if (jStatus.nowPlay != null){
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

        mediaPlayer.setOnPlaying(new Runnable(){
            @Override
            public void run() {
                Duration time_left;

                ls.setCurrent(0.0);
                ls.setTotal(media.getDuration().toSeconds());
                ls.setPlayer(mediaPlayer);
                ls.now_status = jStatus;

                // 播放的时候 进度条要用到这个循环
                while (mediaPlayer.getCurrentTime().toSeconds() < media.getDuration().toSeconds()) {
                    ls.setCurrent(mediaPlayer.getCurrentTime().toSeconds());
                    time_left = mediaPlayer.getTotalDuration().subtract(mediaPlayer.getCurrentTime());


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


}
