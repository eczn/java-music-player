import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.net.*;

public class ImgPanel extends JPanel {
    // 图片数据
    private static BufferedImage image;
    private static BufferedImage default_image;

    // 构造方法
    public ImgPanel() {
        try {
            URL url = ImgPanel.class.getResource("images/main-theme.png");
            default_image = ImageIO.read(url);
            image=default_image;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override // 重写了JPanel的绘制方法
    protected void paintComponent(Graphics g) {
        Image tmp = image.getScaledInstance(435, 435, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(435, 435, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, new Color(255,255,255),null);
        g2d.dispose();
        g.drawImage(dimg, 0, 0, null);
    }

    // 绘制图片
    public void setImage(BufferedImage Image) {
        if (Image != null){
            image = Image;
        } else {
            image = default_image;
        }
        this.paintComponent(getGraphics());
        this.paintAll(getGraphics());

    }

    // 通过mp3agic获取专辑封面并渲染专辑封面
    public void flashImage(String path, JLabel title){
        try {
            // 构造
            Mp3File mp3file = new Mp3File(path);

            // 如果该mp3文件有id3v2标签头
            if (mp3file.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3file.getId3v2Tag();

                File temp = new File(path);
                String MP3FileName = temp.getName();

                // getArtist 获取艺术家信息
                title.setText(id3v2Tag.getArtist() + " - " + MP3FileName);

                // 取得专辑封面的二进制数据
                byte[] imageData = id3v2Tag.getAlbumImage();
                if (imageData != null) {
                    String mimeType = id3v2Tag.getAlbumImageMimeType();
                    ByteArrayInputStream in = new ByteArrayInputStream(imageData);
                    // 将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
                    BufferedImage target_image = ImageIO.read(in);
                    this.setImage(target_image);
                } else { // 如果该文件没有专辑封面 则打印默认的图片
                    try {
                        URL url = ImgPanel.class.getResource("images/main-theme.png");
                        default_image = ImageIO.read(url);
                        image=default_image;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (UnsupportedTagException | InvalidDataException | IOException e1) {
            e1.printStackTrace();
        }
    }
}
