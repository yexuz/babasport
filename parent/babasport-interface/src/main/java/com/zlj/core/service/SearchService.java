package com.zlj.core.service;

import cn.itcast.common.page.Pagination;

public interface SearchService {

	public Pagination selectProductListByQuery(String keyword, Integer pageNo, Long brandId, String price)
			throws Exception;

	public void insertProductTOSolr(Long id);
}
