package com.kwrobot;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CopyObjectResult;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

@Slf4j
public class OSSService {

	@Value("${alibaba.cloud.access-key}")
	private String accessKeyId;
	@Value("${alibaba.cloud.secret-key}")
	private String accessKeySecret;
	@Value("${alibaba.cloud.oss.endpoint}")
	private String endpoint;
	@Value("${alibaba.cloud.oss.bucketName}")
	private String bucketName;
	@Value("${alibaba.cloud.oss.cert.uploadPath}")
	private String uploadPath;
	@Value("${alibaba.cloud.oss.cert.saveLocalPath}")
	private String saveLocalPath;
	@Value("${alibaba.cloud.buildOssUrl}")
	private String buildOssUrl;

	/**
	 * oss上传
	 *
	 * @param file
	 * @return
	 */
	public String uploadFile(MultipartFile file) {
		if (file.isEmpty()) {
			return "upload failed.file isEmpty.";
		}
		String fileName = file.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		if (!"p12".equals(suffix)) {
			return "upload failed.file suffix type is un correct.";
		}
		InputStream inputStream = null;
		try {
			inputStream = file.getInputStream();
		} catch (IOException e) {
			log.error("e-----" + e);
		}
		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		// 上传文件流
		ObjectMetadata objectMetadata = new ObjectMetadata();
		if (inputStream != null) {
			try {
				objectMetadata.setContentLength(inputStream.available());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		objectMetadata.setCacheControl("no-cache");
		objectMetadata.setHeader("Pragma", "no-cache");
		objectMetadata.setContentType(uploadPath);
		objectMetadata.setContentDisposition("inline;filename=" + uploadPath);
		PutObjectResult putResult = ossClient.putObject(bucketName, uploadPath, inputStream, objectMetadata);
		// 关闭client
		ossClient.shutdown();
		return buildOssUrl + uploadPath;
	}

	/**
	 * oss复制（同bucket复制）
	 *
	 * @param orgFilePathName 源文件路径和名字
	 * @param desFilePathName 目标文件路径和名字
	 * @return
	 */
	public String copyFile(String orgFilePathName, String desFilePathName) {
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		CopyObjectResult objectResult = ossClient.copyObject(bucketName, orgFilePathName, bucketName, desFilePathName);
		ossClient.shutdown();
		return objectResult.getETag();
	}

	/**
	 * oss 下载
	 *
	 * @return
	 */
	public String downloadFile() {
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		String classPath = null;
		try {
			classPath = getClass().getResource("/").toURI().getPath();
			ObjectMetadata objRes = ossClient.getObject(new GetObjectRequest(bucketName, uploadPath),
					new File(classPath + saveLocalPath));
			ossClient.shutdown();
			return objRes.getETag();
		} catch (URISyntaxException e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * oss 判断文件是否存在
	 */
	public Boolean checkFileExists() {
		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		// 判断文件是否存在。如果返回值为true，则文件存在，否则存储空间或者文件不存在。
		boolean found = ossClient.doesObjectExist(bucketName, uploadPath);
		ossClient.shutdown();
		return found;
	}

}
