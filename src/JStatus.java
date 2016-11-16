import javafx.scene.media.MediaPlayer;

public class JStatus {
    // 是否在播放
    public boolean isPlay;
    public MediaPlayer nowPlay;
    public boolean isListed;
    public int status;
    //
//    private boolean

    public JStatus(){
        isPlay = false;
        isListed = false;

        nowPlay = null;
//        status = 0;
    }

}
