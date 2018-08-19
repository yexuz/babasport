package com.zlj.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zlj.core.bean.product.Product;
import com.zlj.core.bean.product.ProductQuery;
import com.zlj.core.bean.product.Sku;
import com.zlj.core.bean.product.SkuQuery;
import com.zlj.core.dao.product.ProductDao;
import com.zlj.core.dao.product.SkuDao;

import cn.itcast.common.page.Pagination;

/**
 * 全文检索 Solr
 * 
 * @author zhengliangjun
 *
 */
@Service("searchService")
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SolrServer solrServer;

	// 全文搜索
	@Override
	public Pagination selectProductListByQuery(String keyword, Integer pageNo, Long brandId, String price)
			throws Exception {

		List<Product> products = new ArrayList<>();
		ProductQuery productQuery = new ProductQuery();
		// 当前页
		productQuery.setPageNo(Pagination.cpn(pageNo));
		// 每页显示 12条
		productQuery.setPageSize(15);

		// 拼接条件
		StringBuilder params = new StringBuilder();

		SolrQuery solrQuery = new SolrQuery();
		// 关键词
		solrQuery.set("q", "name_ik:" + keyword);
		params.append("keyword=").append(keyword);
		// 过滤条件
		if (null != brandId) {
			solrQuery.addFilterQuery("brandId:" + brandId);
		}
		if (null != price) {
			String[] p = price.split("-");
			if (p.length == 2) {
				solrQuery.addFilterQuery("price:[" + p[0] + " TO " + p[1] + "]");
			} else {
				;
				solrQuery.addFilterQuery("price:[" + p[0] + " TO *]");
			}
		}
		// 高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("name_ik");
		// 样式
		solrQuery.setHighlightSimplePre("<span style='color:red'>");
		solrQuery.setHighlightSimplePost("</span>");
		// 排序
		solrQuery.addSort("price", ORDER.asc);
		// 分页 limit 开始行，每页显示条数
		solrQuery.setStart(productQuery.getStartRow());
		solrQuery.setRows(productQuery.getPageSize());

		// 执行查询
		QueryResponse response = solrServer.query(solrQuery);

		// 取高亮
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

		// 结果集
		SolrDocumentList docs = response.getResults();
		// 发现的条数(总条数)构建分页时用到
		long numFound = docs.getNumFound();
		for (SolrDocument doc : docs) {
			// 创建商品对象
			Product product = new Product();

			// 商品id
			String id = (String) doc.get("id");
			product.setId(Long.parseLong(id));
			// 商品名称 ik
			// String name = (String) doc.get("name_ik");
			// product.setName(name);
			Map<String, List<String>> map = highlighting.get(id);
			List<String> list = map.get("name_ik");
			product.setName(list.get(0));
			// 商品图片
			String url = (String) doc.get("url");
			product.setImgUrl(url);
			// 价格
			product.setPrice((Float) doc.get("price"));
			// 品牌Id
			product.setBrandId(Long.parseLong(((Integer) doc.get("brandId")).toString()));

			products.add(product);
		}
		Pagination pagination = new Pagination(productQuery.getPageNo(), productQuery.getPageSize(), (int) numFound,
				products);

		String url = "/search";
		pagination.pageView(url, params.toString());
		return pagination;
	}

	@Autowired
	private ProductDao productDao;
	@Autowired
	private SkuDao skuDao;

	// 保存商品信息到Solr服务
	public void insertProductTOSolr(Long id) {
		// TODO 保存商品信息到Solr服务器
		SolrInputDocument doc = new SolrInputDocument();
		// 商品id
		doc.setField("id", id);
		// 商品名称
		Product p = productDao.selectByPrimaryKey(id);
		doc.setField("name_ik", p.getName());
		// 图片
		doc.setField("url", p.getImages()[0]);
		// 价格
		SkuQuery query = new SkuQuery();
		query.setFields("price");
		query.setOrderByClause("price asc");
		query.setPageNo(1);
		query.setPageSize(1);
		query.createCriteria().andProductIdEqualTo(p.getId());
		List<Sku> skus = skuDao.selectByExample(query);
		doc.setField("price", skus.get(0).getPrice());
		// 品牌id
		doc.setField("brandId", p.getBrandId());
		// 时间
		try {
			solrServer.add(doc);
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
