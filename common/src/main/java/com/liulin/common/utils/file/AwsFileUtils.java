package com.liulin.common.utils.file;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.liulin.common.config.LiulinConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author: LinLiu
 * @Date: 2021/9/28 9:09 上午
 */
public class AwsFileUtils {

    private static final Logger log = LoggerFactory.getLogger(AwsFileUtils.class);

    private final static Regions clientRegion = Regions.AP_SOUTHEAST_2;

    private final static String URL_PREFIX=
            "https://"+ LiulinConfig.getBucketName() +".s3."+Regions.AP_SOUTHEAST_2.getName()+
            ".amazonaws.com/";


    public static void putObject(String keyName,File file){
//        log.info(LiulinConfig.getBucketName()+","+LiulinConfig.getAwsKeyId()+","+LiulinConfig.getAwsSecretId());
        log.info(keyName);
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(LiulinConfig.getAwsKeyId(), LiulinConfig.getAwsSecretId());
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(clientRegion)
                .build();
        PutObjectRequest request = new PutObjectRequest(LiulinConfig.getBucketName(), keyName, file);
        s3Client.putObject(request);
    }

    public static String getUrl(String keyName){
        return URL_PREFIX+keyName;
    }

    public static String getKey(String webUrl){
        URL url = null;
        try {
            url = new URL(webUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String key = url.getPath().substring(1);
        return key;
    }

    public static void amazonS3Downloading(String key,String targetFilePath){
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(LiulinConfig.getAwsKeyId(), LiulinConfig.getAwsSecretId());
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(clientRegion)
                .build();
        S3Object object = s3Client.getObject(new GetObjectRequest(LiulinConfig.getBucketName(),key));
        if(object!=null){
            log.info("Content-Type: " + object.getObjectMetadata().getContentType());
            InputStream input = null;
            FileOutputStream fileOutputStream = null;
            byte[] data = null;
            try {
                //获取文件流
                input=object.getObjectContent();
                data = new byte[input.available()];
                int len = 0;
                fileOutputStream = new FileOutputStream(targetFilePath);
                while ((len = input.read(data)) != -1) {
                    fileOutputStream.write(data, 0, len);
                }
                System.out.println("下载文件成功");
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(fileOutputStream!=null){
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(input!=null){
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static String amazonS3DownloadingByUrl(String url,String targetFilePathPrefix){
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(LiulinConfig.getAwsKeyId(), LiulinConfig.getAwsSecretId());
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(clientRegion)
                .build();
        S3Object object = s3Client.getObject(new GetObjectRequest(LiulinConfig.getBucketName(),getKey(url)));
        if(object!=null){
            log.info("Content-Type: " + object.getObjectMetadata().getContentType());
            InputStream input = null;
            FileOutputStream fileOutputStream = null;
            byte[] data = null;
            try {
                //获取文件流
                input=object.getObjectContent();
                data = new byte[input.available()];
                int len = 0;
                File file = new File(targetFilePathPrefix+getKey(url));
                if(file.getParentFile().exists()){
                    file.getParentFile().mkdirs();
                }
                fileOutputStream = new FileOutputStream(targetFilePathPrefix+getKey(url));
                while ((len = input.read(data)) != -1) {
                    fileOutputStream.write(data, 0, len);
                }
                System.out.println("下载文件成功");
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(fileOutputStream!=null){
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(input!=null){
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return targetFilePathPrefix+getKey(url);
    }



}
