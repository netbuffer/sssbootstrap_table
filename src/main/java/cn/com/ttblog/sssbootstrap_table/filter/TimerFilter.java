package cn.com.ttblog.sssbootstrap_table.filter;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 计时器。用于记录请求执行时间。
 */
public class TimerFilter implements Filter {
	private FilterConfig filterConfig;
	private static final Logger timerFilterlogger = LoggerFactory
			.getLogger(TimerFilter.class);
	public static final NumberFormat FORMAT = new DecimalFormat("0.000");

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {
		boolean enable = Boolean.parseBoolean(filterConfig
				.getInitParameter("enable"));
		if (enable) {
			long begin = System.currentTimeMillis();
			chain.doFilter(request, response);
			if (timerFilterlogger.isDebugEnabled()) {
				long end = System.currentTimeMillis();
				BigDecimal processed = new BigDecimal(end - begin)
						.divide(new BigDecimal(1000));
				String uri = ((HttpServletRequest) request).getRequestURI();
				timerFilterlogger.debug("costs in {} second(s). URI={}",
						FORMAT.format(processed), uri);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void destroy() {
	}

}
