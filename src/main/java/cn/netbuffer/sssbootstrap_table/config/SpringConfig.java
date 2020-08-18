package cn.netbuffer.sssbootstrap_table.config;

import cn.netbuffer.sssbootstrap_table.condition.EnableRestTemplateCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import javax.annotation.PostConstruct;

@Configuration
public class SpringConfig {
    private static final Logger LOG= LoggerFactory.getLogger(SpringConfig.class);

    @PostConstruct
    public void init(){
        LOG.info("custom config class!");
    }

    /**
     * 应用EnableRestTemplateCondition条件做判断是否创建对应的bean
     * @return
     */
    @Bean
    @Conditional(EnableRestTemplateCondition.class)
    public RestTemplate getRestTemplate(){
        LOG.info("实例化RestTemplate");
        return new RestTemplate();
    }

}
