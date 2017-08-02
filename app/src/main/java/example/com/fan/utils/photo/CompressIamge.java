package example.com.fan.utils.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import example.com.fan.utils.StringUtil;

/**
 * 压缩图片的
 */
public class CompressIamge {

    /**
     * 根据图片路径返回图片
     */
    public static Bitmap getBitmapFromUri(String path, Context mContext) {
        if (StringUtil.isEmpty(path))
            return null;
        Uri uri = Uri.fromFile(new File(path));
        try {
            // 读取uri所在的图片
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            return compressImageFromFile(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getBitmapFromUri2(String path, Context mContext) {
        if (StringUtil.isEmpty(path))
            return null;
        Uri uri = Uri.fromFile(new File(path));
        try {
            // 读取uri所在的图片
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            return getimage(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFileName(String pathandname) {
        int start = pathandname.lastIndexOf("/");
        int end = pathandname.length();
        if (start != -1 && end != -1) {
            return pathandname.substring(start + 1, end);
        } else {
            return null;
        }

    }

    /**
     * 复制图片到指定文件夹
     * 成功返回新路径 失败返回原来路径
     */
    public static String copy(String oldFileStr) {
        File oldFile = new File(oldFileStr);

        String newFileStr = FileManager.getSaveFilePath() + "copy/" + getFileName(oldFileStr);
        File newFile = new File(newFileStr);

        if (!oldFile.exists()) {
            return "";
        }

        if (!oldFile.isFile()) {
            return "";
        }
        if (!oldFile.canRead()) {
            return "";
        }
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        if (newFile.exists()) {
            newFile.delete();
        }

        try {
            FileInputStream fosfrom = new FileInputStream(oldFile);
            FileOutputStream fosto = new FileOutputStream(newFile);

            byte[] bt = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            //关闭输入、输出流
            fosfrom.close();
            fosto.close();
            return newFileStr;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return oldFileStr;
    }

    /**
     * 保存图片
     * 用于裁剪后
     */
    public static String saveToFile(Bitmap bitmap) {

        String newFileStr = FileManager.getSaveFilePath() + "copy/" + System.currentTimeMillis() + ".jpeg";
        File newFile = new File(newFileStr);

        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        if (newFile.exists()) {
            newFile.delete();
        }

        //压缩图片
        try {
            //创建FileOutputStream对象
            FileOutputStream fos = new FileOutputStream(newFile);
            //开始压缩图片
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos)) {
                fos.flush();
                //关闭流对象
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFileStr;
    }

    /**
     * 压缩质量大小 、尺寸
     *
     * @param srcPath
     * @return
     */
    public static Bitmap compressImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容  
//        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        Bitmap bitmap;

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//  
        float ww = 700f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

        newOpts.inPreferredConfig = Config.RGB_565;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效  
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收  

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩  
        //其实是无效的,大家尽管尝试

        // 把图片压缩并且放入本地
//        compressBmpToFile(bitmap, new File(FileManager.getSaveFilePath() + "copy/" + System.currentTimeMillis() + ".jpeg"));
        return bitmap;
    }

    /**
     * 压缩质量大小 不压缩尺寸
     *
     * @param srcPath
     * @return
     */
    public static Bitmap compressImageNoFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//
        float ww = 700f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

        newOpts.inPreferredConfig = Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        //其实是无效的,大家尽管尝试
        return bitmap;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage   ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    public static Bitmap compressImageFromFile(String srcPath, float hh, float ww) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

//        newOpts.inDither=false;    /*不进行图片抖动处理*/
//        newOpts.inPreferredConfig=null;  /*设置让解码器以最佳方式解码*/

        newOpts.inPreferredConfig = Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        //其实是无效的,大家尽管尝试

        // 把图片压缩并且放入本地
        compressBmpToFile(bitmap, new File(srcPath));
        return bitmap;
    }

    public static void compressBmpToFile(Bitmap bmp, File file) {
        if (file.exists()) {
            if (file.isFile())
                file.delete();
        }

        //质量压缩
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 80;//个人喜欢从80开始, 是压缩率，表示压缩70%; 如果不压缩是100，表示压缩率为0
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            options -= 10;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //图片按比例大小压缩方法（根据路径获取图片并压缩）;
    private static Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        try {
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        } catch (OutOfMemoryError e) {
            // 捕获OutOfMemoryError，避免直接崩溃
        }
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    //质量压缩方法;
    private static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 300) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
}
