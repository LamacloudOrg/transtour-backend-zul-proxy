package com.transtour.backend.zulproxy.config;

import com.transtour.backend.zulproxy.filter.JWTAuthorizationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http
                .csrf().disable()
                .addFilter(new JWTAuthorizationFilter())
                .authorizeRequests()
                .antMatchers("/v1/user/oauth/token").permitAll()
                .anyRequest().authenticated();
    }
}
