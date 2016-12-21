/**
 * Created by eczn on 2016/12/4.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

// 关于我
public class AboutMe extends JFrame {
    // 构造器
    public AboutMe(Jplayer futher){
        // 居中在Jplayer这个Frame的中央
        int x = futher.getX();
        int y = futher.getY();
        setBounds(x+175,y+100,550,230);
        System.out.println("x: "+x);
        System.out.println("y: "+y);

        // 窗口图标 标题 右上角叉叉默认操作
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("关于我...");
        setIconImage( // 利用toolkit绘图
                Toolkit.getDefaultToolkit().getImage(
                        Jplayer.class.getResource("images/origin-dev.png")
                )
        );

        // 网址
        JButton more = new JButton("JPlayer - website");
        more.setBounds(235, 105, 180, 30);
        more.setCursor(new Cursor(Cursor.HAND_CURSOR));
        more.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    // 点开进入 介绍页
                    URI uri = new URI("http://cscw.frandre.cc/JavaPlayer");
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
}

// 绘制头像
class Avatar extends Canvas {
    public Avatar(){
        super();
    }

    @Override
    public void paint(Graphics g){
        g.setColor(new Color(0,0,0));
        g.setFont(new Font("Microsoft Yahei", 384, 16));

        g.drawString("Name:  赖博志",235,55);
        g.drawString("From:  CscwFE@GDUT",235,75);
        g.drawString("toDo:  FE, Program, ACG",235,95);
//        g.drawString("Page:  http://frandre.cc/", 235,115);

        // 利用toolkit绘图
        Toolkit tool = this.getToolkit();
        Image image = tool.getImage(Jplayer.class.getResource("images/origin.png")); // 图片 origin
        g.drawImage(image, 55, 35, 145, 100, new Color(215,215,215), this);
    }
}
