package com.cp;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//输出日志
@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement//开启事务
public class ReggieTakeOutApplication {

    public static void main(String[] args) {

        SpringApplication.run(ReggieTakeOutApplication.class, args);
        log.info("项目启动成功");
    }

}
