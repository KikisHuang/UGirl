package example.com.fan.utils.pay.ali;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import example.com.fan.bean.AliPayBean;

/**
 * Created by yy on 2015/7/20.
 */
public class alipayTool {
    private Activity maActivity;

    /**
     * call alipay sdk pay. 调用SDK支付
     */
//    public void pay(Activity mActivity) {//Activity mActivity, String orderInfostr, String signstr
//        this.maActivity = mActivity;
////        // 订单
////        String orderInfo = orderInfostr;
//
////        // 对订单做RSA 签名
////        String sign = signstr;
//
//        String orderInfostr = getOrderInfo("商品", "商品描述", "0.01", "2088221300610799", "lyl1220@foxmail.com");
//        String sign = sign(orderInfostr);
//
//        Log.d("orderStr", orderInfostr);
//
//        try {
//            // 仅需对sign 做URL编码
//            sign = URLEncoder.encode(sign, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        // 完整的符合支付宝参数规范的订单信息
//        final String payInfo = orderInfostr + "&sign=\"" + sign + "\"&"
//                + getSignType();
//
//        Runnable payRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                // 构造PayTask 对象
//                PayTask alipay = new PayTask(maActivity);
//                // 调用支付接口，获取支付结果
//                String result = alipay.pay(payInfo, true);
//
//                Message msg = new Message();
//                msg.what = 1;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//
//        // 必须异步调用
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }


    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(Activity mActivity, String info) {
        this.maActivity = mActivity;
        // 订单
//        info += "&notify_url="+ "http://ht.mozu123.com/payNotify/notify/getPayNotify";
//
//        // 对订单做RSA 签名
//        String sign = signstr;
//        try {
//            // 仅需对sign 做URL编码
//
//            sign = URLEncoder.encode(sign, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

//        // 完整的符合支付宝参数规范的订单信息
//        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
//                + getSignType();

        Log.e("payInfo=======", info);

        final String finalInfo = info;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(maActivity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(finalInfo, true);

                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public String getOrderInfo2(AliPayBean ab) {
        String orderinfo = "app_id=" + "\"" + ab.getApp_id() + "\"";
        orderinfo += "&biz_content=" + "\"" + ab.getBiz_content() + "\"";
        orderinfo += "&charset=" + "\"" + ab.getCharset() + "\"";
        orderinfo += "&format=" + "\"" + ab.getFormat() + "\"";
        orderinfo += "&method=" + "\"" + ab.getMethod() + "\"";
        orderinfo += "&notify_url=" + "\"" + ab.getNotify_url() + "\"";
        orderinfo += "&sign_type=" + "\"" + ab.getSign_type() + "\"";
        orderinfo += "&timestamp=" + "\"" + ab.getTimestamp() + "\"";
        orderinfo += "&version=" + "\"" + ab.getVersion() + "\"";
        orderinfo += "&sign=" + "\"" + ab.getSign() + "\"";

        return orderinfo;
    }

    /**
     * 构造支付订单参数列表
     *
     * @return
     */
    public static Map<String, String> buildOrderParamMap(AliPayBean ab) {
        Map<String, String> keyValues = new HashMap<String, String>();

        keyValues.put("app_id", ab.getApp_id());

        keyValues.put("biz_content", ab.getBiz_content());

        keyValues.put("charset", ab.getFormat());

        keyValues.put("method", ab.getMethod());

        keyValues.put("sign_type", ab.getSign_type());

        keyValues.put("timestamp", ab.getTimestamp());

        keyValues.put("version", ab.getVersion());

        keyValues.put("sign", ab.getSign());

        return keyValues;
    }

    /**
     * 构造支付订单参数信息
     *
     * @param map 支付订单参数
     * @return
     */
    public static String buildOrderParam(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, true));
            sb.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, true));

        return sb.toString();
    }

    /**
     * 拼接键值对
     *
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String price, String partner, String seller) {
        //biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%221%22%2C%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22070510292011586%22%7D&
        // method=alipay.trade.app.pay&
        // charset=utf-8&
        // version=1.0&
        // app_id=2017061207472579&timestamp=2016-07-29+16%3A55%3A53&
        // sign_type=RSA2&
        // sign=xsA6fXTlMKF1WjM2aeYKIFswppR7l6pHEU9SHvlBZABTTRMUCbA1Rp2nA6%2F1LLVodOLXoCYPDtcuIT5V1ZWtyWd07ykUi0F8FQyEyJT0SzSCF1HLwQ1ifO9LclKg8%2FnmOmfo1QagJn9qivKOrkh%2FWhIySFEoNXte0yl%2B3C72eRb2RBSnMChoyml%2FcuP9qxIGRU9xzl0ud1ZP%2B1D3kh5JSrdANKJgMoW2NqRAF0e3sxeCGvv%2FARS6fVlZm7p8c%2ByFEOiSMliMacrParq3p3U95Bk2I8pqEcWBN3QMdKyv9c08cmvOXxY3CLIPIdPQqJiLyC%2BjU6YDMALlLrc81hQUcQ%3D%3D

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + partner + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + seller + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.html"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    public interface alipayResult {
        void result(int result);

        void failure();
    }

    private alipayResult alipayResult;

    public void setalipayResultListener(alipayResult alipayResult) {
        this.alipayResult = alipayResult;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    Log.e("支付回调", "" + resultInfo);
                    Log.e("支付回调", "" + payResult.toString());

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        if (alipayResult != null) {
                            alipayResult.result(Activity.RESULT_OK);
                        }

                        Toast.makeText(maActivity, "支付成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            if (alipayResult != null) {
                                alipayResult.result(8000);
                            }

                            Toast.makeText(maActivity, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            if (alipayResult != null) {
                                alipayResult.failure();
                            }

                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(maActivity, "支付失败",Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case 2: {
                    Toast.makeText(maActivity, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
//    public String sign(String content) {
//        return SignUtils.sign(content, RSA_PRIVATE);
//    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA2\"";
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }
}
