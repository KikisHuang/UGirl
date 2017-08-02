package example.com.fan.utils;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.UnsupportedEncodingException;

/**
 * Created by lian on 2017/7/12.
 * 生成二维码业务类;
 */
public class QRCodeUtils {

    public static Bitmap getQrCode(String str) {
        Bitmap bitmap = null;
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
//            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 300, 300);//这种方法不支持生成中文二维码
            result = new MultiFormatWriter().encode(new String(str.getBytes("UTF-8"),"ISO-8859-1"),
                    BarcodeFormat.QR_CODE, 350, 350);
            // 使用 ZXing Android Embedded 要写的代码
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();

            bitmap = barcodeEncoder.createBitmap(result);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException iae) { // ?
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 如果不使用 ZXing Android Embedded 的话，要写的代码
//        int w = result.getWidth();
//        int h = result.getHeight();
//        int[] pixels = new int[w * h];
//        for (int y = 0; y < h; y++) {
//            int offset = y * w;
//            for (int x = 0; x < w; x++) {
//                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
//            }
//        }
//        bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels,0,100,0,0,w,h);

        return bitmap;
    }

}
