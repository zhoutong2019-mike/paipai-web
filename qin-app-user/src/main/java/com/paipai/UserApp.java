package com.paipai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;

//@EnableScheduling
//@EnableAsync
//@EnableEurekaClient
@EnableCaching
@MapperScan(basePackages = "com.paipai.dao")
@SpringBootApplication
public class UserApp {
	public static void main(String[] args) {
		SpringApplication.run(UserApp.class, args);
	}
}
