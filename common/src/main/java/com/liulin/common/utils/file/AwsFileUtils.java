package com.liulin.common.utils.file;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.liulin.common.config.LiulinConfig;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: LinLiu
 * @Date: 2021/9/28 9:09 上午
 */
public class AwsFileUtils {

    private final static Regions clientRegion = Regions.AP_SOUTHEAST_2;

    private final static String URL_PREFIX=
            "https://"+ LiulinConfig.getBucketName() +".s3."+Regions.AP_SOUTHEAST_2.getName()+
            ".amazonaws.com/";


    public static void putObject(String keyName,File file){
        System.out.println(LiulinConfig.getBucketName()+","+LiulinConfig.getAwsKeyId()+","+LiulinConfig.getAwsSecretId());
        System.out.println(LiulinConfig.getName()+","+LiulinConfig.getProfile());
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
