package com.tchepannou.kiosk.core.servlet;

import com.tchepannou.kiosk.core.service.LogService;
import com.tchepannou.kiosk.core.service.TransactionIdProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogFilterTest {

    @Mock
    private LogService logService;

    @Mock
    private TransactionIdProvider transactionIdProvider;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    private LogFilter filter;

    private String transactionId;

    @Before
    public void setUp (){
        transactionId = UUID.randomUUID().toString();
        when(transactionIdProvider.get()).thenReturn(transactionId);

        filter = new LogFilter(logService, transactionIdProvider);

    }

    @Test
    public void testLogRequestResponse() throws Exception {
        // GIVEN
        when(response.getStatus()).thenReturn(200);
        when(request.getRequestURI()).thenReturn("/foo/bar");

        // WHEN
        filter.doFilter(request, response, chain);

        // THEN
        verify(logService).add("HttpStatus", 200);
        verify(logService).add("HttpUrl", "/foo/bar");
        verify(logService).add("TransactionId", transactionId);
        verify(logService).log();
    }

    @Test
    public void testLogRequestResponseException() throws Exception {
        // GIVEN
        when(response.getStatus()).thenReturn(500);
        when(request.getRequestURI()).thenReturn("/foo/bar");

        final IOException ex = new IOException();
        doThrow(ex).when (chain).doFilter(request, response);

        // WHEN
        filter.doFilter(request, response, chain);

        // THEN
        verify(logService).add("HttpStatus", 500);
        verify(logService).add("HttpUrl", "/foo/bar");
        verify(logService).add("TransactionId", transactionId);
        verify(logService).log(ex);
    }
}
