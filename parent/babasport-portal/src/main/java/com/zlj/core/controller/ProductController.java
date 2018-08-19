package com.zlj.core.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zlj.core.bean.product.Brand;
import com.zlj.core.bean.product.Color;
import com.zlj.core.bean.product.Product;
import com.zlj.core.bean.product.Sku;
import com.zlj.core.service.CmsService;
import com.zlj.core.service.SearchService;
import com.zlj.core.service.product.BrandService;

import cn.itcast.common.page.Pagination;

@Controller
public class ProductController {

	@Autowired
	private SearchService searchService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private CmsService cmsService;

	// 首页入口
	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("search")
	public String search(String keyword, Integer pageNo, Long brandId, String price, Model model) throws Exception {
		keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
		Pagination pagination = searchService.selectProductListByQuery(keyword, pageNo, brandId, price);
		model.addAttribute("pagination", pagination);
		model.addAttribute("keyword", keyword);
		model.addAttribute("brandId", brandId);
		model.addAttribute("price", price);
		// 查询品牌结果集
		List<Brand> brands = brandService.selectBrandListFromRedis();
		model.addAttribute("brands", brands);
		Map<String, String> map = new HashMap<String, String>();
		if (null != brandId) {
			for (Brand brand : brands) {
				if (brandId == brand.getId()) {
					map.put("品牌", brand.getName());
					break;
				}
			}
		}
		if (StringUtils.isNotBlank(price)) {
			if (price.contains("-")) {
				map.put("价格", price);
			} else {
				map.put("价格", price + "以上");
			}
		}
		model.addAttribute("map", map);

		return "search";
	}

	@RequestMapping(value = "/product/detail")
	public String detail(Long id, Model model) {
		// 商品
		Product product = cmsService.selectProductById(id);

		List<Sku> skus = cmsService.selectSkuListByProductId(id);

		// 遍历一次 相同不要
		Set<Color> colors = new HashSet<>();
		for (Sku sku : skus) {
			colors.add(sku.getColor());
		}
		model.addAttribute("product", product);
		model.addAttribute("skus", skus);
		model.addAttribute("colors", colors);
		return "product";
	}

}