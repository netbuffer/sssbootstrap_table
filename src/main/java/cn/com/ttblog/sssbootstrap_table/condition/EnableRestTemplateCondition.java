package cn.com.ttblog.sssbootstrap_table.condition;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class EnableRestTemplateCondition implements Condition {

    private static final Logger LOG = LoggerFactory.getLogger(EnableRestTemplateCondition.class);

    /**
     * 实现条件判断逻辑
     *
     * @param context
     * @param metadata
     * @return 布尔值即是否允许创建
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        LOG.debug("context.getEnvironment():{},metadata:{}",
                ToStringBuilder.reflectionToString(context.getEnvironment()),
                ToStringBuilder.reflectionToString((metadata)));
        return true;
    }
}
