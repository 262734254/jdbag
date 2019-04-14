package com.xiu.jd.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DateHelper { 

private static DateHelper instance = new DateHelper(); 

public static DateHelper getInstance() { 
return instance; 
} 

/** 
* 日期标值枚举 
* @author spt 
*/ 
public enum DateRangeFlag { 
THIS_DAY, THIS_WEEK, THIS_MONTH 
} 

/** 
* 根据指定格式的日期字符串获得日期 
* @param dateStr 日期字符串 格式如：(yyyy-MM-dd) 2011-08-12
* HH:mm:ss是当前时间的时和分钟秒钟
* @return 日期 
*/ 
public static Date getDateByString(String dateStr) { 
String[] elements = dateStr.split("-"); 
if (elements.length != 3) { 
return new Date(); 
} 

Calendar cal = Calendar.getInstance(); 
cal.set(Calendar.YEAR, Integer.parseInt(elements[0])); 
cal.set(Calendar.MONTH, Integer.parseInt(elements[1]) - 1); 
//该月对于的天
cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(elements[2])); 

return cal.getTime(); 
} 

/** 
 * 将日期(格式为:yyyy-MM-dd)转化为字符串
* 根据日期，返回指定格式的日期字符串"yyyy-MM-dd" 
* @param date 
* @return 指定格式的日期字符串 
*/ 
public static String getFormatString(Date date) { 
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
return date == null ? "" : sdf.format(date); 
} 



/** 
* 方法名：getFullFormateString 
* 方法描述：获取包含小时、分钟、秒的时间字符串 "yyyy-MM-dd HH:mm:ss"  yyyy-MM-ddHH:mm:ss
* @param date 日期 
* @return 指定格式的日期字符串 
*/ 
public static String getFullFormateString(Date date){ 
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
return date == null ? "" : sdf.format(date); 
} 

public static Date stringToDate(String stringDate){
	if(stringDate==null){
		return new Date();
	}
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	Date date=new Date();
	try {
		 date=sdf.parse(stringDate);
		
	} catch (ParseException e) {
		return new Date();
	}
	return date;
}

/** 
* 
* 方法描述：获取当前日期包含小时、分钟、秒的时间字符串 "yyyy-MM-dd HH:mm:ss" 
* @param date 日期 
* @return 指定格式的日期字符串 
*/ 
public static String getNowFullFormateString(){ 
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
return sdf.format(new Date()); 
} 


/** 
* 根据指定的日期范围标值，返回指定的开始日期和结束日期 
* 
* @param date 
*            日期 
* @param flag 
*            日期范围标值 
* @return 日期数组，第一个元素为开始日期，第二个元素为结束日期 
*/ 
public static Date[] getDatesByFlag(Date date, DateRangeFlag flag) { 
//存放开始及其结束的时间
Date[] dates = new Date[2]; 
Calendar beginCal = Calendar.getInstance(); 
Calendar endCal = Calendar.getInstance(); 
beginCal.setTime(date); 
endCal.setTime(date); 

switch (flag) { 
//天例如:2012-03-10 00:00:00  2012-03-10 23:59:59
case THIS_DAY: 
beginCal.set(Calendar.DAY_OF_MONTH, beginCal.get(Calendar.DAY_OF_MONTH) - 1); 
beginCal.set(Calendar.HOUR_OF_DAY, 0); 
beginCal.set(Calendar.MINUTE, 0); 
beginCal.set(Calendar.SECOND, 0); 

endCal.set(Calendar.DAY_OF_MONTH, endCal.get(Calendar.DAY_OF_MONTH) - 1); 
endCal.set(Calendar.HOUR_OF_DAY, 23); 
endCal.set(Calendar.MINUTE, 59); 
endCal.set(Calendar.SECOND, 59); 
dates[0] = beginCal.getTime(); 
dates[1] = endCal.getTime(); 
break; 

case THIS_WEEK: 
beginCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); 
beginCal.set(beginCal.get(Calendar.YEAR), beginCal 
.get(Calendar.MONTH), 
beginCal.get(Calendar.DAY_OF_MONTH) - 7); 
beginCal.set(Calendar.HOUR_OF_DAY, 0); 
beginCal.set(Calendar.MINUTE, 0); 
beginCal.set(Calendar.SECOND, 0); 

endCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); 
endCal.set(endCal.get(Calendar.YEAR), endCal.get(Calendar.MONTH), 
endCal.get(Calendar.DAY_OF_MONTH) - 1); 
endCal.set(Calendar.HOUR_OF_DAY, 23); 
endCal.set(Calendar.MINUTE, 59); 
endCal.set(Calendar.SECOND, 59); 

