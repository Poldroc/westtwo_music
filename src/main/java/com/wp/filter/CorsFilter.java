package com.wp.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebFault;
import java.io.IOException;

@WebFilter(filterName = "CorsFilter")
@Component
@Slf4j
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException, IOException {

        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        //放行所有,类似*,这里*无效
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //允许请求方式
        response.setHeader("Access-Control-Allow-Methods", "POST,PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        //需要放行header头部字段 如需鉴权字段，自行添加，如Authorization
        response.setHeader("Access-Control-Allow-Headers", "content-type,x-requested-with,token,Authorization,authorization");
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("CORS过滤器放行异常:",e);
        }

    }

    @Override
    public void destroy() {

    }


}
