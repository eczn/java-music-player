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
    public void live_callback(){

    }

    private boolean httpDownload(String httpUrl, String saveFile){
        int bytesum = 0;
        int byteread = 0;

        URL url = null;
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            return false;
        }

        try {
            URLConnection conn = url.openConnection();
//            http://eczn.website/mzt.mp3
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(saveFile);

            // buf 2MB
            byte[] buffer = new byte[2048000];
//            setVisible(true);
//            process.setVisible(true);
//            repaint();
//            process.repaint();
//            process.setText("@@下载进度：");
//            tHpro.start();
//            tHpro.total = getURLFileLength(httpUrl);
//            process.setText("下载中 耐心等待");

            while ((byteread = inStream.read(buffer)) != -1) {
//                tHpro.now_for = bytesum;
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
//            tHpro.onReady = false;

            process.setText("完成 已保存在用户目录下的 FutureSoft/JP/Livedown 里");
//            process.setVisible(false);
//            setVisible(false);

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
//            live_callback();
        }
    }

    public int getURLFileLength(String urlStr){
        int fileLength;

        try {
            URL theUrl = new URL(urlStr);
            HttpURLConnection urlcon = (HttpURLConnection)theUrl.openConnection();

            fileLength = urlcon.getContentLength();
            System.out.println("@@@@@@ fileLength!!: "+fileLength);


//            return fileLength;
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

    public void liveAdown(String urlStr, Livelist ll){
        String usrHome = System.getProperty("user.home");
//        String softHome = "/FutureSoft/JP/";
        setVisible(true);
        String[] urlStrs = urlStr.split("/");

        String fileName = urlStrs[urlStrs.length-1];

        Boolean dlSuccess = false;

        dlSuccess = httpDownload(urlStr, usrHome+"/FutureSoft/JP/Livedown/"+fileName);

        if (dlSuccess){
            ll.addElem(usrHome+"/FutureSoft/JP/Livedown/"+fileName);
        } else {

        }
    }


    public Livedown() {

//        setVisible(true);
        setBounds(0, 0, 200, 150);


        process = new JLabel("下载进度");
        process.setBounds(50,0,200,150);
        process.setVisible(true);
        process.setVerticalAlignment(SwingConstants.CENTER);
        process.setHorizontalAlignment(SwingConstants.CENTER);
        process.repaint();
        add(process);
//        tHpro = new DataEcho(process);





//        System.out.println("Livedown!");
//        int size;
//        String temp = "http://localhost/music/1.mp3";
//        size = getURLFileLength(temp);
//        System.out.println("theSize@@@@ "+ size);

    }

    public static void main(String[] args){
        Livedown temp = new Livedown();
    }

}

// word process dis
class DataEcho extends Thread {
    private JLabel targetContainer;
    public int now_for;
    public int total;
    public boolean onReady;

    public DataEcho(JLabel out){
        targetContainer = out;
        now_for = 0;
        total = 999999;
        targetContainer.setText("下载中");
        onReady = true;
    }

    // thread
    public void run(){
//        int i = 0;


//        targetContainer.setText("to begin download");
        String temp = targetContainer.getText();

        while ( onReady ){
//            targetContainer.setText("下载进度："+now_for+"  /  "+total);
            targetContainer.updateUI();
//            if (targetContainer.getText() == temp){
//                temp = targetContainer.getText();
//                System.out.println("@@ 1");



                targetContainer.setText("下载进度："+now_for+"  /  "+total);
                targetContainer.repaint();
//            } else {
//                System.out.println("@@ 2");
//            }

            try {
                Thread.sleep(100);
//                targetContainer.repaint();
//                targetContainer.setText("下载进度："+now_for+"  /  "+total);
//                targetContainer.repaint();

            } catch (InterruptedException IntE){
                System.out.println("InterruptedException: livedown");
                System.out.println(IntE);
            }
            System.out.println("              Thread!  " + now_for);
        }
    }
}