dates[0] = beginCal.getTime(); 
dates[1] = endCal.getTime(); 
break; 
case THIS_MONTH: 
beginCal.set(Calendar.MONTH, beginCal.get(Calendar.MONTH) - 1); 
beginCal.set(beginCal.get(Calendar.YEAR), beginCal 
.get(Calendar.MONTH), 1); 
beginCal.set(Calendar.HOUR_OF_DAY, 0); 
beginCal.set(Calendar.MINUTE, 0); 
beginCal.set(Calendar.SECOND, 0); 

endCal.set(Calendar.MONTH, endCal.get(Calendar.MONTH)); 
endCal.set(Calendar.DAY_OF_MONTH, -1); 
endCal.set(Calendar.HOUR_OF_DAY, 23); 
endCal.set(Calendar.MINUTE, 59); 
endCal.set(Calendar.SECOND, 59); 

dates[0] = beginCal.getTime(); 
dates[1] = endCal.getTime(); 
break; 
} 
return dates; 
} 

public static DateRangeFlag getDateRangeEnum(int dateFlag) { 
switch (dateFlag) { 
case 2: 
return DateRangeFlag.THIS_WEEK; 
case 3: 
return DateRangeFlag.THIS_MONTH; 

default: 
return DateRangeFlag.THIS_DAY; 
} 
} 

public static Date getBeginDate(Date beginDate) { 
Calendar cal = Calendar.getInstance(); 
cal.setTime(beginDate); 
cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)); 

cal.set(Calendar.HOUR_OF_DAY, 0); 
cal.set(Calendar.MINUTE, 0); 
cal.set(Calendar.SECOND, 0); 
return cal.getTime(); 
} 

public static Date getEndDate(Date endDate) { 
Calendar cal = Calendar.getInstance(); 
cal.setTime(endDate); 

cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)); 
cal.set(Calendar.HOUR_OF_DAY, 23); 
cal.set(Calendar.MINUTE, 59); 
cal.set(Calendar.SECOND, 59); 
return cal.getTime(); 
} 

/** 
* 返回当前时间在当年是第几周 
**/ 
public static int getWeekOfYear(){ 
Calendar cal = Calendar.getInstance(); 
cal.setTime(new Date()); 
return cal.get(Calendar.WEEK_OF_YEAR)-1; 
} 

/** 
* @param flag  true:返回上周第一天， false：返回上周最后一天 
* 返回上周的开始日期或结束日期 
**/ 
public static String getFirstDateOfPreWeek(boolean flag){ 
Calendar cal = Calendar.getInstance(); 
//获取当前时间 
long today = cal.getTimeInMillis(); 
//设定周一是每周的第一天 
cal.setFirstDayOfWeek(Calendar.MONDAY); 
//得到当天处在当周的第几天，周一是当周的第一天 
int dayNum = cal.get(Calendar.DAY_OF_WEEK)-1; 
// System.out.println(dayNum); 

if(dayNum == 0){ 
if(flag){ 
cal.setTimeInMillis(today - (13 - dayNum) * 24 * 60 * 60 * 1000); 
}else{ 
cal.setTimeInMillis(today - (7 - dayNum) * 24 * 60 * 60 * 1000); 
} 
}else if(dayNum >= 1 && dayNum < 7){ 
if(flag){ 
cal.setTimeInMillis(today - (dayNum - 1 + 7) * 24 * 60 * 60 * 1000); 
    }else{ 
    cal.setTimeInMillis(today + (7 - dayNum - 7) * 24 * 60 * 60 * 1000); 
    } 
} 
return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()); 
} 

