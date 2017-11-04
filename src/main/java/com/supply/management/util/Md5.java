package com.supply.management.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import com.supply.management.config.contants.ServerConfig;

public class Md5
{
	public final static String MD5_KEY = "PuYang!!!!XiaoHe";

	/** 
	 * Md5加密 
	 * **/
	public static String getMD5Str(String str)
	{
		MessageDigest messageDigest = null;

		try
		{
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes(ServerConfig.DEFAULT_ENCODING));
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++)
		{
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString().toUpperCase(Locale.getDefault());
	}
	
	
	/**
	 * MD5对url进行加密
	 * 
	 * @param key
	 * @return
	 */
	public static String hashKeyForURL(String key)
	{
		String cacheKey;
		try
		{
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		}
		catch (NoSuchAlgorithmException e)
		{
			cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}

	private static String bytesToHexString(byte[] bytes)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++)
		{
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1)
			{
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}
}
