package com.zlj.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlj.core.bean.product.Product;
import com.zlj.core.bean.product.Sku;
import com.zlj.core.bean.product.SkuQuery;
import com.zlj.core.dao.product.ColorDao;
import com.zlj.core.dao.product.ProductDao;
import com.zlj.core.dao.product.SkuDao;

/**
 * 评论 晒单 广告 静态化
 * 
 * @author zhengliangjun
 *
 */
@Service("cmsService")
public class CmsServiceImpl implements CmsService {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private SkuDao skuDao;

	@Autowired
	private ColorDao colorDao;

	// 查询商品
	@Override
	public Product selectProductById(Long id) {
		return productDao.selectByPrimaryKey(id);
	}

	// 查询Sku结果集（包含颜色）查询有货的 库存大于0
	@Override
	public List<Sku> selectSkuListByProductId(Long productId) {
		SkuQuery query = new SkuQuery();
		query.createCriteria().andProductIdEqualTo(productId).andStockGreaterThan(0);
		List<Sku> skus = skuDao.selectByExample(query);
		for (Sku sku : skus) {
			sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));
		}
		return skus;
	}
}
