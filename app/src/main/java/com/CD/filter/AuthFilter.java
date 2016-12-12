package com.CD.filter;

import com.CD.constant.RoleConstant;
import com.CD.constant.SessionConstant;
import com.CD.entity.api.UserViewModel;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * ProjectName: QJZ
 * PackageName: com.CD.filter
 * User: C0dEr
 * Date: 2016-11-24
 * Time: 13:21
 * Description:todo 权限过滤，销售只能查看自己的客户
 */
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        if (session.getAttribute(SessionConstant.CURRENTUSER) == null) {

            ((HttpServletResponse) response).setHeader("Content-Type", "application/json;charset=utf-8");
            ((HttpServletResponse) response).getWriter().write("{\n" +
                    "    \"statusCode\":1,\n" +
                    "    \"message\":\"用户未登陆\",\n" +
                    "    \"data\":{}\n" +
                    "}");
        } else if (((UserViewModel) session.getAttribute(SessionConstant.CURRENTUSER)).role.equals(RoleConstant.SALESMAN)) {
            //如果是销售判断权限

        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
