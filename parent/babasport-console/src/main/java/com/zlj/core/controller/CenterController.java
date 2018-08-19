package com.zlj.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 后台管理
 * @author lx
 *
 */
@Controller
@RequestMapping(value = "/control")
public class CenterController {


//	@Autowired
//	private TestTbService testTbService;
//	//入口
//	/**
//	 * ModelAndView  : 跳转视图+数据     不用
//	 * void  : 异步时 ajax    
//	 * String  : 跳转视图      +  Model
//	 */
//	@RequestMapping(value = "/test/index.do")
//	public String index(Model model){
//		TestTb testTb = new TestTb();
//		testTb.setName("jerry");
//		testTb.setBirthday(new Date());
//		testTbService.saveTestTb(testTb);
//		return "index";
//	}

	//入口
	@RequestMapping(value = "/index.do")
	public String index(Model model){
		return "index";
	}
	
	//头
	@RequestMapping(value = "/top.do")
	public String top(Model model){
		return "top";
	}
	
	//身体
	@RequestMapping(value = "/main.do")
	public String main(Model model){
		
		return "main";
	}
	
	//身体--左
	@RequestMapping(value = "/left.do")
	public String left(Model model){
		return "left";
	}
	
	//身体--右
	@RequestMapping(value = "/right.do")
	public String right(Model model){
		return "right";
	}
	
	//商品身体
	@RequestMapping(value = "/frame/product_main.do")
	public String product_main(Model model){
		return "frame/product_main";
	}
	
	//商品身体--左
	@RequestMapping(value = "/frame/product_left.do")
	public String product_left(Model model){
		return "frame/product_left";
	}
}
	
	