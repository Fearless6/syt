package com.jd.yygh.hosp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 一个胖胖的瘦子
 * @date 2022/6/12
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.jd.yygh")
public class ServiceHospApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospApplication.class, args);
    }
}
