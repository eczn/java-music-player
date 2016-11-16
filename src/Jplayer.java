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
    public Container jp;
    public JLabel test;

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

    // 播放器
    public String path;
    public MediaPlayer mediaPlayer;
    private Media media;

    // List
    private Vector<String> PlayList;

    public static void main(String[] args){
        Jplayer myP =  new Jplayer();
    }
    private void JP_View_init(){
        setTitle("JPlayer");
        setIconImage(
                Toolkit.getDefaultToolkit().getImage(
                        Jplayer.class.getResource("images/clio-emt-speaker.png")
                )
        );
        setResizable(false);
        // 设置背景颜色
        setBackground(Color.WHITE);
        setBounds(200, 100, 800, 435);
        // 点右上角按钮的时候的默认操作，这里设置为  EXIT_ON_CLOSE 也就是直接退出
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

        contentPane = new ImgPanel();
//        contentPane.setVisible(true);
//        contentPane.repaint();
        Container jp = getContentPane();
        jp.add(contentPane);
//        setContentPane(jp);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(244, 244, 244));

        theHead = new JPanel();
        theHead.setBounds(0, 0, 800, 50);
        theHead.setBackground(new Color(0, 0, 0, 183));
        theHead.setLayout(null);
        {
//            title = new JLabel("jPlayer - FileName.mp3");
            title = new JLabel("welcome to jPlayer - FileName.mp3");
            title.setForeground(Color.WHITE);
            title.setHorizontalAlignment(SwingConstants.CENTER);
            title.setVerticalAlignment(SwingConstants.CENTER);
            title.setFont(new Font("Microsoft Yahei", Font.BOLD , 22));
//            panel_1.setFont(new Font("Consolas", Font.BOLD, 20));
            title.setBounds(0, 0, 800, 50);
            title.setVisible(true);
            title.repaint();
            theHead.add(title);
        }

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
        playBtn.setBounds(560, 130, 60, 60);
        playBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
//                playBtn.setIcon(temp);
                ButtonModel model = playBtn.getModel();
                if (model.isPressed()){
                    System.out.print("!!");
                }
                thePlay();
//                playBtn.setIcon(pre);
            }

        });
        playBtn.setVisible(true);
        playBtn.repaint();
        contentPane.add(playBtn);

        test = new JLabel("!!");
        test.setBounds(560, 200, 60, 60);
        test.setVisible(true);
        test.repaint();
        test.setText("#!@!#!$!$!$");
        contentPane.add(test);

        URL[] nextTemp = {
                Jplayer.class.getResource("images/next_icon.png"),
                Jplayer.class.getResource("images/next-pressed.png"),
                Jplayer.class.getResource("images/next-pressed.png")
        };
        nextBtn = new Btns(nextTemp,"next");
        nextBtn.setBorder(null);
        nextBtn.setBounds(680, 130, 60, 60);
        contentPane.add(nextBtn);
        nextBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

            }
        });


        URL[] preTemp = {
                Jplayer.class.getResource("images/pre_icon.png"),
                Jplayer.class.getResource("images/pre-pressed.png"),
                Jplayer.class.getResource("images/pre-pressed.png")
        };
        preBtn = new Btns(preTemp,"pre");
        preBtn.setBorder(null);
        preBtn.setBounds(440, 130, 60, 60);
        preBtn.setVisible(true);
        preBtn.repaint();
        contentPane.add(preBtn);


        URL[] listTemp = {
                Jplayer.class.getResource("images/list_icon.png"),
                Jplayer.class.getResource("images/list-pressed.png"),
                Jplayer.class.getResource("images/list-pressed.png")
        };
        listBtn = new Btns(listTemp,"list");
        listBtn.setBorder(null);
        listBtn.setBounds(700, 350, 60, 60);
        listBtn.setVisible(true);
        listBtn.repaint();
        contentPane.add(listBtn);
//        Jplayer jp2other = this;
        listBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
//                String[] temp = {"E:/CloudMusic/1.mp3", "E:/CloudMusic/Diri - Diri Da Dance.mp3", "E:/CloudMusic/2.mp3", "four"};
//                new Livelist(temp, jp2other);
                livelist.setVisible(true);
//                System.out.println("asd");
            }
        });

    }

    public Livelist livelist;
    public Jplayer(){
        // default path
        path = "E:/CloudMusic/1.mp3";
        livelist = new Livelist(this);
        jStatus = new JStatus();


        JP_View_init();



        contentPane.repaint();




//        SwingUtilities.updateComponentTreeUI(this);

        this.setVisible(true);
    }

//    public boolean playNew;

    public void thePlay(){
        JFXPanel fxPanel;
        fxPanel = new JFXPanel();

        jStatus.isPlay = !jStatus.isPlay;
        if (jStatus.isPlay){
            if (jStatus.nowPlay == null){
//                path = "file:/E:/CloudMusic/1.mp3";
                String filePath = "file:/"+path;
                File Song = new File(path);

                URI uri = Song.toURI();
                String thePath = uri.toASCIIString();
//                URI uri = new URI(filePath);
//                URL url = uri.toURL();

                media = new Media(thePath);
                mediaPlayer = new MediaPlayer(media);

                contentPane.flashImage(path, title);
//                id3v2Tag.getArtist

            }
            mediaPlayer.play();
        } else {
            mediaPlayer.pause();
            jStatus.nowPlay = mediaPlayer;
        }


        title.repaint();
        theHead.repaint();
//        theHead.updateUI();
        contentPane.repaint();


        System.out.println("before");





        mediaPlayer.setOnPlaying(new Runnable() {
            public void run() {
//                System.out.println("~~");
//                mediaPlayer.play();

                Duration t;

                while (mediaPlayer.getCurrentTime().toSeconds() < media.getDuration().toSeconds()) {
                    // ===================================
//                    Play_Slider.setValue((int) mediaPlayer.getCurrentTime().toSeconds());
                    // ===================================
                    t = mediaPlayer.getTotalDuration().subtract(mediaPlayer.getCurrentTime());

//                    test.getText();
//                    test.setText("1");

                    System.out.println("12312323");

                    System.out.println(mediaPlayer.getCurrentTime().toSeconds()+"   " + t.toString());
//                    System.out.println();
//                    Time_Left.setText(
//                            Integer.toString((int) t.toMinutes()) + ":" + Integer.toString((int) t.toSeconds() % 60));
//                    Current_duration.setText(Integer.toString((int) mediaPlayer.getCurrentTime().toMinutes()) + ":"
//                            + Integer.toString((int) mediaPlayer.getCurrentTime().toSeconds() % 60));
                    // ===================================
                    try {
                        Thread.sleep(300); // 1000 milliseconds is one second.
                    } catch (InterruptedException ex) {
                        System.out.println(ex);
                    }

                }
            }
        });

        this.repaint();
    }


}
