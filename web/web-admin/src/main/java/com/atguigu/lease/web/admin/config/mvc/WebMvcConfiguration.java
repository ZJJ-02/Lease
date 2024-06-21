package com.atguigu.lease.web.admin.config.mvc;

import com.atguigu.lease.common.config.mvc.CodeEnumConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Value("${lease.auth.path-patterns.include}")
    private String[] includePathPatterns;

    @Value("${lease.auth.path-patterns.exclude}")
    private String[] excludePathPatterns;

    @Autowired
    private CodeEnumConverter codeEnumConverter;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.authenticationInterceptor)
                .addPathPatterns(includePathPatterns)
                .excludePathPatterns(excludePathPatterns);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(this.codeEnumConverter);
    }

    /**
     * 解决跨域传输问题
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")          // 添加路径规则
                .allowCredentials(true)               // 是否允许在跨域的情况下传递Cookie
                .allowedOriginPatterns("*")           // 允许请求来源的域规则
                .allowedMethods("*")                  // 允许所有的请求方式
                .allowedHeaders("*");
    }
}
