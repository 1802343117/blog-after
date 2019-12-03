package com.scs.web.blog.service;

import com.scs.web.blog.domain.vo.ArticleVo;
import com.scs.web.blog.entity.Article;

import java.util.List;

/**
 * @author zhao
 * @className ArticleService
 * @Description TODO
 * @Date 2019/11/15
 * @Version 1.0
 **/
public interface ArticleService {
    /**
     * 获取热门文章
     * @return
     */
    List<ArticleVo> getHotArticles();
}