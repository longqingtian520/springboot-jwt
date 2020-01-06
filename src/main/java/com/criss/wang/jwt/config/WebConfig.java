package com.criss.wang.jwt.config;

import com.criss.wang.jwt.filter.CROSFilter;
import com.criss.wang.jwt.filter.HttpBasicAuthorizeAttribute;
import com.criss.wang.jwt.interceptor.JwtInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截路径可自行配置多个 可用 ，分隔开
        registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/**");
    }

    /**
     * 跨域支持
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS", "HEAD")
                .maxAge(3600 * 24);
    }

//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        HttpBasicAuthorizeAttribute httpBasicFilter = new HttpBasicAuthorizeAttribute();
//        registrationBean.setFilter(httpBasicFilter);
//        List<String> urlPatterns = new ArrayList<>();
//        urlPatterns.add("/*");
//        registrationBean.setUrlPatterns(urlPatterns);
//        return registrationBean;
//    }

}
