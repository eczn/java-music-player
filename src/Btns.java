import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
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
        btnName = "btn";
        justText = false;
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

//        this.paintAll(getGraphics());


//        Color c = new Color(0,0,0);
//        g.setColor(c);
//
//        Toolkit tool = this.getToolkit();
//        Image image = tool.getImage(imgSrc[1]);
//        g.drawImage(image, 0, 0, new Color(255,255,255), null);
        firInit = true;
    }
    public boolean firInit;
    @Override
    protected void paintComponent(Graphics g) {
//        if (firInit){
//            try {
//                System.out.println("!");
//                BufferedImage image;
//                URL url = ImgPanel.class.getResource("images/origin-dev.png");
//                image = ImageIO.read(url);
//
//                Image tmp = image.getScaledInstance(60,60,Image.SCALE_SMOOTH);
//                BufferedImage dimg = new BufferedImage(380, 435, BufferedImage.TYPE_INT_ARGB);
//
//                Graphics2D g2d = dimg.createGraphics();
//                g2d.drawImage(tmp, 0, 0, null);
//                g2d.dispose();
//                g.drawImage(dimg, 0, 0, null);
//                firInit = false;
//            } catch (IOException e){
//                e.printStackTrace();
//            }
//        }


        if (justText){
            g.drawString(btnName, 0,0);
        } else {
//            System.out.println(btnName);
            ButtonModel model = getModel();
            g.clearRect(0,0,60,60);
            if (model.isPressed()) {
//                System.out.print("!!");
                Color c = new Color(0,0,0);
                g.setColor(c);

                Toolkit tool = this.getToolkit();
                Image image = tool.getImage(imgSrc[1]);
                g.drawImage(image, 0, 0, new Color(255,255,255), null);

            } else {
//                System.out.print("no");

                Color c = new Color(120,120,120);
                g.setColor(c);

                g.clearRect(0,0,60,60);
                Toolkit tool = this.getToolkit();
                Image image = tool.getImage(imgSrc[0]);
                g.drawImage(image, 0, 0, new Color(255,255,255), null);
            }

            if (model.isRollover()){ // hover
//                System.out.print("@@");
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

        repaint();
    }
}
