import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URL;

public class Btns extends JButton {
    // "imgSrc" have 3 image URLs
    // just like this:
    // imgSrc[0]: normal Status Icon src
    // imgSrc[1]: onClicked Status Icon src
    // imgSrc[2]: Hover Status Icon src

    protected URL[] imgSrc;
    public String btnName;
    public boolean justText;

    public Btns() { // construtor
        btnName = "btn";
        justText = false;
    }

    public Btns(String text){
        super(text);
        btnName = text;
        justText = true;
    }

    public Btns(URL[] ImageSrc_output, String text){
//        super(text);
        btnName = text;
        imgSrc = ImageSrc_output;
        justText = false;

        firInit = true;

        repaint();
    }
    public boolean firInit;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (justText){
            g.drawString(btnName, 0,0);
        } else {


            ButtonModel model = getModel();
//            g.clearRect(0,0,60,60);

            if (model.isPressed()) {
                Color c = new Color(0,0,0);
                g.setColor(c);

                Toolkit tool = this.getToolkit();
                Image image = tool.getImage(imgSrc[1]);

//                g.drawImage(image, 0, 0, new Color(255,255,255), null);
                g.drawImage(image, 0, 0, null);

            } else {
                Color c = new Color(120,120,120);
                g.setColor(c);

//                g.clearRect(0,0,60,60);
                Toolkit tool = this.getToolkit();
                Image image = tool.getImage(imgSrc[0]);
                g.drawImage(image, 0, 0, new Color(255,255,255), null);
//                g.drawImage(image, 0, 0, null);
            }

            if (model.isRollover()){ // hover
                Color c = new Color(0,0,0);
                g.setColor(c);
                Toolkit tool = this.getToolkit();
                Image image = tool.getImage(imgSrc[2]);
//                g.drawImage(image, 0, 0, new Color(255,255,255), null);
                g.drawImage(image, 0, 0, null);

            }
        }

        if (firInit){
            repaint();
            g.clearRect(0,0,60,60);
            firInit = false;
        }
    }
}
