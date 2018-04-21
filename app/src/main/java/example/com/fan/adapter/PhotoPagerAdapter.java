package example.com.fan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import example.com.fan.bean.MirrorBean;
import example.com.fan.bean.PageTopBean;
import example.com.fan.bean.mcPublishImgUrls;
import example.com.fan.fragment.son.PictureSlideFragment;
import example.com.fan.utils.MzFinal;

/**
 * Created by lian on 2017/7/17.
 */
public class PhotoPagerAdapter extends FragmentStatePagerAdapter {
    private List<MirrorBean> urlList;
    private String id;
    private PageTopBean pageTopBean;
    public PhotoPagerAdapter(FragmentManager fm, List<MirrorBean> urlList, String id, PageTopBean pageTopBean) {
        super(fm);
        this.urlList = urlList;
        this.id = id;
        this.pageTopBean=  pageTopBean;
    }

    @Override
    public Fragment getItem(int position) {
        List<mcPublishImgUrls> list = urlList.get(0).getMcPublishImgUrls();
        return PictureSlideFragment.newInstance(list.get(position).getPath(), list.get(position).getBasePath(), list.get(position).getNeedMoney(), id, MzFinal.AlbumENDAdvertShow && position == list.size() - 1 ? pageTopBean : null);//返回展示不同网络图片的PictureSlideFragment
    }

    @Override
    public int getCount() {
        return urlList.get(0).getMcPublishImgUrls().size();//指定ViewPager的总页数
    }
}
