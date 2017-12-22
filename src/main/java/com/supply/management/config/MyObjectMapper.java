package com.supply.management.config;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class MyObjectMapper extends ObjectMapper
{

	private static final long serialVersionUID = 4402127997078513582L;

	public MyObjectMapper()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.setDateFormat(dateFormat);
		this.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
//		this.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
//		this.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
//		// 设置null值不参与序列化(字段不被显示)
//		this.setSerializationInclusion(Include.NON_NULL);
//
//		// 禁用空对象转换json校验
//		this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		// 驼峰命名法转换为小写加下划线
//		this.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
	}
}