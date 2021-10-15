package com.transtour.backend.zulproxy.security;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //@Autowired
    //private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
             //   .csrf().disable()
                //configure CORS -- uses a Bean by the name of     corsConfigurationSource (see method below)
                //CORS must be configured prior to Spring Security
                .cors().and()
                .csrf().disable()
                //configuring security - irrelevant for this problem
                .authorizeRequests()
                          .anyRequest().permitAll() //.authenticated()
                         .and()
                .httpBasic().disable();
        //.authenticationEntryPoint(authenticationEntryPoint);
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new     UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(CorsConfiguration.ALL);
  //      config.addAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL));
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");

        config.addAllowedHeader("Content-type");
        config.addAllowedHeader("Content-Disposition");
        config.addAllowedHeader("Accept");
        config.addAllowedHeader("Access-Control-Allow-Origin");
        config.addAllowedHeader("Access-Control-Allow-Credentials");
        config.addAllowedHeader("Access-Control-Max-Age");


        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
