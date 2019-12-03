package com.scs.web.blog.service;


import com.scs.web.blog.domain.dto.UserDto;
import com.scs.web.blog.util.Result;

import java.util.Map;

/**
 * @author zhao
 * @className UserService
 * @Description TODO
 * @Date 2019/11/9
 * @Version 1.0
 **/
public interface UserService {
    /**
     * 用户登录功能
     * @param userDto
     * @return
     */
    Map<String,Object> signIn(UserDto userDto);

    /**
     * 注册新用户
     * @param userDto
     * @return
     */
    Map<String,Object> register(UserDto userDto);

    /**
     * 获取热门用户信息
     * @return
     */
    Result getHotUsers();
}