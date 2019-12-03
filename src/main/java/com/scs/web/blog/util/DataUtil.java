package com.scs.web.blog.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scs.web.blog.domain.dto.City;
import com.scs.web.blog.domain.dto.Province;
import com.scs.web.blog.domain.dto.Provinces;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * @author zhao
 * @className DataUtil
 * @Description 数据生成工具，用来生成用户的一些数据
 * @Date 2019/11/9
 * @Version 1.0
 **/
public class DataUtil {
    /**
     * 获得电话号码
     * @return
     */
    public static String getMobile(){
        StringBuilder stringBuilder = new StringBuilder("139");
        Random random = new Random();
        for (int i = 0;i<8; i++){
            int num = random.nextInt(10);
            stringBuilder.append(num);
        }
        return stringBuilder.toString();
    }

    public static String getPassword(){
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0;i<6; i++){
            int num = random.nextInt(10);
            password.append(num);
        }
        //调用common-codec的md5加密功能
        return DigestUtils.md5Hex(password.toString());
    }

    public static String getGender(){
        String[] genders = new String[]{"男","女"};
        Random random = new Random();
        int index = random.nextInt(2);
        return genders[index];
    }

    public  static LocalDate getBirthday(){
        LocalDate now = LocalDate.now();
        Random random = new Random();
        int bound = random.nextInt(8888);
        //当前日期的前bound天
        return now.minusDays(bound);
    }

//    public static String getAddress() {
//        StringBuilder address = new StringBuilder();
//        ClassLoader classLoader = DataUtil.class.getClassLoader();
//        URL resource = classLoader.getResource("https://raw.githubusercontent.com/mqxu/blog/master/src/main/resources/address.json");
//        assert resource != null;
//        String path = resource.getPath();
//        File file = new File(path);
//        Reader reader = null;
//        try {
//            reader = new FileReader(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        assert reader != null;
//        BufferedReader br = new BufferedReader(reader);
//        String line;
//        try {
//            while ((line = br.readLine()) != null) {
//                address.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Gson gson = new GsonBuilder().create();
//        Provinces provinces = gson.fromJson(address.toString(), Provinces.class);
//        List<Province> provinceList = provinces.getProvinces();
//        int size = provinceList.size();
//        Random random = new Random();
//        int index = random.nextInt(size);
//        Province province = provinceList.get(index);
//        List<City> cityList = province.getCities();
//        size = cityList.size();
//        index = random.nextInt(size);
//        City city = cityList.get(index);
//        return province.getName() + city.getName();
//    }

    public static int getUserId(){
        Random random = new Random();
        int bound = random.nextInt(72)+1;
        return bound;
    }

    /**
     * 生成时间
     * @return
     */
    public static LocalDateTime getCreateTime(){
        LocalDateTime now = LocalDateTime.now();
        Random random = new Random();
        int bound = random.nextInt(999);
        return now.minusHours(bound);
    }

    public static Long getUserrId(){
        Random random = new Random();
        long bound = random.nextInt(61);
        return bound;
    }

}