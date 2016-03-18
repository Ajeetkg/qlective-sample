package com.ajeetkg.config;

import com.illumina.prs.common.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RestTemplate restTemplate;

    @Value("${remote.service.userauth.baseurl}") String userAuthServiceBaseUrl;
    @Value("${remote.service.userauth.clientId}") String clientId;
    @Value("${remote.service.userauth.clientSecret}") String clientSecret;
    @Value("${remote.service.userauth.accessTokenHeader}") String accessTokenHeader;
    @Value("${remote.service.userauth.tokenUrlPath}") String tokenUrlPath;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // dont want CSRF if this is a pure API application without UI
                .authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .and()
                        // Custom Token based authentication based on the header previously given to the client
                .addFilterBefore(getTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);;
    }

    @Bean
    public AuthenticationConfiguration getAuthenticationConfiguration() {
        AuthenticationConfiguration config = new AuthenticationConfiguration();
        config.setAuthUrl(userAuthServiceBaseUrl);
        config.setClientId(clientId);
        config.setClientSecret(clientSecret);
        config.setTokenUrlPath(tokenUrlPath);
        config.setAccessTokenHeader(accessTokenHeader);

        return config;
    }

    @Bean
    public DomainHostNameService getDomainHostNameService() {
        return new DomainHostNameService();
    }

    @Bean
    public TokenService getTokenService() {
        return new TokenService(getAuthenticationConfiguration());
    }

    @Bean
    public Cache getAuthCache() {
        return new NullCache(); //TODO: use cache
    }

    @Bean
    public TokenAuthenticationService getTokenAuthenticationService() {
        return new TokenAuthenticationService(restTemplate,
                getAuthenticationConfiguration(),
                getTokenService(),
                getDomainHostNameService(),
                getAuthCache());
    }

    @Bean
    public TokenAuthenticationFilter getTokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(getTokenAuthenticationService());
    }
}
