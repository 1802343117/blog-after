package com.scs.web.blog.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scs.web.blog.domain.dto.UserDto;
import com.scs.web.blog.factory.ServiceFactory;
import com.scs.web.blog.service.UserService;
import com.scs.web.blog.util.Message;
import com.scs.web.blog.util.ResponseObject;
import com.scs.web.blog.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author zhao
 * @className UserController
 * @Description TODO
 * @Date 2019/11/9
 * @Version 1.0
 **/
@WebServlet(urlPatterns = {"/api/sign-in","/api/register","/api/hot"})
public class UserController extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService = ServiceFactory.getUserServiceInstance();


    /**
     * 根据请求地址，取得最后的内容，结合请求方法来决定分发到不同的方法
     *
     * @param uri
     * @return
     */
    private String getPatten(String uri) {
        int len = "/api".length();
        return uri.substring(len);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //去掉了地址共同的前缀，对不同的部分进行分发
        String patten = getPatten(req.getRequestURI());
        switch (patten) {
            case "/hot":
                getHotUser(req, resp);
                break;
//            case "/list?page=*":
//                getPageUser(req, resp);
//                break;
//            case "/*":
//                getUser(req, resp);
//                break;
        }
    }

    private void getHotUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson gson = new GsonBuilder().create();
        Result result = userService.getHotUsers();
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(result));
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        logger.info("登录用户信息：" + stringBuilder.toString());
        Gson gson = new GsonBuilder().create();
        UserDto userDto = gson.fromJson(stringBuilder.toString(), UserDto.class);
//        Map<String, Object> map = userService.signIn(userDto);
//        String msg = (String) map.get("msg");
//        ResponseObject ro;
//        if (msg.equals(Message.SIGN_IN_SUCCESS)) {
//            ro = ResponseObject.success(200, msg, map.get("data"));
//        } else {
//            ro = ResponseObject.success(200, msg);
//        }


        Map<String, Object> map = null;
        // 获取请求路径
        String requestPath = req.getRequestURI().trim();
        System.out.println(requestPath);
        switch (requestPath) {
            case "/api/sign-in":
                System.out.println("进入登录");
                map = userService.signIn(userDto);
                break;
            case "/api/register":
                System.out.println("进入注册");
                map = userService.register(userDto);
                break;
        }
        String msg = (String) map.get("msg");
        ResponseObject ro;
        switch (msg) {
            case Message.SIGN_IN_SUCCESS:
            case Message.REGISTER_SUCCESS:
                ro = ResponseObject.success(200, msg, map.get("data"));
                break;
            case Message.PASSWORD_ERROR:
            case Message.MOBILE_NOT_FOUND:
            case Message.REGISTER_DEFEATED:
            default:
                ro = ResponseObject.success(200, msg);
        }




        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(ro));
        out.close();
    }
    @Override
    public void init() throws ServletException {
        logger.info("UserController初始化");
    }
}