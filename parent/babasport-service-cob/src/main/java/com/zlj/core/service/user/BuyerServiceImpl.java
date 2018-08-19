package com.zlj.core.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlj.core.bean.user.Buyer;
import com.zlj.core.bean.user.BuyerQuery;
import com.zlj.core.dao.user.BuyerDao;

@Service("buyerService")
public class BuyerServiceImpl implements BuyerService {
	@Autowired
	private BuyerDao buyerDao;

	@Override
	public Buyer selectBuyerByUsername(String username) {
		BuyerQuery buyerQuery = new BuyerQuery();
		buyerQuery.createCriteria().andUsernameEqualTo(username);
		List<Buyer> buyers = buyerDao.selectByExample(buyerQuery);
		if (null != buyers && buyers.size() > 0) {
			return buyers.get(0);

		}
		return null;
	}

}
