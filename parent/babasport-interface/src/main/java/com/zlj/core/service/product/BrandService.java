package com.zlj.core.service.product;

import java.util.List;

import com.zlj.core.bean.product.Brand;

import cn.itcast.common.page.Pagination;

public interface BrandService {
	public Pagination selectPagintionByQuery(String name, Integer isDisplay, Integer pageNo);

	public Brand selectBrandById(Long id);

	public void updateBrandById(Brand brand);

	public void deletes(Long[] ids);

	public List<Brand> selectBrandListByQuery(Integer isDisplayy);

	public List<Brand> selectBrandListFromRedis();
}
