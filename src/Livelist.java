import javafx.util.Duration;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Vector;

public class Livelist extends JFrame {
    // 基本量
    public JList list;
    public Vector<String> vec;
    public Jplayer jP;
    public int select_on;
    // 链表
    DefaultListModel<String> model;
    // 私有UI组件
    private ImgPanel contentPane;
    private JButton listAdd;
    private JButton listSave;
    private JButton listURL;
    private JButton listDel;
    private Livedown liveDown;
    private boolean isOdd;
    private int now_play_in;
    public boolean firstLaunch = false;

    // 读文件，文件不存在则创建并通知JPlayer 这是第一次启动JPlayer
    public void listLoader(){
        vec = new Vector();
        model = new DefaultListModel<>();
        String fileName;

        // 用户目录
        String usrHome = System.getProperty("user.home");
        fileName = usrHome+"/FutureSoft/JP/LiveList.data";

        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            if (file.exists()){ // 文件存在
                System.out.println("以行为单位读取文件内容，一次读一整行：");
                // 用file构造FileReader 然后再构造为 带缓存的 BufferedReader
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                int line = 1;
                // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    // 显示行号
                    System.out.println("line " + line + ": " + tempString);
                    model.addElement(tempString);
                    line++;
                }
                reader.close();
            } else { // 文件不存在
                System.out.println("文件不存在，故自动创建一个");
                listSave();
            }
        } catch (IOException e) { // 异常
            e.printStackTrace();
        } finally { // 在try之后 解除文件占用
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {

                }
            }
        }
    }

    // 保存列表文件 路径不存在会自动创建
    public void listSave(){
        String usrHome = System.getProperty("user.home");
        String fileName;
        fileName = usrHome+"/FutureSoft/JP/LiveList.data";

        File file = new File(fileName);

        try {
            PrintWriter output = new PrintWriter(file);
            int i = 0;
            for (i=0;i<model.getSize();i++) {
                output.println(model.getElementAt(i));
            }
            output.close();
        } catch ( FileNotFoundException fnfe){
            System.out.print(fnfe);
            // JP所在
            File whereJP = new File(usrHome+"/FutureSoft/JP/");
            // Livedown所在
            File whereLivedown = new File(usrHome+"/FutureSoft/JP/Livedown");
            whereJP.mkdirs();
            whereLivedown.mkdirs();
            // 通知JPlayer提示用户“这是第一次使用JPlayer”
            firstLaunch = true;
            return;
        }

    }

    // 测试用
    public Livelist(){
        vec = new Vector();
        liveDown = new Livedown();
        listLoader();
        now_play_in = 0;
    }

    // 构造器 用Jplayer类构造
    public Livelist(Jplayer jP_input){
        jP = jP_input;
        liveDown = new Livedown();
        select_on = -1;
        listLoader();

        // 利用model构造JList
        list = new JList<>(model);
        isOdd = true;

        JScrollPane jsp = new JScrollPane(list);
        // 标题
        setTitle("Live-List");
        setVisible(false);
        setResizable(false);
        setBounds(300, 200, 400, 450);
        setContentPane(jsp);

        // 鼠标监听
        list.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent mouseEvent) {
                JList theList = (JList) mouseEvent.getSource();
                int index = theList.locationToIndex(mouseEvent.getPoint());
                if (mouseEvent.getClickCount() == 2) {
                    if (index>=0){
                        jP.path = model.getElementAt(index);
                        jP.thePlay(10);
                        now_play_in = index;
                    }
                } else if (mouseEvent.getClickCount() == 1){
                    select_on = index;
                }
            }
        });
        setBackground(Color.WHITE);

        // 按钮 添加
        listAdd = new JButton("+ add");
        listAdd.setBounds(0, 390, 80, 30);
        listAdd.setHorizontalAlignment(SwingConstants.CENTER);
        listAdd.setVerticalAlignment(SwingConstants.CENTER);
        listAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser jfc=new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
                jfc.showDialog(new JLabel(), "选择");
                File file=jfc.getSelectedFile();

                if (file.isDirectory()){
                    System.out.println("文件夹:"+file.getAbsolutePath());
                    return;
                } else if (file.isFile()){
                    System.out.println("文件:"+file.getAbsolutePath());
                    String willBePath = file.getAbsolutePath().replace("\\", "/");
                    model.addElement(willBePath);
                }
                listSave();
                list.repaint();

            }
        });
        listAdd.setVisible(true);
        listAdd.repaint();
        list.add(listAdd);
        list.setVisible(true);
        list.repaint();

        // 按钮保存
        listSave = new JButton("↓ save");
        listSave.setBounds(80, 390, 80, 30);
        listSave.setHorizontalAlignment(SwingConstants.CENTER);
        listSave.setVerticalAlignment(SwingConstants.CENTER);
        listSave.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                listSave();
            }
        });
        listSave.setVisible(true);
        listSave.repaint();
        list.add(listSave);
        list.setVisible(true);
        list.repaint();

        Livelist Livelist_temp = this;

        // 按钮 URL下载
        listURL = new JButton("+ URL");
        listURL.setBounds(160, 390, 80, 30);
        listURL.setHorizontalAlignment(SwingConstants.CENTER);
        listURL.setVerticalAlignment(SwingConstants.CENTER);
        listURL.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                System.out.println("btn_playBtn click!");
                String URLInput = JOptionPane.showInputDialog("输入歌曲URL");
                if (URLInput == null){
                    return;
                } else {
                    liveDown.callHimToDownloadIt(URLInput, Livelist_temp);
                }
                System.out.println("@@@@ From Graph @@@@ " + URLInput);
            }
        });
        listURL.setVisible(true);
        listURL.repaint();
        list.add(listURL);
        list.repaint();

        // 按钮 删除
        listDel = new JButton("- del");
        listDel.setBounds(240, 390, 80, 30);
        listDel.setHorizontalAlignment(SwingConstants.CENTER);
        listDel.setVerticalAlignment(SwingConstants.CENTER);
        listDel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (select_on >= 0){
                    model.removeElementAt(select_on);
                }

                listSave();
            }
        });
        listDel.setVisible(true);
        listDel.repaint();
        list.add(listDel);

    }

    // 向列表添加元素到底部
    public void addElem(String target){
        model.addElement(target);
    }

    // 循环下一首
    public void toNext(){
        now_play_in++;
        if (now_play_in > model.getSize()-1){
            now_play_in = 0;
        }
        jP.path = model.getElementAt(now_play_in);
        jP.thePlay(10);

    }

    // 循环上一首
    public void toPre(){
        now_play_in--;
        if (now_play_in < 0){
            now_play_in = model.getSize()-1;
        }
        jP.path = model.getElementAt(now_play_in);
        jP.thePlay(10);
    }

    // 随机
    public void toRandom(){
        int temp = now_play_in;
        int rand = (int)(Math.random() * model.getSize());


        if ( temp == rand ){
            toRandom();
        } else {
            now_play_in = rand;
        }

        if (now_play_in < 0){
            now_play_in = model.getSize()-1;
            toRandom();
        } else if (now_play_in > model.getSize()-1){
            toRandom();
        }
        jP.path = model.getElementAt(now_play_in);
        jP.thePlay(10);

    }

    // Jplayer会调用这个 并用这个来判断播放模式并选择 是toNext 还是toPre 或者toRandom
    public void musicEnd(){
        if (jP.jStatus.playmode == jP.jStatus.SINGLE_LOOP){ // 单曲循环
            jP.mediaPlayer.seek(new Duration(0.0));
        } else if (jP.jStatus.playmode == jP.jStatus.LIST_LOOP){ // 列表循环
            toNext();
        } else if (jP.jStatus.playmode == jP.jStatus.RANDOM_LOOP){ // 随机播放
            toRandom();
        }
    }
}
