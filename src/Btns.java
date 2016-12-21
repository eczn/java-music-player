import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Btns extends JButton {
    // "imgSrc" have 3 image URLs
    // just like this:
    // imgSrc[0]: 图标的正常状态
    // imgSrc[1]: 图标被点击
    // imgSrc[2]: 鼠标悬停在图片上
    protected URL[] imgSrc;
    public String btnName;
    public boolean justText;
    public boolean firInit;

    // 测试用
    public Btns() {
        btnName = "btn";
        justText = false;
        this.setBorder(null);
    }

    // 测试用
    public Btns(String text){
        super(text);
        btnName = text;
        justText = true;
    }

    // 主构造器
    public Btns(URL[] ImageSrc_output, String text){
        btnName = text;
        imgSrc = ImageSrc_output;
        justText = false;
        this.setBorder(null);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        firInit = true;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 如果这个图标仅仅是文字图标 则直接绘制字符串
        if (justText){
            g.drawString(btnName, 0,0);
        } else { // 不然的话要渲染图标
            ButtonModel model = getModel();

            // 如果按钮这时候被点击
            if (model.isPressed()) {
                Color c = new Color(0,0,0);
                g.setColor(c);
                // 利用toolkit绘图
                Toolkit tool = this.getToolkit();
                Image image = tool.getImage(imgSrc[1]);

                g.drawImage(image, 0, 0, null);

            } else { // 没被点击
                Color c = new Color(120,120,120);
                g.setColor(c);
                // 利用toolkit绘图
                Toolkit tool = this.getToolkit();
                Image image = tool.getImage(imgSrc[0]);
                g.drawImage(image, 0, 0, new Color(255,255,255), null);
            }

            // 此外，如果你鼠标悬停
            if (model.isRollover()){ // hover
                Color c = new Color(0,0,0);
                g.setColor(c);
                // 利用toolkit绘图
                Toolkit tool = this.getToolkit();
                Image image = tool.getImage(imgSrc[2]);
                g.drawImage(image, 0, 0, null);
            }
        }

        // 第一次初始化的时候应该要重绘一次 以免JButton的背景覆盖住本Btns
        if (firInit){
            repaint();
            g.clearRect(0,0,60,60);
            repaint();
            firInit = false;
        }
    }
}
