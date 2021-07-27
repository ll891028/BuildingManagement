package com.liulin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author liulin
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class LiuLinApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(LiuLinApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  物业管理系统启动成功   ლ(´ڡ`ლ)ﾞ  \n");

    }
}