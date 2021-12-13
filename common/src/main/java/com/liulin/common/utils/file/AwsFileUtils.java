package com.liulin.common.utils.file;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.liulin.common.config.LiulinConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

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
}
