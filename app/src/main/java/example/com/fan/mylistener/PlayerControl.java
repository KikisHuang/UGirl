package example.com.fan.mylistener;

/**
 * Created by lian on 2017/6/17.
 */
public interface PlayerControl {

    void seekTo(long positionMs);

    void play();

    void pause();

    long getDuration();

    long getBufferedPosition();

    long getCurrentPosition();

    void setGyroEnabled(boolean val);

    boolean isGyroEnabled();

    void setDualScreenEnabled(boolean val);

    boolean isDualScreenEnabled();

    void toFullScreen();

    void toolbarTouch(boolean start);

    void coverEnabled();
}
