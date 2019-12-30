package com.yksys.isystem.zuul;

import com.yksys.isystem.zuul.locator.JdbcRouteLocator;
import com.yksys.isystem.zuul.locator.ResourceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName ZuulApplication
 * @Description TODO
 * @Author YuKai Fan
 * @Date 2019/11/10 11:39
 * @Version 1.0
 **/
@SpringBootApplication(scanBasePackages = "com.yksys.isystem", exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@EnableFeignClients
@EnableDiscoveryClient
@EnableZuulProxy
public class ZuulApplication implements CommandLineRunner {
    @Autowired
    private ResourceLocator resourceLocator;
    @Autowired
    private JdbcRouteLocator jdbcRouteLocator;

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        jdbcRouteLocator.doRefresh();
        resourceLocator.refresh();
    }
}
