package com.skt.doss.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.skt.doss.common.interceptor.RequestLoggingInterceptor;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

  @Autowired
  private RequestLoggingInterceptor requestLoggingInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    
    registry.addInterceptor(requestLoggingInterceptor)
    .excludePathPatterns(
        "/swagger-ui.html",
        "/v2/api-docs",
        "/swagger-resources/**",
        "/*.ico",
        "/index.html"
        );
  }

}
