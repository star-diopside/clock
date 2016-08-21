package jp.gr.java_conf.star_diopside.clock.config;

import org.aopalliance.intercept.Interceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Jsr330ScopeMetadataResolver;

import jp.gr.java_conf.star_diopside.spark.commons.core.interceptor.LoggingObjectDetailsInterceptor;

@Configuration
@ComponentScan(value = "jp.gr.java_conf.star_diopside.clock", scopeResolver = Jsr330ScopeMetadataResolver.class)
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean
    public Interceptor logging() {
        return new LoggingObjectDetailsInterceptor();
    }

    @Bean
    public Advisor loggingAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* jp.gr.java_conf.star_diopside.clock..*(..))"
                + " && !execution(* jp.gr.java_conf.star_diopside.clock.model.Clock.update())"
                + " && !execution(* jp.gr.java_conf.star_diopside.clock.controller..*(..))");
        return new DefaultPointcutAdvisor(pointcut, logging());
    }
}
