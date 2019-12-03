package com.scs.web.blog.service;


import com.scs.web.blog.util.Result;

/**
 * @author zhao
 * @className TopicService
 * @Description TODO
 * @Date 2019/11/17
 * @Version 1.0
 **/
public interface TopicService {
    /**
     * 获取热门专题
     * @param id
     * @return
     */
    Result getHotTopics();

}