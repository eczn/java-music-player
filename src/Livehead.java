import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;

/**
 * Created by eczn on 2016/12/4.
 */
public class Livehead extends JPanel {
    private Jplayer jp;
    private boolean isDragged = false;
    private Point tmp;
    private Point loc;
    private Livehead father = this;
    public JLabel title;

    public Livehead(){
        super();
    }
    public Livehead(Jplayer JPlayer){
        super();
        jp = JPlayer;
        drag_init();
        header_btns_init();

        title = new JLabel("welcome, Click the list and choose music");
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Microsoft Yahei", Font.BOLD , 22));
        title.setBounds(0, 0, 855, 60);
        title.setVisible(true);
        title.repaint();
        this.add(title);

    }

    private void drag_init(){
        this.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                isDragged = false;
                jp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            public void mousePressed(MouseEvent e) {
                tmp = new Point(e.getX(), e.getY());
                isDragged = true;
                jp.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if(isDragged) {
                    loc = new Point(jp.getLocation().x + e.getX() - tmp.x,
                            jp.getLocation().y + e.getY() - tmp.y);

                    jp.setLocation(loc);
                }
            }
        });
    }

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

    private class HeaderBtns extends Btns {
        public HeaderBtns(URL[] ImageSrc_output, String text){
            super(ImageSrc_output, text);
//            this.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    jp.setExtendedState(jp.ICONIFIED);
//                }
//
//                @Override
//                public void mouseEntered(MouseEvent e) {
//                    father.setCursor(new Cursor(Cursor.HAND_CURSOR));
//                }
//                @Override
//                public void mouseExited(MouseEvent e) {
//                    father.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//                }
//            });
            repaint();
        }
    }
}


