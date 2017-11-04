package example.com.fan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import example.com.fan.MyAppcation;
import example.com.fan.R;
import example.com.fan.base.sign.save.SPreferences;
import example.com.fan.mylistener.ShareBitmapListener;
import example.com.fan.mylistener.ShareListener;
import okhttp3.Call;

import static example.com.fan.utils.JsonUtils.getCode;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.SynUtils.saveImage;
import static example.com.fan.view.dialog.CustomProgress.Cancle;
import static example.com.fan.view.dialog.CustomProgress.Show;

/**
 * Created by lian on 2017/5/25.
 */
public class ShareUtils {
    private static final String TAG = getTAG(ShareUtils.class);
    private static long time = 0;

    /**
     * 分享通用方法;
     *
     * @param context 上下文
     * @param id      模特id
     * @param name    模特名称
     * @param info    分享的内容
     * @param id2     专辑id
     */
    public static void ShareApp(final Context context, String id, final String name, final String info, String id2) {

        /**
         * 获取分享路径;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URl + MzFinal.GETSHAREURL)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("modelId", id)
                .addParams(MzFinal.ID, id2)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(context, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            new ShareAction((Activity) context).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                                    .withTitle(name)
                                    .withText(info)
                                    .withMedia(new UMImage(context, R.mipmap.logo))
                                    .withTargetUrl(response)
                                    .open();
                        } catch (Exception e) {

                        }
                    }
                });

    }

    /**
     * 获取分享路径分享通用方法;
     *
     * @param context  上下文
     * @param id       模特id
     * @param id2      专辑id
     * @param listener 获取路径回调接口
     */
    public static void getShareUrl(final Context context, String id, String id2, final ShareListener listener) {
        if (listener != null) {
            /**
             * 获取分享路径;
             */
            OkHttpUtils
                    .get()
                    .url(MzFinal.URl + MzFinal.GETSHAREURL)
                    .addParams(MzFinal.KEY, SPreferences.getUserToken())
                    .addParams("modelId", id)
                    .addParams(MzFinal.ID, id2)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.toast2_bottom(context, "网络不顺畅...");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                if (response.length() > 0)
                                    listener.onSucceed(response);
                                else
                                    listener.onFail();
                            } catch (Exception e) {

                            }
                        }
                    });
        }
    }

    /**
     * 分享图片获取;
     */
    public static void getShareImg(String key, ShareBitmapListener listenr) {
        new CommonAsynctask(listenr).execute(MzFinal.URl + MzFinal.SHAREQCIMAGEJPG + "?key=" + key);
    }

    static class CommonAsynctask extends AsyncTask {

        private ShareBitmapListener listenr;

        public CommonAsynctask(ShareBitmapListener listenr) {
            this.listenr = listenr;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Object doInBackground(Object[] params) {
            String url = (String) params[0];
            Bitmap yzm_imagview = postImag(url, listenr);

            return yzm_imagview;
        }


        @Override
        protected void onPostExecute(Object o) {
            listenr.onQRCode((Bitmap) o);
        }
    }

    //请求图片方法
    public static Bitmap postImag(String yzm_logPath, ShareBitmapListener listenr) {
        HttpURLConnection connection = null;
        // TODO Auto-generated method stub
        try {
            URL url = new URL(yzm_logPath);
            connection = (HttpURLConnection) url.openConnection();
           /* if (sessionid != null) {
                connection.setRequestProperty("Cookie", ";" + sessionid);
            }*/
            connection.setReadTimeout(10 * 1000);
            connection.setConnectTimeout(10 * 1000);
            connection.setRequestMethod("POST");
            // 设置请求的头
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) "
                            + "Gecko/20100101 Firefox/27.0");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-type",
                    "application/x-www-form-urlencoded");
            // 链接服务器
            int Code = connection.getResponseCode();
            if (Code == 200) {
                InputStream in = connection.getInputStream();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                int len = 0;
                byte[] data = new byte[1024];
                try {
                    while ((len = in.read(data)) != -1) {
                        stream.write(data, 0, len);
                    }
                    return BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.toByteArray().length);


                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Log.i(TAG, "Error === " + e);
                    listenr.onError(e);
                } finally {
                    try {
                        stream.flush();
                        stream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        Log.i(TAG, "Error === " + e);
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.i(TAG, "Error === " + e);
            listenr.onError(e);
        } finally {
            connection.disconnect();

        }
        return null;
    }

    /**
     * 系统分享方法;
     *
     * @param context
     * @param id
     */
    public static void getSystemShare(final Context context, String id) {
        Show(context, "请稍后", false, null);
        if (System.currentTimeMillis() - time > 2000) {
            time = System.currentTimeMillis();
            try {

                Map<String, String> params = new HashMap<>();
                params.put(MzFinal.KEY, SPreferences.getUserToken());
                if (!id.isEmpty())
                    params.put(MzFinal.ID, id);

                GetBuilder get = OkHttpUtils.get();
                get.url(MzFinal.URl + MzFinal.SHAREQCIMAGE);
                get.params(params);

                RequestCall build1 = get.build();
                build1.execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(context, "网络不顺畅...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                Log.i(TAG, "response =====" + response);

                                Glide.with(context.getApplicationContext()).asBitmap().load(new JSONObject(response).optString("data")).into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(final Bitmap resource, Transition<? super Bitmap> transition) {
                                        Cancle();
                                        String path = saveImage(resource);
                                        path = insertImageToSystem(context, path);
                                        Intent imageIntent = new Intent(Intent.ACTION_SEND);
                                        imageIntent.setType("image/jpeg");
                                        imageIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
                                        context.startActivity(Intent.createChooser(imageIntent, "分享"));

                                    }
                                });
                            } else
                                ToastUtil.ToastErrorMsg(context, response, code);
                        } catch (Exception e) {
                            Log.i(TAG, "" + e);
                            if(MyAppcation.crashHandler!=null)
                            MyAppcation.crashHandler.uncaughtException(new Thread(), e);
                            Cancle();
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Cancle();
            }
        }
    }

    private static String insertImageToSystem(Context context, String imagePath) {
        String url = "";
        try {
            url = MediaStore.Images.Media.insertImage(context.getContentResolver(), imagePath, "", "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return url;
    }

    //往SD卡写入文件的方法
    public static void savaFileToSD(String filename, byte[] bytes, ShareListener listener) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/YouGridShare";
            File dir1 = new File(filePath);
            if (!dir1.exists()) {
                dir1.mkdirs();
            }
            filename = filePath + "/" + filename;
            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filename);
            output.write(bytes);
            //将bytes写入到输出流中
            output.close();
            //关闭输出流
            listener.onSucceed(filename);
        } else
            listener.onFail();
    }
}
