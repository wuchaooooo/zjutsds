package com.wuchaooooo.service.zjutsds.config;

import org.springframework.boot.autoconfigure.velocity.VelocityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;

@Configuration
public class VelocityConfig {

	@Bean
	public VelocityLayoutViewResolver velocityLayoutViewResolver(VelocityProperties properties) {
		VelocityLayoutViewResolver viewResolver = new VelocityLayoutViewResolver();
		viewResolver.setViewClass(VelocityLayoutToolboxView.class);
		properties.applyToViewResolver(viewResolver);// 设置默认属性，比如前缀和后缀
		properties.setSuffix(".vm");
		return viewResolver;
	}

}
