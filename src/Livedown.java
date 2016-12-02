//import java.io.File;
import com.sun.media.sound.ModelAbstractChannelMixer;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
//import java.net.URL;


public class Livedown extends JFrame {
    public JLabel process;
//    public DataEcho tHpro;
//    public JTextArea process;
//    public DefaultStringModel
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

    public void liveAdown(String urlStr, Livelist ll){
        String usrHome = System.getProperty("user.home");
//        String softHome = "/FutureSoft/JP/";


        String[] urlStrs = urlStr.split("/");

        String fileName = urlStrs[urlStrs.length-1];

//        download.run(urlStr, usrHome+"/FutureSoft/JP/Livedown/"+fileName);
//        download.run("http://frandre.cc/mzt.mp3", usrHome+"/FutureSoft/JP/Livedown/"+fileName);
        download = new Download(process, urlStr, usrHome+"/FutureSoft/JP/Livedown/"+fileName);
        download.start();
        setVisible(true);
    }

    public static void main(String[] args){
        Livedown temp = new Livedown();
        temp.setVisible(true);
    }

}

// word process dis
class Download implements Runnable {
    private JLabel targetContainer;
    public int now_for;
    public int total;
    public boolean onReady;
    public String httpUrl;
    public String saveFile;
    Thread toDownload;

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

//        while ( onReady ){
//            targetContainer.setText("下载进度："+now_for+"  /  "+total);
            targetContainer.updateUI();

//            System.out.println("Thread!  " + now_for);


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

                // buf 1KB
                byte[] buffer = new byte[2048];

                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    System.out.println(bytesum);
                    targetContainer.setText("下载进度："+bytesum+"  /  "+total);
                    fs.write(buffer, 0, byteread);
                    Thread.sleep(300);
                }

                fs.close();
                targetContainer.setText("完成 已保存在用户目录下的 FutureSoft/JP/Livedown 里");

                return;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            } catch (InterruptedException IntE){
                System.out.println("InterruptedException: livedown");
            } finally {

            }


    }

}
