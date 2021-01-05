package com.ceshi.study.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ceshi.study.model.User;
import com.thoughtworks.xstream.XStream;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class WxUtil {
	/**
	 * js转化为实体
	 * @param <T>
	 * @param jsonstr
	 * @param cls
	 * @return
	 */
	public static <T> T json2Obj(String jsonstr,Class<T> cls){
    	JSONObject jo = JSONObject.parseObject(jsonstr);
		T obj = (T) JSONObject.toJavaObject(jo, cls);
		return obj;
    }
	
	/**
	 * md5
	 * @param b
	 * @return
	 */
	public static String md5(byte[] b) {
        try {
        	MessageDigest md = MessageDigest.getInstance("MD5");
        	 md.reset();
             md.update(b);
             byte[] hash = md.digest();
             StringBuffer outStrBuf = new StringBuffer(32);
             for (int i = 0; i < hash.length; i++) {
                 int v = hash[i] & 0xFF;
                 if (v < 16) {
                 	outStrBuf.append('0');
                 }
                 outStrBuf.append(Integer.toString(v, 16).toLowerCase());
             }
             return outStrBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new String(b);
        }
    }
	
	/**
	 * 判断字符串是否为空
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s){
		if(s==null||"".equals(s.trim()))
			return true;
		return false;
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
	
	/**
	 * 签名
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String sign(TreeMap<String,String> params,String appkey) throws Exception{
        //签名明文组装不包含sign字段
		if(params.containsKey("sign")){
            params.remove("sign");
        }
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, String> entry:params.entrySet()){
			if(entry.getValue()!=null&&entry.getValue().length()>0){
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
		if(sb.length()>0){
			sb.append("key="+appkey);
		}
		System.out.println("---"+sb.toString());
		//记得是md5编码的加签
		String sign = md5(sb.toString().getBytes("UTF-8")).toUpperCase();
		params.remove("key");
		return sign;
	}
	
	 public static boolean validSign(TreeMap<String,String> param,String appkey) throws Exception{
		 if(param!=null&&!param.isEmpty()){
			 if(!param.containsKey("sign")){
                 return false;
             }
			 String sign = param.get("sign").toString();
			 String mysign = sign(param, appkey);
			 return sign.toLowerCase().equals(mysign.toLowerCase());
		 }
		 return false;
	 }

	public static TreeMap<String, String> objectToMap(Object obj) throws Exception {
		TreeMap<String, String> map = new TreeMap<String,String>();
		Class<?> clazz = obj.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			String fieldName = field.getName();
			Object value = field.get(obj);
			if (value != null) {
				if (value instanceof String && "".equals(value.toString())) {
				} else {
					map.put(fieldName, value.toString());
				}
			}
		}
		return map;
	}

	public static String buildXml(Object o) {
		XStream xs = new XStream();
		xs.alias("xml", o.getClass());
		String xml = xs.toXML(o);
		xml = xml.replaceAll("__", "_");
		return xml;
	}

	public static WxOrderResp parseWxOrderResp(String xml) {
		XStream xs = new XStream();
		xs.alias("xml", WxOrderResp.class);
		return (WxOrderResp) xs.fromXML(xml);
	}

	public static <T> T parseObject(String xml, Class<T> tClass) {
		XStream xs = new XStream();
		xs.alias("xml", tClass);
		return (T) xs.fromXML(xml);
	}

	public static void main(String[] args){
		String xml = "<xml>";
		xml +="<id>1</id>";
		//xml +="<name>sss</name>";
		xml +="<age>20</age>";
		xml += "</xml>";
		User a = parseObject(xml,User.class);
		System.out.println(a.getName());
	}

}
