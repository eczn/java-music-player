import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

// 下载模块
public class Livedown extends JFrame {
    // 基本参数 其中 Download是定义在此文件里的第二个类
    public JLabel process;
    public Download download;

    // 构造器
    public Livedown() {
        // UI初始化
        setBounds(200, 150, 500, 150);
        process = new JLabel("下载进度");
        process.setBounds(0,0,300,150);
        process.setVerticalAlignment(SwingConstants.CENTER);
        process.setHorizontalAlignment(SwingConstants.CENTER);
        process.repaint();
        setContentPane(process);
    }

    // 输入了URL后会执行这个 意思与方法名一样，叫Livedown去下载它
    public void callHimToDownloadIt(String urlStr, Livelist livelist){
        // 下载到用户目录下
        String usrHome = System.getProperty("user.home");
        String[] urlStrs = urlStr.split("/");

        String fileName = urlStrs[urlStrs.length-1];

        // 新建Download类
        download = new Download(process, urlStr, usrHome+"/FutureSoft/JP/Livedown/"+fileName);

        // 是livelist叫我去下载的 (whoTellMeToDownload)
        download.whoTellMeToDownload = livelist;



        // 设置可见， 然后开始下载进程
        setVisible(true);
        download.start();
    }

}

// Download实现了Runnable接口
class Download implements Runnable {
    // 目标显示 即将进度写在targetContainer里面
    private JLabel targetContainer;

    // 现在下载到哪里了
    public int now_for;
    // 文件总大小
    public int total;
    // 文件下载URL
    public String httpUrl;
    // 保存的名字
    public String saveFile;

    // 下载线程
    Thread toDownload;

    // 下载进度条
    ProcessBar myBar;

    // who tell me to download
    public Livelist whoTellMeToDownload;

    public Download(JLabel out, String url, String toWhere){
        super();
        httpUrl = url;
        saveFile = toWhere;

        // 新建进度条，进度条继承自Canvas
        myBar = new ProcessBar(500, 150, new Color(193,203,224), new Color(233,233,233));
        targetContainer = out;
        targetContainer.add(myBar);

        myBar.setBounds(0, 0, 500, 10);
        now_for = 0;
        total = getURLFileLength(httpUrl);
        targetContainer.setText("下载中");

        // 把线程建好
        toDownload = new Thread(this);
    }

    // 获取URL给的文件的大小
    public int getURLFileLength(String urlStr){
        int fileLength;

        try {
            URL theUrl = new URL(urlStr);
            // 连接
            HttpURLConnection urlcon = (HttpURLConnection)theUrl.openConnection();

            // 长度
            fileLength = urlcon.getContentLength();
            System.out.println("@@@@@@ fileLength!!: "+fileLength);
        } catch (MalformedURLException GetLengthE ){ // URL输入有误 异常
            // url faild
            fileLength = -1;
            System.out.println("GLE!");
            System.out.println(GetLengthE);
        } catch (IOException IOE){ // io异常 可能没空间了
            fileLength = -2;
            System.out.println("IOE!");
            System.out.println(IOE);
        }

        return fileLength;
    }

    // 开始线程
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

        try { // try to download
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(saveFile);

            // 2KB缓冲区
            byte[] buffer = new byte[4096];

            Thread.sleep(2000); // 等待两秒后下载

            // 下载循环，如果不新建线程，这个循环会阻塞main线程
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;

                System.out.println(bytesum);
                targetContainer.setText("下载进度："+bytesum+"  /  "+total);

                Double rate = Double.parseDouble(Integer.toString(bytesum)) / total;
                myBar.small_lenth = (int)(rate * myBar.total);
                myBar.repaint();
                fs.write(buffer, 0, byteread);

                // 每 8 毫秒走一次循环
                Thread.sleep(8);
            }

            fs.close();
            msg = "完成 已保存在用户目录下的 FutureSoft/JP/Livedown 里";
        } catch (FileNotFoundException e) { // 异常
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
        } finally { // 把msg写入targetContainer
            // 添加进列表
            whoTellMeToDownload.addElem(saveFile);
            myBar.small_lenth = 0;
            // 将msg写入targetContainer
            targetContainer.setText(msg);
        }
    }
}

// 进度条
class ProcessBar extends Canvas {
    public int small_lenth;
    public int total;
    public int height;
    Color top_color;
    Color bottom_color;

    // 构造器
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
