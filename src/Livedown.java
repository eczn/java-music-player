//import java.io.File;
//import com.sun.media.sound.ModelAbstractChannelMixer;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class Livedown extends JFrame {
    public JLabel process;
    public Download download;

    public Livedown() {
        setBounds(200, 150, 500, 150);
        process = new JLabel("下载进度");
        process.setBounds(0,0,300,150);
        process.setVerticalAlignment(SwingConstants.CENTER);
        process.setHorizontalAlignment(SwingConstants.CENTER);
        process.repaint();
        setContentPane(process);
    }

    public void callHimToDownloadIt(String urlStr, Livelist livelist){
        String usrHome = System.getProperty("user.home");

        String[] urlStrs = urlStr.split("/");

        String fileName = urlStrs[urlStrs.length-1];

        download = new Download(process, urlStr, usrHome+"/FutureSoft/JP/Livedown/"+fileName);
        download.whoTellMeToDownload = livelist;

        setVisible(true);
        download.start();

    }

}

// word process dis
class Download implements Runnable {
    private JLabel targetContainer;
    public int now_for;
    public int total;
    public String httpUrl;
    public String saveFile;
    Thread toDownload;
    ProcessBar myBar;
    public Livelist whoTellMeToDownload;

    public Download(JLabel out, String url, String toWhere){
        super();
        httpUrl = url;
        saveFile = toWhere;
//        Bounds: (0,0,300,150);
        myBar = new ProcessBar(500, 150, new Color(193,203,224), new Color(233,233,233));
        targetContainer = out;
        targetContainer.add(myBar);

        myBar.setBounds(0, 0, 500, 10);
        now_for = 0;
        total = getURLFileLength(httpUrl);
        targetContainer.setText("下载中");
        toDownload = new Thread(this);
    }

    public int getURLFileLength(String urlStr){
        int fileLength;

        try {
            URL theUrl = new URL(urlStr);
            HttpURLConnection urlcon = (HttpURLConnection)theUrl.openConnection();

            fileLength = urlcon.getContentLength();
            System.out.println("@@@@@@ fileLength!!: "+fileLength);

        } catch (MalformedURLException GetLengthE ){
            // url faild
            fileLength = -1;
            System.out.println("GLE!");
            System.out.println(GetLengthE);

        } catch (IOException IOE){
            fileLength = -2;
            System.out.println("IOE!");
            System.out.println(IOE);
        }

        return fileLength;
    }

    public void start(){
        try {
            toDownload.start();
        } catch (Exception exe){
            exe.printStackTrace();
        }
    }

    public void run(){
        String temp = targetContainer.getText();
        targetContainer.setText("准备下载... ");
        int bytesum = 0;
        int byteread = 0;
        URL url = null;
        String msg = "msg";

        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e1) {
            targetContainer.setText("URL地址错误！请重新输入");
            return;
        }

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(saveFile);

            // buf 2KB
            byte[] buffer = new byte[4096];

            Thread.sleep(2000);
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;

                System.out.println(bytesum);
                targetContainer.setText("下载进度："+bytesum+"  /  "+total);

                Double rate = Double.parseDouble(Integer.toString(bytesum)) / total;
                myBar.small_lenth = (int)(rate * myBar.total);
                myBar.repaint();
                fs.write(buffer, 0, byteread);
                Thread.sleep(8);
            }

            fs.close();
            msg = "完成 已保存在用户目录下的 FutureSoft/JP/Livedown 里";
        } catch (FileNotFoundException e) {
            msg = "文件不存在 （404）， 请检查URL输入";
            e.printStackTrace();
        } catch (InterruptedException e){
            msg = "下载线程意外中止， 请重试";
            e.printStackTrace();
        } catch (UnknownHostException e){
            msg = "域名不存在（UnknownHost），请检查输入或者网络配置";
            e.printStackTrace();
        } catch (IOException e) {
            msg = "IO异常， 请检查磁盘是否可用（是否有足够空间存储音频或者有足够权限访问）";
            e.printStackTrace();
        } finally {
            whoTellMeToDownload.addElem(saveFile);
            myBar.small_lenth = 0;
            // 将msg写入targetContainer
            targetContainer.setText(msg);
        }
    }
}

class ProcessBar extends Canvas {
    public int small_lenth;
    public int total;
    public int height;
//    public double percentage;
    Color top_color;
    Color bottom_color;

    public ProcessBar(int t, int h, Color top, Color bottom){
        top_color = top;
        total = t;
        height = h;
        bottom_color = bottom;
    }

    public void setColor(Color top, Color bottom){
        if (top != null){
            top_color = top;
        }
        if (bottom != null){
            bottom_color = bottom;
        }
    }

    public void setHeight(int h){
        height = h;
    }

    public void setSmall_lenth(int len){
        small_lenth = len;
    }

    // 绘图
    public void paint(Graphics g){
        System.out.println("small_lenth: "+small_lenth);
        g.clearRect(0, 0, total, height);
        if (bottom_color != null){
            g.setColor(bottom_color);
            g.fillRect(0, 0, total, height);
        }
        if (top_color != null){
            g.setColor(top_color);
            g.fillRect(0, 0 , small_lenth, height);
        }
    }
}
