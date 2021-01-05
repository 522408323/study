package com.ceshi.study.test;

import com.ceshi.study.model.User;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang3.StringUtils;

import javax.jnlp.UnavailableServiceException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName: CeshiTest
 * @Author: shenyafei
 * @Date: 2020/8/11
 * @Desc
 **/
public class XmlTest {
    public static final String HEAD = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
    /**
     * @Desc: 构建请求报文
     * @param: [o, isreq]
     * @Return: java.lang.String
     * @Author: lixiping
     * @Date: 2019/4/9
     */
    public static String buildXml(Object o) {
        XStream xs = new XStream();
        xs.alias("xml", User.class);
        String xml = xs.toXML(o);
        xml = xml.replaceAll("__", "_");
        return xml;
    }

    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String,Object>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            if (value != null) {
                if (value instanceof String && "".equals(value.toString())) {
                } else {
                    map.put(fieldName, value);
                }
            }
        }
        return map;
    }
    /**
     * 生成随机码
     * @param n
     * @return
     */
    public static String getValidatecode(int n) {
        Random random = new Random();
        String sRand = "";
        n = n == 0 ? 4 : n;// default 4
        for (int i = 0; i < n; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
        }
        return sRand;
    }
    /***
     * @param args
     */
    public static void main(String[] args) throws Exception {
        User u = new User();
        u.setAge(20);
        u.setId(1);
        u.setName("");
        //System.out.println(buildXml(u));

        //System.out.println(objectToMap(u));
        //System.out.println(getValidatecode(16));
        System.out.println(new BigDecimal("11.1234").intValue());
    }
}
