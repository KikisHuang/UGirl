package example.com.fan.utils;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/6/7.
 */
public class StringUtil {
    /**
     * 检查str是否为空或者空白，如果不为空返回str.trim(),否则返回"无"
     *
     * @param str
     * @return
     */
    public static String checkNull(String str) {
        if (str == null || str.length() == 0 || "".equals(str.trim())) {
            return "";
        }
        return str.trim();
    }

    public static boolean cleanNull(String str) {
        if (str == null || str.length() == 0 || "".equals(str.trim()) || str.equals("null")) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isJsonNull(JSONObject ob, String str) {
        if (ob.isNull(str) || ob.optString(str).length() == 0 || "".equals(ob.optString(str.trim()))) {
            return true;
        }

        return false;
    }

    public static boolean isNumber(String str) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern
                .compile("[0-9]*");
        java.util.regex.Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }
}
