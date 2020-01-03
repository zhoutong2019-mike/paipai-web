package com.paipai.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.paipai.interceptor.VerifyInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new VerifyInterceptor()).addPathPatterns("/**").
		excludePathPatterns("/**/*regist*/**", "/**/*login*/**", "/**/*success*/**", 
				"/**/*error*/**", "/**/*http*/**","/**/*goRegist*/**","/**/*isExist*/**","/*test/*");
		super.addInterceptors(registry);
	}
	

}