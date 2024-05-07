package com.example.security.apikey;


import com.example.security.userdetailsservice.jpa.JpaUserDetailsService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class ApiKeyAuthenticationConfigurerNew extends AbstractHttpConfigurer<ApiKeyAuthenticationConfigurerNew, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) {
        JpaUserDetailsService userDetailsService = http
                .getSharedObject(ApplicationContext.class)
                .getBean(JpaUserDetailsService.class);

        ApiKeyAuthenticationProvider apiKeyAuthenticationProvider = new ApiKeyAuthenticationProvider(userDetailsService);

        http.authenticationProvider(apiKeyAuthenticationProvider);
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        AuthenticationConverter apiKeyAuthenticationConverter = new ApiKeyAuthenticationConverter();
        ApiKeyAuthenticationFilterNew apiKeyAuthenticationFilter = new ApiKeyAuthenticationFilterNew(authenticationManager, apiKeyAuthenticationConverter);
        http.addFilterBefore(apiKeyAuthenticationFilter, BasicAuthenticationFilter.class);
    }
}