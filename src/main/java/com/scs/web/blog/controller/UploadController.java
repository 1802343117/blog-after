package com.scs.web.blog.controller;
import com.scs.web.blog.util.FileUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.swing.*;
import java.io.IOException;
/**
 * @author zhao
 * @className UploadController
 * @Description TODO
 * @Date 2019/11/21
 * @Version 1.0
 **/
@MultipartConfig(maxFileSize = 1024 * 1024 *50)
@WebServlet(urlPatterns = "/api/upload")
public class UploadController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Part part = req.getPart("filename");
        int i = 0;
        for (Part part : req.getParts()) {
        String name = part.getSubmittedFileName();
        System.out.println("1:"+name);


        String path = req.getSession().getServletContext().getRealPath("");
        String secPath = path + FileUtil.createFile(path);
        System.out.println("2:"+path);
        System.out.println("3:"+secPath);

        System.out.println("4:"+secPath + name);

        System.out.println("00000000000000");

//            File file = new File("D:\\blog\\target\\blog" + name);
        //判断是否选择文件
        if (name == "") {
            JOptionPane.showMessageDialog(null, "请选择文件");
            req.getRequestDispatcher("/upload.jsp").forward(req, resp);
        }


//            //判断文件大小是否超限
//            System.out.println(file.isFile());
//            long maxFileSize = 1024 * 1024 * 50;
//            if (file.length() > maxFileSize) {
//                JOptionPane.showMessageDialog(null, "文件大小超过限制！应小于" + maxFileSize
//                        / 1024 / 1024 + "M");
//                part = null;
//                req.getRequestDispatcher("/upload.jsp").forward(req, resp);
//            }

        //在项目target目录下建立新的文件夹名为当前日期
        part.write(secPath + name);
        //将新文件改名

        System.out.println("00000000000000");

        FileUtil.rename(secPath + name, secPath + "UUID" + i++);

//            resp.setContentType("image/jpg");
        req.setAttribute("msg", "上传成功！ ");
        req.setAttribute("url", "http://localhost:8080/" + name);
        req.getRequestDispatcher("/upload.jsp").forward(req, resp);
        }
    }
}
