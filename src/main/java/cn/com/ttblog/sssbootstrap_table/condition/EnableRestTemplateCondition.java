package cn.com.ttblog.sssbootstrap_table.condition;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class EnableRestTemplateCondition implements Condition{

    private static final Logger LOG= LoggerFactory.getLogger(EnableRestTemplateCondition.class);

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        LOG.debug("context.getEnvironment():{},metadata:{}",context.getEnvironment(),metadata);
        return false;
    }
}
