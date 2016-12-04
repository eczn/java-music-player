/**
 * Created by eczn on 2016/12/4.
 */
import javax.swing.*;
import java.awt.*;

public class AboutMe extends JFrame {
    public AboutMe(){
        setVisible(true);
        setBounds(0,0,550,230);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Avatar myAvatar = new Avatar();
        myAvatar.setBounds(0,0,550,230);

        System.out.println(getSize());
        this.add(myAvatar);
    }


    // this main is for testing
    public static void main(String[] args){
        new AboutMe();
    }

}

class Avatar extends Canvas {
    public Avatar(){

    }

    @Override
    public void paint(Graphics g){
        g.setColor(new Color(0,0,0));
        g.setFont(new Font("微软雅黑", 384, 16));

        g.drawString("Name:  赖博志",235,55);
        g.drawString("From:  CscwFE@GDUT",235,75);
        g.drawString("toDo:  FE, Node",235,95);
        g.drawString("Page:  http://frandre.cc/", 235,115);

        Toolkit tool = this.getToolkit();
        Image image = tool.getImage(Jplayer.class.getResource("images/origin.png"));

        g.drawImage(image, 55, 35, 145, 100, new Color(215,215,215), this);

    }
}