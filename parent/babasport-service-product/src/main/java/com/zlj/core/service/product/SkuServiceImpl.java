package com.zlj.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlj.core.bean.product.Color;
import com.zlj.core.bean.product.Sku;
import com.zlj.core.bean.product.SkuQuery;
import com.zlj.core.dao.product.ColorDao;
import com.zlj.core.dao.product.SkuDao;

@Service("skuService")
public class SkuServiceImpl implements SkuService {

	@Autowired
	private SkuDao skuDao;

	@Autowired
	private ColorDao colorDao;

	// 商品id 查询 库存结果集
	@Override
	public List<Sku> selectSkuListByProductId(Long productId) {
		SkuQuery skuQuery = new SkuQuery();
		skuQuery.createCriteria().andProductIdEqualTo(productId);
		List<Sku> skus = skuDao.selectByExample(skuQuery);
		for (Sku sku : skus) {
			Color color = colorDao.selectByPrimaryKey(sku.getColorId());
			sku.setColor(color);
		}
		return skus;
	}

	@Override
	public int updateSkuById(Sku sku) {
		return skuDao.updateByPrimaryKeySelective(sku);
	}

}
