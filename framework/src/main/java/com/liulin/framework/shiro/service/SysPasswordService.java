package com.liulin.framework.shiro.service;

import java.util.concurrent.TimeUnit;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.liulin.common.constant.Constants;
import com.liulin.common.core.domain.entity.SysUser;
import com.liulin.common.core.redis.RedisCache;
import com.liulin.common.exception.user.UserPasswordNotMatchException;
import com.liulin.common.exception.user.UserPasswordRetryLimitExceedException;
import com.liulin.common.utils.MessageUtils;
import com.liulin.framework.manager.AsyncManager;
import com.liulin.framework.manager.factory.AsyncFactory;

/**
 * 登录密码方法
 * 
 * @author liulin
 */
@Component
public class SysPasswordService
{
    @Autowired
    private RedisCache redisCache;

    @Value(value = "${user.password.maxRetryCount}")
    private String maxRetryCount;

    /**
     * 登录记录 cache key
     */
    private final String SYS_LOGINRECORDCACHE_KEY = "sys_loginRecordCache:";

    /**
     * 设置cache key
     * 
     * @param loginName 登录名
     * @return 缓存键key
     */
    private String getCacheKey(String loginName)
    {
        return SYS_LOGINRECORDCACHE_KEY + loginName;
    }

    public void validate(SysUser user, String password)
    {
        String loginName = user.getLoginName();

        Integer retryCount = redisCache.getCacheObject(getCacheKey(loginName));

        if (retryCount == null)
        {
            retryCount = 0;
            redisCache.setCacheObject(getCacheKey(loginName), retryCount, 10, TimeUnit.MINUTES);
        }

        if (retryCount >= Integer.valueOf(maxRetryCount).intValue())
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount)));
            throw new UserPasswordRetryLimitExceedException(Integer.valueOf(maxRetryCount).intValue());
        }

        if (!matches(user, password))
        {
            retryCount = retryCount + 1;
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.count", retryCount)));
            redisCache.setCacheObject(getCacheKey(loginName), retryCount, 10, TimeUnit.MINUTES);
            throw new UserPasswordNotMatchException();
        }
        else
        {
            clearLoginRecordCache(loginName);
        }
    }

    public boolean matches(SysUser user, String newPassword)
    {
        return user.getPassword().equals(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
    }

    public void clearLoginRecordCache(String loginName)
    {
        redisCache.deleteObject(getCacheKey(loginName));
    }

    public String encryptPassword(String loginName, String password, String salt)
    {
        return new Md5Hash(loginName + password + salt).toHex();
    }
}
