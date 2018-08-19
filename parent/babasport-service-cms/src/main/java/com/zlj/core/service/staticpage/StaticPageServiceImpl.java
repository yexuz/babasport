package com.zlj.core.service.staticpage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 静态化
 * 
 * @author zhengliangjun
 *
 */
public class StaticPageServiceImpl implements StaticPageService, ServletContextAware {

	private Configuration conf;

	public void setFreeMarkerConfigurer(FreeMarkerConfig freeMarkerConfig) {
		this.conf = freeMarkerConfig.getConfiguration();
	}

	// 静态化 商品 AcitveMa
	@Override
	public void productStaticPage(Map<String, Object> root, String id) {
		// 输出路径
		String path = getPath("/html/product/" + id + ".html");
		File file = new File(path);
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		Writer out = null;

		try {
			// 读文件 UTF-8
			Template template = conf.getTemplate("product.html");
			// 输出 UTF-8
			out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			template.process(root, out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 静态化订单

	// 获取路径
	public String getPath(String name) {
		return servletContext.getRealPath(name);
	}

	private ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
