package com.transtour.backend.zulproxy.config;

import com.transtour.backend.zulproxy.filter.JWTAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
@Order(1)
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http
                .csrf().disable()
                .addFilter(new JWTAuthorizationFilter())
                .authorizeRequests()
                .antMatchers("/api/service-user/v1/user/oauth/token").permitAll()
                .anyRequest().authenticated();
    }
}
