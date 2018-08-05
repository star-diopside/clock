package jp.gr.java_conf.stardiopside.clock.config;

import org.aopalliance.intercept.Interceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import jp.gr.java_conf.stardiopside.silver.commons.support.interceptor.LoggingObjectDetailsInterceptor;

@Configuration
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean
    public Interceptor logging() {
        return new LoggingObjectDetailsInterceptor();
    }

    @Bean
    public Advisor loggingAdvisor() {
        var pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* jp.gr.java_conf.stardiopside.clock..*(..))"
                + " && !execution(* jp.gr.java_conf.stardiopside.clock.model.Clock.update())"
                + " && !execution(* jp.gr.java_conf.stardiopside.clock.controller..*(..))");
        return new DefaultPointcutAdvisor(pointcut, logging());
    }
}
