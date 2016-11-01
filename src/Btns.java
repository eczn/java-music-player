import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Btns extends JButton {
    // imgSrc:
    // [0]: normal Status Icon src
    // [1]: onClicked Status Icon src
    // [2]: Hover Status Icon src
    protected String[] imgSrc;

    public Btns() { // construtor

    }

    public Btns(ImageIcon ii){
        super(ii);
    }
    public Btns(String test){
        super(test);

    }
    public Btns(String[] ImageSrc_output, String text){
        super(text);
        imgSrc = ImageSrc_output;
    }


//    private Color startColor = new Color(24,116,205);
//    private Color endColor = new Color(82, 82, 82);
//    private Color pressedColor = new Color(204, 67, 0);
//    private GradientPaint GP;

    protected void paintComponent(Graphics g) {
        ButtonModel model = getModel();

//        Color c = g.getColor();


        if (model.isPressed()) {
            System.out.print("!!");
            Color c = new Color(0,0,0);
            g.setColor(c);
//            g.fillOval(100, 100, 50, 50);

//            g.fillArc(0,0,60,60, 0, 360);
//            g.fillArc(0,0,60,60, 0, 360);
//            g.fillArc(0,0,60,60, 0, 360);
//            g.fillArc(0,0,60,60, 0, 360);
            g.clearRect(0,0,60,60);



//            ImageObserver
//            g.drawImag


        } else {
            System.out.print("no");
            g.clearRect(0,0,60,60);
            Color c = new Color(120,120,120);
            g.setColor(c);
//            g.fillOval(100, 100, 50, 50);
//            g.fillRect(0,0,60,60);
//            g.fillArc(0,0,60,60, 0, 360);
//            g.fillArc(0,0,60,60, 0, 360);
//            g.fillArc(0,0,60,60, 0, 360);
//            g.fillArc(0,0,60,60, 0, 360);
            g.clearRect(0,0,60,60);
            Toolkit tool = this.getToolkit();
            Image image = tool.getImage(Jplayer.class.getResource("images/play_icon.png"));
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
            g.fillArc(0,0,60,60, 0, 360);

            g.drawArc(0,0,60,60,0,360);
//            return;
        }
    }
}
