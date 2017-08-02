package example.com.fan.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lian on 2017/6/30.
 */
public class DateUtils {
    /**
     * 获取月日方法;
     *
     * @return
     */
    public static String getMonthAndDay(String str) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String m = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String d = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        return m + "月" + d + "日";
    }

    /**
     * 获取年月日方法;
     *
     * @return
     */
    public static String getDate(String str) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String s = "";
        try {
            Date date = sf.parse(str);
            s = sf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return s;
    }


    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = sdf1.parse(smdate);
        Date d2 = sdf1.parse(bdate);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        d1 = sdf.parse(sdf.format(d1));
        d2 = sdf.parse(sdf.format(d2));
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(d2);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }
}
