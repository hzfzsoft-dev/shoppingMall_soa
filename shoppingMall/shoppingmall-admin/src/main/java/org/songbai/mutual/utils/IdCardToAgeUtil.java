package org.songbai.mutual.utils;

import org.songbai.mutual.utils.date.DiffDaysUtil;
import org.songbai.mutual.utils.date.SimpleDateFormatUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Autuor fangchao
 * @Date 2017/9/7 上午11:22
 */
public class IdCardToAgeUtil {

    public  static String DAY_AGE="dayAge";
    public  static String AGE="age";

    public static Map<String,Integer> getAgeByIdCard(String idCard){

        String birthday=idCard.substring(6,14);
        Date birthdayDate= SimpleDateFormatUtil.stringToDate(birthday);

        Calendar cardCalendar = Calendar.getInstance();
        cardCalendar.setTime(birthdayDate);
        int year = cardCalendar.get(Calendar.YEAR);
        int month = cardCalendar.get(Calendar.MONTH)+1;
        int day = cardCalendar.get(Calendar.DAY_OF_MONTH);

        Calendar calendar = Calendar.getInstance();
        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH)+1;
        int dayOfMonthNow = cardCalendar.get(Calendar.DAY_OF_MONTH);

        if (cardCalendar.after(calendar)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        Map<String,Integer> data=new HashMap<String,Integer>();
        int age = yearNow - year;
        //不满一岁计算天数
        if(age==0){
           Integer dayAge=DiffDaysUtil.differentDays(birthdayDate,new Date());
           data.put(DAY_AGE,dayAge);
           return data;
        }
        if (monthNow <= month) {
            if (monthNow == month) {
                if (dayOfMonthNow < day)
                    age--;
            }else{
                age--;
            }
        }
        data.put(AGE,age);
        return data;
    }

    public static void main(String[] args) {


       Map<String,Integer> data =getAgeByIdCard("330182201606202218");
       for (String key : data.keySet()){
           System.out.println("key----"+key+"---value===="+data.get(key));
       }
    }
}
