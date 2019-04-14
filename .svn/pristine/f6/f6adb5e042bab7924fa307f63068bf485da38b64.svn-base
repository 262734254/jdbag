/**
 * @Title CommonUtil.java
 * @author <a href="mailto:adam2008th@hotmail.com">Adam Peng</a>
 * @Description TODO
 * @date 2010-8-26
 * @version v1.0
 */
package com.xiu.jd.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
/**
 * @ClassName: CommonUtil
 * @Description: TODO
 * @author Kevin Liu
 * @date 2013-05-13 上午10:27:03
 */
public class CommonUtil {
    public static final String SYS_DIR = System.getProperty("user.dir");
    public static String PROJECT_ROOT_PATH = SYS_DIR + "/sitemap";
    
    /*
     * Suppress default constructor noninstantiability
     */
    private CommonUtil() {
        throw new AssertionError();
    }
    
    public static <K, V> Map<K, V> newHashMap() {
        return new HashMap<K, V>();
    }
    
    public static <T> List<T> newArrayList() {
        return new ArrayList<T>();
    }
    
    public static <T> Set<T> newHashSet() {
        return new HashSet<T>();
    }
    
    /**
     * 获得给定时间的后几天时间
     * @param @param date
     * @param @param number
     * @param @return
     * @author KevinLiu
     */
    public static Date getDateByCon(Date date, int number) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.set(Calendar.DAY_OF_MONTH, cld.get(Calendar.DAY_OF_MONTH) + number);
        Date da = cld.getTime();
        return da;
    }
    
    /**
     * 把字符串”yyyy-MM-dd“转换成 DATE 的时间
     * @param dateStr
     * @return
     */
    public static Date getDateByString(String dateStr) {
        String[] ss = dateStr.split("-");
        Calendar cld = Calendar.getInstance();
        cld.set(Calendar.YEAR, (int) Integer.parseInt(ss[0].trim()));
        cld.set(Calendar.MONTH, (int) Integer.parseInt(ss[1].trim()) - 1);
        cld.set(Calendar.DAY_OF_MONTH, (int) Integer.parseInt(ss[2].trim()));
        cld.set(Calendar.HOUR_OF_DAY, 0);
        cld.set(Calendar.MINUTE, 0);
        cld.set(Calendar.SECOND, 0);
        Date da = cld.getTime();
        return da;
    }
    
    /**
     * @Description 把字符串”yyyy-MM“转换成 DATE 的时间
     */
    public static Date getDateByYYYYMM(String dateStr) {
        String[] ss = dateStr.split("-");
        Calendar cld = Calendar.getInstance();
        cld.set(Calendar.YEAR, (int) Integer.parseInt(ss[0].trim()));
        cld.set(Calendar.MONTH, (int) Integer.parseInt(ss[1].trim()) - 1);
        cld.set(Calendar.DAY_OF_MONTH, 01);
        cld.set(Calendar.HOUR_OF_DAY, 0);
        cld.set(Calendar.MINUTE, 0);
        cld.set(Calendar.SECOND, 0);
        Date da = cld.getTime();
        return da;
    }
    
    /**
     * @Description 获得当月的第一天的日期格式
     */
    public static Date getFirstDayForMonth() {
        Calendar cld = Calendar.getInstance();// 获取当前日期
        cld.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        cld.set(Calendar.HOUR_OF_DAY, 0);
        cld.set(Calendar.MINUTE, 0);
        cld.set(Calendar.SECOND, 0);
        cld.set(Calendar.MILLISECOND, 0);
        Date da = cld.getTime();
        return da;
    }
    
    /**
     * @Description 获得当月的最后一天的日期格式
     */
    public static Date getLastDayForMonth() {
        Calendar cld = Calendar.getInstance();// 获取当前日期
        cld.set(Calendar.DAY_OF_MONTH, cld.getActualMaximum(Calendar.DAY_OF_MONTH));
        cld.set(Calendar.HOUR_OF_DAY, 0);
        cld.set(Calendar.MINUTE, 0);
        cld.set(Calendar.SECOND, 0);
        cld.set(Calendar.MILLISECOND, 0);
        Date da = cld.getTime();
        return da;
    }
    
    /**
     * @Description 获得给定时间的后几秒钟时间
     * @param date
     * @param number
     * @return Date
     * @throws
     */
    public static Date getDateBySecond(Date date, int number) {
        long ms = date.getTime() + 30 * 60 * 1000;
        Date da = new Date(ms);
        return da;
    }
    
    /**
     * @Description 获得当前时间的yyyyMMdd格式
     * @return String
     */
    public static String getNowDate() {
        Calendar calCurrent = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(calCurrent.getTime());
    }
    
    /**
     * @Description 获得当前时间的yyyyMMddHHmmss格式
     * @return String
     */
    public static String getNowTime() {
        Calendar calCurrent = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(calCurrent.getTime());
    }
    
    
    /**
     * @Description 获得指定时间的yyyy-MM-dd格式
     * @param date
     * @return String
     */
    public static String getDateString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date.getTime());
    }
    
    /**
     * @Description 获得指定时间的yyyy-MM-dd格式
     * @param date
     * @return String
     */
    public static Date getDate(String date,String format) {
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
    }
    
    /**
     * 截取括号内的内容
     * @Title: getStringForPage
     * @Description: TODO
     * @param str
     * @return
     */
    public static String getStringForPage(String str) {
        if (str == null) {
            return null;
        }
        return str.substring(str.indexOf("("), str.indexOf(")"));
    }
    
    /**
     * 判断字符串是否数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        str = str.trim();
        if (str == null || str.length() == 0) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*[\\.]?[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
    
    /**
     * 判断两个日期的 在指定的精度上是否相等
     * @param DATE1
     * @param DATE2
     * @param dateFormat "yyyy-MM-dd hh:mm:ss" 相等的精度
     * @return true 相等 false 不相等
     */
    public static boolean compare_date(Date DATE1, Date DATE2, String dateFormat) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        if (DATE1 == null || DATE2 == null) {
            return false;
        }
        try {
            String dt1 = df.format(DATE1);
            String dt2 = df.format(DATE2);
            // System.out.println(DATE1.toLocaleString()+"/"+DATE2.toLocaleString());
            // System.out.println(dt1);
            if (dt1.equals(dt2)) {
                return true;
            }
        }catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }
    
    /**
     * 判断文件类型是否是指定的文件类型
     * @param enables 数组类型 允许的文件类型字符数组
     * @param fileType 需要判断的文件类型
     * @return
     */
    public static boolean enableUploadFileType(String[] enables, String fileType) {
        fileType = fileType.trim();
        for (int i = 0; i < enables.length; i++) {
            if (enables[i].trim().equals(fileType)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 将浮点数进行四舍五入
     * @param v 需要操作的浮点数
     * @param scale 四舍五入保留的位数
     * @return
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 将异常信息转换成字符串
     * @param e
     * @return
     */
    public static String stackToString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
    
    /**
     * @Description 根据request获取访问者的IP
     * @return String
     * @throws
     */
    public static String getRequestIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    /**
     * @Description 产生随机数
     * @return
     * @return String
     * @throws
     */
    public static String getRandomNumber() {
        String RandomFilename = "";
        Random rand = new Random();// 生成随机数
        int random = rand.nextInt();
        Calendar calCurrent = Calendar.getInstance();
        int intDay = calCurrent.get(Calendar.DATE);
        int intMonth = calCurrent.get(Calendar.MONTH) + 1;
        int intYear = calCurrent.get(Calendar.YEAR);
        String now = String.valueOf(intYear) + String.valueOf(intMonth) + String.valueOf(intDay) + "_";
        RandomFilename = now + String.valueOf(random > 0 ? random : (-1) * random);
        return RandomFilename;
    }
    
    /**
     * @Description 检测密码是否符合要求 满足条件返回true,否则返回false
     * @param password
     * @return
     * @return boolean
     * @throws
     */
    public static boolean checkPassword(String password) {
        if (password == null || password.length() < 6 || password.length() > 16) {
            return false;
        }
        return true;
    }
    
    public String getWebRoot() {
        String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        if (path.indexOf("cogobuy") > 0) {
            path = path.substring(0, path.indexOf("cogobuy") + 7);
        }
        return path;
    }
    
    public static void main(String[] args) {
        // System.out.println(CommonUtil.getDateString(new Date()));
        // Calendar cld = Calendar.getInstance();
        // cld.setTime(date);
        // cld.set(Calendar.DAY_OF_MONTH, cld.get(Calendar.DAY_OF_MONTH) - 30);
        // cld.set(Calendar.MONTH, cld.get(Calendar.MONTH) - 3);
        // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // System.out.println(format.format(cld.getTime()));
    	Date date = new Date();
    	System.out.println(date);
    	System.out.println(getDateByCon(date,20));
    	System.out.println(getDateByYYYYMM("2013-05"));
    	System.out.println(getDateBySecond(date,20));
    }
}