/** 
* @param flag  true:返回上两周第一天， false：返回上两周最后一天 
* 返回上两周的开始日期或结束日期 
**/ 
public static String getFirstDateOfPre2Week(boolean flag){ 
Calendar cal = Calendar.getInstance(); 
//获取当前时间 
long today = cal.getTimeInMillis(); 
//设定周一是每周的第一天 
cal.setFirstDayOfWeek(Calendar.MONDAY); 
//得到当天处在当周的第几天，周一是当周的第一天 
int dayNum = cal.get(Calendar.DAY_OF_WEEK)-1; 
// System.out.println(dayNum); 

if(dayNum == 0){ 
if(flag){ 
cal.setTimeInMillis(today - (20 - dayNum) * 24 * 60 * 60 * 1000); 
}else{ 
cal.setTimeInMillis(today - (14 - dayNum) * 24 * 60 * 60 * 1000); 
} 
}else if(dayNum >= 1 && dayNum < 7){ 
if(flag){ 
cal.setTimeInMillis(today - (dayNum - 1 + 14) * 24 * 60 * 60 * 1000); 
    }else{ 
    cal.setTimeInMillis(today + (7 - dayNum - 14) * 24 * 60 * 60 * 1000); 
    } 
} 
return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()); 
} 

/** 
* 返回当前时间的年份 
**/ 
public static int getYear(){ 
Calendar cal = Calendar.getInstance(); 
cal.setTime(new Date()); 
return cal.get(Calendar.YEAR); 
} 

/** 
* 返回当前时间的月份 
**/ 
public static int getMonth(){ 
Calendar cal = Calendar.getInstance(); 
cal.setTime(new Date()); 
return cal.get(Calendar.MONTH); 
} 

/** 
* 判断某个年份是否为闰年 
* @return true是闰年，false不是闰年 
**/ 
public static boolean isLeapYear(int yearparam){ 
boolean flag = false;   
if(yearparam%4 != 0){       
flag = false;   
}else if(yearparam%100 != 0){      
flag = true;   
}else if(yearparam%400 != 0){   
flag = false;      
}else{   
flag = true;   
}          
  
// if(flag == true){   
// System.out.println("是闰年");   
// }else{   
// System.out.println("不是闰年");   
// } 

return flag; 
} 

public static boolean isLeapYear2(int yearparam){ 
Calendar calendar = Calendar.getInstance();   
boolean flag = ((GregorianCalendar)calendar).isLeapYear(yearparam); 

// if(flag == true){   
// System.out.println("是闰年");   
// }else{   
// System.out.println("不是闰年");   
// } 

return flag; 
} 

/** 
* 返回当前年当前月份最大天数 
**/ 
public static int getMaxDayOfCurMonth(){ 
Calendar cal = Calendar.getInstance(); 
    cal.set(Calendar.DATE, 1);  //把日期设置为当月第一天 
    cal.roll(Calendar.DATE, -1);  //日期回滚一天，也就是最后一天 
    int maxDate = cal.get(Calendar.DATE); 
//    System.out.println("当月最大天数:" + maxDate); 
    return maxDate; 
} 

/** 
* 返回当前年上月份最大天数 
**/ 
public static int getMaxDayOfPreMonth(){ 
//取得系统当前时间 
    Calendar cal = Calendar.getInstance(); 
    //取得系统当前时间所在月第一天时间对象 
    cal.set(Calendar.DAY_OF_MONTH, 1); 
    //日期减一,取得上月最后一天时间对象 
    cal.add(Calendar.DAY_OF_MONTH, -1); 
    //输出上月最后一天日期 
//    System.out.println("上月最大天数：" + cal.get(Calendar.DAY_OF_MONTH)); 
    int maxDate = cal.get(Calendar.DAY_OF_MONTH); 
    return maxDate; 
} 

/** 
* 返回某年某月最大天数 
**/ 
public static int getMaxDayOfMonth(int yearparam, int monthparam){ 
int[] daysInMonth = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};  //平年每月天数 
if(isLeapYear2(yearparam)){  //判断是否为闰年 
if(monthparam == 2){ 
return 29; 
    } 
} 

return daysInMonth[monthparam - 1]; 
} 

