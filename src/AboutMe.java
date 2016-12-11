/**
 * Created by eczn on 2016/12/4.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public class AboutMe extends JFrame {
    public AboutMe(Jplayer futher){
//        setVisible(true);
        int x = futher.getX();
        int y = futher.getY();
        setBounds(x+175,y+100,550,230);
        System.out.println("x: "+x);
        System.out.println("y: "+y);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("关于我...");
        // 窗口图标
        setIconImage(
                Toolkit.getDefaultToolkit().getImage(
                        Jplayer.class.getResource("images/origin-dev.png")
                )
        );


        JButton more = new JButton("JPlayer - website");
        more.setBounds(235, 105, 180, 30);
        more.setCursor(new Cursor(Cursor.HAND_CURSOR));
        more.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    URI uri = new URI("http://frandre.cc/JavaPlayer");
                    Desktop.getDesktop().browse(uri);
                } catch (Exception exce){
                    exce.printStackTrace();
                }
            }
        });
        add(more);

        setResizable(false);
        // 设置背景颜色
        setBackground(Color.WHITE);

        Avatar myAvatar = new Avatar();

        myAvatar.setBounds(0,0,550,230);

        System.out.println(getSize());
        this.add(myAvatar);

    }


    // this main is for testing

}

class Avatar extends Canvas {
    public Avatar(){
        super();
    }

    @Override
    public void paint(Graphics g){
        g.setColor(new Color(0,0,0));
        g.setFont(new Font("微软雅黑", 384, 16));

        g.drawString("Name:  赖博志",235,55);
        g.drawString("From:  CscwFE@GDUT",235,75);
        g.drawString("toDo:  FE, Node",235,95);
//        g.drawString("Page:  http://frandre.cc/", 235,115);

        Toolkit tool = this.getToolkit();
        Image image = tool.getImage(Jplayer.class.getResource("images/origin.png"));

        g.drawImage(image, 55, 35, 145, 100, new Color(215,215,215), this);

    }
}