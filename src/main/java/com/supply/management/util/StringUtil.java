package com.supply.management.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * @Description: 字符串相关操作 
 * @Project: EhaiStoreSystemPad
 * @Author: 刘阳
 * @Date: 2015-4-28 
 * @ModifiedBy: 
 * @ModifyDate:
 */
public class StringUtil
{
	/**
	 * 判断字符串是否为空，只要字符串数组中有一个为空，则返回true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String... str)
	{
		for (int i = 0; i < str.length; i++)
		{
			if (str[i] == null || "".equals(str[i].trim()))
				return true;
		}

		return false;
	}

//	public static boolean equals(final String str1, final String str2)
//	{
//		if (str1 == null || str2 == null)
//			return false;
//		return str1.equals(str2);
//	}

	/**
	 * 去除字符串回车、制表、空格、换行符
	 * 
	 * @param str
	 * @return
	 */
	public static String filter(final String str)
	{
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		return m.replaceAll("");
	}

	/**
	 * 判断字符串是否为一个远程URL（待改进）
	 * @param str
	 * @return
	 */
	public static boolean isRemoteURL(String str)
	{
		if (str == null)
			return false;
		return str.contains("http://") || str.contains("https://");
	}

	/**
	 * 判断一个字符串是否为实数形式
	 * @param str
	 * @return
	 */
	public static boolean isReal(String str)
	{
		if (str == null)
			return false;

		//表达式的功能：验证必须为数字（整数或小数）
		String pattern = "[0-9]+(.[0-9]+)?";
		//对()的用法总结：将()中的表达式作为一个整体进行处理，必须满足他的整体结构才可以。
		//(.[0-9]+)? ：表示()中的整体出现一次或一次也不出现
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.matches();

	}

	/**
	 *	判断字符串是否为整数形式
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str){
		if (str == null)
			return false;
		Matcher mer = Pattern.compile("^[0-9]+$").matcher(str);
		return mer.find();
	}

	/**
	 * 获取URL文件后缀
	 * @param url
	 * @return
     */
	public static String getURLSufix(String url)
	{
		if (isEmpty(url) || !url.contains("."))
			return null;

		return url.substring(url.lastIndexOf('.'), url.length());
	}


	public static String getUUID()
	{
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}
