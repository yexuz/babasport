package com.zlj.common.conversion;

import org.springframework.core.convert.converter.Converter;

public class CustomConvert implements Converter<String, String> {

	@Override
	public String convert(String source) {
		try {
			if (null != source) {
				source = source.trim();
				if (!"".equals(source)) {
					return source;
				}
			}
		} catch (Exception e) {

		}
		return null;
	}

}
