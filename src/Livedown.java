//import java.io.File;
import java.io.*;
import java.net.*;
//import java.net.URL;


public class Livedown {
    public void live_callback(){

    }

    private boolean httpDownload(String httpUrl,String saveFile){
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

            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(saveFile);

            // buf
            byte[] buffer = new byte[4096];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }

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
        System.out.println("Livedown!");

        int size;
        String temp = "http://localhost/music/1.mp3";
        size = getURLFileLength(temp);
        System.out.println("theSize@@@@ "+ size);

    }

    public static void main(String[] args){



        Livedown temp = new Livedown();
    }


}
