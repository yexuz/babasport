package com.zlj.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zlj.core.bean.product.Brand;
import com.zlj.core.bean.product.Color;
import com.zlj.core.bean.product.Product;
import com.zlj.core.service.product.BrandService;
import com.zlj.core.service.product.ProductService;

import cn.itcast.common.page.Pagination;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;

	@RequestMapping(value = "/list.do")
	public String list(Integer pageNo, String name, Long brandId, Boolean isShow, Model model) {
		List<Brand> brands = brandService.selectBrandListByQuery(1);
		model.addAttribute("brands", brands);

		Pagination pagination = productService.selectPaginationByQuery(pageNo, name, brandId, isShow);
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		model.addAttribute("brandId", brandId);
		if (null != isShow) {
			model.addAttribute("isShow", isShow);
		} else {
			model.addAttribute("isShow", false);
		}
		return "product/list";
	}

	@RequestMapping(value = "/toAdd.do")
	public String toAdd(Model model) {
		// 品牌结果集
		List<Brand> brands = brandService.selectBrandListByQuery(1);
		model.addAttribute("brands", brands);
		// 颜色结果集
		List<Color> colors = productService.selectColorList();
		model.addAttribute("colors", colors);
		return "product/add";
	}

	@RequestMapping(value = "/add.do")
	public String add(Product product, Model model) {
		productService.insertProduct(product);
		return "redirect:/product/list.do";
	}

	@RequestMapping(value = "/isShow.do")
	@ResponseBody
	public String isShow(Long[] ids) {
		int cnt = productService.isShow(ids);
		JSONObject jo = new JSONObject();
		if (cnt == ids.length) {
			jo.put("message", "上架成功");
			return jo.toString();
		}
		jo.put("message", "上架失败");
		return jo.toJSONString();
	}
}