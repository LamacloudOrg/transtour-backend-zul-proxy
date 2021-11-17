package com.transtour.backend.zulproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class ZulProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZulProxyApplication.class, args);
	}

}
