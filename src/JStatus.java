import javafx.scene.media.MediaPlayer;

import java.net.URL;

// state class
public class JStatus {
    // 是否在播放
    public boolean isPlay;
    public MediaPlayer nowPlay;
    public boolean isListed;
    public boolean isListOpen;
    public int playmode;

    public static int SINGLE_LOOP = 0;
    public static int LIST_LOOP = 1;
    public static int RANDOM_LOOP = 2;
    public static URL[][] ImagesURL;

    public JStatus(){
        isPlay = false;
        isListed = false;
        isListOpen = false;
        nowPlay = null;

        playmode = 0;

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

    public URL[] modeToggle(){
        playmode++;
        if (playmode >= 3){
            playmode = 0;
        }

        return ImagesURL[playmode];
    }

    public URL[] getResources(int i){
        return ImagesURL[i];
    }
}
