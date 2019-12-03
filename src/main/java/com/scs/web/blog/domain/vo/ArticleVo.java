package com.scs.web.blog.domain.vo;

import lombok.Data;

/**
 * @author zhao
 * @className ArticleVo
 * @Description TODO
 * @Date 2019/11/15
 * @Version 1.0
 **/
@Data
public class ArticleVo {
    private Long id;
    private Long userId;
    private String nickname;
    private String avatar;
    private String title;
    private String content;
    private String diamond;
    private Integer likes;
    private Integer comments;
}