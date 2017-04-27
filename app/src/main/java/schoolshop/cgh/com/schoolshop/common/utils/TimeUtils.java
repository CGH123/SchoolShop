package schoolshop.cgh.com.schoolshop.common.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowYMDHMSTime() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
        return mDateFormat.format(new Date());
    }

    /**
     * MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowMDHMSTime() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat(
            "MM-dd HH:mm:ss");
        return mDateFormat.format(new Date());
    }

    /**
     * MM-dd
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowYMD() {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(
            "yyyy-MM-dd");
        return mDateFormat.format(new Date());
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static String getMDHMS(Date date) {
        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return mDateFormat.format(date);
    }

    /**
     * yyyy-MM-dd
     */
    @SuppressLint("SimpleDateFormat")
    public static String getYMD(Date date) {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(
            "yyyy-MM-dd");
        return mDateFormat.format(date);
    }

    /**
     * MM-dd
     */
    @SuppressLint("SimpleDateFormat")
    public static String getMD(Date date) {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(
            "MM-dd");
        return mDateFormat.format(date);
    }

    /**
     * yyyy-MM-dd
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDiff(Date date) {
        Date now = new Date();
        int diff = (int) (now.getTime() - date.getTime()) / (1000 * 60);
        //根据时间返回多少时间之前
        if(diff < 60){
            return diff + "分钟前";
        }
        else if(diff < 24*60){
            return diff/60 + "小时前";
        }
        else{
            return "大于24小时";
        }
    }

}
