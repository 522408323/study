package com.ceshi.study.init;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 读取项目基本配置
 */
@Component
@Getter
@RefreshScope
public class ReadPropertiesBase {

    @Value("${sys.ceshi.str:}")
    private String sysCeshiStr;

    @Value("${ceshi.name:}")
    private String ceshiName;

}
