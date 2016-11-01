import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.net.URL;

public class Btns extends JButton {
    // imgSrc:
    // [0]: normal Status Icon src
    // [1]: onClicked Status Icon src
    // [2]: Hover Status Icon src
    protected URL[] imgSrc;
//    protected Image[] imgs;
    public String btnName;
    public boolean justText;
    public Btns() { // construtor

    }

//    public Btns(ImageIcon ii){
//        super(ii);
//    }
    public Btns(String text){
        super(text);
        btnName = text;
        justText = true;
    }

//    public Btns(String[] ImageSrc_output, String text){
//        super(text);
//        imgSrc = ImageSrc_output;
//    }
    public Btns(URL[] ImageSrc_output, String text){
        super(text);
        btnName = text;
        imgSrc = ImageSrc_output;
        justText = false;
    }

    protected void paintComponent(Graphics g) {
        if (justText){
            g.drawString(btnName, 0,0);
        } else {
            System.out.println(btnName);
            ButtonModel model = getModel();
            g.clearRect(0,0,60,60);
            if (model.isPressed()) {
                System.out.print("!!");
                Color c = new Color(0,0,0);
                g.setColor(c);

                Toolkit tool = this.getToolkit();
                Image image = tool.getImage(imgSrc[1]);
                g.drawImage(image, 0, 0, new Color(255,255,255), null);

            } else {
                System.out.print("no");

                Color c = new Color(120,120,120);
                g.setColor(c);

                g.clearRect(0,0,60,60);
                Toolkit tool = this.getToolkit();
                Image image = tool.getImage(imgSrc[0]);
                g.drawImage(image, 0, 0, new Color(255,255,255), null);
            }

            if (model.isRollover()){ // hover
                System.out.print("@@");
                Color c = new Color(0,0,0);
                g.setColor(c);
    //            g.fillOval(100, 100, 50, 50);

    //            g.fillArc(0,0,60,60, 0, 360);
    //            g.fillArc(0,0,60,60, 0, 360);
    //            g.fillArc(0,0,60,60, 0, 360);

                Toolkit tool = this.getToolkit();
                Image image = tool.getImage(imgSrc[2]);
                g.drawImage(image, 0, 0, new Color(255,255,255), null);
    //            return;
            }
        }
    }
}
