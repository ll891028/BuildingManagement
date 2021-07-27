package com.liulin.common.exception.user;

import com.liulin.common.exception.base.BaseException;

/**
 * 用户信息异常类
 * 
 * @author liulin
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
