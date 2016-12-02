//import java.io.File;
//import com.sun.media.sound.ModelAbstractChannelMixer;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class Livedown extends JFrame {
    public JLabel process;
    public Download download;


    public Livedown() {
        setBounds(200, 150, 500, 150);
        process = new JLabel("下载进度");
        process.setBounds(50,0,200,150);
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

        download.start();
        setVisible(true);
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
    public Livelist whoTellMeToDownload;

    public Download(JLabel out, String url, String toWhere){
        super();
        httpUrl = url;
        saveFile = toWhere;

        targetContainer = out;
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
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            return;
        }

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(saveFile);

            // buf 2KB
            byte[] buffer = new byte[2048];

            Thread.sleep(2000);
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                targetContainer.setText("下载进度："+bytesum+"  /  "+total);
                fs.write(buffer, 0, byteread);
                Thread.sleep(1);
            }

            fs.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException IntE){
            System.out.println("InterruptedException: livedown");
        } finally {
            whoTellMeToDownload.addElem(saveFile);
            targetContainer.setText("完成 已保存在用户目录下的 FutureSoft/JP/Livedown 里");
        }
    }
}
