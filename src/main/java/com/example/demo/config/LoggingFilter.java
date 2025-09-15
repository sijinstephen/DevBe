package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Component
public class LoggingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, jakarta.servlet.ServletException {
        // Wrap request/response to cache content
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

        // Log request details
        logRequest(wrappedRequest);

        // Proceed with the filter chain
        chain.doFilter(wrappedRequest, wrappedResponse);

        // Log response details
        logResponse(wrappedResponse);

        // Copy content to original response
        wrappedResponse.copyBodyToResponse();
    }

    private void logRequest(ContentCachingRequestWrapper request) throws IOException {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("\n--- REQUEST BEGIN---\n");
        logMessage.append("Method: ").append(request.getMethod()).append("\n");
        logMessage.append("URI: ").append(request.getRequestURI()).append("\n");
        logMessage.append("Query: ").append(request.getQueryString() != null ? request.getQueryString() : "none").append("\n");
        logMessage.append("Headers: ");
        Collections.list(request.getHeaderNames()).forEach(headerName ->
                logMessage.append(headerName).append(": ").append(request.getHeader(headerName)).append("; "));
        logMessage.append("\n");
                logMessage.append("\n--- REQUEST END ---\n");

        // Get request body (after reading, ContentCachingRequestWrapper caches it)
        byte[] content = request.getContentAsByteArray();
        if (content.length > 0) {
            String body = new String(content, StandardCharsets.UTF_8);
            logMessage.append("Body: ").append(body).append("\n");
        } else {
            logMessage.append("Body: none\n");
        }

        logger.info(logMessage.toString());
    }

    private void logResponse(ContentCachingResponseWrapper response) throws IOException {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("\n--- RESPONSE BEGIN---\n");
        logMessage.append("Status: ").append(response.getStatus()).append("\n");
        logMessage.append("Headers: ");
        response.getHeaderNames().forEach(headerName ->
                logMessage.append(headerName).append(": ").append(response.getHeader(headerName)).append("; "));
        logMessage.append("\n");
                logMessage.append("\n--- RESPONSE END ---\n");

        // Get response body
        byte[] content = response.getContentAsByteArray();
        if (content.length > 0) {
            String body = new String(content, StandardCharsets.UTF_8);
            logMessage.append("Body: ").append(body).append("\n");
        } else {
            logMessage.append("Body: none\n");
        }

        logger.info(logMessage.toString());
    }
}