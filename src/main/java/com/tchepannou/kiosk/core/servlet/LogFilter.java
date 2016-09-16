package com.tchepannou.kiosk.core.servlet;

import com.tchepannou.kiosk.core.service.LogService;
import com.tchepannou.kiosk.core.service.TransactionIdProvider;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogFilter implements Filter{

    private final LogService logService;
    private final TransactionIdProvider transactionId;

    public LogFilter(final LogService logService, final TransactionIdProvider transactionId) {
        this.logService = logService;
        this.transactionId = transactionId;
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain)
            throws IOException, ServletException {
        doFilter((HttpServletRequest)servletRequest, (HttpServletResponse)servletResponse, filterChain);
    }

    @Override
    public void destroy() {

    }

    private void doFilter(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {

        long start = System.currentTimeMillis();
        Throwable exception = null;
        try {

            filterChain.doFilter(request, response);

        } catch (Exception ex){

            exception = ex;

        } finally {

            long latencyMillis = System.currentTimeMillis() - start;
            log(latencyMillis, request, response);

            if (exception == null){
                logService.log();
            } else {
                logService.log(exception);
            }
        }
    }

    private void log(
            final long latencyMillis,
            final HttpServletRequest request,
            final HttpServletResponse response
    ){
        logService.add("HttpLatency", latencyMillis);
        logService.add("HttpUrl", request.getRequestURI());

        logService.add("HttpStatus", response.getStatus());
        logService.add("TransactionId", transactionId.get());
    }
}
