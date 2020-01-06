package com.criss.wang.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import sun.misc.BASE64Decoder;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 加一个http basic认证
 */
@Order(1)
@WebFilter(urlPatterns = "/*",filterName = "HttpBasicAuthorizeAttribute")
public class HttpBasicAuthorizeAttribute implements Filter {

    private static String NAME = "test";
    private static String PASSWORD = "test";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String result = checkHTTPBasicAuthorize(request);
        if (!"OK".equals(result)) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json;charset=utf-8");
            ObjectMapper objectMapper = new ObjectMapper();
            String msg = "no permitted";
            httpServletResponse.getWriter().write(msg);
            return;
        }else{
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    private String getFromBASE64(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }

    private String checkHTTPBasicAuthorize(ServletRequest request) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String auth = httpServletRequest.getHeader("Authorization");
            if (auth != null && auth.length() > 6) {
                String headStr = auth.substring(0, 5).toLowerCase();
                if (headStr.compareTo("basic") == 0) {
                    auth = auth.substring(6, auth.length());
                    String decodeAuth = getFromBASE64(auth);
                    if (decodeAuth != null) {
                        String[] userArray = decodeAuth.split(":");
                        if (userArray != null && userArray.length == 2) {
                            if (userArray[0].compareTo(NAME) == 0 && userArray[1].compareTo(PASSWORD) == 0) {
                                return "OK";
                            }
                        }
                    }
                }
            }
            return "faliure";
        } catch (Exception e) {

        }
        return "faliure";
    }

}
