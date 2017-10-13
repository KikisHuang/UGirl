package example.com.fan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import example.com.fan.R;
import example.com.fan.bean.mcPublishImgUrls;
import example.com.fan.mylistener.addImgListener;
import example.com.fan.utils.DeviceUtils;
import example.com.fan.utils.OverallViewHolder;

import static example.com.fan.utils.GlideImgUtils.getRequestOptions;

/**
 * Created by Administrator on 2017/9/17.
 */

public class AddPrivatePhotoAdapter extends BaseAdapter {
    private static final String TAG = "AddPrivatePhotoAdapter";
    private LayoutInflater inflater;
    private Context context;
    private addImgListener add;
    private List<mcPublishImgUrls> data;

    public AddPrivatePhotoAdapter(Context context, List<mcPublishImgUrls> list, addImgListener add) {
        this.data = list;
        this.context = context.getApplicationContext();
        this.inflater = LayoutInflater.from(context);
        this.add = add;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View root = convertView;
        if (root == null)
            root = inflater.inflate(R.layout.add_img_item, null);
        ImageView img = OverallViewHolder.ViewHolder.get(root, R.id.img);


        int w = (int) (DeviceUtils.getWindowWidth(context) / 4.5);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(w, w);
        img.setLayoutParams(lp);
        if (position == 0) {
            Glide.with(context).load(R.mipmap.add_icon1).apply(getRequestOptions(true, 0, 0,false)).into(img);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    add.onAdd(position);
                }
            });

        } else {
            Glide.with(context).load(data.get(position).getPath()).into(img);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    add.onChange(position);
                }
            });
        }

        return root;
    }

}
