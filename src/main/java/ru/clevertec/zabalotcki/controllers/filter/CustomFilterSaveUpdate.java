package ru.clevertec.zabalotcki.controllers.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

public class CustomFilterSaveUpdate implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String idParam = request.getParameter("id");

        if (idParam != null) {
            chain.doFilter(request, response);
        } else {
            HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(httpRequest) {
                @Override
                public String getMethod() {
                    return "POST";
                }
            };
            chain.doFilter(wrappedRequest, response);
        }
    }
}
