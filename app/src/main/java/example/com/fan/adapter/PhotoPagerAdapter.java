package example.com.fan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import example.com.fan.bean.MirrorBean;
import example.com.fan.bean.mcPublishImgUrls;
import example.com.fan.fragment.son.PictureSlideFragment;

/**
 * Created by lian on 2017/7/17.
 */
public class PhotoPagerAdapter extends FragmentStatePagerAdapter {
    private List<MirrorBean> urlList;
    private String id;
    private boolean isPay;

    public PhotoPagerAdapter(FragmentManager fm, List<MirrorBean> urlList, String id, boolean isPay) {
        super(fm);
        this.urlList = urlList;
        this.isPay = isPay;
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {

        List<mcPublishImgUrls> list = urlList.get(0).getMcPublishImgUrls();
        return PictureSlideFragment.newInstance(list.get(position).getPath(), list.get(position).getBasePath(), list.get(position).getNeedMoney(), id, isPay);//返回展示不同网络图片的PictureSlideFragment
    }

    @Override
    public int getCount() {
        return urlList.get(0).getMcPublishImgUrls().size();//指定ViewPager的总页数
    }
}
