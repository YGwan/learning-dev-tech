package org.example.config;

import org.example.logging.aop.LogAspect1;
import org.example.logging.aop.LogAspect2;
import org.example.logging.aop.LogAspect3;
import org.example.logging.aop.LogAspect4;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogAspectConfig {

    @Bean
    public LogAspect1 logAspect1() {
        return new LogAspect1();
    }

    @Bean
    public LogAspect2 logAspect2() {
        return new LogAspect2();
    }

    @Bean
    public LogAspect3 logAspect3() {
        return new LogAspect3();
    }

    @Bean
    public LogAspect4 logAspect4() {
        return new LogAspect4();
    }
}
