package com.zlj;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class SolrTest {

	@Autowired
	private SolrServer solrServce;

	@Test
	public void solrSpirngTest() throws Exception {
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", 4);
		doc.setField("name", "fbb4");
		solrServce.add(doc);
		solrServce.commit();
	}
	// @Test
	// public void solrTest() throws Exception {
	// String baseUlr = "http://192.168.200.128:8080/solr";
	// SolrServer solrServce = new HttpSolrServer(baseUlr);
	// SolrInputDocument doc = new SolrInputDocument();
	// doc.setField("id", 3);
	// doc.setField("name", "fbb");
	// solrServce.add(doc);
	// solrServce.commit();
	// }
}
