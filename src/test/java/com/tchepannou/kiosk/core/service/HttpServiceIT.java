package com.tchepannou.kiosk.core.service;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpServiceIT {
    private static final int PORT = 18080;

    private Server server;
    private final HttpService service = new HttpService();

    @Before
    public void setUp() throws Exception {
        server = new Server(PORT);
        server.setHandler(createHandler());
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void shouldGet() throws IOException {
        final OutputStream out = new ByteArrayOutputStream();
        service.get("http://127.0.0.1:" + PORT + "/", out);
        assertThat(out.toString()).isEqualTo("hello world");
    }

    private Handler createHandler() {
        return new DefaultHandler() {
            @Override
            public void handle(final String s, final Request r, final HttpServletRequest request, final HttpServletResponse response)
                    throws IOException, ServletException {
                System.out.println(s);
                final Enumeration<String> names = request.getHeaderNames();
                while (names.hasMoreElements()) {
                    final String name = names.nextElement();
                    System.out.println(name + "=" + request.getHeader(name));
                }

                response.getOutputStream().write("hello world".getBytes());
                r.setHandled(true);
            }
        };
    }
}
