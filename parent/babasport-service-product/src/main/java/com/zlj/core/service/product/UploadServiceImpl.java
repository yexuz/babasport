package com.zlj.core.service.product;

import org.springframework.stereotype.Service;

import com.zlj.common.fdfs.FastDFSUtils;

@Service("uploadService")
public class UploadServiceImpl implements UploadService {

	@Override
	public String uploadPic(byte[] pic, String name, long size) {
		return FastDFSUtils.uploadPic(pic, name, size);
	}
}
