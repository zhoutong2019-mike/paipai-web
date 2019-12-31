package com.paipai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import tk.mybatis.spring.annotation.MapperScan;

//@EnableScheduling
//@EnableAsync
@EnableEurekaClient
@EnableCaching
@MapperScan(basePackages = "com.paipai.dao")
@SpringBootApplication
public class CakeStart {
	public static void main(String[] args) {
		SpringApplication.run(CakeStart.class, args);
	}
}
