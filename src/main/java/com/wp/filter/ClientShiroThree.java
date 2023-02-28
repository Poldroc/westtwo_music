package com.wp.filter;


import com.wp.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientShiroThree extends AuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse response1) throws Exception {
        System.out.println("onAccessDenied");
        HttpServletResponse response = (HttpServletResponse) response1;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //response.setHeader("Access-Control-Allow-Origin", "http://localhost:5175");


        String ajax = request.getHeader("x-requested-with");
        if (null==ajax) {
            System.out.println("=====不是ajax");
            response.sendRedirect("user/login");
        }else {
            System.out.println("=====是ajax"+ajax);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("访问有问题");
        }
        return false;
    }
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse response, Object mappedValue) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader(TokenUtil.tokenHeard);
        System.out.println(request.getMethod()+ "================"+token);
        if (null == token||"".equals(token)) {
            System.out.println(request.getMethod()+ "---------token为空");
            return false;
        }
        //验证token的真实性
        try {
            TokenUtil.getTokenBody(token);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("----------------token有问题");
            return false;
        }
        return true;
    }
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        //无条件放行OPTIONS
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            setHeader(httpRequest, httpResponse);
            return true;
        }
        return super.preHandle(request, response);
    }

    /**
     * 为response设置header，实现跨域
     */
    private void setHeader(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods","POST,PUT,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "content-type,x-requested-with,token,Authorization,authorization");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
    }



}
