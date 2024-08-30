package com.dailypost.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@ComponentScan(basePackages = "com.dailypost")
public class WebConfig implements WebMvcConfigurer {
	

	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver template = new SpringResourceTemplateResolver();
		template.setPrefix("classpath:/templates");
		template.setSuffix(".html");
		template.setTemplateMode(TemplateMode.HTML);
		template.setCacheable(false);
		return template;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine template = new SpringTemplateEngine();
		template.setTemplateResolver(templateResolver());
		return template;
	}
	
	@Bean
	public ThymeleafViewResolver viewResolver() {
		ThymeleafViewResolver view = new ThymeleafViewResolver();
		view.setTemplateEngine(templateEngine());
		return view;
	}
	
}
