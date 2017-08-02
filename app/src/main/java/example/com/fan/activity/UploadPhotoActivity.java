package example.com.fan.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import example.com.fan.mylistener.CutPhotoListener;
import example.com.fan.mylistener.DestroyListener;
import example.com.fan.utils.ToastUtil;
import example.com.fan.utils.photo.AppSDUtil;
import example.com.fan.utils.photo.CutUtil;

import static android.support.v4.content.FileProvider.getUriForFile;
import static example.com.fan.utils.SynUtils.getTAG;
import static example.com.fan.utils.photo.KitKatPhoto.getPath;
import static example.com.fan.utils.photo.KitKatPhoto.selectImage;

/**
 * Created by lian on 2017/6/12.
 */
public class UploadPhotoActivity extends Activity implements CutPhotoListener, DestroyListener {
    private static final String TAG = getTAG(UploadPhotoActivity.class);
    private int flag;
    /**
     * 选择照片请求
     */
    private static final int SELECT_PIC_KITKAT = 22, SELECT_PIC = 54;
    /**
     * 拍照请求
     */
    private static final int REQUEST_RESULT_TAKE_PICTURE = 10002;
    public static CutPhotoListener listener;
    public static DestroyListener dlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = this;
        dlistener = this;
        flag = Integer.parseInt(getIntent().getStringExtra("photo_flag"));
        Judge();
    }

    private void Judge() {
        switch (flag) {
            case 0:
                intentPictureSelect();
                break;
            case 1:
                intentPicturesTake();
                break;
        }
    }

    //相册;
    private void intentPictureSelect() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, SELECT_PIC_KITKAT);
            } else {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, SELECT_PIC);
            }
        } catch (Exception e) {
            Log.i(TAG, "ERROR ========" + e);
            finish();
        }
    }

    //相机;
    public void intentPicturesTake() {
        String iRootPath;
        try {
            iRootPath = AppSDUtil.getImageFolder();
        } catch (IllegalAccessException e) {
            Toast.makeText(this, "获取SD卡不成功", Toast.LENGTH_SHORT)
                    .show();
            finish();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            //7.0及其以后版本使用升级后的代码处理
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {//判断是否有相机应用
                File imagePath = new File(iRootPath, System.currentTimeMillis() + ".jpg");
                Uri photoURI = getUriForFile(this, this.getPackageName() + ".fileprovider", imagePath);

                SharedPreferences preferences = this.getSharedPreferences(
                        "PicturesUri", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("uri", photoURI.toString());
                editor.putString("rootPath", imagePath.toString());
                editor.commit();

                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_RESULT_TAKE_PICTURE);

            }
        } else {
            File file = new File(iRootPath, System.currentTimeMillis() + ".jpg");
            Uri mOutPutFileUri = Uri.fromFile(file);
            // pictureBox.setFilePach(mOutPutFileUri.getPath());
            // pictureImage.setTag(mOutPutFileUri);//保存路径

            SharedPreferences preferences = this.getSharedPreferences(
                    "PicturesUri", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("uri", mOutPutFileUri.toString());
            editor.putString("rootPath", file.toString());
            editor.commit();

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);// 添加照片存储路径
            startActivityForResult(intent, REQUEST_RESULT_TAKE_PICTURE);
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            String urlSTR;

            switch (requestCode) {

                case SELECT_PIC_KITKAT:
                    if (data == null || data.getData() == null) {
                    } else {
                        uri = data.getData();
                    }
                    if (DocumentsContract.isDocumentUri(this, uri) == false) {
                        urlSTR = getPath(this, uri);
                    } else {
                        urlSTR = selectImage(this, uri);
                    }
                    doAfterGetBitPath(urlSTR);
                    break;
                case SELECT_PIC:
                    if (data == null || data.getData() == null) {
                    } else {
                        uri = data.getData();
                    }
                    urlSTR = selectImage(this, uri);
                    doAfterGetBitPath(urlSTR);
                    break;

                case REQUEST_RESULT_TAKE_PICTURE:
                    SharedPreferences preferences = this
                            .getSharedPreferences("PicturesUri",
                                    Context.MODE_PRIVATE);
                    String uriStr = preferences.getString("uri", null);
                    String rootPath = preferences.getString("rootPath",
                            null);
                    if (uriStr != null) {
                        doAfterGetBitPath(rootPath);
                    }
                    break;
                default:
                    break;
            }

        } else {
            finish();
        }
    }

    private void doAfterGetBitPath(String pathstr) {
//        pathstr = CompressIamge.copy(pathstr);
        Log.i(TAG, "doAfterGetBitPath   pathStr   ===" + pathstr);
        CutUtil cu = new CutUtil();
        cu.CutBitmap(pathstr, this, "info");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listener = null;
        dlistener = null;
    }

    @Override
    public void onSucceed(String url, Bitmap bitmap) {
        setResult(1012);
        if (PersonalInfoActivity.listener != null)
            PersonalInfoActivity.listener.PhotoLBitmapistener(url, bitmap);
        finish();
    }

    @Override
    public void onFail() {
        ToastUtil.toast2_bottom(UploadPhotoActivity.this, "保存图片失败...");
        finish();
    }

    @Override
    public void onMyDestroy(int position) {
        finish();
    }
}
