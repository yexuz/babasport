package com.zlj.core.controller;

import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zlj.common.utils.RequestUtils;
import com.zlj.core.bean.user.Buyer;
import com.zlj.core.service.user.SessionProvider;

/**
 * 单点登陆 去登陆 get 提交登陆表单 post 加密MD5 + 十六进制 （实在不行加盐）
 * 
 * @author zhengliangjun
 *
 */
@Controller
public class LoginController {

	// @Autowired
	// private BuyerService buyerService;
	//
	@Autowired
	private SessionProvider sessionProvider;

	@RequestMapping(value = "/login.aspx", method = { RequestMethod.GET })
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/login.aspx", method = { RequestMethod.POST })
	public String login(String username, String password, String returnUrl, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// 1:用户名不能为空
		if (null != username) {
			// 2：密码不能为空
			if (null != password) {
				// 3:用户名必须正确
				Buyer buyer = buyerService.selectBuyerByUsername(username);
				if (null != buyer) {
					// 4:密码必须正确
					if (buyer.getPassword().equals(encodePassword(password))) {
						// 5:保存用户到session中
						sessionProvider.setAttributeForUsername(RequestUtils.getCSESSIONID(request, response),
								buyer.getUsername());
						// 6:跳转到之前访问的页面
						return "redirect:" + returnUrl;
					} else {
						model.addAttribute("error", "密码必须正确");
					}
				} else {
					model.addAttribute("error", "用户名必须正确");
				}
			} else {
				model.addAttribute("error", "密码不能为空");
			}
		} else {
			model.addAttribute("error", "用户名不能为空");
		}
		model.addAttribute("username", username);
		model.addAttribute("returnUrl", returnUrl);
		return "login";
	}

	@RequestMapping("isLogin")
	@ResponseBody
	public MappingJacksonValue isLogin(String callback, HttpServletRequest request, HttpServletResponse response) {
		Integer result = 0;
		String username = sessionProvider.getAttributeForUsername(RequestUtils.getCSESSIONID(request, response));
		if (StringUtils.isNotBlank(username)) {
			result = 1;
		}
		MappingJacksonValue mjv = new MappingJacksonValue(result);
		mjv.setJsonpFunction(callback);
		return mjv;
	}

	public String encodePassword(String password) {
		// 1.MD5 算法
		String algorithm = "MD5";
		char[] encodeHex = null;
		try {
			MessageDigest instance = MessageDigest.getInstance(algorithm);
			byte[] digest = instance.digest(password.getBytes());
			encodeHex = Hex.encodeHex(digest);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new String(encodeHex);
	}

	@RequestMapping("index1")
	public String index1(HttpServletRequest request) {
		sessionProvider.setAttributeUser(request);
		return "index";
	}

	@RequestMapping("index2")
	public String index2(HttpServletRequest request, Model model) {
		Buyer buyer = sessionProvider.getAttributeUser(request);
		model.addAttribute("user", buyer);
		return "index2";
	}
}
