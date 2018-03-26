package com.justpay.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @Description:时间工具类
 * @author Yeauty
 * @version 1.0
 * @date 2017年6月16日下午2:19:44
 */
public class TimeUtils {

	private TimeUtils() {
	}

	/**
	 * 
	 * @Description:获取某天是星期几
	 * @param date
	 * @return		返回1是星期日、2是星期一、3是星期二、4是星期三、5是星期四、6是星期五、7是星期六 
	 * @author:Yeauty
	 * @time:2017年8月15日 上午11:28:16
	 */
	public static int getWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int i = calendar.get(Calendar.DAY_OF_WEEK);
		return i;
	}
	
	/**
	 * 
	 * @Description:得到传入时间的当天开始（0点时间戳）
	 * @param date
	 * @return
	 * @author Yeauty
	 * @time：2017年6月22日 下午3:02:37
	 */
	public static Date getDateStart(Date date){
		if(date==null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);  
		calendar.set(Calendar.MINUTE, 0);  
		calendar.set(Calendar.SECOND, 0);  
		calendar.set(Calendar.MILLISECOND, 0);  
        return calendar.getTime();
	}
	
	/**
	 * 
	 * @Description:得到传入时间的当天任意时间
	 * @param date
	 * @return
	 * @author Yeauty
	 * @time：2017年6月22日 下午3:02:37
	 */
	public static Date getDateEveryTime(Date date,int hour,int min,int second,int millisecond){
		if(date==null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);  
		calendar.set(Calendar.MINUTE, min);  
		calendar.set(Calendar.SECOND, second);  
		calendar.set(Calendar.MILLISECOND, millisecond);  
		return calendar.getTime();
	}
	
	/**
	 * 
	 * @Description:得到当前时间0点时间戳
	 * @return
	 * @author Yeauty
	 * @time：2017年6月16日 下午2:26:47
	 */
	public static Date getTodayStart(){
		Calendar todayStart = Calendar.getInstance();  
        todayStart.set(Calendar.HOUR_OF_DAY, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        return todayStart.getTime();
	}
	
	/**
	 * 
	 * @Description:增加时间
	 * @param date
	 * @param num
	 * @param basis		Calendar.DATE 天  、 Calendar.HOUR_OF_DAY 时 、  MINUTE 分 、  SECOND 秒 、  MILLISECOND 毫秒
	 * @return
	 * @author Yeauty
	 * @time：2017年6月26日 上午9:38:15
	 */
	public static Date addDate(Date date,int num , int basis){
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);
		calendar.add(basis,num);
		return calendar.getTime();
	}
	
	/**
	 * 
	 * @Description:减多少
	 * @param date
	 * @param num
	 * @return
	 * @author Yeauty
	 * @time：2017年6月16日 下午2:27:08
	 */
	public static Date subtractDate(Date date,int num , int basis){
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);
		calendar.add(basis,-num);
		return calendar.getTime();
	}
	
	/**
	 * 
	 * @Description:加多少天
	 * @param date
	 * @param num
	 * @return
	 * @author Yeauty
	 * @time：2017年6月16日 下午2:27:00
	 */
	public static Date addDays(Date date,int num){
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);
		calendar.add(Calendar.DATE,num);
		return calendar.getTime();
	}
	
	/**
	 * 
	 * @Description:减多少天
	 * @param date
	 * @param num
	 * @return
	 * @author Yeauty
	 * @time：2017年6月16日 下午2:27:08
	 */
	public static Date subtractDays(Date date,int num){
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);
		calendar.add(Calendar.DATE,-num);
		return calendar.getTime();
	}
	
	/**
	 * 
	 * @Description:当前时间，并格式化
	 * @return
	 * @author Yeauty
	 * @time：2017年6月27日 上午11:08:27
	 */
	public static String currentTimeFormat(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ss HH:mm:ss");
		return simpleDateFormat.format(new Date());
	}
	
	/**
	 * 
	 * @Description:简单通用的格式化
	 * @param date
	 * @return
	 * @author Yeauty
	 * @time：2017年6月27日 下午2:37:13
	 */
	public static String simpleFormat(Date date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 
	 * @Description:简单格式化 精确到天
	 * @param date
	 * @return
	 * @author Yeauty
	 * @time：2017年6月27日 下午2:37:13
	 */
	public static String simpleFormatToDay(Date date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 
	 * @Description:用中文格式化日期，精确到天
	 * @param date
	 * @return
	 * @author Yeauty
	 * @time：2017年7月12日 上午10:13:20
	 */
	public static String FormatWithCNToDay(Date date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 
	 * @Description:两个日期间相差多少天
	 * @param startDate
	 * @param endDate
	 * @return
	 * @author Yeauty
	 * @time：2017年6月27日 下午4:16:52
	 */
	public static int daysBetween(Date startDate, Date endDate){
		long between_days=(endDate.getTime()-startDate.getTime())/(1000*3600*24);  
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/**
	 * 
	 * @Description:将一个String格式的日期格式化为Date
	 * @param date
	 * @param pattern
	 * @return
	 * @throws ParseException
	 * @author Yeauty
	 * @time：2017年6月28日 下午3:56:17
	 */
	public static Date stringToDate(String date,String pattern) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.parse(date);
	}

	/**
	 *
	 * @Description:将一个Date格式的日期格式化为String
	 * @param date
	 * @param pattern
	 * @return
	 * @throws ParseException
	 * @author Yeauty
	 * @time：2017年6月28日 下午3:56:17
	 */
	public static String dateToString(Date date,String pattern) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

}
