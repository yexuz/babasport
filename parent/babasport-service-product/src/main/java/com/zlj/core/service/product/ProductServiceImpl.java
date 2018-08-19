package com.zlj.core.service.product;

import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlj.core.bean.product.Color;
import com.zlj.core.bean.product.ColorQuery;
import com.zlj.core.bean.product.Product;
import com.zlj.core.bean.product.ProductQuery;
import com.zlj.core.bean.product.ProductQuery.Criteria;
import com.zlj.core.bean.product.Sku;
import com.zlj.core.dao.product.ColorDao;
import com.zlj.core.dao.product.ProductDao;
import com.zlj.core.dao.product.SkuDao;

import cn.itcast.common.page.Pagination;
import redis.clients.jedis.Jedis;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ColorDao colorDao;
	@Autowired
	private SkuDao skuDao;
	@Autowired
	private Jedis jedis;
	@Autowired
	private SolrServer solrServer;
	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
	public Pagination selectPaginationByQuery(Integer pageNo, String name, Long brandId, Boolean isShow) {
		ProductQuery productQuery = new ProductQuery();
		productQuery.setPageNo(Pagination.cpn(pageNo));
		Criteria createCriteria = productQuery.createCriteria();
		productQuery.setOrderByClause("id desc");
		StringBuilder params = new StringBuilder();
		if (null != name) {
			createCriteria.andNameLike("%" + name + "%");
			params.append("name=").append(name);
		}
		if (null != brandId) {
			createCriteria.andBrandIdEqualTo(brandId);
			params.append("&brandId=").append(brandId);
		}
		if (null != isShow) {
			createCriteria.andIsShowEqualTo(isShow);
			params.append("&isShow=").append(isShow);
		} else {
			createCriteria.andIsShowEqualTo(false);
			params.append("&isShow=").append(false);
		}
		Pagination pagination = new Pagination(productQuery.getPageNo(), productQuery.getPageSize(),
				productDao.countByExample(productQuery), productDao.selectByExample(productQuery));

		pagination.pageView("/product/list.do", params.toString());
		return pagination;
	}

	// 加载颜色
	@Override
	public List<Color> selectColorList() {
		ColorQuery query = new ColorQuery();
		query.createCriteria().andParentIdNotEqualTo(0L);
		return colorDao.selectByExample(query);
	}

	// 保存商品
	@Override
	public void insertProduct(Product product) {
		Long id = jedis.incr("pno");
		product.setId(id);
		// 下架状态
		product.setIsShow(false);
		// 删除状态
		product.setIsDel(true);
		productDao.insertSelective(product);
		// 返回ID
		// 保存SKU
		String[] colors = product.getColors().split(",");
		String[] sizes = product.getSizes().split(",");
		// 颜色
		for (String color : colors) {
			for (String size : sizes) {
				// 保存SKU
				Sku sku = new Sku();
				// 商品ID
				sku.setProductId(product.getId());
				// 颜色ID
				sku.setColorId(Long.parseLong(color));
				// 尺码
				sku.setSize(size);
				// 市场价格
				sku.setMarketPrice(999f);
				// 售价
				sku.setPrice(666f);
				// 运费 默认10元
				sku.setDeliveFee(10f);
				// 库存
				sku.setStock(0);
				// 购买限制
				sku.setUpperLimit(200);
				sku.setCreateTime(new Date());

				skuDao.insertSelective(sku);
			}
		}
	}

	@Override
	public int isShow(Long[] ids) {
		Product product = new Product();
		product.setIsShow(true);
		int cnt = 0;
		for (final Long id : ids) {
			product.setId(id);
			// 商品状态改变
			cnt = cnt + productDao.updateByPrimaryKeySelective(product);

			// 发送消息到ActiveMQ中
			// jmsTemplate.send("brandId", messageCreator);
			jmsTemplate.send(new MessageCreator() {

				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(id.toString());
				}
			});
			// 静态化
		}
		return cnt;
	}
}
