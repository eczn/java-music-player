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

    public void liveAdown(String urlStr, Livelist ll){
        String usrHome = System.getProperty("user.home");
//        String softHome = "/FutureSoft/JP/";

        String[] urlStrs = urlStr.split("/");

        String fileName = urlStrs[urlStrs.length-1];

        Boolean dlSuccess = false;

        dlSuccess = httpDownload(urlStr, usrHome+"/FutureSoft/JP/Livedown/"+fileName);

        if (dlSuccess){
//            ll.
        } else {

        }
    }

    void Livedown() {

    }

//    public static void main(String[] args){
//        String usrHome = System.getProperty("user.home");
//        String softHome = "/FutureSoft/JP/";
////        String urlStr = "http://vally.eczn.website/index.html";
//        String urlStr = "http://localhost/music/1.mp3";
//
//        String[] urlStrs = urlStr.split("/");
//
//        String fileName = urlStrs[urlStrs.length-1];
////        fileName = usrHome+"/FutureSoft/JP/LiveList.data";
////        httpDownload(String httpUrl,String saveFile)
//
//        httpDownload(urlStr, usrHome+"/FutureSoft/JP/Livedown/"+fileName);
//    }


}