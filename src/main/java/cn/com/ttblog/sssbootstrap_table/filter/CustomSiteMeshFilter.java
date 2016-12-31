package cn.com.ttblog.sssbootstrap_table.filter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * sitemesh使用:http://wiki.sitemesh.org/wiki/pages/viewpage.action?pageId=1081348
 * 测试过程中发现使用xml配置路径，出现了装饰后的内容乱码问题，但是使用java config无此问题，原因不明
 */
public class CustomSiteMeshFilter extends ConfigurableSiteMeshFilter {

    private static final Logger LOG= LoggerFactory.getLogger(CustomSiteMeshFilter.class);

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.addDecoratorPath("/*", "/layout.html")
                .addExcludedPath("*.html");
        LOG.info("添加装饰参数:{}",builder);
    }
}