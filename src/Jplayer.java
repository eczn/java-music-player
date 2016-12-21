import com.sun.awt.AWTUtilities;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.net.URI;
import java.io.*;
import java.net.*;

public class Jplayer extends JFrame {
    // 专辑封面
    private ImgPanel contentPane;

    // livelist 列表
    public Livelist livelist;

    // 文件拖动
    public Droparea droparea;

    // 顶部条
    public Livehead theHead;

    // 按钮
    private Btns playBtn;
    private Btns nextBtn;
    private Btns preBtn;
    private Btns listBtn;
    private Btns modeBtn;

    // 播放状态
    public JStatus jStatus;

    // 进度
    public LiveSlider ls;
    // 音量
    public Vol volBar;

    // 播放器
    public String path;
    public MediaPlayer mediaPlayer;
    private Media media;

    public Jplayer that = this;
    public Point loc = null;
    public Point tmp = null;
    public boolean isDragged = false;

    // program entry
    public static void main(String[] args){
        Jplayer myP =  new Jplayer();
    }
    // init
    private void JP_View_init(){
        setTitle("JPlayer");
        // 程序图标
        setIconImage(
                Toolkit.getDefaultToolkit().getImage(
                        Jplayer.class.getResource("images/origin-dev.png")
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
                new RoundRectangle2D.Double(0D, 0D, this.getWidth(),
                        this.getHeight(), 24.0D, 24.0D));

        contentPane = new ImgPanel();

        Container jp = getContentPane();
        jp.add(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(244, 244, 244));

        // 标题栏
        theHead = new Livehead(this);
        theHead.setBounds(0, 0, 855, 60);
        theHead.setBackground(new Color(0, 0, 0, 183));
        theHead.setLayout(null);
        theHead.setVisible(true);
        theHead.repaint();
        contentPane.add(theHead);

        // 播放/暂停按钮
        URL[] playIcons = {
            Jplayer.class.getResource("images/play_icon.png"),
            Jplayer.class.getResource("images/play-pressed.png"),
            Jplayer.class.getResource("images/play-pressed.png")
        };
        playBtn = new Btns(playIcons,"play");
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

        // 下一首
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
                if (jStatus.playmode != jStatus.RANDOM_LOOP){
                    livelist.toNext();
                } else {
                    livelist.musicEnd();
                }
            }
        });
        contentPane.add(nextBtn);

        // 上一首
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

        // 关于我
        Btns aboutBtn;
        URL[] aboutTemp = {
                Jplayer.class.getResource("images/about.png"),
                Jplayer.class.getResource("images/about.png"),
                Jplayer.class.getResource("images/about.png")
        };
        aboutBtn = new Btns(aboutTemp,"pre");
        aboutBtn.setBorder(null);
        aboutBtn.setBounds(495, 280, 60, 60);

        Jplayer that = this;
        aboutBtn.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                JFrame temp = new AboutMe(that);
                temp.setVisible(true);
            }
        });
        contentPane.add(aboutBtn);

        // 列表按钮
        URL[] listTemp = {
                Jplayer.class.getResource("images/list_icon.png"),
                Jplayer.class.getResource("images/list-pressed.png"),
                Jplayer.class.getResource("images/list-pressed.png")
        };
        listBtn = new Btns(listTemp,"list");
        listBtn.setBorder(null);
        listBtn.setBounds(745, 310, 60, 40);
        listBtn.setBackground(null);
        listBtn.setOpaque(false);

        listBtn.setVisible(true);
        listBtn.repaint();
        contentPane.add(listBtn);
        listBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                livelist.setVisible(true);
            }
        });

        // 模式选择按钮
        modeBtn = new Btns(jStatus.getResources(jStatus.SINGLE_LOOP), "playmode");
        modeBtn.setBorder(null);
        modeBtn.setBounds(685, 280, 60, 60);
        modeBtn.setBackground(null);
        modeBtn.setOpaque(false);

        modeBtn.setVisible(true);
        modeBtn.repaint();
        contentPane.add(modeBtn);
        modeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                URL[] res;
                res = jStatus.modeToggle();
                System.out.println("  playmode: "+jStatus.playmode);
                modeBtn.imgSrc = res;
                modeBtn.repaint();
            }
        });

        // 音量条
        volBar = new Vol(this);
        volBar.setBounds(435, 355, 420, 160);
        contentPane.add(volBar);

        // droparea 是可拖放区域，占据整个程序窗口 （透明）
        droparea = new Droparea(this);
        droparea.setBounds(0,0,855,435);
        contentPane.add(droparea);

        // 进度条
        ls = new LiveSlider(300, 60, true, this);
        ls.setBorder(null);
        ls.setBounds(495, 200, 300, 60);

        contentPane.repaint();
        contentPane.add(ls);

        setVisible(true);
    }

    // 程序入口
    public Jplayer(){
        // 值的初始化
        path = null;
        livelist = new Livelist(this);
        jStatus = new JStatus();

        // 图形初始化
        JP_View_init();

        // 第一次使用本程序
        if (livelist.firstLaunch){
            JOptionPane.showMessageDialog(this,"欢迎来到JPlayer，欢迎您第一次使用本程序！");
            JOptionPane.showMessageDialog(this,"第一次使用本程序会在 用户目录下创建 FutureSoft 文件夹用于保存数据");
            JOptionPane.showMessageDialog(this,"请点击右下角的 \"菜单按钮\" 开始你的JPlayer之旅，或者把MP3文件直接拖进来也行 [-]..[-]");
            JOptionPane.showMessageDialog(this,"按add添加音乐， 添加后别忘了按save保存列表，如果你需要可以点URL使用下载功能");
        }
    }

    public void thePlay(int status){
        // Toolkit 初始化
        JFXPanel fxPanel;
        fxPanel = new JFXPanel();

        // 把图标图片换为暂停
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
                    contentPane.flashImage(path, theHead.title);

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
                System.out.println("thePlay(): pause");
                // 把图标图片换为播放
                playBtn.imgSrc[0] = Jplayer.class.getResource("images/play_icon.png");
                playBtn.imgSrc[1] = Jplayer.class.getResource("images/play-pressed.png");
                playBtn.imgSrc[2] = Jplayer.class.getResource("images/play-pressed.png");

                jStatus.isPlay = false;
            } else if (status == 10) {
                if (jStatus.nowPlay != null) {
                    System.out.println("thePlay(): 10, nowPlay != null");
                    mediaPlayer.stop();
                }

                System.out.println("thePlay(): 10, nowPlay == null");
                File Song = new File(path);
                URI uri = Song.toURI();
                String thePath = uri.toASCIIString();

                media = new Media(thePath);
                mediaPlayer = new MediaPlayer(media);
                contentPane.flashImage(path, theHead.title);

                jStatus.isPlay = true;
                jStatus.nowPlay = mediaPlayer;
                mediaPlayer.pause();
                mediaPlayer.play();
            }

        } catch (Exception e){
            e.printStackTrace();
            // 把图标图片换为播放
            playBtn.imgSrc[0] = Jplayer.class.getResource("images/play_icon.png");
            playBtn.imgSrc[1] = Jplayer.class.getResource("images/play-pressed.png");
            playBtn.imgSrc[2] = Jplayer.class.getResource("images/play-pressed.png");
            this.repaint();
            // 出错结束 thePlay()
            return;
        }

        // 当执行 .play的时候
        mediaPlayer.setOnPlaying(new Runnable(){
            @Override
            public void run() {
                // 进度条位置清空
                ls.setCurrent(0.0);
                // 设置总长度
                ls.setTotal(media.getDuration().toSeconds());
                // 设置ls需要的JStatus
                ls.now_status = jStatus;
                // 音量初始化
                mediaPlayer.setVolume(volBar.now_vol);
            }
        });
        // 错误的时候
        mediaPlayer.setOnError(new Runnable() {
            @Override
            public void run() {
                System.out.println("media error");
            }
        });
        // 到达音频结尾的时候
        mediaPlayer.setOnEndOfMedia(new Runnable(){
            public void run(){
                System.out.println("END!");
                // 根据模式来选择下一首应该播什么
                livelist.musicEnd();
            }
        });
        this.repaint();
    }

    // 设置音量
    public void setMediaPlayerVol(Double vol){
        mediaPlayer.setVolume(vol);
    }
}
