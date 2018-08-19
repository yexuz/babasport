package com.zlj.core.service.product;

import java.util.List;

import com.zlj.core.bean.product.Color;
import com.zlj.core.bean.product.Product;

import cn.itcast.common.page.Pagination;

public interface ProductService {

	public Pagination selectPaginationByQuery(Integer pageNo, String name, Long brandId, Boolean isShow);

	public List<Color> selectColorList();

	public void insertProduct(Product product);

	public int isShow(Long[] ids);
}
