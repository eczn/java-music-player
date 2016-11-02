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


    private ImgPanel contentPane;
//    private Btns listAdd;
//    private Btns listSave;
    private JButton listAdd;
    private JButton listSave;
    private JButton listURL;
    private Livedown liveDown;
    private boolean isOdd;

    public void listLoader(){
        vec = new Vector();
        model = new DefaultListModel<>();
        String fileName;

//        model.addElement("Hello");
        String usrHome = System.getProperty("user.home");
        fileName = usrHome+"/FutureSoft/JP/LiveList.data";

        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
//                vec.add(tempString);
                model.addElement(tempString);
                line++;
            }

            reader.close();
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

        if (file.exists()){

        }
        try {
            PrintWriter output = new PrintWriter(file);
//                    output.println("a");
            int i = 0;
            for (i=0;i<model.getSize();i++){
                output.println(model.getElementAt(i));
            }

            output.close();
        } catch ( FileNotFoundException fnfe){
            System.out.print(fnfe);
        }

    }

    public Livelist(){
        vec = new Vector();
        liveDown = new Livedown();
        listLoader();

//        System.out.println(vec.get(1));
    }

    DefaultListModel<String> model;
    public Livelist(Jplayer jP){
        liveDown = new Livedown();
        listLoader();

        list = new JList<>(model);
        isOdd = true;

//        list = new JList(vec);

        setTitle("Live-List");
        setVisible(true);

        setResizable(false);

        setBounds(300, 200, 400, 450);

        setContentPane(list);

        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                isOdd = !isOdd;
                if (isOdd){
//                    System.out.println( vec.get(list.getSelectedIndex()) );
//                    URL url = new URL(vec.get(list.getSelectedIndex()))
//                    String targetFile = vec.get(list.getSelectedIndex());

//                    jP.path = list.get(list.getSelectedIndex());

                    jP.path = model.getElementAt(list.getSelectedIndex());


                    jP.jStatus.isPlay = false;
                    if (jP.jStatus.isPlay){
                        jP.mediaPlayer.stop();
                    }
                    jP.thePlay();
                }
            }
        });
        setBackground(Color.WHITE);


        listAdd = new JButton("+ add");
//        listAdd = new JButton("+ add");
        listAdd.setBounds(0, 390, 80, 30);
        listAdd.setHorizontalAlignment(SwingConstants.CENTER);
        listAdd.setVerticalAlignment(SwingConstants.CENTER);
        listAdd.addMouseListener(new MouseAdapter() {
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

        this.setVisible(true);


        Livelist Livelist_temp = this;


        listURL = new JButton("- URL");
        listURL.setBounds(160, 390, 80, 30);
        listURL.setHorizontalAlignment(SwingConstants.CENTER);
        listURL.setVerticalAlignment(SwingConstants.CENTER);
        listURL.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                System.out.println("btn_playBtn click!");
                String URLInput = JOptionPane.showInputDialog("输入歌曲URL");



                liveDown.liveAdown(URLInput, Livelist_temp);

                System.out.println("@@@@ From Graph @@@@ " + URLInput);
//                JFileChooser myFC = new JFileChooser();
//                new FileChooser(listAdd);

            }
        });
        listURL.setVisible(true);
        listURL.repaint();
        list.add(listURL);
        list.repaint();





//
//        String ddd = JOptionPane.showInputDialog("输入歌曲URL");
//        liveDown.liveAdown(ddd, this);

    }
    JButton open;

    public void addElem(String target){
        model.addElement(target);

    }

    public static void main(String[] args){
//        listLoader();
//        new Livelist();
    }

}
