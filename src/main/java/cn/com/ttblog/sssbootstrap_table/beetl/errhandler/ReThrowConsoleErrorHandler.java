package cn.com.ttblog.sssbootstrap_table.beetl.errhandler;

import org.beetl.core.exception.BeetlException;
import org.beetl.ext.web.WebErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Writer;

/**
 * 捕获beetl错误处理
 * ConsoleErrorHandler/WebErrorHandler
 */
public class ReThrowConsoleErrorHandler extends WebErrorHandler {

    private static final Logger LOG= LoggerFactory.getLogger(ReThrowConsoleErrorHandler.class);

    @Override
    public void processExcption(BeetlException ex, Writer writer) {
        LOG.error("beetl渲染出错:{}",ex);
        super.processExcption(ex, writer);
        throw ex;
    }
}