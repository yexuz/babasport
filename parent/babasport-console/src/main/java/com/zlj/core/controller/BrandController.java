package com.zlj.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zlj.core.bean.product.Brand;
import com.zlj.core.service.product.BrandService;

import cn.itcast.common.page.Pagination;

/**
 * 品牌管理
 * 
 * @author zhengliangjun
 *
 */
@Controller
@RequestMapping(value = "/brand")
public class BrandController {

	@Autowired
	private BrandService brandService;

	@RequestMapping(value = "/list.do")
	public String list(String name, Integer isDisplay, Integer pageNo, Model model) {
		Pagination pagination = brandService.selectPagintionByQuery(name, isDisplay, pageNo);
		model.addAttribute("pagination", pagination);

		model.addAttribute("name", name);
		if (null != isDisplay) {
			model.addAttribute("isDisplay", isDisplay);
		} else {
			model.addAttribute("isDisplay", 1);
		}
		return "brand/list";
	}

	@RequestMapping(value = "/toEdit.do")
	public String toEdit(Long id, Model model) {
		Brand brand = brandService.selectBrandById(id);
		model.addAttribute("brand", brand);
		return "brand/edit";
	}

	@RequestMapping(value = "/edit.do")
	public String edit(Brand brand, Model model) {
		brandService.updateBrandById(brand);
		return "redirect:/brand/list.do";
	}

	@RequestMapping(value = "/deletes.do")
	public String deletes(Long[] ids) {
		brandService.deletes(ids);
		return "forward:/brand/list.do";
	}
}
