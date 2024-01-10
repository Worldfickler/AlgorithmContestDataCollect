package org.algotithmcontestdatacollect.managebackend.Configure;

import org.algotithmcontestdatacollect.managebackend.Interceptor.AdminInterceptor;
import org.algotithmcontestdatacollect.managebackend.Interceptor.JWTInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class IntercaptorConfig implements WebMvcConfigurer {
    @Autowired
    private JWTInterceptor JWTinterceptor;
    @Autowired
    private AdminInterceptor Admininterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(JWTinterceptor).addPathPatterns("/**").excludePathPatterns("/api/login");
        registry.addInterceptor(Admininterceptor).addPathPatterns("/api/admin/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
