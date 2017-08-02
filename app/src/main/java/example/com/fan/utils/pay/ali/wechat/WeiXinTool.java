package example.com.fan.utils.pay.ali.wechat;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import example.com.fan.utils.MzFinal;

import static example.com.fan.utils.SynUtils.getTAG;

/**
 * Created by yy on 2015/7/25.
 */
public class WeiXinTool {
    private static final String TAG = getTAG(WeiXinTool.class);
    private Context context;
    PayReq req;
    final IWXAPI msgApi;

    StringBuffer sb;


    public WeiXinTool(Context context) {
        this.context = context;
        req = new PayReq();
        sb = new StringBuffer();
        msgApi = WXAPIFactory.createWXAPI(context, null);
        msgApi.registerApp(MzFinal.WECHATPAY);
    }

    public void pay(SortedMap<String, String> parameters) {
        GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
        getPrepayId.execute(parameters);
    }


    /**
     * 生成签名
     */
//
//    private String genPackageSign(List<BasicNameValuePair> params) {
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < params.size(); i++) {
//            sb.append(params.get(i).getName());
//            sb.append('=');
//            sb.append(params.get(i).getValue());
//            sb.append('&');
//        }
//        sb.append("key=");
//        sb.append(Constants.API_KEY);
//
//
//        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
//        Log.e("orion", packageSign);
//        return packageSign;
//    }

//    private String genAppSign(List<BasicNameValuePair> params) {
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < params.size(); i++) {
//            sb.append(params.get(i).getName());
//            sb.append('=');
//            sb.append(params.get(i).getValue());
//            sb.append('&');
//        }
//        sb.append("key=");
//        sb.append(Constants.API_KEY);
//
//        this.sb.append("sign str\n" + sb.toString() + "\n\n");
//        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
//        Log.e("orion", appSign);
//        return appSign;
//    }

    private String toXml(List<BasicNameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");


            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        Log.e("orion", "拼接xml" + sb.toString());
        try {
            return new String(sb.toString().getBytes(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    class BasicNameValuePair {
        private String Name;
        private String Value;

        public BasicNameValuePair(String Key, String Value) {
            this.Name = Key;
            this.Value = Value;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String value) {
            Value = value;
        }

        @Override
        public String toString() {
            return "BasicNameValuePair{" +
                    "Name='" + Name + '\'' +
                    ", Value='" + Value + '\'' +
                    '}';
        }
    }

    private class GetPrepayIdTask extends AsyncTask<Object, Void, String> {

        private ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(context, "", "");
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog != null) {
                dialog.dismiss();
            }
//            Log.e("===", "prepay_id----" + result.get("prepay_id"));
            /** doInBackground 方法后获取result */
//            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
//            result
            genPayReq();

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected String doInBackground(Object... params) {
//            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");//unifiedorder
//            String entity = genProductArgs();
//
//            Log.e("orion", "预支付执行之前" + entity);
//
//            byte[] buf = Util.httpPost(url, entity);
//
//            String content = new String(buf);
//            Log.e("orion", "预支付返回" + content);
//            Map<String, String> xml = decodeXml(content);

            Map<String, String> xml = (Map<String, String>) params[0];
            return getRequestXml(xml);
        }
    }


    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if ("xml".equals(nodeName) == false) {
                            //实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("orion", e.toString());
        }
        return null;

    }


//    private String genNonceStr() {
//        Random random = new Random();
//        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
//    }
//
//    private long genTimeStamp() {
//        return System.currentTimeMillis() / 1000;
//    }
//
//
//    private String genOutTradNo() {
//        Random random = new Random();
//        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
//    }

    public void setData(Map<String, String> mapList) {
        this.mapList = mapList;
    }

    public void setData(String appid, String body, String mch_id, String nonce_str, String out_trade_no, String spbill_create_ip, String
            total_fee, String trade_type, String sign, String notify_url) {
        mapList = new HashMap<>();
        mapList.put("appid", appid);
        mapList.put("body", body);
        mapList.put("mch_id", mch_id);
        mapList.put("nonce_str", nonce_str);
        mapList.put("out_trade_no", out_trade_no);
        mapList.put("spbill_create_ip", spbill_create_ip);
        mapList.put("total_fee", total_fee);
        mapList.put("trade_type", trade_type);
        mapList.put("sign", sign);
        mapList.put("notify_url", notify_url);

    }

    private Map<String, String> mapList;

    private Map<String, String> mapList2;

    public void setMap(JSONObject payobject) {
        String timestamp = payobject.optString("timestamp");
        String sign = payobject.optString("sign");
        String noncestr = payobject.optString("noncestr");
        String partnerid = payobject.optString("partnerid");
        String prepayid = payobject.optString("prepayid");
        String package1 = payobject.optString("package");
        String appid = payobject.optString("appid");

        mapList2 = new TreeMap<String, String>();
        mapList2.put("timestamp", timestamp);
        mapList2.put("sign", sign);
        mapList2.put("noncestr", noncestr);
        mapList2.put("partnerid", partnerid);
        mapList2.put("prepayid", prepayid);
        mapList2.put("package", package1);
        mapList2.put("appid", appid);
    }

    //
    private String genProductArgs() {

        try {
            List<BasicNameValuePair> packageParams = new LinkedList<BasicNameValuePair>();

//            packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
//            packageParams.add(new BasicNameValuePair("body", "weixin"));
//            packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
//            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
//            packageParams.add(new BasicNameValuePair("notify_url", "http://121.40.35.3/test"));
//            packageParams.add(new BasicNameValuePair("out_trade_no",genOutTradNo()));
//            packageParams.add(new BasicNameValuePair("spbill_create_ip","127.0.0.1"));
//            packageParams.add(new BasicNameValuePair("total_fee", "1"));
//            packageParams.add(new BasicNameValuePair("trade_type", "APP"));

            packageParams.add(new BasicNameValuePair("appid", mapList.get("appid")));
            packageParams.add(new BasicNameValuePair("body", mapList.get("body")));
            packageParams.add(new BasicNameValuePair("mch_id", mapList.get("mch_id")));
            packageParams.add(new BasicNameValuePair("nonce_str", mapList.get("nonce_str")));
            packageParams.add(new BasicNameValuePair("notify_url", mapList.get("notify_url")));
            packageParams.add(new BasicNameValuePair("out_trade_no", mapList.get("out_trade_no")));
            packageParams.add(new BasicNameValuePair("spbill_create_ip", mapList.get("spbill_create_ip")));
            packageParams.add(new BasicNameValuePair("total_fee", mapList.get("total_fee"))); //只能为整数， 以分为单位 。
            packageParams.add(new BasicNameValuePair("trade_type", mapList.get("trade_type")));

//            String sign = genPackageSign(packageParams);
//            Log.i(TAG, "old --- sign ==========" + sign);
//            packageParams.add(new BasicNameValuePair("sign", mapList.get("sign")));
//            packageParams.add(new BasicNameValuePair("sign", sign));

            Log.i(TAG, "packageParams ================" + packageParams.toString());

            String xmlstring = toXml(packageParams);

            return xmlstring;

        } catch (Exception e) {
            Log.e(TAG, "出现异常 " + e.getMessage().toString());
            return null;
        }

    }

    private void genPayReq() {
//        if ("FAIL".equals(resultunifiedorder.get("return_code"))) {
//            return;
//        }
//        req.appId = Constants.APP_ID;
//        req.partnerId = Constants.MCH_ID;
////        req.prepayId = resultunifiedorder.get("prepay_id");
//        req.prepayId = mapList.get("prepayid");
//        req.packageValue = "Sign=WXPay";
//        req.nonceStr = genNonceStr();
//        req.timeStamp = String.valueOf(genTimeStamp());


        req.appId = mapList2.get("appid");
        req.partnerId = mapList2.get("partnerid");
//        req.prepayId = resultunifiedorder.get("prepay_id");
        req.prepayId = mapList2.get("prepayid");
        req.packageValue = mapList2.get("package");
        req.nonceStr = mapList2.get("noncestr");
        req.timeStamp = mapList2.get("timestamp");


//
//
//        List<BasicNameValuePair> signParams = new LinkedList<BasicNameValuePair>();
//        signParams.add(new BasicNameValuePair("appid", req.appId));
//        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
//        signParams.add(new BasicNameValuePair("package", req.packageValue));
//        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
//        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
//        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
//
//        req.sign = genAppSign(signParams);
////        req.sign = mapList.get("sign");
//
//        sb.append("sign\n" + req.sign + "\n\n");

        sendPayReq();
        Log.e("orion", "sendPayReq：：：：：：" + sb.toString());

    }

    public void sendPayReq() {
        req.appId = mapList2.get("appid");
        req.partnerId = mapList2.get("partnerid");
//        req.prepayId = resultunifiedorder.get("prepay_id");
        req.prepayId = mapList2.get("prepayid");
        req.packageValue = mapList2.get("package");
        req.nonceStr = mapList2.get("noncestr");
        req.timeStamp = mapList2.get("timestamp");
        req.sign = mapList2.get("sign");

        msgApi.registerApp(req.appId);
        msgApi.sendReq(req);
    }

    // 请求xml组装
    public static String getRequestXml(Map<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key) || "sign".equalsIgnoreCase(key)) {
                sb.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
            } else {
                sb.append("<" + key + ">" + value + "</" + key + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }
}
