package com.paipai.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.paipai.interceptor.VerifyInterceptor;

@Configuration
public class LoginInterceptor extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new VerifyInterceptor()).addPathPatterns("/**").excludePathPatterns("/**/*login*/**",
				"/**/*success*/**", "/**/*error*/**");
		super.addInterceptors(registry);
	}

}
