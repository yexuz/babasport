package com.zlj.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 单点登陆 去登陆 get 提交登陆表单 post 加密MD5 + 十六进制 （实在不行加盐）
 * 
 * @author zhengliangjun
 *
 */
@Controller
public class LoginController {

	@RequestMapping("in")
	@ResponseBody
	public String in() {
		return "In";
	}

	@RequestMapping("index1")
	public String index1(HttpServletRequest request) {
		request.getSession().setAttribute("user", "123");
		return "index";
	}

	@RequestMapping("index2")
	public String index2(HttpServletRequest request, Model model) {
		model.addAttribute("user", request.getSession().getAttribute("user"));
		return "index2";
	}
}
