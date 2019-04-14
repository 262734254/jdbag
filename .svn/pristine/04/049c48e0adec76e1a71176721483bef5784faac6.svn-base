package com.xiu.jd.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class StringUtil
{
	/**
	 * Integer型转换为String型
	 * 
	 * @param num
	 *            Integer型
	 * @return String型
	 */
	public static String integerToString(Integer num)
	{
		if (num == null)
		{
			return "";
		}
		return num.toString();
	}

	/**
	 * String型转换为Integer型
	 * 
	 * @param str
	 *            String型
	 * @return Integer型
	 */
	public static Integer stringToInteger(String str)
	{
		if (isEmpty(str))
		{
			return null;
		}
		return new Integer(str);
	}

	/**
	 * int型转换为String型
	 * 
	 * @param num
	 *            int型
	 * @return String型
	 */
	public static String intToString(int num)
	{
		if (num == -1)
		{
			return "";
		}
		return String.valueOf(num);
	}

	/**
	 * String型转换为int型
	 * 
	 * @param str
	 *            String型
	 * @return int型
	 */
	public static int stringToInt(String str)
	{
		if (isEmpty(str))
		{
			return -1;
		}
		return Integer.parseInt(str);
	}

	/**
	 * Long型转换为String型
	 * 
	 * @param num
	 *            Long型
	 * @return String型
	 */
	public static String longToString(Long num)
	{
		if (num == null)
		{
			return "";
		}
		return num.toString();
	}

	/**
	 * String型转换为Long型
	 * 
	 * @param str
	 *            String型
	 * @return Long型
	 */
	public static Long stringToLong(String str)
	{
		if (isEmpty(str))
		{
			return null;
		}
		return Long.parseLong(str);
	}

	/**
	 * Short型转换为String型
	 * 
	 * @param num
	 *            Short型
	 * @return String型
	 */
	public static String shortToString(Short num)
	{
		if (num == null)
		{
			return "";
		}
		return num.toString();
	}

	/**
	 * String型转换为Short型
	 * 
	 * @param str
	 *            String型
	 * @return Short型
	 */
	public static Short stringToShort(String str)
	{
		if (isEmpty(str))
		{
			return null;
		}
		return Short.parseShort(str);
	}

	/**
	 * short型转换为String型
	 * 
	 * @param num
	 *            short型
	 * @return String型
	 */
	public static String smallShortToString(short num)
	{
		if (num == (short) -1)
		{
			return "";
		}
		return String.valueOf(num);
	}

	/**
	 * String型转换为short型
	 * 
	 * @param str
	 *            String型
	 * @return short型
	 */
	public static short stringToSmallShort(String str)
	{
		if (isEmpty(str))
		{
			return (short) -1;
		}
		return Short.parseShort(str);
	}

	/**
	 * BigDecimal型转换为String型
	 * 
	 * @param num
	 *            BigDecimal型
	 * @return String型
	 */
	public static String bigDecimalToString(BigDecimal num)
	{
		if (num == null)
		{
			return "";
		}
		return num.toString();
	}

	/**
	 * String型转换为BigDecimal型
	 * 
	 * @param str
	 *            String型
	 * @return BigDecimal型
	 */
	public static BigDecimal stringToBigDecimal(String str)
	{
		if (isEmpty(str))
		{
			return null;
		}
		return new BigDecimal(str);
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            判断字符串
	 * @return true：空/false：非空
	 */
	public static boolean isEmpty(String str)
	{
		if (str == null || "".equals(str.trim()))
		{
			return true;
		}
		return false;
	}

	/**
	 * 将空字符串转换为Null
	 * 
	 * @param str
	 *            字符串
	 * @return 字符串
	 */
	public static String formatString(String str)
	{
		if ("".equals(str))
		{
			return null;
		}
		return str;
	}

	/**
	 * 将Textarea字符串中特殊字符替换出来
	 * 
	 * @param str
	 *            字符串
	 * @return 字符串
	 */
	public static String formatTextarea(String str)
	{
		if ("".equals(str))
		{
			return null;
		}
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < str.length(); i++)
		{
			char ch = str.charAt(i);
			if (ch == '<')
			{
				result.append("&lt;");
			}
			else if (ch == '>')
			{
				result.append("&gt;");
			}
			else if (ch == '&')
			{
				result.append("&amp;");
			}
			else if (ch == '"')
			{
				result.append("&quot;");
			}
			else if (ch == '\r')
			{
				result.append("<br>");
			}
			else if (ch == '\n')
			{
				if (i > 0 && str.charAt(i - 1) == '\r')
				{
				}
				else
				{
					result.append("<br>");
				}
			}
			else if (ch == '\t')
			{
				result.append("&nbsp;&nbsp;&nbsp;&nbsp");
			}
			else if (ch == ' ')
			{
				result.append("&nbsp;");
			}
			else
			{
				result.append(ch);
			}
		}
		return result.toString();
	}

	/**
	 * 数字型字符串增加逗号操作
	 * 
	 * @param str
	 *            数字型字符串
	 * @return added String 增加逗号的数字型字符串
	 */
	public static String addComma(String str)
	{

		if (str == null)
		{
			return "";
		}

		StringBuffer firstStr;
		String secondStr;
		String finishStr;

		if (str.indexOf(".") != -1)
		{

			firstStr = new StringBuffer(str.substring(0, str.lastIndexOf(".")));
			secondStr = str.substring(str.lastIndexOf("."));
		}
		else
		{

			firstStr = new StringBuffer(str);
			secondStr = "";
		}

		int length = firstStr.length();
		if (length > 3)
		{
			int i = length - 3;
			while (0 < i)
			{
				firstStr.insert(i, ",");
				i -= 3;
			}
		}

		finishStr = firstStr.append(secondStr).toString();

		return finishStr.trim();
	}

	/**
	 * 数字型字符串去掉先头的０操作 例如 "00123" -> "123" "000" -> "0" "000.00123" -> "0.00123"
	 * "" ->"0"
	 * 
	 * @param str
	 *            数字型字符串
	 * @return converted String 去掉先头的０的数字型字符串
	 */
	public static String deleteFrontZero(String str)
	{

		String match = "[0-9\\.]*";

		if (str == null)
		{
			return "";
		}

		Pattern pattern = Pattern.compile(match);
		if (!pattern.matcher(str).matches())
		{
			return "";
		}

		StringBuffer sb = new StringBuffer(str);
		for (int i = 0; i < str.length(); i++)
		{
			String indexStr = String.valueOf(sb.charAt(0));
			if ("0".equals(indexStr))
			{
				sb.deleteCharAt(0);
			}
			else
			{
				break;
			}
		}

		if (sb.length() == 0 || ".".equals(String.valueOf(sb.charAt(0))))
		{
			sb.insert(0, "0");
		}
		return sb.toString();
	}

	/**
	 * 取得下一个字符操作
	 * 
	 * @param str
	 *            取值用字符
	 * @return String 取得下一个字符
	 */
	public static String getNextChar(String str)
	{
		if (str == null)
		{
			return null;
		}
		if (str.length() > 1)
		{
			return null;
		}
		byte[] strByte = str.getBytes();
		int strIntVal = (int) strByte[0] + 1;

		byte newStrByte = (byte) (strIntVal);

		String newStr = new String(new byte[] { newStrByte });

		return newStr;
	}

	/**
	 * 将字符串转换成xml用字符串
	 * 
	 * @param xmls
	 *            字符串
	 * @return xml用字符串
	 */
	public static StringBuffer translateXMLString(String xmls)
	{
		if (xmls == null)
		{
			xmls = "";
		}
		StringBuffer stringbuffer = new StringBuffer(xmls);
		int i = 0;
		for (int j = stringbuffer.length(); i < j; i++)
		{
			char c = stringbuffer.charAt(i);
			switch (c)
			{
			case 60: // '<'
				stringbuffer.replace(i, i + 1, "&lt;");
				j += 3;
				i += 3;
				break;
			case 62: // '>'
				stringbuffer.replace(i, i + 1, "&gt;");
				j += 3;
				i += 3;
				break;
			case 38: // '&'
				stringbuffer.replace(i, i + 1, "&amp;");
				j += 4;
				i += 4;
				break;
			case 39: // '\''
				stringbuffer.replace(i, i + 1, "&apos;");
				j += 5;
				i += 5;
				break;
			case 34: // '"'
				stringbuffer.replace(i, i + 1, "&quot;");
				j += 5;
				i += 5;
				break;
			case 0xb:
				stringbuffer.replace(i, i + 1, " ");
				break;
			}
		}
		return stringbuffer;
	}

	/**
	 * 判断字符串是否以指定字符串开头
	 * 
	 * @param s
	 *            判断字符串
	 * @param begin
	 *            指定字符串
	 * @return true:是 false:否
	 */
	public static boolean startsWith(String s, char begin)
	{
		return startsWith(s, (new Character(begin)).toString());
	}

	/**
	 * 判断字符串是否以指定字符串开头
	 * 
	 * @param s
	 *            判断字符串
	 * @param begin
	 *            指定字符串
	 * @return true:是 false:否
	 */
	public static boolean startsWith(String s, String begin)
	{
		if ((s == null) || (begin == null))
		{
			return false;
		}

		if (begin.length() > s.length())
		{
			return false;
		}

		String temp = s.substring(0, begin.length());

		if (temp.equalsIgnoreCase(begin))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 字符文本换行操作(默认的80个字符换一行)
	 * 
	 * @param text
	 *            字符文本
	 * @return 换行后的字符文本
	 */
	public static String wrap(String text)
	{
		return wrap(text, 80);
	}

	/**
	 * 字符文本换行操作
	 * 
	 * @param text
	 *            字符文本
	 * @param width
	 *            指定的行文字长度
	 * @return 换行后的字符文本
	 */
	public static String wrap(String text, int width)
	{
		if (text == null)
		{
			return null;
		}

		StringBuffer sb = new StringBuffer();

		try
		{
			BufferedReader br = new BufferedReader(new StringReader(text));

			String s = "";

			while ((s = br.readLine()) != null)
			{
				if (s.length() == 0)
				{
					sb.append("\n");
				}
				else
				{
					while (true)
					{
						int pos = s.lastIndexOf(' ', width);

						if ((pos == -1) && (s.length() > width))
						{
							sb.append(s.substring(0, width));
							sb.append("\n");

							s = s.substring(width, s.length()).trim();
						}
						else if ((pos != -1) && (s.length() > width))
						{
							sb.append(s.substring(0, pos));
							sb.append("\n");

							s = s.substring(pos, s.length()).trim();
						}
						else
						{
							sb.append(s);
							sb.append("\n");

							break;
						}
					}
				}
			}
		}
		catch (IOException ioe)
		{
			sb.append("");
		}

		return sb.toString();
	}

	/**
	 * 取得异常的信息字符
	 * 
	 * @param t
	 *            异常
	 * @return 字符
	 */
	public static final String stackTrace(Throwable t)
	{
		String s = null;
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			t.printStackTrace(new PrintWriter(baos, true));
			s = baos.toString();
		}
		catch (Exception e)
		{
			s = "";
		}
		return s;
	}

	/**
	 * 分割字符串
	 * 
	 * @param s
	 *            字符串
	 * @param delimiter
	 *            分割字符
	 * @return 分割后的字符数组
	 */
	public static String[] split(String s, String delimiter)
	{

		if (s == null || delimiter == null)
		{
			return new String[0];
		}

		if (!s.endsWith(delimiter))
		{
			s += delimiter;
		}

		s = s.trim();

		if (s.equals(delimiter))
		{
			return new String[0];
		}

		List<String> nodeValues = new ArrayList<String>();
		if (delimiter.equals("\n") || delimiter.equals("\r"))
		{
			try
			{
				BufferedReader br = new BufferedReader(new StringReader(s));

				String line = null;

				while ((line = br.readLine()) != null)
				{
					nodeValues.add(line);
				}

				br.close();
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}

		}
		else
		{
			int offset = 0;
			int pos = s.indexOf(delimiter, offset);
			while (pos != -1)
			{
				nodeValues.add(s.substring(offset, pos));
				offset = pos + delimiter.length();
				pos = s.indexOf(delimiter, offset);
			}
		}

		return (String[]) nodeValues.toArray(new String[0]);
	}

	/**
	 * 分割boolean型字符串
	 * 
	 * @param s
	 *            字符串
	 * @param delimiter
	 *            分割字符
	 * @param x
	 *            取得异常对应项目的默认值
	 * @return 分割后的boolean型数组
	 */
	public static boolean[] split(String s, String delimiter, boolean x)
	{
		String[] array = split(s, delimiter);
		boolean[] newArray = new boolean[array.length];
		for (int i = 0; i < array.length; i++)
		{
			boolean value = x;
			try
			{
				value = Boolean.valueOf(array[i]).booleanValue();
			}
			catch (Exception e)
			{
				value = x;
			}
			newArray[i] = value;
		}
		return newArray;
	}

	/**
	 * 分割double型字符串
	 * 
	 * @param s
	 *            字符串
	 * @param delimiter
	 *            分割字符
	 * @param x
	 *            取得异常对应项目的默认值
	 * @return 分割后的double型数组
	 */
	public static double[] split(String s, String delimiter, double x)
	{
		String[] array = split(s, delimiter);
		double[] newArray = new double[array.length];
		for (int i = 0; i < array.length; i++)
		{
			double value = x;
			try
			{
				value = Double.parseDouble(array[i]);
			}
			catch (Exception e)
			{
				value = x;
			}
			newArray[i] = value;
		}
		return newArray;
	}

	/**
	 * 分割float型字符串
	 * 
	 * @param s
	 *            字符串
	 * @param delimiter
	 *            分割字符
	 * @param x
	 *            取得异常对应项目的默认值
	 * @return 分割后的float型数组
	 */
	public static float[] split(String s, String delimiter, float x)
	{
		String[] array = split(s, delimiter);
		float[] newArray = new float[array.length];
		for (int i = 0; i < array.length; i++)
		{
			float value = x;
			try
			{
				value = Float.parseFloat(array[i]);
			}
			catch (Exception e)
			{
				value = x;
			}
			newArray[i] = value;
		}
		return newArray;
	}

	/**
	 * 分割int型字符串
	 * 
	 * @param s
	 *            字符串
	 * @param delimiter
	 *            分割字符
	 * @param x
	 *            取得异常对应项目的默认值
	 * @return 分割后的int型数组
	 */
	public static int[] split(String s, String delimiter, int x)
	{
		String[] array = split(s, delimiter);
		int[] newArray = new int[array.length];
		for (int i = 0; i < array.length; i++)
		{
			int value = x;
			try
			{
				value = Integer.parseInt(array[i]);
			}
			catch (Exception e)
			{
				value = x;
			}
			newArray[i] = value;
		}
		return newArray;
	}

	/**
	 * 分割long型字符串
	 * 
	 * @param s
	 *            字符串
	 * @param delimiter
	 *            分割字符
	 * @param x
	 *            取得异常对应项目的默认值
	 * @return 分割后的long型数组
	 */
	public static long[] split(String s, String delimiter, long x)
	{
		String[] array = split(s, delimiter);
		long[] newArray = new long[array.length];
		for (int i = 0; i < array.length; i++)
		{
			long value = x;
			try
			{
				value = Long.parseLong(array[i]);
			}
			catch (Exception e)
			{
				value = x;
			}
			newArray[i] = value;
		}
		return newArray;
	}

	/**
	 * 分割short型字符串
	 * 
	 * @param s
	 *            字符串
	 * @param delimiter
	 *            分割字符
	 * @param x
	 *            取得异常对应项目的默认值
	 * @return 分割后的short型数组
	 */
	public static short[] split(String s, String delimiter, short x)
	{
		String[] array = split(s, delimiter);
		short[] newArray = new short[array.length];
		for (int i = 0; i < array.length; i++)
		{
			short value = x;
			try
			{
				value = Short.parseShort(array[i]);
			}
			catch (Exception e)
			{
				value = x;
			}
			newArray[i] = value;
		}
		return newArray;
	}

	/**
	 * @Description 判断字符是不是数字
	 * @param str
	 * @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isNum(String str)
	{
		if (isEmpty(str))
		{
			return false;
		}
		else
		{
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(str);
			if (!isNum.matches())
			{
				return false;
			}
			return true;
		}
	}

	/**
	 * @Description 将数字按照STR_FORMAT转换成固定长度的字符串，不够位自动补零
	 * @param num
	 * @param STR_FORMAT
	 * @return
	 * @return String
	 * @throws
	 */
	public static String numberFormat(int num, String STR_FORMAT)
	{
		DecimalFormat df = new DecimalFormat(STR_FORMAT);
		return df.format(num);
	}

	public static int specialLength(String str)
	{
		int count = 0;
		String regex = "[\u4e00-\u9fa5]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		while (m.find())
		{
			count++;
		}
		return str.length() + count;
	}

	/**
	 * @Description 实现按字节长度截取字符串
	 * @param s
	 * @param length
	 * @return
	 * @throws Exception
	 * @return String
	 * @throws
	 */
	public static String bSubstring(String s, int length) throws Exception
	{

		byte[] bytes = s.getBytes("Unicode");
		int n = 0; // 表示当前的字节数
		int i = 2; // 要截取的字节数，从第3个字节开始
		for (; i < bytes.length && n < length; i++)
		{
			// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
			if (i % 2 == 1)
			{
				n++; // 在UCS2第二个字节时n加1
			}
			else
			{
				// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
				if (bytes[i] != 0)
				{
					n++;
				}
			}
		}
		// 如果i为奇数时，处理成偶数
		if (i % 2 == 1)

		{
			// 该UCS2字符是汉字时，去掉这个截一半的汉字
			if (bytes[i - 1] != 0)
				i = i - 1;
			// 该UCS2字符是字母或数字，则保留该字符
			else
				i = i + 1;
		}

		return new String(bytes, 0, i, "Unicode");
	}

	/**
	 * @Description 将数组转换成整数列表
	 * @param array
	 * @return
	 * @return List<Integer>
	 * @throws
	 */
	public static List<Integer> arrayToList(Integer[] array)
	{
		if (array != null && array.length > 0)
		{
			List<Integer> list = new ArrayList<Integer>();
			for (Integer id : array)
			{
				if (id != null)
				{
					list.add(id);
				}
			}
			return list;
		}
		return null;
	}

	/**
	 * @Description
	 * @param str
	 * @return String
	 */
	public static String trim(String str)
	{
		if (str != null)
		{
			return str.trim();
		}
		return null;
	}

	/**
	 * @Description 生成UUID
	 * @return 生成的32位UUID
	 * @throws
	 */
	public static synchronized String generatorUUID()
	{
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}

	/**
	 * @Description 是否包含中文
	 * @param string
	 * @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isContainChinese(String string)
	{
		char[] t1 = string.toCharArray();
		boolean flag = false;
		for (char c : t1)
		{
			if (java.lang.Character.toString(c).matches("[\\u4E00-\\u9FA5]+"))
			{
				flag = true;
				break;
			}
		}
		return flag;
	}
}
