package com.leyou.config.myconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName MyFilter
 * @Description
 * @Author Administrator
 * @Date 2020/10/26 19:30
 * @Version
 **/
public class MyFilter implements Filter{
    private Logger logger = LoggerFactory.getLogger(MyFilter.class);

    //初始化方法
    @Override
    public void init(FilterConfig filterConfig) throws ServletException{
        logger.info("过滤器启动");
    }
    //拦截方法
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException{
        HttpServletRequest request =(HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String url = request.getRequestURI();
        String suffix="/bus-refresh";
        //过滤 /actuator/bus-refresh请求
        if(!url.endsWith(suffix)){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        CustometRequestWrapper requestWrapper = new CustometRequestWrapper(request);
        filterChain.doFilter((ServletRequest)requestWrapper,servletResponse);
    }

    @Override
    public void destroy(){
        System.out.println("过滤器销毁");
    }
}