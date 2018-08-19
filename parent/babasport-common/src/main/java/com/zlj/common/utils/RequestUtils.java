package com.zlj.common.utils;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestUtils {

	public static String getCSESSIONID(HttpServletRequest request, HttpServletResponse response) {
		// 1：取出Cookie
		Cookie[] cookies = request.getCookies();
		// 2：判断Cookie中是否有CSESSIONID
		if (null != cookies && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				// 3:有 直接用
				if ("CSESSIONID".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		String csessionid = UUID.randomUUID().toString().replaceAll("-", "");
		// 4:没有 创建一个CSESSIONID 并保存到Cookie中 同时把Cookie写回浏览器 使用此生成的CSESSIONID
		Cookie cookie = new Cookie("CSESSIONID", csessionid);
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		// cookie.setDomain(".jd.com"); //此处解决跨域
		response.addCookie(cookie);

		return csessionid;
	}
}
