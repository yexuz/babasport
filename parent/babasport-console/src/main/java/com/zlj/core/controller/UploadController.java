package com.zlj.core.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.zlj.common.web.Constants;
import com.zlj.core.service.product.UploadService;

@Controller
public class UploadController {

	@Autowired
	private UploadService uploadService;

	@RequestMapping(value = "/upload/uploadPic.do")
	public void uploadPic(@RequestParam(required = false) MultipartFile pic, HttpServletResponse response)
			throws IOException {
		String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
		String url = Constants.IMG_URL + path;
		JSONObject jo = new JSONObject();
		jo.put("url", url);

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jo.toString());
	}

	@RequestMapping(value = "/upload/uploadPics.do")
	@ResponseBody
	public List<String> uploadPics(@RequestParam(required = false) MultipartFile[] pics, HttpServletResponse response)
			throws IOException {
		List<String> urls = new ArrayList<String>();
		for (MultipartFile pic : pics) {
			String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
			String url = Constants.IMG_URL + path;
			urls.add(url);
		}
		System.out.println(urls);
		return urls;
	}

	// 富文本图片上传
	@RequestMapping(value = "/upload/uploadFck.do")
	public void uploadFck(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 无敌版接收
		// 强转Spring 提供 MultipartRequest
		MultipartRequest mr = (MultipartRequest) request;
		Map<String, MultipartFile> fileMap = mr.getFileMap();
		Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
		for (Entry<String, MultipartFile> entry : entrySet) {
			MultipartFile pic = entry.getValue();

			String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
			String url = Constants.IMG_URL + path;
			JSONObject jo = new JSONObject();
			jo.put("error", 0);
			jo.put("url", url);

			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(jo.toString());
		}
	}

	@RequestMapping(value = "/upload/uploadPic2.do")
	@ResponseBody
	public String uploadPic2(@RequestParam(required = false) MultipartFile pic) throws IOException {
		String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
		String url = Constants.IMG_URL + path;
		String ss = "{\"url\":\"" + url + "\"}";
		Map<String, String> result = new HashMap<>();
		result.put("url", url);
		System.out.println(ss);
		return ss;
	}

	@RequestMapping(value = "/upload/uploadPic3.do")
	public void uploadPic3(@RequestParam(required = false) MultipartFile pic, HttpServletResponse response)
			throws IOException {
		String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
		String url = Constants.IMG_URL + path;
		response.setContentType("application/json;charset=UTF-8");
		String ss = "{\"url\":\"" + url + "\"}";
		response.getWriter().write(ss);
	}
}
