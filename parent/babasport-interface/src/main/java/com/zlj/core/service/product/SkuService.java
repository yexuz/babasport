package com.zlj.core.service.product;

import java.util.List;

import com.zlj.core.bean.product.Sku;

public interface SkuService {

	public List<Sku> selectSkuListByProductId(Long productId);

	public int updateSkuById(Sku sku);
}
