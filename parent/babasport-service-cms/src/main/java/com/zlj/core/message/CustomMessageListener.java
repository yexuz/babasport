package com.zlj.core.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.zlj.core.bean.product.Color;
import com.zlj.core.bean.product.Product;
import com.zlj.core.bean.product.Sku;
import com.zlj.core.service.CmsService;
import com.zlj.core.service.staticpage.StaticPageService;

public class CustomMessageListener implements MessageListener {

	@Autowired
	private CmsService cmsService;

	@Autowired
	private StaticPageService staticPageService;

	@Override
	public void onMessage(Message message) {
		ActiveMQTextMessage am = (ActiveMQTextMessage) message;
		try {
			System.out.println("ActiveMq中的消息是cms:" + am.getText());
			String id = am.getText();
			// 数据
			Map<String, Object> root = new HashMap<>();
			// 商品
			Product product = cmsService.selectProductById(Long.parseLong(id));

			List<Sku> skus = cmsService.selectSkuListByProductId(Long.parseLong(id));

			// 遍历一次 相同不要
			Set<Color> colors = new HashSet<>();
			for (Sku sku : skus) {
				colors.add(sku.getColor());
			}

			root.put("product", product);
			root.put("skus", skus);
			root.put("colors", colors);
			staticPageService.productStaticPage(root, id);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
