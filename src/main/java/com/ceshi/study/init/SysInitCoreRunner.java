package com.ceshi.study.init;

import com.ceshi.study.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@Slf4j
public class SysInitCoreRunner implements CommandLineRunner {


    @Override
    public void run(String... var1) throws Exception {
        log.info("===============================================");
        log.info("===============开始系统初始化执行===============");
        log.info("===============================================");
        ReadPropertiesBase readPropertiesBase = (ReadPropertiesBase) SpringContextUtil.getBean("readPropertiesBase");
        log.info("==========str:{}==================",readPropertiesBase.getSysCeshiStr());
        log.info("==========name:{}==================",readPropertiesBase.getCeshiName());
    }
}
