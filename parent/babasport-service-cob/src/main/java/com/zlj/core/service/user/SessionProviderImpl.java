package com.zlj.core.service.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.zlj.common.web.Constants;
import com.zlj.core.bean.user.Buyer;
import com.zlj.core.dao.user.BuyerDao;

import redis.clients.jedis.Jedis;

/**
 * 保存用户名或验证码到Redis中
 * 
 * @author zhengliangjun
 *
 */
public class SessionProviderImpl implements SessionProvider {

	@Autowired
	private Jedis jedis;
	private Integer exp = 30;
	@Autowired
	private BuyerDao buyerDao;

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	@Override
	public void setAttributeForUsername(String name, String value) {
		// 保存用户名到Redis中
		jedis.set(name + ":" + Constants.USER_NAME, value);
		jedis.expire(name + ":" + Constants.USER_NAME, exp * 60);
	}

	@Override
	public String getAttributeForUsername(String name) {
		String value = jedis.get(name + ":" + Constants.USER_NAME);
		if (StringUtils.isNotBlank(value)) {
			jedis.expire(name + ":" + Constants.USER_NAME, 60 * exp);
		}
		return value;
	}

	@Override
	public void setAttributeUser(HttpServletRequest request) {
		Buyer buyer = buyerDao.selectByPrimaryKey(1L);
		request.getSession().setAttribute("user", buyer);
	}

	@Override
	public Buyer getAttributeUser(HttpServletRequest request) {
		Buyer buyer = (Buyer) request.getSession().getAttribute("user");
		return buyer;
	}

	//
}
