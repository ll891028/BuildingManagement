package com.liulin.framework.shiro.service;

import com.liulin.common.core.domain.entity.SysDept;
import com.liulin.common.core.redis.RedisCache;
import com.liulin.common.utils.*;
import com.liulin.system.domain.SysUserDept;
import com.liulin.system.service.ISysDeptService;
import com.liulin.system.service.ISysUserDeptService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.liulin.common.constant.Constants;
import com.liulin.common.constant.ShiroConstants;
import com.liulin.common.constant.UserConstants;
import com.liulin.common.core.domain.entity.SysUser;
import com.liulin.common.enums.UserStatus;
import com.liulin.common.exception.user.CaptchaException;
import com.liulin.common.exception.user.UserBlockedException;
import com.liulin.common.exception.user.UserDeleteException;
import com.liulin.common.exception.user.UserNotExistsException;
import com.liulin.common.exception.user.UserPasswordNotMatchException;
import com.liulin.framework.manager.AsyncManager;
import com.liulin.framework.manager.factory.AsyncFactory;
import com.liulin.system.service.ISysUserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 登录校验方法
 * 
 * @author liulin
 */
@Component
public class SysLoginService
{
    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private ISysUserDeptService sysUserDeptService;

    /**
     * 登录
     */
    public SysUser login(String username, String password)
    {
        // 验证码校验
        if (ShiroConstants.CAPTCHA_ERROR.equals(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA)))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }

        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }

        // 查询用户信息
        SysUser user = userService.selectUserByLoginName(username);
        List<SysUserDept> sysUserDepts = sysUserDeptService.selectSysUserDeptByUserId(user.getUserId());
        //查询用户buildings信息
        SysDept query = new SysDept();
        query.setTypeList(new ArrayList<>(Arrays.asList(2L,3L,4L,5L)));
        List<SysDept> buildings = null;
        if(user.isAdmin()) {
            buildings = sysDeptService.selectDeptList(query);
        } else{
            String[] deptIds = new String[sysUserDepts.size()];
            for (int i = 0; i < sysUserDepts.size(); i++) {

                deptIds[i] =String.valueOf(sysUserDepts.get(i).getDeptId());
            }
            buildings = sysDeptService.selectDeptByIds(deptIds);

        }

        user.setBuildingsList(buildings);
        SysDept building = redisCache.getCacheObject("user:building:"+user.getUserId());
        if(building==null){
            if(CollectionUtils.isNotEmpty(buildings)){
                user.setBuilding(buildings.get(0));
                redisCache.setCacheObject("user:building:"+user.getUserId(),user.getBuilding());
            }

        }else{
            //查询是否该building被系统移除
            SysDept sysDept = buildings.stream().filter(b -> b.getDeptId().equals(building.getDeptId())).findAny().orElse(null);
            if(sysDept==null){
                //已被移除
                user.setBuilding(buildings.get(0));
                redisCache.setCacheObject("user:building:"+user.getUserId(),user.getBuilding());
//                CacheUtils.put("user:building:"+user.getUserId(),user.getBuilding());
            }else{
                building.getDeptId();
                Optional<SysDept> first = buildings.stream().filter(b -> b.getDeptId() .equals(building.getDeptId()) ).findFirst();
                user.setBuilding(first.get());
            }
        }
        if(building!=null){
            SysDept company = sysDeptService.selectCompanyByBuildingId(building.getParentId());
            user.setCompany(company);
        }else{
            if(user.isDirector()){

                SysDept company = sysDeptService.selectDeptById(user.getDeptId());
                user.setCompany(company);
            }
        }

        /**
        if (user == null && maybeMobilePhoneNumber(username))
        {
            user = userService.selectUserByPhoneNumber(username);
        }

        if (user == null && maybeEmail(username))
        {
            user = userService.selectUserByEmail(username);
        }
        */

        if (user == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
            throw new UserNotExistsException();
        }
        
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.delete")));
            throw new UserDeleteException();
        }
        
        if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.blocked", user.getRemark())));
            throw new UserBlockedException();
        }

        passwordService.validate(user, password);

        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        recordLoginInfo(user);
        return user;
    }

    /**
    private boolean maybeEmail(String username)
    {
        if (!username.matches(UserConstants.EMAIL_PATTERN))
        {
            return false;
        }
        return true;
    }

    private boolean maybeMobilePhoneNumber(String username)
    {
        if (!username.matches(UserConstants.MOBILE_PHONE_NUMBER_PATTERN))
        {
            return false;
        }
        return true;
    }
    */

    /**
     * 记录登录信息
     */
    public void recordLoginInfo(SysUser user)
    {
        user.setLoginIp(ShiroUtils.getIp());
        user.setLoginDate(DateUtils.getNowDate());
        userService.updateUserInfo(user);
    }

    public static void main(String[] args) {
        SysUser user = new SysUser();
        SysDept sysDept = new SysDept();
        sysDept.setDeptId(20L);
        user.setBuilding(sysDept);
        Optional.ofNullable(user).map(u -> u.getBuilding()).map(b->b.getDeptId()).ifPresent(deptId->
                        System.out.println(deptId)
                );
    }
}
