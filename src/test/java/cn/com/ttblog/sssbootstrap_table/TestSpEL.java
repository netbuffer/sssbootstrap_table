package cn.com.ttblog.sssbootstrap_table;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * https://docs.spring.io/spring-framework/docs/4.2.x/spring-framework-reference/html/expressions.html
 */
public class TestSpEL {

    private ExpressionParser parser = new SpelExpressionParser();

    private static final Logger LOGGER = LoggerFactory.getLogger(TestSpEL.class);

    @Test
    public void testSimple() {
        Expression exp = parser.parseExpression("'Hello World'");
        String message = (String) exp.getValue();
        LOGGER.info("message:{}", message);
    }
}
