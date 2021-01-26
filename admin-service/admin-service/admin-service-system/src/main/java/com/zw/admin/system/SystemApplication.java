package com.zw.admin.system;

import com.zw.admin.framework.api.service.RemoteLogService;
import com.zw.admin.framework.core.annotation.EnableCustomConfig;
import com.zw.admin.framework.core.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 系统服务
 * @author: ZhouWei
 * @create: 2021-01
 **/
@EnableCustomConfig
@EnableRyFeignClients
@EnableFeignClients(clients = RemoteLogService.class)
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.zw.admin"})
@SpringBootApplication()
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

}
