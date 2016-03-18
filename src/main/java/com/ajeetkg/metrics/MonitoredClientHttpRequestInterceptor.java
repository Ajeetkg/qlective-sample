package com.ajeetkg.metrics;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class MonitoredClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logger.trace("About to execute http request: [{}].", request);
        logger.trace("Body: [{}].", body);

        ClientHttpResponse response = execution.execute(request, body);

        logger.trace("Response status: [{}]", response.getStatusCode());

        return response;
    }
}
