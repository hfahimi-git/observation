package ir.parliran.global;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AddResponseHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        httpServletResponse.setCharacterEncoding("UTF-8");
        if (!httpServletRequest.getRequestURI().startsWith("/assets/")) {
            httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            httpServletResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            httpServletResponse.setDateHeader("Expires", 0); // Proxies.
        }
        chain.doFilter(request, httpServletResponse);
    }

}