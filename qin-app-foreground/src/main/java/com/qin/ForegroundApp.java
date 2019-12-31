package com.qin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;
//@EnableScheduling定时器
//@EnableAsync定时器多线程
//@EnableEurekaClient///eureka
//@EnableCaching//缓存
@MapperScan(basePackages = "com.qin.dao")
@SpringBootApplication
public class ForegroundApp {

	public static void main(String[] args) {
		SpringApplication.run(ForegroundApp.class, args);
	}

}
