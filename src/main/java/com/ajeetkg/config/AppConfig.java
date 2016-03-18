package com.ajeetkg.config;


import com.ajeetkg.metrics.MonitoredClientHttpRequestInterceptor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

@Configuration
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

    private static final HostnameVerifier PROMISCUOUS_VERIFIER = (s, sslSession ) -> true;

    @Value("${remote.service.timeout.read:2000}")
    int restTemplateReadTimeout;

    @Value("${remote.service.timeout.connection:2000}")
    int restTemplateConnectionTimeout;

    @Bean
    public RestTemplate getRestTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(restTemplateReadTimeout);
        factory.setConnectTimeout(restTemplateConnectionTimeout);

        RestTemplate rest = new RestTemplate(factory);
        List<ClientHttpRequestInterceptor> interceptors = rest.getInterceptors();
        interceptors.add(clientHttpRequestInterceptor());
        rest.setInterceptors(interceptors);

        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter(objectMapper()));

        rest.setRequestFactory( new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                if(connection instanceof HttpsURLConnection){
                    ((HttpsURLConnection) connection).setHostnameVerifier(PROMISCUOUS_VERIFIER);
                }
                super.prepareConnection(connection, httpMethod);
            }
        });
        return rest;

    }

    @Bean
    public ClientHttpRequestInterceptor clientHttpRequestInterceptor() {
        return new MonitoredClientHttpRequestInterceptor();
    }

    @Bean
    public ObjectMapper objectMapper() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        // Serialize `LocalDateTime` to string; otherwise it'll be serialized to an array
        builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return builder.build();
    }

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
