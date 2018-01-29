package com.supply.management.config;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringEscapeUtils;
import org.owasp.encoder.Encode;
import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class MyObjectMapper extends ObjectMapper
{

	private static final long serialVersionUID = 4402127997078513582L;

	public MyObjectMapper()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.setDateFormat(dateFormat);
		this.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
		this.getJsonFactory().setCharacterEscapes(new HTMLCharacterEscapes());
//		
//		SimpleModule module = new SimpleModule("HTML XSS Serializer",
//                new Version(1, 0, 0, "FINAL","com.yihaomen","ep-jsonmodule"));
//        module.addSerializer(new JsonHtmlXssSerializer(String.class));
//        this.registerModule(module);
		
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
	
	public static class HTMLCharacterEscapes extends CharacterEscapes {

        private final int[] asciiEscapes;

        public HTMLCharacterEscapes() {
            // start with set of characters known to require escaping (double-quote, backslash etc)
            asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
            // and force escaping of a few others:
            asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
            asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
            asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;
            asciiEscapes['"'] = CharacterEscapes.ESCAPE_CUSTOM;
            asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;
        }


        @Override
        public int[] getEscapeCodesForAscii() {
            return asciiEscapes;
        }

        // and this for others; we don't need anything special here
        @Override
        public SerializableString getEscapeSequence(int ch) {
        	SerializableString serializableString = new SerializedString(Encode.forHtml(Character.toString((char) ch)));
        	return serializableString;

        }
    }
	
	class JsonHtmlXssSerializer extends JsonSerializer<String> {

        public JsonHtmlXssSerializer(Class<String> string) {
            super();
        }

        public Class<String> handledType() {
            return String.class;
        }

        public void serialize(String value, JsonGenerator jsonGenerator,
                SerializerProvider serializerProvider) throws IOException,
                JsonProcessingException {
            if (value != null) {
                String encodedValue = HtmlUtils.htmlEscape(value.toString());
                jsonGenerator.writeString(encodedValue);
            }
        }
    }
}