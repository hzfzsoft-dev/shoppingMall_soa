package org.fzsoft.shoppingmall.utils.date;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 12 on 2016/11/9.
 */
public class SimpleDateFormatUtil {

    public static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyyMMdd HH:mm:ss.SSS");
    public static final FastDateFormat DATE_FORMAT1 = FastDateFormat.getInstance("yyyyMMdd");
    public static final FastDateFormat DATE_FORMAT2 = FastDateFormat.getInstance("yyyy-MM-dd");
    public static final FastDateFormat DATE_FORMAT3 = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS");
    public static final FastDateFormat DATE_FORMAT4 = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS");
    public static final FastDateFormat DATE_FORMAT5 = FastDateFormat.getInstance("yyyyMMddHHmm");
    public static final FastDateFormat DATE_FORMAT6 = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    public static final FastDateFormat DATE_FORMAT7 = FastDateFormat.getInstance("yyyy-MM-dd HH:mm");

    public static final FastDateFormat DATE_FORMAT8 = FastDateFormat.getInstance("yyyyMMddHHmmssSSS");
    public static final FastDateFormat DATE_FORMAT9 = FastDateFormat.getInstance("yyyyMMddHHmmss");
    public static final FastDateFormat DATE_FORMAT10 = FastDateFormat.getInstance("yyyyMMddHH:mm:ss.SSS");
    public static final FastDateFormat DATE_FORMAT11 = FastDateFormat.getInstance("HH:mm");


    public final static FastDateFormat MINUTES_FORMAT = FastDateFormat.getInstance("HHmm");

    /**
     * 将long型的时间精确到秒格式转成date
     *
     * @return
     */
    public static Date getLongToDate(Long date) {

        return new Date(date * 1000);
    }
    public static Date longToDate(String date){
    	 try {
    		 SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	 Long lDate = new Long(date);
        	 String sDate = s.format(lDate);
        	 Date dDate = s.parse(sDate);
        	 return dDate;
		} catch (Exception e) {
			
		}
    	return null;
    }
    public static String dateToString(Date date) {

        return DATE_FORMAT6.format(date);
    }


    public static String dateToString(Date date, FastDateFormat fastDateFormat) {

        return fastDateFormat.format(date);
    }

    public static Date stringToDate(String str) {

        try {

            if (str == null) {
                return null;
            }
            if (str.length() == "yyyy-MM-dd HH:mm:ss".length()) {
                return DATE_FORMAT6.parse(str);
            } else if (str.length() == "yyyy-MM-dd".length()) {
                return DATE_FORMAT2.parse(str);
            } else if (str.length() == "yyyy-MM-dd HH:mm:ss.SSS".length()) {
                return DATE_FORMAT3.parse(str);
            } else if (str.length() == "yyyyMMddHHmm".length()) {
                return DATE_FORMAT5.parse(str);
            } else if (str.length() == "yyyyMMdd".length()) {
                return DATE_FORMAT1.parse(str);
            } else if(str.length() == "HH:mm".length()){
            	return DATE_FORMAT11.parse(str);
            }
        } catch (ParseException e) {

        }
        return null;
    }

//    public static Long getDateToLongSeconds(Date date){
//    	Calendar calendar = Calendar.getInstance();
//    	calendar.setTime(date);
//    	
//    }


}
