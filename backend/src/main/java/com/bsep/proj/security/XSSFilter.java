package com.bsep.proj.security;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;

// this filter does nothing, you could add some logic to sanitize input
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class XSSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        System.out.println("\n\nParameters");
//
//        Enumeration params = request.getParameterNames();
//        while(params.hasMoreElements()){
//            String paramName = (String)params.nextElement();
//            System.out.println(paramName + " = " + request.getParameter(paramName));
//        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
