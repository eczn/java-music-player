import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;

/**
 * Created by eczn on 2016/12/4.
 */
// 重写的标题栏
public class Livehead extends JPanel {
    // 基本参数
    private Jplayer jp;
    private boolean isDragged = false;
    private Point tmp;
    private Point loc;
    private Livehead father = this;
    public JLabel title;

    // 测试用
    public Livehead(){
        super();
    }

    // 主构造器
    public Livehead(Jplayer JPlayer){
        super();
        jp = JPlayer;
        // 标题栏拖动
        drag_init();

        // 最小化 和 关闭按钮的初始化
        header_btns_init();

        // 设置初始文字
        title = new JLabel("welcome, Click the list and choose music");

        // 颜色
        title.setForeground(Color.WHITE);

        // 上下左右居中
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);

        // 字体设置 微软雅黑
        title.setFont(new Font("Microsoft Yahei", Font.BOLD , 22));
        title.setBounds(0, 0, 855, 60);

        // 可见：√
        title.setVisible(true);
        title.repaint();
        this.add(title);
    }

    // 重写的标题栏是不可以拖动的 所以必须亲自写
    private void drag_init(){
        this.addMouseListener(new MouseAdapter() {
            // 释放
            public void mouseReleased(MouseEvent e) {
                isDragged = false;
                jp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            // 按下
            public void mousePressed(MouseEvent e) {
                tmp = new Point(e.getX(), e.getY());
                isDragged = true;
                jp.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            // 拖动
            public void mouseDragged(MouseEvent e) {
                // isDragged是上面的 按下 和 释放 共同协作提供的参数
                // 意思是： 当按下鼠标并滑动的时候
                if(isDragged) {
                    // 获取坐标
                    loc = new Point(jp.getLocation().x + e.getX() - tmp.x,
                            jp.getLocation().y + e.getY() - tmp.y);

                    // jp是Frame的子类，设置loc即可设置其在电脑屏幕中的位置
                    jp.setLocation(loc);
                }
            }
        });
    }

    // 图标初始化
    private void header_btns_init(){
        URL[] closeIcons = {
                Jplayer.class.getResource("images/close_icon.png"),
                Jplayer.class.getResource("images/close_icon.png"),
                Jplayer.class.getResource("images/close-pressed.png")
        };

        HeaderBtns toClose = new HeaderBtns(closeIcons, "close");
        toClose.setBounds(795, 0, 60, 60);
        toClose.setBorder(null);
        toClose.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        URL[] smallIcons = {
                Jplayer.class.getResource("images/small_icon.png"),
                Jplayer.class.getResource("images/small_icon.png"),
                Jplayer.class.getResource("images/small-pressed.png")
        };

        HeaderBtns toSmall = new HeaderBtns(smallIcons, "small");
        toSmall.setBounds(735, 0, 60, 60);
        toSmall.setBorder(null);
        toSmall.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jp.setExtendedState(jp.ICONIFIED);
            }
        });


        this.add(toSmall);
        this.add(toClose);
        toSmall.repaint();
        toClose.repaint();
    }

    // 图标继承自Btns
    private class HeaderBtns extends Btns {
        public HeaderBtns(URL[] ImageSrc_output, String text){
            super(ImageSrc_output, text);
            repaint();
        }
    }
}
