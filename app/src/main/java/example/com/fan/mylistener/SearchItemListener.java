package example.com.fan.mylistener;

/**
 * Created by lian on 2017/6/23.
 */
public interface SearchItemListener {

    void onItemClick(int tag, String id);

    void onSearch(boolean flag, String s);
}
