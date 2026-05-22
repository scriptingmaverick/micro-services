package com.hotel.logger;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {
  private final Logger loggingFilter = LoggerFactory.getLogger("RequestLogger");

  @Override
  public void doFilter(ServletRequest rawRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) rawRequest;

    loggingFilter.info("{} {}", request.getMethod(), request.getRequestURI());

    long start = System.currentTimeMillis();
    chain.doFilter(request, response);

    HttpServletResponse httpResponse = (HttpServletResponse) response;
    loggingFilter.info("{} {} {} {}ms", request.getMethod(), request.getRequestURI(), httpResponse.getStatus(), System.currentTimeMillis() - start);
  }
}
