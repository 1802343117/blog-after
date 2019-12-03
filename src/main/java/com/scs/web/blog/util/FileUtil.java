package com.scs.web.blog.util;

import java.io.File;
import java.time.LocalDate;

/**
 * @author zhao
 * @className FileUtil
 * @Description TODO
 * @Date 2019/11/21
 * @Version 1.0
 **/
public class FileUtil {
    public static String createFile(String path) {
        LocalDate date = LocalDate.now();
        File file = null;
        try {
            file = new File(path + date.toString());
            if (!file.exists()) {
                file.mkdirs();
                return date.toString()+ "/";
            } else {
                return date.toString()+ "/";
            }
        } catch (Exception e) {
            System.out.println("异常");
        }finally {
            file = null;
        }
        return null;
    }

    public static void rename(String path1, String path2) {
        if (path1.indexOf(".") >= 0) {
//            读取文件后缀名
            String pathSuffix = path1.substring(path1.lastIndexOf(".") + 1);
            new File(path1).renameTo(new File(path2 + "." + pathSuffix));
        } else {
            System.out.println("不存在");
        }

    }
}
