package com.zlj.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.zlj.core.bean.product.Sku;
import com.zlj.core.service.product.SkuService;

@Controller("skuController")
@RequestMapping(value = "/sku")
public class SkuController {

	@Autowired
	private SkuService skuService;

	@RequestMapping(value = "/list.do")
	public String list(Long id, Model model) {
		List<Sku> skus = skuService.selectSkuListByProductId(id);
		model.addAttribute("skus", skus);
		return "sku/list";
	}

	@RequestMapping(value = "/addSku.do")
	@ResponseBody
	public String addSku(Sku sku) {
		int cnt = skuService.updateSkuById(sku);
		JSONObject jo = new JSONObject();
		if (cnt > 0) {
			jo.put("message", "保存成功");
			return jo.toString();
		}
		jo.put("message", "保存失败");
		return jo.toJSONString();
	}

}
