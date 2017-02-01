package cn.com.ttblog.sssbootstrap_table.config;

import cn.com.ttblog.sssbootstrap_table.condition.EnableRestTemplateCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Bean
    @Conditional(EnableRestTemplateCondition.class)
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
