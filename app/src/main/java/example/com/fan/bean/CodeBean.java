package example.com.fan.bean;

/**
 * Created by lian on 2017/6/12.
 */
public class CodeBean {

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    int code;
    String[] data;

    public String getErroMsg() {
        return erroMsg;
    }

    public void setErroMsg(String erroMsg) {
        this.erroMsg = erroMsg;
    }

    String erroMsg;

}
