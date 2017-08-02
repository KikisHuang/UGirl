package example.com.fan.base.sign;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import example.com.fan.view.Popup.SharePopupWindow;

import static example.com.fan.utils.SynUtils.getTAG;

public class Signature {
    private static final String TAG = getTAG(Signature.class);

    /**
     * 签名生成算法 DES
     *
     * @param HashMap<String, Object> params 请求参数集，所有参数必须已转换为字符串类型
     * @return 签名
     * @throws IOException
     */
    public static String getSignatureByDES(HashMap<String, Object> params)
            throws IOException {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, Object> sortedParams = new TreeMap<String, Object>(params);
        Set<Entry<String, Object>> entrys = sortedParams.entrySet();
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        basestring.append("{");
        for (Entry<String, Object> param : entrys) {
            if (param.getValue() != null && param.getValue().toString().length() > 0) {
                basestring.append(param.getKey()).append("=")
                        .append(param.getValue());
                basestring.append(",");
            } else {
                params.remove(param.getKey());
            }
        }
        basestring = basestring.deleteCharAt(basestring.length() - 1);
        basestring.append("}");
        return CustomMixedEncryptionUtil.createSign(basestring.toString().replaceAll(" ", ""));
    }

    /**
     * Json 转成 Map<>
     *
     * @param jsonStr
     * @return
     */
    public static String getMapForJson(JSONObject jsonStr) {
//		JSONObject jsonObject ;
        try {
//			jsonObject = new JSONObject(jsonStr);

            Iterator<String> keyIter = jsonStr.keys();
            String key;
            Object value;
            HashMap<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = jsonStr.get(key);
                valueMap.put(key, value);
            }
            return getSignatureByDES((HashMap<String, Object>) valueMap);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Log.i(TAG, e.toString());
        }


        return null;
    }

}
