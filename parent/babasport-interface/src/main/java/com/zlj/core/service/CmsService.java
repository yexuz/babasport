package com.zlj.core.service;

import java.util.List;

import com.zlj.core.bean.product.Product;
import com.zlj.core.bean.product.Sku;

public interface CmsService {

	public Product selectProductById(Long id);

	public List<Sku> selectSkuListByProductId(Long productId);
}
