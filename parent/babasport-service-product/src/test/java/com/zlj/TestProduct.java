package com.zlj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zlj.core.dao.product.ProductDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class TestProduct {

	@Autowired
	private ProductDao productDao;

	@Test
	public void testAdd() {

		System.out.println(productDao.selectByPrimaryKey(441L));
	}

}
