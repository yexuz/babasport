package com.zlj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zlj.core.bean.product.BrandQuery;
import com.zlj.core.dao.product.BrandDao;
import com.zlj.core.service.TestTbService;
import com.zlj.core.service.product.BrandService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class TestTbTest {

	@Autowired
	private TestTbService testTbService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private BrandDao brandDao;

	@Test
	public void testAdd() {

		BrandQuery brandQuery = new BrandQuery();
		brandQuery.setPageNo(2);
		brandQuery.setIsDisplay(1);
		brandQuery.setPageSize(3);

		Integer selectCount = brandDao.selectCount(brandQuery);
		System.out.println("sadasd" + selectCount);
	}

}
