package com.ceshi.study.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: JobUtil
 * @Author: shenyafei
 * @Date: 2020/12/29
 * @Desc
 **/
public class JobUtil {

    /***
     * 获取job传入参数
     * @param jobParams
     * @return
     */
    public Map<String,Object> getTaskParams(String jobParams) {
        Map<String,Object> map = new HashMap<String,Object>();
            if (jobParams != null) {
                String[] strSelecteds = jobParams.split(",");
                for (String s : strSelecteds) {
                    String[] strs = s.split(":");
                    if (strs.length > 1) {
                        String mKey = strs[0].trim();
                        if (map.get(mKey) == null) {
                            // System.out.println(mKey+"--"+strs[1].trim());
                            map.put(mKey, strs[1].trim());
                        }
                    }
                }
            }
        return map;
    }

    public static void execTaskJob(String classSrc, String methodName, String triggerName, Map<String, Object> map){

    }
}
