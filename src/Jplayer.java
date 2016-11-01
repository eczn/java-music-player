import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import javafx.util.Duration;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map.Entry;
import java.util.Random;
import java.util.RandomAccess;
import java.util.Vector;

import java.awt.Font;

import java.awt.CardLayout;
import java.awt.Toolkit;


import java.util.*;
import java.io.*;
import java.net.*;

public class Jplayer extends JFrame {
    // main container
    private ImgPanel contentPane;

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
//        System.out.print("hello, java\n");
//        try {
//            Mp3File mp3file = new Mp3File("E:/CloudMusic/1.mp3");
//            System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
//            System.out.println("Bitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
//            System.out.println("Sample rate: " + mp3file.getSampleRate() + " Hz");
//            System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
//            System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
//            System.out.println("Has custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));
//        } catch (UnsupportedTagException | InvalidDataException | IOException e1) {
//            System.out.println(e1);
//        }

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
        // 点右上角按钮的时候的默认操作，这里设置为  EXIT_ON_CLOSE 也就是直接退出
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setVisible(true);

        contentPane = new ImgPanel();
        setBounds(200, 100, 800, 435);
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(244, 244, 244));

        theHead = new JPanel();
        theHead.setBounds(0, 0, 800, 50);
        theHead.setBackground(new Color(0, 0, 0, 183));
        theHead.setLayout(null);
        {
            title = new JLabel("jPlayer - FileName.mp3");

            title.setForeground(Color.WHITE);
            title.setHorizontalAlignment(SwingConstants.CENTER);
            title.setVerticalAlignment(SwingConstants.CENTER);
            title.setFont(new Font("Microsoft Yahei", Font.BOLD , 22));
//            panel_1.setFont(new Font("Consolas", Font.BOLD, 20));
            title.setBounds(0, 0, 800, 50);

            theHead.add(title);
        }

        contentPane.add(theHead);
        

//        j.setSize(300, 200);
//        j.setVisible(true);


//        playBtn = new JButton("play");

//        ImageIcon pre = new ImageIcon(
//                Jplayer.class.getResource("images/play_icon.png")
//        );
//        ImageIcon temp = new ImageIcon(
//                Jplayer.class.getResource("images/play-pressed.png")
//        );



        playBtn = new Btns("play");
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
        playBtn.repaint();
        contentPane.add(playBtn);


        nextBtn = new Btns("next");
        nextBtn.setBorder(null);
        nextBtn.setBounds(440, 130, 60, 60);
        contentPane.add(nextBtn);
        nextBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

            }
        });


        listBtn = new Btns("list");
        listBtn.setBorder(null);
        listBtn.setBounds(440, 240, 60, 60);
        contentPane.add(listBtn);

        Jplayer jp2other = this;
        listBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
//                String[] temp = {"E:/CloudMusic/1.mp3", "E:/CloudMusic/Diri - Diri Da Dance.mp3", "E:/CloudMusic/2.mp3", "four"};
//                new Livelist(temp, jp2other);
                new Livelist(jp2other);
                System.out.println("asd");
            }
        });

        preBtn = new Btns("pre");
        preBtn.setBorder(null);
        preBtn.setBounds(680, 130, 60, 60);
        contentPane.add(preBtn);
    }

    public Jplayer(){
        this.repaint();
        JP_View_init();
        this.repaint();
        jStatus = new JStatus();

        // default path
        path = "E:/CloudMusic/1.mp3";


        contentPane.repaint();
        this.repaint();
    }


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



        mediaPlayer.setOnReady(new Runnable() {
            public void run() {
                mediaPlayer.play();
                Duration t;
                while (mediaPlayer.getCurrentTime().toSeconds() < media.getDuration().toSeconds()) {
                    // ===================================
//                    Play_Slider.setValue((int) mediaPlayer.getCurrentTime().toSeconds());

                    // ===================================
                    t = mediaPlayer.getTotalDuration().subtract(mediaPlayer.getCurrentTime());

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
                        Thread.currentThread().interrupt();
                    }

                }
            }
        });

        this.repaint();
    }


}
