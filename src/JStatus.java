import javafx.scene.media.MediaPlayer;

import java.net.URL;

// state class
public class JStatus {
    // 参数
    public boolean isPlay;
    public MediaPlayer nowPlay;
    public boolean isListed;
    public boolean isListOpen;
    public int playmode;

    // 静态常量
    public static int SINGLE_LOOP = 0;
    public static int LIST_LOOP = 1;
    public static int RANDOM_LOOP = 2;
    // 图标图形的文件路径
    public static URL[][] ImagesURL;

    // 构造器
    public JStatus(){
        isPlay = false;
        isListed = false;
        isListOpen = false;
        nowPlay = null;

        playmode = 0;

        // 二维数组
        ImagesURL = new URL[20][3];
        URL[] List_loop = {
                Jplayer.class.getResource("images/playmode/list.png"),
                Jplayer.class.getResource("images/playmode/list.png"),
                Jplayer.class.getResource("images/playmode/list.png")
        };
        URL[] Single_loop = {
                Jplayer.class.getResource("images/playmode/loop.png"),
                Jplayer.class.getResource("images/playmode/loop.png"),
                Jplayer.class.getResource("images/playmode/loop.png")
        };
        URL[] Random_loop = {
                Jplayer.class.getResource("images/playmode/random.png"),
                Jplayer.class.getResource("images/playmode/random.png"),
                Jplayer.class.getResource("images/playmode/random.png")
        };

        ImagesURL[0] = Single_loop;
        ImagesURL[1] = List_loop;
        ImagesURL[2] =  Random_loop;
    }

    // 切换播放模式
    public URL[] modeToggle(){
        playmode++;
        if (playmode >= 3){
            playmode = 0;
        }

        return ImagesURL[playmode];
    }

    // 返回图片资源 用路径URL数组的形式给出
    public URL[] getResources(int i){
        // ImagesURL是二维数组 
        return ImagesURL[i];
    }
}
