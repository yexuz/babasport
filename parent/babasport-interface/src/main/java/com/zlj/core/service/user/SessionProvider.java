package com.zlj.core.service.user;

import javax.servlet.http.HttpServletRequest;

import com.zlj.core.bean.user.Buyer;

public interface SessionProvider {

	// 保存用户名
	public void setAttributeForUsername(String name, String value);

	// 去用户名
	public String getAttributeForUsername(String name);

	public void setAttributeUser(HttpServletRequest request);

	public Buyer getAttributeUser(HttpServletRequest request);
}
