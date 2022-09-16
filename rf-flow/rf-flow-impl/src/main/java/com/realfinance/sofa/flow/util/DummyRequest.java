package com.realfinance.sofa.flow.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DummyRequest extends HttpServletRequestWrapper {
    private static final HttpServletRequest UNSUPPORTED_REQUEST = (HttpServletRequest) Proxy
            .newProxyInstance(DummyRequest.class.getClassLoader(),
                    new Class[] { HttpServletRequest.class },
                    new UnsupportedOperationExceptionInvocationHandler());

    private String requestURI;
    private String contextPath = "";
    private String servletPath;
    private String pathInfo;
    private String queryString;
    private String method;

    public DummyRequest() {
        super(UNSUPPORTED_REQUEST);
    }

    public String getCharacterEncoding() {
        return "UTF-8";
    }

    public Object getAttribute(String attributeName) {
        return null;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    @Override
    public String getRequestURI() {
        return this.requestURI;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public String getContextPath() {
        return this.contextPath;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    @Override
    public String getServletPath() {
        return this.servletPath;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getPathInfo() {
        return this.pathInfo;
    }

    @Override
    public String getQueryString() {
        return this.queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}

final class UnsupportedOperationExceptionInvocationHandler implements InvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        throw new UnsupportedOperationException(method + " is not supported");
    }
}