public static String getDateString(int day){
	
	SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	Calendar calendar = Calendar.getInstance(); 
	calendar.set(Calendar.HOUR_OF_DAY, 23*day); 
	//calendar.setTime(new Date()); 
	Date date=calendar.getTime();
	return dateFormat.format(date);
}

 public static void main(String[] args) {
    //  System.out.println(getCurrentMonth()+"============");
	//System.out.println("===================以一天为粒度 2012-03-10 00:00:00 到 2012-03-10 23:59:59==========================");
	/*for(int day=0;day<=368;day++){
		String dateString="2012-01-0"+day;
		Date date=getFormatDate(dateString);
		Date dateDay[]= getDatesByFlag(date,DateRangeFlag.THIS_DAY);
		System.out.println(getFullFormateString(dateDay[0]));
		System.out.println(getFullFormateString(dateDay[1]));
		System.out.println("=============================");
	} */
	
	/*Date dateDay[]= getDatesByFlag(new Date(),DateRangeFlag.THIS_DAY);
	System.out.println(getFullFormateString(dateDay[0]));
	System.out.println(getFullFormateString(dateDay[1]));*/
	/*
	System.out.println("===================以一个月为粒度   2012-02 2012-02-01 00:00:00 到 2012-02-28 23:59:59==========================");
	Date dateMonth[]= getDatesByFlag(new Date(),DateRangeFlag.THIS_MONTH);
	System.out.println(getFullFormateString(dateMonth[0]));
	System.out.println(getFullFormateString(dateMonth[1]));*/
	 //前台传递过来的参数 2012-03-10
	
	 try {
		 Map<Integer, List<String>> map= getCurrentMonthLastThreeDay();
		  Date toDay=getCurrentDate();
		  //
		 if(map!=null){
			 for(Map.Entry<Integer, List<String>> entry :map.entrySet()){
			  Integer key=  entry.getKey();
			 // System.out.println(key +"   ");
			  List<String> values= entry.getValue();
			 // System.out.println("size="+values.size());
			  if(values!=null && values.size()>0){
				  for(String value: values){
					  System.out.print(value+"   ");
				  }
				  System.out.println();
				  //2014-07-29
				 
				  String beforeDate=values.get(0);
				  String afterDate=values.get(1);
				System.out.println("beforeDate "+beforeDate+"  ,afterDate "+afterDate);
				 /*  System.out.println(getFormatDate(beforeDate).before(toDay));
				  System.out.println(getFormatDate(afterDate).after(toDay));
				  System.out.println("beforeDate "+beforeDate+"  ,afterDate "+afterDate);
				  System.out.println(getFormatDate(afterDate).before(toDay));
				  System.out.println(getFormatDate(afterDate).after(toDay));
				  
				  System.out.println("==============");
				  System.out.println(toDay.before(getFormatDate(values.get(0))));
				  System.out.println(toDay.after(getFormatDate(values.get(0))));
				  System.out.println();
				  System.out.println(toDay.before(getFormatDate(values.get(0))));
				  System.out.println(toDay.after(getFormatDate(values.get(0))));*/
				  
				  // if(getFormatDate(values.get(0)).before(toDay) && toDay.after(getFormatDate(values.get(0)))){
				  if(getFormatDate(beforeDate).before(toDay) && toDay.after(getFormatDate(beforeDate))){
					  System.out.println("可以执行了");
				  }else{
					  System.out.println("===========不可以执行的============");
				  }
				 // System.out.println(DateHelper.getFormatDate(beforeDate).before(toDay) && DateHelper.getFormatDate(afterDate).after(toDay));
				  
				  /*System.err.println(stringToDate(beforeDate).before(toDay));
				  System.err.println(stringToDate(afterDate).before(toDay));*/
				  if( (toDay.compareTo(stringToDate(beforeDate)))==1 && (stringToDate(afterDate).compareTo(toDay)==1)){
					  System.out.println("======可以执行了=========");
				  }else{
					  System.out.println("=----===========不可以执行的============---");
				  }
				  System.out.println(toDay.compareTo(stringToDate(beforeDate))); //1
				  System.out.println(stringToDate(afterDate).compareTo(toDay)); //1
				 // System.out.println("before "+ getFullFormateString(getFormatDate(beforeDate)));
				  System.out.println("after "+ getFullFormateString(stringToDate(afterDate)));
				  System.out.println("current "+ getFullFormateString(toDay));
				  System.out.println(" "+getFullFormateString(getFormatDate(afterDate)));
			  }
			  
			    
			 }
		 }
		
		 //System.out.println(getFullFormateString(getStartMonthOfDay("2012-03-10")));
		 //System.out.println(getFullFormateString(getEndMonthOfDay("2012-03-12")));
		 /**
		  * 2012-03-09 00:00:00
            2012-03-09 23:59:59
            
            start 2012-03-10   2012-03-10 00:00:00
            end   2012-03-13   2012-03-13 00:00:00
            
		  */
		/* Date now=sdf.parse("2012-03-10");
		 Date dateDay[]= getDatesByFlag(now,DateRangeFlag.THIS_DAY);
			System.out.println(getFullFormateString(dateDay[0]));
			System.out.println(getFullFormateString(dateDay[1]));*/
	
		 
		 
		
		
	} catch (Exception e){
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
 
/**
 * 判断是不是当前月份的最后一天
 * @param currentSystemDate 当前系统时间,长格式的
 * @param monthLastDate 当前页的最后一天
 * @return
 */
 public static boolean isCurrentMonthLastDate(Date currentSystemDate ,String monthLastDate){
	 try{
		String currentDateString= getFormatString(currentSystemDate);//长格式
	    Date currentDate=getFormatDate(currentDateString); //短格式
		 Date lastDate=getFormatDate(monthLastDate); //当前月最后一天短格式
		 if(currentDate.compareTo(lastDate)==0){
			return true;
		 }
	 }catch(Exception e){
		 e.printStackTrace();
	 }
	 return false;
 }

   
   /** System.out.println(getFullFormateString(getMonthOfDay("2012-03-13")));
  *   start 2012-03-10   2012-03-10 00:00:00
      end   2012-03-13   2012-03-13 00:00:00
  * @param formatString
  * @return
  */
 public static Date getStartMonthOfDay(String formatString){
	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	 try {
		Date now=sdf.parse(formatString);
		  Calendar calendar = Calendar.getInstance();
		  calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
		  calendar.set(Calendar.HOUR_OF_DAY, 0);
		  calendar.set(Calendar.MINUTE, 0);
		  calendar.set(Calendar.SECOND, 0);
		  return now;
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 return null;
 }
 
 public static Date getEndMonthOfDay(String formatString){
	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	 try {
		Date now=sdf.parse(formatString);
	
	 Calendar cal = Calendar.getInstance(); 
	 cal.setTime(now);
	 cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)); 
	 cal.set(Calendar.HOUR_OF_DAY, 23); 
	 cal.set(Calendar.MINUTE, 59); 
	 cal.set(Calendar.SECOND, 59); 
	 return cal.getTime();
	 }catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
		 return null;
 }
 
 public static Date getFormatDate(String formatString){
	 try{
	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	 Date date=sdf.parse(formatString);
	 return date;
	 }catch(Exception e){
		 System.out.println("格式日期异常"+e);
	 }
	 return null;
 }
 
 public static Date parseFormatDate(String formatString){
	 try{
	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 Date date=sdf.parse(formatString);
	 return date;
	 }catch(Exception e){
		 System.out.println("格式日期异常"+e);
	 }
	 return null;
 }
 
 /** 
 * 返回当前年当前月份最后三天范围
 * 只有有两个
 * 例如:2014年7月份最后三天为29号,30,31号
 * 返回的为<7 List (2014-07-29 00:00:00 , 2014-07-31 23:59:59)>
 **/ 
 public static Map<Integer,List<String> > getCurrentMonthLastThreeDay(){ 
	 Map<Integer,List<String> > map=new HashMap<Integer, List<String>>();
     Calendar cal = Calendar.getInstance(); 
     cal.set(Calendar.DATE, 1);  //把日期设置为当月第一天 
     cal.roll(Calendar.DATE, -1);  //日期回滚一天，也就是最后一天 
     int maxDate = cal.get(Calendar.DATE); 
     int year= cal.get(Calendar.YEAR);
     int month= cal.get(Calendar.MONTH)+1;//需要加1,才是中国的月
	 List<String> list=new ArrayList<String>();
	 int i=1;
    for(int today=maxDate-2;today<=maxDate;today++){
    	if(i!=2){
    		   if (i==1){
    	    		list.add(year+"-0"+month+"-"+today+" 00:00:00");
    	    	}else if (i==3){
    	    		list.add(year+"-0"+month+"-"+today+" 23:59:59");
    	    	}
    	}
     
    	i++;
    }
    map.put(month, list);
     return map; 
 } 
 /**
  * 获取当前月份
  * @return
  */
 public static Integer getCurrentMonth(){
	 Calendar cal = Calendar.getInstance(); 
     //cal.set(Calendar.DATE, 1);  //把日期设置为当月第一天 
     //cal.roll(Calendar.DATE, -1);  //日期回滚一天，也就是最后一天 
    // int maxDate = cal.get(Calendar.DATE); 
    // int year= cal.get(Calendar.YEAR);
     int month= cal.get(Calendar.MONTH)+1;//需要加1,才是中国的月
     return month;
 }

 
 public static Date getCurrentDate(){
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			String toDate=dateFormat.format(new Date());
			return dateFormat.parse(toDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
 }
} 
