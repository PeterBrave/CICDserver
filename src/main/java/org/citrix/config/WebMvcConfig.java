package org.citrix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by citrix on 2018/1/2.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }
}
