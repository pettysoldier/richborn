/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.yami.shop.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yami.shop.bean.model.AttachFile;
import com.yami.shop.dao.AttachFileMapper;
import com.yami.shop.service.AttachFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 *
 * Created by lgh on 2018/07/27.
 */
@Service
public class AttachFileServiceImpl extends ServiceImpl<AttachFileMapper, AttachFile> implements AttachFileService {

    @Autowired
    private AttachFileMapper attachFileMapper;

//    @Autowired
//    private UploadManager uploadManager;
//
//    @Autowired
//    private BucketManager bucketManager;
//	@Autowired
//	private Qiniu qiniu;
//
//    @Autowired
//    private Auth auth;

    public final static String NORM_MONTH_PATTERN = "yyyy-MM-";

	public static final String PIC_DICTIONARY = "/work/prd-images";
//	public static final String PIC_DICTIONARY = "/Users/zhushengguo/upload/";

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String uploadFile(byte[] bytes,String originalName) throws IOException {
//		String extName = FileUtil.extName(originalName);
//		String fileName =DateUtil.format(new Date(), NORM_MONTH_PATTERN)+ IdUtil.simpleUUID() + "." + extName;
//
//		AttachFile attachFile = new AttachFile();
//		attachFile.setFilePath(fileName);
//		attachFile.setFileSize(bytes.length);
//		attachFile.setFileType(extName);
//		attachFile.setUploadTime(new Date());
//		attachFileMapper.insert(attachFile);
//
//		String upToken = auth.uploadToken(qiniu.getBucket(),fileName);
//	    Response response = uploadManager.put(bytes, fileName, upToken);
//	    Json.parseObject(response.bodyString(),  DefaultPutRet.class);
//		return fileName;
		return null;
	}

	@Override
	public String uploadFileLocal(byte[] bytes, String originalName) throws IOException {
		String extName = FileUtil.extName(originalName);
		String fileName =DateUtil.format(new Date(), NORM_MONTH_PATTERN)+ IdUtil.simpleUUID() + "." + extName;
		AttachFile attachFile = new AttachFile();
		attachFile.setFilePath(fileName);
		attachFile.setFileSize(bytes.length);
		attachFile.setFileType(extName);
		attachFile.setUploadTime(new Date());
		attachFileMapper.insert(attachFile);
		File file = new File(PIC_DICTIONARY+fileName);
		if(file.exists()){
			file.delete();
		}
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(bytes);
		fileOutputStream.flush();
		fileOutputStream.close();
		return fileName;
	}

	@Override
	public void deleteFile(String fileName){
//		attachFileMapper.delete(new LambdaQueryWrapper<AttachFile>().eq(AttachFile::getFilePath,fileName));
//		try {
//			bucketManager.delete(qiniu.getBucket(), fileName);
//		} catch (QiniuException e) {
//			throw new RuntimeException(e);
//		}
	}


}
