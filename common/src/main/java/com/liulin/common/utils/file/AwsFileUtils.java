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

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: LinLiu
 * @Date: 2021/9/28 9:09 上午
 */
public class AwsFileUtils {

    private final static Regions clientRegion = Regions.AP_SOUTHEAST_1;
    private final static String BUCKET_NAME = "gtech-bucket";
    private final static String ACCESS_KEY_ID="AKIAQR2GE2AFBQ67A7XZ";
    private final static String SECRET_KEY_ID="nHOycAF6eu7r2ik9huwhpr0Aoj/7BoGR4klKylE9";
    private final static String URL_PREFIX="https://"+BUCKET_NAME+".s3."+Regions.AP_SOUTHEAST_1.getName()+".amazonaws.com/";

    public static void main(String[] args) throws IOException {
        Regions clientRegion = Regions.AP_SOUTHEAST_2;
//        String bucketName = "gtech-bucket";
        String bucketName = "gtech-bucket-sy";
        String fileObjKeyName = "attachments/report.xlsx";
        String fileName = "/Users/liulin/upload/report.xlsx";
        String ACCESS_KEY_ID="AKIAQR2GE2AFBQ67A7XZ";
        String SECRET_KEY_ID="nHOycAF6eu7r2ik9huwhpr0Aoj/7BoGR4klKylE9";

        try {
            BasicAWSCredentials awsCreds = new BasicAWSCredentials(ACCESS_KEY_ID, SECRET_KEY_ID);
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .withRegion(clientRegion)
                    .build();

            // Upload a text string as a new object.
//            s3Client.putObject(bucketName, stringObjKeyName, "Uploaded String Object");

            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType("plain/text");
//            metadata.addUserMetadata("title", "someTitle");
//            request.setMetadata(metadata);
            s3Client.putObject(request);
//            boolean b = s3Client.doesBucketExistV2(bucketName);
//            System.out.println("是否存在："+b);
            //枚举bucket
//            List<Bucket> buckets = s3Client.listBuckets();
//            for (Bucket bucket : buckets) {
//                System.out.println("Bucket: " + bucket.getName());
//            }
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }

    public static void putObject(String keyName,File file){
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(ACCESS_KEY_ID, SECRET_KEY_ID);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(clientRegion)
                .build();
        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, keyName, file);
        s3Client.putObject(request);
    }

    public static String getUrl(String keyName){
        return URL_PREFIX+keyName;
    }
}
