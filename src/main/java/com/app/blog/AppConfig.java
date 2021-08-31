package com.app.blog;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public FilterRegistrationBean<JwtFilter> filterRegistrationBean() {

		FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
		JwtFilter jwtFilter = new JwtFilter();

		registrationBean.setFilter(jwtFilter);
		registrationBean.addUrlPatterns("/api/*");
		registrationBean.setOrder(2);

		return registrationBean;
	}
}
