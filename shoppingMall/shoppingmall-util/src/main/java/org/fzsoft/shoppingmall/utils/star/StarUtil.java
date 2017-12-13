package org.fzsoft.shoppingmall.utils.star;


/**
 * 处理隐藏信息
 * @author fangchao
 *
 * 2017年7月14日 下午3:09:07
 */
public class StarUtil {
	
	
	/**
	 * mock用户姓名
	 * @param realName
	 * @return
	 */
    public static String mockRealNameByname(String realName) {
       return StarUtil.hideStrBySym(realName, 1, 0,realName.length()-1);
    }
    
    /**
	 * mock身份证号
	 * @param card
	 * @return
	 */
    public static String mockIdentityCardByCard(String card) {
       return  StarUtil.hideStrBySym(card, 4, 4, 6);
    }
    
    
    /**
	 * mock手机号
	 * @param card
	 * @return
	 */
    public static String mockPhoneNum(String phone) {
       return  StarUtil.hideStrBySym(phone, 3, 4,4);
    }
    
    /**处理结果
	 * @param str 要处理的字符串
	 * @param pre 需要显示该字符串的前几位
	 * @param after 需要显示该字符串的后几位
	 * @param count 隐藏部分用几个星号代替
	 * @return
	 */
	public static String hideStrBySym(String str,int pre,int after,int count) {
	    try {
	        StringBuffer symbol = new StringBuffer();
	        if(count>0){
	            for(int i=0;i<count;i++){
	                symbol.append("*");
	            }
	        }
	        if(null==str||"".equals(str)){
	            return "";
	        }
	        String str1 = str.substring(pre,str.length()-after);
	        str1 = str.replace(str1, symbol.toString());
	        return str1;
        }
        catch (Exception e) {
            return "";
        }
	}
	
}
