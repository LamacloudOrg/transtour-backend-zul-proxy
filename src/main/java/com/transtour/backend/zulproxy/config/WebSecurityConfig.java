package com.transtour.backend.zulproxy.config;

import com.transtour.backend.zulproxy.filter.JWTAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Order(1)
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http.cors().and()
                        .csrf().disable()
                        .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                        .authorizeRequests()
                        .antMatchers("/api/service-user/v1/user/oauth/token", "/api/service-user/v1/user/activateAccount").permitAll()
                .anyRequest().authenticated();

    }

}
