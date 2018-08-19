package com.zlj.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlj.core.bean.TestTb;
import com.zlj.core.dao.TestTbDao;

@Service("testTbService")
@Transactional
public class TestTbServiceImpl implements TestTbService {

	@Autowired
	private TestTbDao testTbDao;

	@Override
	public void saveTestTb(TestTb testTb) {
		testTbDao.saveTestTb(testTb);
	}
}
