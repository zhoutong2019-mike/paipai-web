package com.qin;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;
//@EnableScheduling定时器
//@EnableAsync定时器多线程
//@EnableEurekaClient///eureka
//@EnableCaching//缓存
@EnableBatchProcessing
@MapperScan(basePackages = "com.qin.repository")
@SpringBootApplication
@EnableScheduling
public class BootModelApp {

	public static void main(String[] args) {
		SpringApplication.run(BootModelApp.class, args);
	}

}
