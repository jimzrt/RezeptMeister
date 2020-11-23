package com.jimzrt.RezeptMeister;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/", "file:static/")
				.setCachePeriod(3600 * 24);

	}

	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof org.springframework.http.converter.json.MappingJackson2HttpMessageConverter) {
				ObjectMapper mapper = ((MappingJackson2HttpMessageConverter) converter).getObjectMapper();
				mapper.registerModule(new Hibernate5Module());
			}
		}
	}
}