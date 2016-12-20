import javafx.embed.swing.JFXPanel;
import javafx.util.Duration;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.*;
import java.util.Vector;

public class Livelist extends JFrame {
    public JList list;
    public Vector<String> vec;
    DefaultListModel<String> model;

    private ImgPanel contentPane;
    private JButton listAdd;
    private JButton listSave;
    private JButton listURL;
    private JButton listDel;
    private Livedown liveDown;
    private boolean isOdd;
    private int now_play_in;
    public boolean firstLaunch = false;

    public void listLoader(){
        vec = new Vector();
        model = new DefaultListModel<>();
        String fileName;

        String usrHome = System.getProperty("user.home");
        fileName = usrHome+"/FutureSoft/JP/LiveList.data";

        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            if (file.exists()){
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
            } else {
                System.out.println("文件不存在，故自动创建一个");
                listSave();
//                FileNotFoundException temp = new FileNotFoundException();
//                throw temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {

                }
            }
        }
    }

    public void listSave(){
        String usrHome = System.getProperty("user.home");
        String fileName;
        fileName = usrHome+"/FutureSoft/JP/LiveList.data";

        File file = new File(fileName);

//        if (file.exists()){
//
//        }
        try {
            PrintWriter output = new PrintWriter(file);
            int i = 0;
            for (i=0;i<model.getSize();i++) {
                output.println(model.getElementAt(i));
            }

            output.close();
        } catch ( FileNotFoundException fnfe){
            System.out.print(fnfe);
            File whereJP = new File(usrHome+"/FutureSoft/JP/");
            File whereLivedown = new File(usrHome+"/FutureSoft/JP/Livedown");

            firstLaunch = true;
            whereJP.mkdirs();
            whereLivedown.mkdirs();
            return;
        }

    }

    public Livelist(){
        vec = new Vector();
        liveDown = new Livedown();
        listLoader();
        now_play_in = 0;
    }

    public Jplayer jP;
    public int select_on;
    public Livelist(Jplayer jP_input){
        jP = jP_input;
        liveDown = new Livedown();
        select_on = -1;
        listLoader();

        list = new JList<>(model);
        isOdd = true;

        JScrollPane jsp = new JScrollPane(list);

        setTitle("Live-List");

        setVisible(false);

        setResizable(false);

        setBounds(300, 200, 400, 450);

        // !!
        setContentPane(jsp);

        list.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent mouseEvent) {
                JList theList = (JList) mouseEvent.getSource();
//                System.out.println("### "+ mouseEvent.get);
                int index = theList.locationToIndex(mouseEvent.getPoint());
                if (mouseEvent.getClickCount() == 2) {
//                    System.out.println("### index: "+ index);
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

//        listSave = new Btns("| save");
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

        listDel = new JButton("- del");
        listDel.setBounds(240, 390, 80, 30);
        listDel.setHorizontalAlignment(SwingConstants.CENTER);
        listDel.setVerticalAlignment(SwingConstants.CENTER);
        listDel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (select_on >= 0){
//                    System.out.println(select_on);
                    model.removeElementAt(select_on);
                }

                listSave();
            }
        });
        listDel.setVisible(true);
        listDel.repaint();
        list.add(listDel);

    }

    public void addElem(String target){
        model.addElement(target);
    }

    public void toNext(){
        now_play_in++;
        if (now_play_in > model.getSize()-1){
            now_play_in = 0;
//            return;
        }
        jP.path = model.getElementAt(now_play_in);
        jP.thePlay(10);

    }
    public void toPre(){
        now_play_in--;
        if (now_play_in < 0){
            now_play_in = model.getSize()-1;
//            return;
        }
        jP.path = model.getElementAt(now_play_in);
        jP.thePlay(10);

    }
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

    public void musicEnd(){
        if (jP.jStatus.playmode == jP.jStatus.SINGLE_LOOP){
            jP.mediaPlayer.seek(new Duration(0.0));
//            jP.mediaPlayer.stop();
        } else if (jP.jStatus.playmode == jP.jStatus.LIST_LOOP){
            toNext();
        } else if (jP.jStatus.playmode == jP.jStatus.RANDOM_LOOP){
            toRandom();
        }
    }

}
