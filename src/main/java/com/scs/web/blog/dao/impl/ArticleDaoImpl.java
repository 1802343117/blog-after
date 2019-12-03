package com.scs.web.blog.dao.impl;


import com.scs.web.blog.dao.ArticleDao;
import com.scs.web.blog.domain.vo.ArticleVo;
import com.scs.web.blog.entity.Article;
import com.scs.web.blog.util.DataUtil;
import com.scs.web.blog.util.DbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhao
 * @className ArticleDaoImpl
 * @Description TODO
 * @Date 2019/11/10
 * @Version 1.0
 **/
public class ArticleDaoImpl implements ArticleDao {
    private static Logger logger = LoggerFactory.getLogger(ArticleDaoImpl.class);

    @Override
    public int[] batchInsert(List<Article> articleList) throws SQLException {
        Connection connection = DbUtil.getConnection();
        String sql = "INSERT INTO t_article (user_id,title,content,cover,diamond,comments,likes,publish_time) VALUES(?,?,?,?,?,?,?,?) ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        articleList.forEach(article -> {
            try {
                pstmt.setInt(1, DataUtil.getUserId());
                pstmt.setString(2, article.getTitle());
                pstmt.setString(3, article.getContent());
                pstmt.setString(4, article.getCover());
                pstmt.setInt(5, article.getDiamond());
                pstmt.setInt(6, article.getComments());
                pstmt.setInt(7, article.getLikes());
                pstmt.setObject(8, article.getPublishTime());
                pstmt.addBatch();
            } catch (SQLException e) {
                logger.error("批量导入文章信出错");
            }
        });
        int[] result = pstmt.executeBatch();
        connection.commit();
        DbUtil.close(null, pstmt, connection);
        return result;
    }


    @Override
    public List<ArticleVo> selectHotArticles() throws SQLException {
        List<ArticleVo> articleVoList = new ArrayList<>(20);
        Connection connection = DbUtil.getConnection();
        //在文章表和用户表联查，得到结视图对象
        String sql = "SELECT a.id,a.user_id,a.title,a.content,a.diamond,a.comments,a.likes,b.id,b.nickname,b.avatar\n" +
                "FROM t_article a\n" +
                "LEFT JOIN t_user b\n" +
                "ON a.user_id = b.id\n" +
                "ORDER BY a.comments DESC LIMIT 20";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            ArticleVo article = new ArticleVo();
            article.setId(rs.getLong("id"));
            article.setUserId(rs.getLong("user_id"));
            article.setTitle(rs.getString("title"));
            article.setContent(rs.getString("content"));
            article.setDiamond(rs.getString("diamond"));
            article.setNickname(rs.getString("nickname"));
            article.setAvatar(rs.getString("avatar"));
            article.setLikes(rs.getInt("likes"));
            article.setComments(rs.getInt("comments"));
            articleVoList.add(article);
        }
        return articleVoList;
    }


}