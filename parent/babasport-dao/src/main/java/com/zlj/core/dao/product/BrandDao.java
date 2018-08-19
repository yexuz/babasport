package com.zlj.core.dao.product;

import java.util.List;

import com.zlj.core.bean.product.Brand;
import com.zlj.core.bean.product.BrandQuery;

/**
 * 查询
 * 
 * @author zhengliangjun
 *
 */
public interface BrandDao {

	// 查询结果集
	public List<Brand> selectBrandListByQuery(BrandQuery brandQuery);

	// 查询总条数
	public Integer selectCount(BrandQuery brandQuery);

	// 通过ID查询
	public Brand selectBrandById(Long id);

	// 修改
	public void updateBrandById(Brand brand);

	// 删除
	public void deletes(Long[] ids);
}
