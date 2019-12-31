package com.qin.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


//@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(new VerifyInterceptor()).addPathPatterns("/**").
		//excludePathPatterns("/**/*regist*/**", "/**/*login*/**", "/**/*success*/**", 
		//		"/**/*error*/**", "/**/*http*/**","/**/*goRegist*/**","/**/*isExist*/**","/*test/*");
		super.addInterceptors(registry);
	}
	

}