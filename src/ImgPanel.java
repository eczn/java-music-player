import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ImgPanel extends JPanel {
    private static BufferedImage image;
    private static BufferedImage default_image;

    public ImgPanel() {
        try {
            URL url = ImgPanel.class.getResource("images/main-theme.png");
            default_image = ImageIO.read(url);
            image=default_image;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Image tmp = image.getScaledInstance(435, 435, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(435, 435, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, new Color(255,255,255),null);
        g2d.dispose();
        g.drawImage(dimg, 0, 0, null);
    }

    // paint img by Graphics
    public void setImage(BufferedImage Image) {
        if (Image != null){
            image = Image;
        } else {
            image = default_image;
        }
        this.paintComponent(getGraphics());
        this.paintAll(getGraphics());

    }

    // paint through by id3
    // lib mp3agic needed
    // path:  music paht like: "E:/CloudMusic/1.mp3"
    public void flashImage(String path, JLabel title){
        try {
            Mp3File mp3file = new Mp3File(path);
            if (mp3file.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3file.getId3v2Tag();

                File temp = new File(path);
                String MP3FileName = temp.getName();

                title.setText(id3v2Tag.getArtist() + " - " + MP3FileName);

                byte[] imageData = id3v2Tag.getAlbumImage();
                if (imageData != null) {
                    String mimeType = id3v2Tag.getAlbumImageMimeType();
                    ByteArrayInputStream in = new ByteArrayInputStream(imageData);
                    //将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
                    BufferedImage target_image = ImageIO.read(in);
                    this.setImage(target_image);
                }
            }
        } catch (UnsupportedTagException | InvalidDataException | IOException e1) {
            e1.printStackTrace();
        }
    }
}
