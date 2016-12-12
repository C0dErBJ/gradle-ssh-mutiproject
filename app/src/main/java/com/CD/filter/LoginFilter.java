package com.CD.filter;

import com.CD.constant.SessionConstant;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登陆拦截
 */
public class LoginFilter implements Filter {
    protected Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        //由于web.xml中设置Filter过滤全部请求，可以排除不需要过滤的url
        String requestURI = req.getRequestURI();
        String[] noFilter = new String[]{"login",
                "resources",
                "swagger-ui.html",
                "webjars",
                "/v2/api-docs",
                "swagger-resources",
                "index.js.map",
                "srpc",
                "favicon.ico",
                "captcha"};
        if (requestURI.equals("/")) {
            chain.doFilter(request, response);
            return;
        }
        for (String nf : noFilter) {
            if (requestURI.toLowerCase().contains(nf.toLowerCase())) {
                chain.doFilter(request, response);
                return;
            }
        }


        //判断用户是否登录，进行页面的处理
        if (session.getAttribute(SessionConstant.CURRENTUSER) == null) {
            logger.info("------------------拦截未登录请求(url:" + requestURI + ")-------------------");
            if (request.getContentType() == null || request.getContentType().toLowerCase().equals("text/html")) {
                //未登录用户，重定向到登录页面
                ((HttpServletResponse) response).sendRedirect("login");
            } else {
                ((HttpServletResponse) response).setHeader("Content-Type", "application/json;charset=utf-8");
                ((HttpServletResponse) response).getWriter().write("{\n" +
                        "    \"statusCode\":1,\n" +
                        "    \"message\":\"用户未登陆\",\n" +
                        "    \"data\":{}\n" +
                        "}");
            }
        } else {
            //已登录用户，允许访问
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}

