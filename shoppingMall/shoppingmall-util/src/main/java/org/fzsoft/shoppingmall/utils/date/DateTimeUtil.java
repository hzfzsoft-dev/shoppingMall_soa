package org.fzsoft.shoppingmall.utils.date;

import java.util.Date;

public class DateTimeUtil {
		
	   /**
	    * 一分钟的毫秒数
	    */
       public static final long MINUTE_TIME = 60 * 1000;
		
		/**
		 * 两个时间相差几分钟
		 * @param startDate
		 * @param endDate
		 * @return
		 */
	   public static long getDiffMinute(Date startDate,Date endDate) {
		    long nowTime = endDate.getTime();
	        long time = nowTime - startDate.getTime();
	        return time/MINUTE_TIME;
	    }
	   
	   public static long getDiffMinute(Date startDate) {
		   return  getDiffMinute(startDate, new Date());
	    }
	   
}
