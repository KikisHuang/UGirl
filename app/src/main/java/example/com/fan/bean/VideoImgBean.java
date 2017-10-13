package example.com.fan.bean;

/**
 * Created by lian on 2017/10/13.
 */
public class VideoImgBean {
    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public boolean getSelectFlag() {
        return SelectFlag;
    }

    public void setSelectFlag(boolean selectFlag) {
        SelectFlag = selectFlag;
    }

    private String FilePath;
    private boolean SelectFlag;
}
