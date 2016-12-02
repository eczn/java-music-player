import javafx.scene.media.MediaPlayer;

public class JStatus {
    // 是否在播放
    public boolean isPlay;
    public MediaPlayer nowPlay;
    public boolean isListed;
    public boolean isListOpen;
    public int status;

//    private boolean

    public JStatus(){
        isPlay = false;
        isListed = false;
        isListOpen = false;
        nowPlay = null;
//        status = 0;
    }

}
