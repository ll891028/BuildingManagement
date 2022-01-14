package com.liulin.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 全局配置类
 * 
 * @author liulin
 */
@Component
@ConfigurationProperties(prefix = "liulin")
public class LiulinConfig
{
    /** 项目名称 */
    private static String name;

    /** 版本 */
    private static String version;

    /** 版权年份 */
    private static String copyrightYear;

    /** 实例演示开关 */
    private static boolean demoEnabled;

    /** 上传路径 */
    private static String profile;

    /** 获取地址开关 */
    private static boolean addressEnabled;

    private static String awsKeyId;

    private static String awsSecretId;

    private static String bucketName;

    private static String awsMailUserName;

    private static String awsMailPassword;

    public static String getAwsMailUserName() {
        return awsMailUserName;
    }

    public void setAwsMailUserName(String awsMailUserName) {
        LiulinConfig.awsMailUserName = awsMailUserName;
    }

    public static String getAwsMailPassword() {
        return awsMailPassword;
    }

    public void setAwsMailPassword(String awsMailPassword) {
        LiulinConfig.awsMailPassword = awsMailPassword;
    }

    public static String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        LiulinConfig.bucketName = bucketName;
    }

    public static String getAwsKeyId() {
        return awsKeyId;
    }

    public void setAwsKeyId(String awsKeyId) {
        LiulinConfig.awsKeyId = awsKeyId;
    }

    public static String getAwsSecretId() {
        return awsSecretId;
    }

    public void setAwsSecretId(String awsSecretId) {
        LiulinConfig.awsSecretId = awsSecretId;
    }

    public static String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        LiulinConfig.name = name;
    }

    public static String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        LiulinConfig.version = version;
    }

    public static String getCopyrightYear()
    {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear)
    {
        LiulinConfig.copyrightYear = copyrightYear;
    }

    public static boolean isDemoEnabled()
    {
        return demoEnabled;
    }

    public void setDemoEnabled(boolean demoEnabled)
    {
        LiulinConfig.demoEnabled = demoEnabled;
    }

    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        LiulinConfig.profile = profile;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        LiulinConfig.addressEnabled = addressEnabled;
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }
}
