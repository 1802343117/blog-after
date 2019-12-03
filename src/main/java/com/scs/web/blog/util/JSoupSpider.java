package com.scs.web.blog.util;

import com.scs.web.blog.entity.Article;
import com.scs.web.blog.entity.Topic;
import com.scs.web.blog.entity.User;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author zhao
 * @className JSoupSpider
 * @Description TODO
 * @Date 2019/11/7
 * @Version 1.0
 **/
public class JSoupSpider {

    private static Logger logger = LoggerFactory.getLogger(JSoupSpider.class);

    public static List<User> getUsers() {
        Document document = null;
        List<User> userList = new ArrayList<>(100);
        for (int i = 1; i <= 3; i++) {
            try {
                document = Jsoup.connect("https://www.jianshu.com/recommendations/users?utm_source=desktop&utm_medium=index-users&page=" + i).get();
            } catch (IOException e) {
                logger.error("连接失败");
            }
            Elements divs = document.getElementsByClass("col-xs-8");
            divs.forEach(div -> {
                Element wrapDiv = div.child(0);
                Element link = wrapDiv.child(0);
                Elements linkChildren = link.children();
                User user = new User();
                user.setMobile(DataUtil.getMobile());
                user.setPassword(DataUtil.getPassword());
                user.setGender(DataUtil.getGender());
                user.setAvatar("https:" + linkChildren.get(0).attr("src"));
                user.setNickname(linkChildren.get(1).text());
                user.setIntroduction(linkChildren.get(2).text());
                user.setBirthday(DataUtil.getBirthday());
                user.setCreateTime(LocalDateTime.now());
                userList.add(user);
            });
        }
        return userList;
    }


    public static List<Article> getArticle() {
        Document document = null;
        List<Article> articleList = new ArrayList<>(100);
        int j = 0;
        for (int i = 0; i <= 180; ) {
            try {
                document = Jsoup.connect("https://book.douban.com/review/best/?start=" + i).get();
            } catch (IOException e) {
                logger.error("连接失败");
            }
            Elements mainList = document.getElementsByClass("main review-item");
            mainList.forEach(item -> {
                String cover = item.child(0).child(0).attr("src");
                String nickName = item.child(1).child(1).text();
                String publishTime = null;
                if (item.child(1).children().size() == 4) {
                    publishTime = item.child(1).child(3).text();
                } else {
                    publishTime = item.child(1).child(2).text();
                }

                String title = item.child(2).child(0).child(0).text();
                String content = item.child(2).child(1).text().substring(14);
                String likes = item.child(2).child(3).child(0).text();
                String co = item.child(2).child(3).child(2).text();
                String comments = co.substring(0, co.length() - 2);

                Article article = new Article();
                article.setTitle(title);
                article.setContent(content);
                article.setCover(cover);
                article.setDiamond(new Random().nextInt(100));

                article.setComments(Integer.valueOf(comments));
                article.setLikes(Integer.valueOf(likes));
                article.setPublishTime(Timestamp.valueOf(publishTime).toLocalDateTime());
                articleList.add(article);
            });
            j++;
            i = 2 * j * 10;
        }
        return articleList;
    }

    public static List<Topic> getTopics() {
        List<Topic> topicList = new ArrayList<>(100);
        Connection connection;
        Document document = null;
        for (int i = 1; i <= 3; i++) {
            try {
                //分析页面得到url和惨
                connection = (Connection) Jsoup.connect("https://www.jianshu.com/recommendations/collections?order_by=hot&page=" + i);
                //通过chrome开发者工具查看该请求必须添加请求头
                connection.header("X-PJAX", "true");
                document = connection.get();
            } catch (IOException e) {
                logger.error("连接失败");
            }
            assert document != null;
            Elements list = document.select(".collection-wrap");
            list.forEach(item -> {
                Elements elements = item.children();
                Topic topic = new Topic();
                Element link = elements.select("a").get(0);
                Element logo = link.child(0);
                Element name = link.child(1);
                Element description = link.child(2);
                Element articles = elements.select(".count > a").get(0);
                Element follows = elements.select(".count > a").get(0);

                topic.setAdminId(DataUtil.getUserrId());

                topic.setName(name.text());
                topic.setLogo(logo.attr("src"));
                topic.setDescription(description.text());
                String[] str = StringUtil.getDigital(articles.text());
                topic.setArticles(Integer.parseInt(str[0]));
                str = StringUtil.getDigital(follows.text());
                topic.setFollows(Integer.parseInt(str[0]));
                topic.setCreateTime(DataUtil.getCreateTime());
                topicList.add(topic);
            });

        }
        return topicList;
    }

}